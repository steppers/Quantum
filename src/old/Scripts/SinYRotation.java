package old.Scripts;

import old.engine.components.Component;
import old.engine.core.QuantumGame;
import old.engine.core.Time;
import old.engine.core.GameObject;

public class SinYRotation extends Component
{
    
    public SinYRotation(GameObject gameObject)
    {
        super(gameObject, "SinYRotation");
    }
    
    @Override
    public void update(float delta){
        parent.getTransform().setRotation(parent.getTransform().getRotation().getX(),
                                            (float)(parent.getTransform().getRotation().getY()+90*Math.sin(delta)),
                                            parent.getTransform().getRotation().getZ());
    }
    
}
