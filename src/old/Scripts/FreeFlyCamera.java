package old.Scripts;

import old.engine.components.Component;
import old.engine.config.Options;
import old.engine.core.Input;
import old.engine.core.Time;
import old.engine.core.Window;
import old.engine.core.GameObject;
import old.engine.math.Vector2f;
import old.engine.math.Vector3f;

public class FreeFlyCamera extends Component
{   
    private boolean mouseLocked = false;
    private Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
    private Vector3f movementVector;
    
    public FreeFlyCamera(GameObject gameObject)
    {
        super(gameObject, "FreeFlyCamera");
    }
    
    @Override
    public void input(float delta){
        if (Input.getKey(Input.KEY_ESCAPE)) {
            Input.setCursor(true);
            mouseLocked = false;
        }
        if (Input.getMouseDown(0)) {
            Input.setMousePosition(centerPosition);
            Input.setCursor(false);
            mouseLocked = true;
        }

        movementVector = Vector3f.ZERO_VECTOR;

        if (Input.getKey(Input.KEY_W)) {
            movementVector = movementVector.add(parent.getTransform().forward);
        }
        if (Input.getKey(Input.KEY_S)) {
            movementVector = movementVector.sub(parent.getTransform().forward);
        }
        if (Input.getKey(Input.KEY_A)) {
            movementVector = movementVector.sub(parent.getTransform().left);
        }
        if (Input.getKey(Input.KEY_D)) {
            movementVector = movementVector.add(parent.getTransform().left);
        }

        if (mouseLocked) {
            Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);

            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;

            if (rotY) {
                parent.getTransform().setRotation(parent.getTransform().getRotation().getX(),
                        parent.getTransform().getRotation().getY() - deltaPos.getX() * Options.MOUSE_SENSITIVITY,
                        parent.getTransform().getRotation().getZ());
            }
            if (rotX) {
                parent.getTransform().setRotation(parent.getTransform().getRotation().getX() + deltaPos.getY() * Options.MOUSE_SENSITIVITY,
                        parent.getTransform().getRotation().getY(),
                        parent.getTransform().getRotation().getZ());
            }
            if (rotY || rotX) {
                Input.setMousePosition(centerPosition);
            }
        }
    }
    
    @Override
    public void update(float delta){
        float movAmt = (float) (Options.FF_MOVE_SPEED * delta);
        
        if(Input.getKey(Input.KEY_LSHIFT)){
            movAmt = (float) (Options.FF_MOVE_SPEED * delta);
        }
        
        if (movementVector.length() > 0) {
            movementVector = movementVector.normalized();
        }
        parent.getTransform().setPos(parent.getTransform().getPos().add(movementVector.mul(movAmt)));
    }
    
}
