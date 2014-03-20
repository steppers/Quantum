package engine.ext;

import quantum.test.test;

public class Game {
    test game;

    public Game() {
        game = new test();
    }

    public void input() {
        game.input();
    }

    public void update() {
        game.update();
    }

    public void render() {
        game.render();
    }

    public void CleanUp() {
    }
}