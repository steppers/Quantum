package old.engine.core;

import old.engine.ext.SceneManager;

public abstract class QuantumGame {

    public void init(){

    }

    public void input() {
        SceneManager.input();
    }

    public void update() {
        SceneManager.update();
    }
}
