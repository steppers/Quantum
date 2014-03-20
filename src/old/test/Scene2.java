package old.test;

import old.engine.components.MeshFilter;
import old.engine.components.MeshRenderer;
import old.engine.core.QuantumScene;
import old.engine.core.GameObject;
import old.engine.ext.SceneManager;
import old.engine.graphics.MaterialBank;
import old.engine.graphics.ModelBank;

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