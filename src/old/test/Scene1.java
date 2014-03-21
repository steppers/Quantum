package old.test;

import old.Scripts.FreeFlyCamera;
import old.Scripts.SinYRotation;
import old.engine.components.*;
import old.engine.config.Options;
import old.engine.core.QuantumScene;
import old.engine.core.GameObject;
import old.engine.core.RenderingEngine;
import old.engine.ext.SceneManager;
import old.engine.graphics.MaterialBank;
import old.engine.graphics.MeshGenerator;
import old.engine.graphics.ModelBank;

public class Scene1 extends QuantumScene
{
    GameObject sun;
    
    GameObject SceneModel;
    
    GameObject Scene0;
    GameObject Scene1;
    GameObject Scene2;
    
    GameObject box1;
    
    GameObject camera;
    
    public void init(){
        Scene0 = new GameObject();
        Scene0.AddComponent(new MeshRenderer(Scene0));
        Scene0.getComponent(MeshRenderer.class).setMaterial(MaterialBank.getMaterial("scene1"));
        Scene0.getComponent(MeshFilter.class).setSharedMesh(MeshGenerator.NewPlane(10, 10));
        SceneManager.addGameObject(Scene0);
        
        Scene1 = new GameObject();
        Scene1.AddComponent(new MeshRenderer(Scene1));
        Scene1.getComponent(MeshRenderer.class).setMaterial(MaterialBank.getMaterial("scene1"));
        Scene1.getComponent(MeshFilter.class).setSharedMesh(MeshGenerator.NewPlane(10, 10));
        Scene1.getTransform().setRotation(0, 0, 90);
        Scene1.getTransform().setPos(5, 5, 0);
        SceneManager.addGameObject(Scene1);
        
        Scene2 = new GameObject();
        Scene2.AddComponent(new MeshRenderer(Scene2));
        Scene2.getComponent(MeshRenderer.class).setMaterial(MaterialBank.getMaterial("scene1"));
        Scene2.getComponent(MeshFilter.class).setSharedMesh(MeshGenerator.NewPlane(10, 10));
        Scene2.getTransform().setRotation(-90, 0, 0);
        Scene2.getTransform().setPos(0, 5, -5);
        SceneManager.addGameObject(Scene2);
        
        box1 = new GameObject();
        box1.AddComponent(new MeshRenderer(box1));
        box1.getComponent(MeshRenderer.class).setMaterial(MaterialBank.getMaterial("scene1"));
        box1.getComponent(MeshFilter.class).setSharedMesh(MeshGenerator.NewBox(5, 2, 2));
        box1.getTransform().setPos(0, 1, 0);
        box1.getTransform().setRotation(0, -20, 0);
        SceneManager.addGameObject(box1);
        
        SceneModel = new GameObject();
        SceneModel.AddComponent(new MeshRenderer(SceneModel));
        SceneModel.getComponent(MeshRenderer.class).setMaterial(MaterialBank.getMaterial("scene1"));
        SceneModel.getComponent(MeshFilter.class).setSharedMesh(ModelBank.GetModel("TestScene"));
        SceneModel.getTransform().setPos(20,0,0);
        SceneModel.setRemoveOnSceneLoad(false);
        box1.addChild(SceneModel);
        
        if(SceneManager.FindGameObjectWithTag("Sun") == null){
            sun = new GameObject();
            sun.setTag("Sun");
            sun.AddComponent(new DirectionalLightComponent(sun));
            //sun.AddComponent(new SinYRotation(sun));
            sun.getTransform().setPos(0, 10, 0);
            sun.getTransform().setRotation(-45, -35, 0);
            sun.setRemoveOnSceneLoad(false);
            SceneManager.addGameObject(sun);
        }
    }

    public void initCamera(){
        camera = RenderingEngine.getCamera();
        camera.AddComponent(new FreeFlyCamera(camera));
        camera.AddComponent(new Test1SceneSwap(camera));
    }
}
