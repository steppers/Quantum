package engine.core;

import engine.config.Options;
import engine.ext.Game;


/**
 * Class {@code MainComponent} is the root of the entire Quantum
 * Game Engine. {@code MainComponent} contains the initialisation
 * methods for all major game loops.
 * 
 * @author Oliver Steptoe
 * @version v1.0 22/01/2014
 */
public class MainComponent {
    
    /**Window Title*/
    public static final String TITLE = "Quantum Engine";
    
    /**true when the loop is iterating*/
    private boolean isRunning;
    
    /**game instance*/
    private Game game;

    /**
     * Constructor for {@code MainComponent}
     */
    public MainComponent() {
        System.out.println(RenderUtil.getOpenGLVersion());
        RenderUtil.initGraphics();
        isRunning = false;
        game = new Game();
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
        long frameCounter = 0;

        final double frameTime = 1.0 / Options.FRAME_CAP;

        long lastTime = Time.getTime();
        double unprocessedTime = 0;

        while (isRunning) {
            Window.update();
            boolean render = false;

            long startTime = Time.getTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) Time.SECOND;
            frameCounter += passedTime;

            while (unprocessedTime > frameTime) {
                render = true;

                unprocessedTime -= frameTime;

                if (Window.isCloseRequested()) {
                    stop();
                }

                Time.setDelta(frameTime);

                game.input();
                Input.update();

                game.update();

                if (frameCounter >= Time.SECOND) {
                    System.out.println(frames);
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if (render) {
                render();
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
     * Updates the viewport and renders the game geometry.
     * 
     * Called by {@code run()}.
     */
    private void render() {
        RenderUtil.clearScreen();
        game.render();
        Window.render();
    }

    /**
     * Disposes of the window when application closed
     * 
     * Called by {@code run()}.
     */
    private void cleanUp() {
        Window.dispose();
    }
    
    /**
     * Entry point of program
     * 
     * @param args the runtime arguments.
     */
    public static void main(String[] args) {
        VersionTracker.UpdateVersionData();
        Window.createWindow(TITLE + " | Build No: " + VersionTracker.version);
        if(Options.FULLSCREEN){
            Window.setDisplayMode(Options.DEFAULT_DISPLAY_WIDTH, Options.DEFAULT_DISPLAY_HEIGHT, true);
        }else{
            Window.setDisplayMode(Options.DEFAULT_WINDOWED_DISPLAY_WIDTH, Options.DEFAULT_WINDOWED_DISPLAY_HEIGHT, false);
        }

        MainComponent game = new MainComponent();

        game.start();
    }
}
