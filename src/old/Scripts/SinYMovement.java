package old.Scripts;

import old.engine.components.Component;
import old.engine.core.Time;
import old.engine.core.GameObject;

public class SinYMovement extends Component
{
    
    public SinYMovement(GameObject gameObject)
    {
        super(gameObject, "SinYMovement");
    }
    
    @Override
    public void update(float delta){
        parent.getTransform().setAnimPos(parent.getTransform().getPos().getX(),
                                            (float)(parent.getTransform().getPos().getY()+Math.sin(delta)),
                parent.getTransform().getPos().getZ());
    }
    
}
