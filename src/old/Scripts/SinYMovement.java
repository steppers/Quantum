package old.Scripts;

import old.engine.components.Component;
import old.engine.core.Time;
import old.engine.ext.GameObject;

public class SinYMovement extends Component
{
    
    public SinYMovement(GameObject gameObject)
    {
        super(gameObject, "SinYMovement");
    }
    
    @Override
    public void update(){
        gameObject.getTransform().setAnimPos(gameObject.getTransform().getPos().getX(), 
                                            (float)(gameObject.getTransform().getPos().getY()+Math.sin(Time.getDelta())), 
                                            gameObject.getTransform().getPos().getZ());
    }
    
}
