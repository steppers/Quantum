package old.engine.components;

import old.engine.core.GameObject;
import old.engine.graphics.LightModel;
import old.engine.lights.Attenuation;
import old.engine.lights.BaseLight;
import old.engine.lights.PointLight;
import old.engine.math.Vector3f;

public class PointLightComponent extends Component
{   
    private PointLight light;
    
    public PointLightComponent(GameObject gameObject)
    {
        super(gameObject, "PointLight");
        light = new PointLight(new BaseLight(Vector3f.ONE_VECTOR,1), new Attenuation(2,6f,0.1f), gameObject.getTransform().getPos(), 30);
        LightModel.AddPointLight(light);
        this.setActive(true);
    }
    
    @Override
    public void update(){
        light.setPosition(parent.getTransform().getPosLight());
    }
    
    public void setRange(float range){
        light.setRange(range);
    }
    
    public void setBaseLight(BaseLight light){
        this.light.setBaseLight(light);
    }
    
    public void setAtten(Attenuation atten){
        light.setAtten(atten);
    }
    
    @Override
    public void destroy(){
        LightModel.RemovePointLight(light);
    }    
}
