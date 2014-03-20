package old.game;

import old.engine.config.Constants;
import old.engine.core.CoreEngine;
import old.engine.core.VersionTracker;
import old.test.test;

/**
 * Created by Ollie on 20/03/14.
 */
public class Main {

    /**
     * Entry point of program
     *
     * @param args the runtime arguments.
     */
    public static void main(String[] args) {
        VersionTracker.UpdateVersionData();
        CoreEngine engine = new CoreEngine(900, 600, 60, new test());
        engine.CreateWindow(Constants.TITLE);
        engine.start();
    }

}
