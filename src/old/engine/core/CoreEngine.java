package old.engine.core;

import old.engine.ext.SceneManager;

/**
 * Class {@code CoreEngine} is the root of the entire Quantum
 * Game Engine. {@code CoreEngine} contains the initialisation
 * methods for all major game loops.
 * 
 * @author Oliver Steptoe
 * @version v1.0 22/01/2014
 */
public class CoreEngine {
    
    /**true when the loop is iterating*/
    private boolean isRunning;
    
    /**game instance*/
    private QuantumGame game;

    /**Width and height of the screen*/
    private int width, height;

    /**Time between frames*/
    private double frameTime;

    /**The Rendering Engine*/
    private RenderingEngine renderingEngine;

    /**
     * Constructor for {@code CoreEngine}
     */
    public CoreEngine(int width, int height, double framerate, QuantumGame game) {
        this.isRunning = false;
        this.game = game;
        this.width = width;
        this.height = height;
        this.frameTime = 1.0/framerate;
    }

    public void CreateWindow(String title){
        Window.createWindow(title + " | Build No: " + VersionTracker.version);
        Window.setDisplayMode(this.width, this.height, false);
        this.renderingEngine = new RenderingEngine();
    }

    public void setDisplayMode(int width, int height, boolean fs){
        Window.setDisplayMode(width, height, fs);
        this.renderingEngine = new RenderingEngine();
        this.width = width;
        this.height = height;
    }

    /**
     * Starts the applications main update loop.
     * (Sets {@code isRunning} to {@code true})
     */
    public void start() {
        if (isRunning) {
            return;
        }

        run();
    }

    /**
     * Stops the applications main update loop.
     * (Sets {@code isRunning} to {@code false})
     */
    public void stop() {
        if (!isRunning) {
            return;
        }

        isRunning = false;
    }

    /**
     * Contains all of the games major update loops.
     * 
     * Called by {@code start()}.
     */
    private void run() {
        isRunning = true;

        int frames = 0;
        double frameCounter = 0;

        game.init(renderingEngine);

        double lastTime = Time.getTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;

            double startTime = Time.getTime();
            double passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime;
            frameCounter += passedTime;

            while (unprocessedTime > frameTime) {
                render = true;

                unprocessedTime -= frameTime;

                if (Window.isCloseRequested()) {
                    stop();
                }

                game.input((float)frameTime);
                Input.update();

                game.update((float)frameTime);

                if (frameCounter >= 1.0) {
                    System.out.println(frames);
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if (render) {
                renderingEngine.render(SceneManager.getRootNode());
                Window.render();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        cleanUp();
    }

    /**
     * Disposes of the window when application closed
     * 
     * Called by {@code run()}.
     */
    private void cleanUp() {
        Window.dispose();
    }
}
