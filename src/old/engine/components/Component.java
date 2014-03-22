package old.engine.components;

import old.engine.core.GameObject;
import old.engine.core.QuantumGame;
import old.engine.shaders.Shader;

public class Component 
{
    public GameObject parent;
    private String type;
    private boolean active = true;
    
    public Component(GameObject gameObject, String type)
    {
        this.parent = gameObject;
        this.type = type;
    }
    
    public void input(float delta){
        
    }
    
    public void update(float delta){
        
    }
    
    public void render(Transform transform, Shader shader, boolean useMaterial){
        
    }

    public GameObject getGameObject() {
        return parent;
    }

    public void setGameObject(GameObject gameObject) {
        this.parent = gameObject;
    } 

    public String getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void destroy(){
        
    }
}
