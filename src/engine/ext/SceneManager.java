package engine.ext;

import FBO.FBOManager;
import engine.core.QuantumScene;
import engine.core.RenderUtil;
import java.util.ArrayList;

public class SceneManager {

    private static ArrayList<GameObject> gameObjects = new ArrayList<>();
    private static QuantumScene nextScene;
    private static QuantumScene currentScene;

    public static void addGameObject(GameObject g) {
        gameObjects.add(g);
    }

    public static void destroyGameObject(GameObject g) {
        g.destroy();
        gameObjects.remove(g);
    }

    public static void input() {
        if(nextScene != null){
            updateLoadScene();
        }
        for (GameObject g : gameObjects) {
            g.input();
        }
    }

    public static void update() {
        for (GameObject g : gameObjects) {
            g.update();
        }
    }

    public static void render() {
        //Use the FBO
        FBOManager.useFBO(true);
        RenderUtil.clearScreen();
        for (GameObject g : gameObjects) {
            g.render();
        }
        for (GameObject g : gameObjects) {
            g.getTransform().resetAnimations();
        }
        FBOManager.useFBO(false);
        FBOManager.renderFBOQuad();
    }

    public static GameObject FindGameObjectWithTag(String tag) {
        for (GameObject g : gameObjects) {
            if (g.getTag().equals(tag)) {
                return g;
            } else if (g.getChildWithTag(tag) != null) {
                return g.getChildWithTag(tag);
            }
        }
        return null;
    }
    
    public static ArrayList<GameObject> FindGameObjectsWithTag(String tag) {
        ArrayList<GameObject> gos = new ArrayList<>();
        for (GameObject g : gameObjects) {
            if (g.getTag().equals(tag)) {
                gos.add(g);
            } else if (g.getChildWithTag(tag) != null) {
                gos.add(g.getChildWithTag(tag));
            }
        }
        return null;
    }
    
    public static void loadScene(QuantumScene nextScene){
        SceneManager.nextScene = nextScene;
    }
    
    public static void updateLoadScene(){
        ArrayList<GameObject> ids = new ArrayList<>();
        for (GameObject g : gameObjects) {
            if(g.removesOnSceneLoad()){
                ids.add(g);
            }
        }
        for(GameObject g : ids){
            g.destroy();
            gameObjects.remove(g);
        }
        ids.clear();
        if(currentScene != null)
            currentScene.dispose();
        nextScene.init();
        currentScene = nextScene;
        nextScene = null;
    }
}
