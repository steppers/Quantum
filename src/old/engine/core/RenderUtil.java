package old.engine.core;

import old.FBO.FBOManager;
import old.engine.math.Vector3f;
import java.nio.ByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderUtil {

    //Clears the color buffer and depth buffer
    public static void clearScreen() {
        //TODO: Stencil Buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    //Enable texture rendeing within the old.engine
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

    //Initialises OpenGL
    public static void initGraphics() {
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
    
    //Returns a new complete FrameBuffer object
    //ret[0] fboId
    //ret[1] colorTex
    //ret[2] depthTex
    public static int[] CreateFrameBuffer(){
        int[] ret = new int[3];
        ret[0] = GL30.glGenFramebuffers();
        ret[1] = CreateTexture(Window.getWidth(), Window.getHeight(), false);
        ret[2] = CreateTexture(Window.getWidth(), Window.getHeight(), true);
        GL30.glBindFramebuffer(GL_FRAMEBUFFER, ret[0]);
        //Color Texture
        GL30.glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, ret[1], 0);
        //Depth Texture
        GL30.glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, ret[2], 0);
        
        int i=glCheckFramebufferStatus(GL_FRAMEBUFFER);
	if(i!=GL_FRAMEBUFFER_COMPLETE)
	{
		System.err.println("Framebuffer is not OK, status=" + i);
	}
        
        GL30.glBindFramebuffer(GL_FRAMEBUFFER, 0);
        
        return ret;
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
    public static String getOpenGLVersion() {
        return "OpenGL Version: " + glGetString(GL_VERSION);
    }
}
