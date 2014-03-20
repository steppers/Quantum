package old.FBO;

import old.engine.core.Util;
import old.engine.core.Window;
import old.engine.graphics.TextureBank;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.util.glu.GLU;

public class FBOManager 
{
    
    // Quad variables
    private static int vaoId = 0;
    private static int vboId = 0;
    private static int vboiId = 0;
    private static int indicesCount = 0;
    
    // Shader variables
    private static int vsId = 0;
    private static int fsId = 0;
    private static int pId = 0;
    
    //Texture Sampler positions
    private static int color_Tex;
    private static int normal_Tex;
    private static int position_Tex;
    private static int random_Tex;
    private static int depth_Tex;
    
    //old.FBO Variables
    public static int[] fboData;
    
    public static void init()
    {
        setupQuad();
	setupShaders();
        
        //Setup the fbo
        fboData = CreateFrameBuffer();
        
        color_Tex = GL20.glGetUniformLocation(pId, "color_Tex");
        normal_Tex = GL20.glGetUniformLocation(pId, "normal_Tex");
        position_Tex = GL20.glGetUniformLocation(pId, "position_Tex");
        random_Tex = GL20.glGetUniformLocation(pId, "random_Tex");
        depth_Tex = GL20.glGetUniformLocation(pId, "depth_Tex");
    }
    
    public static void useFBO(boolean isUsed){
        if(isUsed){
            //Bind fbo
            GL30.glBindFramebuffer(GL_FRAMEBUFFER, fboData[0]);
        }else{
            //Bind the screen fbo
            GL30.glBindFramebuffer(GL_FRAMEBUFFER, 0);
        }
    }
    
    public static void renderFBOQuad(){        
        // Render
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);

        GL20.glUseProgram(pId);

        // Bind the color texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fboData[1]);
        GL20.glUniform1i(color_Tex, 0);
        
        // Bind the Normal Texture
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fboData[3]);
        GL20.glUniform1i(normal_Tex, 1);
        
        // Bind the position Texture
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fboData[4]);
        GL20.glUniform1i(position_Tex, 2);
        
        // Bind the Random Normal Texture
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureBank.GetTexture("randomNormal"));
        GL20.glUniform1i(random_Tex, 3);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);

        // Bind to the VAO that has all the information about the vertices
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // Bind to the index VBO that has all the information about the order of the vertices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);

        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);

        // Put everything back to default (deselect)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);

        GL20.glUseProgram(0);
    }
    
    private static void ApplyPostProcesses(){
        
    }
    
    //Returns a new complete FrameBuffer object
    //ret[0] fboId
    //ret[1] colorTex
    //ret[2] depthTex
    public static int[] CreateFrameBuffer(){
        int[] ret = new int[5];
        ret[0] = GL30.glGenFramebuffers();
        ret[1] = CreateTexture(Window.getWidth(), Window.getHeight(), false);
        ret[2] = CreateTexture(Window.getWidth(), Window.getHeight(), true);
        ret[3] = CreateTexture(Window.getWidth(), Window.getHeight(), false);
        ret[4] = CreateTexture(Window.getWidth(), Window.getHeight(), false);
        GL30.glBindFramebuffer(GL_FRAMEBUFFER, ret[0]);
        //Color Texture
        GL30.glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, ret[1], 0);
        //Depth Texture
        GL30.glFramebufferTexture2D(GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, ret[2], 0);
        //Normal Texture
        GL30.glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT1, GL_TEXTURE_2D, ret[3], 0);
        //Position Texture
        GL30.glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT2, GL_TEXTURE_2D, ret[4], 0);
        
        int buffers[] = new int[]{GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2};
        IntBuffer b = Util.createFlippedBuffer(buffers);
        GL20.glDrawBuffers(b);

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
        glTexImage2D(GL_TEXTURE_2D, 0, (isDepth ? GL30.GL_DEPTH_COMPONENT32F : GL_RGBA8), width, height, 0, isDepth ? GL_DEPTH_COMPONENT : GL_RGBA, GL_FLOAT, (ByteBuffer)null);
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
    
    private static void setupQuad() {
        // We'll define our quad using 4 vertices of the custom 'TexturedVertex' class
        TexturedVertex v0 = new TexturedVertex(); 
        v0.setXYZ(-1f, 1f, 0); v0.setRGB(1, 0, 0); v0.setST(0, 1);
        TexturedVertex v1 = new TexturedVertex(); 
        v1.setXYZ(-1f, -1f, 0); v1.setRGB(0, 1, 0); v1.setST(0, 0);
        TexturedVertex v2 = new TexturedVertex(); 
        v2.setXYZ(1f, -1f, 0); v2.setRGB(0, 0, 1); v2.setST(1, 0);
        TexturedVertex v3 = new TexturedVertex(); 
        v3.setXYZ(1f, 1f, 0); v3.setRGB(1, 1, 1); v3.setST(1, 1);

        TexturedVertex[] vertices = new TexturedVertex[] {v0, v1, v2, v3};
        // Put each 'Vertex' in one FloatBuffer
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length *
                        TexturedVertex.elementCount);
        for (int i = 0; i < vertices.length; i++) {
                // Add position, color and texture floats to the buffer
                verticesBuffer.put(vertices[i].getElements());
        }
        verticesBuffer.flip();	
        // OpenGL expects to draw vertices in counter clockwise order by default
        byte[] indices = {
                        2, 1, 0,
                        0, 3, 2
        };
        indicesCount = indices.length;
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        // Create a new Vertex Array Object in memory and select it (bind)
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        // Create a new Vertex Buffer Object in memory and select it (bind)
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL11.GL_FLOAT, 
                        false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, TexturedVertex.colorElementCount, GL11.GL_FLOAT, 
                        false, TexturedVertex.stride, TexturedVertex.colorByteOffset);
        // Put the texture coordinates in attribute list 2
        GL20.glVertexAttribPointer(2, TexturedVertex.textureElementCount, GL11.GL_FLOAT, 
                        false, TexturedVertex.stride, TexturedVertex.textureByteOffset);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);

        // Create a new VBO for the indices and select it (bind) - INDICES
        vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        exitOnGLError("setupFBOQuad");
    }
    
    private static void setupShaders() {		
        // Load the vertex shader
        vsId = loadShader("res/shaders/FBO.vs", GL20.GL_VERTEX_SHADER);
        // Load the fragment shader
        fsId = loadShader("res/shaders/FBO.fs", GL20.GL_FRAGMENT_SHADER);

        // Create a new shader program that links both shaders
        pId = GL20.glCreateProgram();
        GL20.glAttachShader(pId, vsId);
        GL20.glAttachShader(pId, fsId);

        // Position information will be attribute 0
        GL20.glBindAttribLocation(pId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(pId, 1, "in_Color");
        // Textute information will be attribute 2
        GL20.glBindAttribLocation(pId, 2, "in_TextureCoord");

        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);

        exitOnGLError("setupFBOShaders");
    }
    
    private static int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;

        try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line;
                while ((line = reader.readLine()) != null) {
                        shaderSource.append(line).append("\n");
                }
                reader.close();
        } catch (IOException e) {
                System.err.println("Could not read file.");
                e.printStackTrace();
                System.exit(-1);
        }

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
                System.err.println("Could not compile shader.");
                System.exit(-1);
        }

        exitOnGLError("loadFBOShader");

        return shaderID;
    }
    
    private static void exitOnGLError(String errorMessage) {
        int errorValue = GL11.glGetError();

        if (errorValue != GL11.GL_NO_ERROR) {
                String errorString = GLU.gluErrorString(errorValue);
                System.err.println("ERROR - " + errorMessage + ": " + errorString);

                if (Display.isCreated()) Display.destroy();
                System.exit(-1);
        }
    }
    
}
