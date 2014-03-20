package old.engine.components;

import old.engine.core.GameObject;
import old.engine.core.QuantumGame;

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
    
    public void input(){
        
    }
    
    public void update(){
        
    }
    
    public void render(){
        
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
