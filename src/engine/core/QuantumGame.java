package engine.core;

import engine.ext.SceneManager;

public class QuantumGame {
    
    public QuantumGame(){
        
    }
    
    public void input(){
        SceneManager.input();
    }
    
    public void update(){
        SceneManager.update();
    }
    
    public void render(){
        SceneManager.render();
    }
}
