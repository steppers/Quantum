package old.Scripts;

import old.engine.components.Component;
import old.engine.core.Time;
import old.engine.ext.GameObject;

public class SinYRotationInv extends Component
{
    
    public SinYRotationInv(GameObject gameObject)
    {
        super(gameObject, "SinYRotation");
    }
    
    @Override
    public void update(){
        gameObject.getTransform().setRotation(gameObject.getTransform().getRotation().getX(), 
                                            (float)(gameObject.getTransform().getRotation().getY()-90f*Math.sin(Time.getDelta())), 
                                            gameObject.getTransform().getRotation().getZ());
    }
    
}
