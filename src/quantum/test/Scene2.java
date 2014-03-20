package quantum.test;

import engine.components.MeshFilter;
import engine.components.MeshRenderer;
import engine.core.QuantumScene;
import engine.ext.GameObject;
import engine.ext.SceneManager;
import engine.graphics.MaterialBank;
import engine.graphics.ModelBank;

public class Scene2 extends QuantumScene
{
    GameObject bunny;
    
    public void init(){
        bunny = new GameObject();
        bunny.AddComponent(new MeshRenderer(bunny));
        bunny.getComponent(MeshRenderer.class).setMaterial(MaterialBank.getMaterial("scene2"));
        bunny.getComponent(MeshFilter.class).setSharedMesh(ModelBank.GetModel("bunny"));
        bunny.getTransform().setRotation(0, 0, 0);
        SceneManager.addGameObject(bunny);
    }
}