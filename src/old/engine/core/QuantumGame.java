package old.engine.core;

import old.FBO.FBOManager;
import old.engine.ext.SceneManager;

import java.util.ArrayList;

public abstract class QuantumGame {

    public void init(){

    }

    public void input() {
        SceneManager.input();
    }

    public void update() {
        SceneManager.update();
    }

    public void render() {
        SceneManager.render();
    }
}
