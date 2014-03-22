package old.engine.core;

import old.FBO.FBOManager;
import old.engine.components.Camera;
import old.engine.config.Options;
import old.engine.default_shaders.DiffuseShader;
import old.engine.default_shaders.ForwardAmbient;
import old.engine.default_shaders.ForwardDirectional;
import old.engine.default_shaders.SpecularShader;
import old.engine.ext.SceneManager;
import old.engine.lights.BaseLight;
import old.engine.lights.DirectionalLight;
import old.engine.math.Vector3f;
import old.engine.shaders.Shader;
import org.lwjgl.opengl.GL12;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created by Ollie on 20/03/14.
 */
public class RenderingEngine {

    private static GameObject mainCamera;
    private Vector3f ambientLight;
    private DirectionalLight directionalLight;
    private DirectionalLight directionalLight2;

    public RenderingEngine(){
        System.out.println(getOpenGLVersion());

        FBOManager.init();
        initCamera();

        glClearColor(0.5f, 0.5f, 0.7f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        glViewport(0,0,Window.getWidth(),Window.getHeight());

        ambientLight = new Vector3f(0.2f, 0.2f, 0.2f);
        directionalLight = new DirectionalLight(new BaseLight(new Vector3f(0,0,1), 0.3f), new Vector3f(-1,1,1));
        directionalLight2 = new DirectionalLight(new BaseLight(new Vector3f(1,0,0), 0.3f), new Vector3f(1,1,-1));
    }

    private void initCamera(){
        mainCamera = new GameObject();
        mainCamera.AddComponent(new Camera(mainCamera));
        mainCamera.getComponent(Camera.class).setPerspective(Options.FOV, Options.Z_NEAR, Options.Z_FAR);
        mainCamera.getTransform().setPos(-7, 10f, 7f);
        mainCamera.getTransform().setRotation(-37, -135, 0);
        mainCamera.setRemoveOnSceneLoad(false);
        SceneManager.addGameObject(mainCamera);
    }

    public void render(GameObject object){

        Shader forwardAmbient = ForwardAmbient.getInstance();
        Shader forwardDirectional = ForwardDirectional.getInstance();
        forwardAmbient.setRenderingEngine(this);
        forwardDirectional.setRenderingEngine(this);

        //Use the FBO
        FBOManager.useFBO(true);
        clearScreen();

        object.render(forwardAmbient, false);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        object.render(forwardDirectional, false);

        DirectionalLight temp = directionalLight;
        directionalLight = directionalLight2;
        directionalLight2 = temp;

        object.render(forwardDirectional, false);

        temp = directionalLight;
        directionalLight = directionalLight2;
        directionalLight2 = temp;

        glDisable(GL_BLEND);
        glDepthFunc(GL_LESS);
        glDepthMask(true);

        //Reset the animation stuff
        object.getTransform().resetAnimations();

        //Disable FBO rendering and draw the FBO Quad.
        FBOManager.useFBO(false);
        FBOManager.renderFBOQuad();
    }

    public Vector3f getAmbientLight(){
        return ambientLight;
    }

    public DirectionalLight getDirectionalLight(){
        return directionalLight;
    }

    //Clears the color buffer and depth buffer
    private static void clearScreen() {
        //TODO: Stencil Buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    //Enable texture rendering within the engine
    public static void setTextures(boolean enabled) {
        if (enabled) {
            glEnable(GL_TEXTURE_2D);
        } else {
            glDisable(GL_TEXTURE_2D);
        }
    }

    //unbinds the current texture
    public static void unbindTextures() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    //Sets the rendering clear color
    public static void setClearColor(Vector3f color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    //creates a new empty texture id
    public static int CreateTexture(int width, int height, boolean isDepth){
        int texId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texId);
        glTexImage2D(GL_TEXTURE_2D, 0, (isDepth ? GL_DEPTH_COMPONENT : GL_RGBA8), width, height, 0, isDepth ? GL_DEPTH_COMPONENT : GL_RGBA, GL_FLOAT, (ByteBuffer)null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        int i=glGetError();
        if(i!=0)
        {
            System.err.println("Error happened while loading the texture: " + i);
        }

        glBindTexture(GL_TEXTURE_2D, 0);

        return texId;
    }

    //Retrieves the current OpenGL Version
    private static String getOpenGLVersion() {
        return "OpenGL Version: " + glGetString(GL_VERSION);
    }

    public static GameObject getCamera(){
        return mainCamera;
    }

}
