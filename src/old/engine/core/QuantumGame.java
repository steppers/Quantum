package old.engine.core;

import old.engine.ext.SceneManager;

public abstract class QuantumGame {

    public void init(RenderingEngine renderingEngine){

    }

    public void input(float delta) {
        SceneManager.input(delta);
    }

    public void update(float delta) {
        SceneManager.update(delta);
    }
}
