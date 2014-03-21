package old.engine.core;

import old.FBO.FBOManager;
import old.engine.components.Camera;
import old.engine.default_shaders.DiffuseShader;
import old.engine.math.Vector3f;
import org.lwjgl.opengl.GL12;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created by Ollie on 20/03/14.
 */
public class RenderingEngine {

    public RenderingEngine(){
        System.out.println(getOpenGLVersion());

        FBOManager.init();

        glClearColor(0.5f, 0.5f, 0.7f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        glViewport(0,0,Window.getWidth(),Window.getHeight());
    }

    public void render(GameObject object){
        //Use the FBO
        FBOManager.useFBO(true);
        clearScreen();
        object.render(DiffuseShader.getInstance());
        object.getTransform().resetAnimations();
        FBOManager.useFBO(false);
        FBOManager.renderFBOQuad();
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

}
