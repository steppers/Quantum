package old.engine.ext;

import old.engine.core.GameObject;
import old.engine.core.QuantumScene;
import old.engine.math.Vector3f;

import java.util.ArrayList;

public class SceneManager {

    private static QuantumScene nextScene;
    private static QuantumScene currentScene;

    private static GameObject rootNode = new GameObject(Vector3f.ZERO_VECTOR, Vector3f.ZERO_VECTOR, Vector3f.ONE_VECTOR, "ROOT_NODE");

    public static void addGameObject(GameObject g) {
        rootNode.addChild(g);
    }

    public static void destroyGameObject(GameObject g) {
        rootNode.removeChild(g);
        g.destroy();
    }

    public static void input(float delta) {
        if(nextScene != null){
            updateLoadScene();
        }
        rootNode.input(delta);
    }

    public static void update(float delta) {
        rootNode.update(delta);
    }

    public static GameObject FindGameObjectWithTag(String tag) {
        return rootNode.getChildWithTag(tag);
    }
    
    public static ArrayList<GameObject> FindGameObjectsWithTag(String tag) {
        return rootNode.getChildrenWithTag(tag);
    }

    private static ArrayList<GameObject> FindRemoveOnLoads(){
        return rootNode.getRemoveOnLoads();
    }
    
    public static void loadScene(QuantumScene nextScene){
        SceneManager.nextScene = nextScene;
    }
    
    public static void updateLoadScene(){
        ArrayList<GameObject> ids = FindRemoveOnLoads();
        for(GameObject g : ids){
            rootNode.removeChild(g);
            g.destroy();
        }
        ids.clear();
        if(currentScene != null)
            currentScene.dispose();
        nextScene.init();
        currentScene = nextScene;
        nextScene = null;
    }

    public static GameObject getRootNode(){
        return rootNode;
    }
}
