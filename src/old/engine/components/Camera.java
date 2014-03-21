package old.engine.components;

import old.engine.config.Options;
import old.engine.core.Window;
import old.engine.core.GameObject;
import old.engine.math.Matrix4f;
import old.engine.math.Vector3f;
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

    public Matrix4f getViewProjection(){
        Matrix4f cameraRotation = new Matrix4f().initCamera(parent.getTransform().forward, parent.getTransform().up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-parent.getTransform().getPos().getX(),
                -parent.getTransform().getPos().getY(),
                -parent.getTransform().getPos().getZ());

        return perspectiveMatrix.mul(cameraRotation.mul(cameraTranslation));
    }
    
    public Matrix4f getProjectedTransformation(Matrix4f transformation) {
        Matrix4f cameraRotation = new Matrix4f().initCamera(parent.getTransform().forward, parent.getTransform().up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-parent.getTransform().getPos().getX(),
                                                                    -parent.getTransform().getPos().getY(),
                                                                    -parent.getTransform().getPos().getZ());

        return perspectiveMatrix.mul(cameraRotation.mul(cameraTranslation.mul(transformation)));
    }
    
    public Matrix4f getViewMatrix(Matrix4f transformation){
        Matrix4f cameraRotation = new Matrix4f().initCamera(parent.getTransform().forward, parent.getTransform().up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-parent.getTransform().getPos().getX(),
                                                                    -parent.getTransform().getPos().getY(),
                                                                    -parent.getTransform().getPos().getZ());
        return cameraRotation.mul(cameraTranslation);
    }
    
    public Matrix4f getNormalViewMatrix(Matrix4f transformation){
        Matrix4f cameraRotation = new Matrix4f().initCamera(parent.getTransform().forward, parent.getTransform().up);
        return cameraRotation;
    }
}
