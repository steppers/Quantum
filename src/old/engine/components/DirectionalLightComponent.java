package old.engine.components;

import old.engine.core.GameObject;
import old.engine.graphics.LightModel;
import old.engine.lights.BaseLight;
import old.engine.lights.DirectionalLight;
import old.engine.math.Vector3f;

public class DirectionalLightComponent extends Component
{
    
    private DirectionalLight light;
    
    public DirectionalLightComponent(GameObject gameObject)
    {
        super(gameObject, "DirectionalLight");
        light = new DirectionalLight(new BaseLight(Vector3f.ONE_VECTOR,1), Vector3f.ONE_VECTOR);
        LightModel.AddDirectionalLight(light);
    }
    
    @Override
    public void update(float delta){
        Vector3f dir = Vector3f.FORWARD.rotate(parent.getTransform().getRotation().getX(), Vector3f.X_AXIS)
                                .rotate(parent.getTransform().getRotation().getY(), Vector3f.Y_AXIS)
                                .rotate(parent.getTransform().getRotation().getZ(), Vector3f.Z_AXIS);
        light.setDirection(dir);
    }
    
    public void setBaseLight(BaseLight light){
        this.light.setBase(light);
    }
    
    @Override
    public void destroy(){
        LightModel.RemoveDirectionalLight(light);
    }      
}
