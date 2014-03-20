package engine.components;

import engine.config.Options;
import engine.core.Window;
import engine.ext.GameObject;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import org.lwjgl.opengl.Display;

public class Camera extends Component
{
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);
    
    private Matrix4f perspectiveMatrix;
    
    public Camera(GameObject gameObject)
    {
        super(gameObject, "Camera");
    }
    
    public void update(){
        if(Display.wasResized()){
            setPerspective(Options.FOV, Options.Z_NEAR, Options.Z_FAR);
        }
    }
    
    public void setPerspective(float fov, float zNear, float zFar){
        perspectiveMatrix = new Matrix4f().initProjection(fov, Window.getWidth(), Window.getHeight(), zNear, zFar);
    }
    
    public Matrix4f getProjectedTransformation(Matrix4f transformation) {
        Matrix4f cameraRotation = new Matrix4f().initCamera(gameObject.getTransform().forward, gameObject.getTransform().up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-gameObject.getTransform().getPos().getX(),
                                                                    -gameObject.getTransform().getPos().getY(),
                                                                    -gameObject.getTransform().getPos().getZ());

        return perspectiveMatrix.mul(cameraRotation.mul(cameraTranslation.mul(transformation)));
    }
    
    public Matrix4f getViewMatrix(Matrix4f transformation){
        Matrix4f cameraRotation = new Matrix4f().initCamera(gameObject.getTransform().forward, gameObject.getTransform().up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-gameObject.getTransform().getPos().getX(),
                                                                    -gameObject.getTransform().getPos().getY(),
                                                                    -gameObject.getTransform().getPos().getZ());
        return cameraRotation.mul(cameraTranslation);
    }
    
    public Matrix4f getNormalViewMatrix(Matrix4f transformation){
        Matrix4f cameraRotation = new Matrix4f().initCamera(gameObject.getTransform().forward, gameObject.getTransform().up);
        return cameraRotation;
    }
}
