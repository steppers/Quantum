package old.test;

import old.game.QuantumGame;
import old.engine.default_Materials.DiffuseMaterial;
import old.engine.default_Materials.SpecularMaterial;
import old.engine.ext.SceneManager;
import old.engine.graphics.MaterialBank;
import old.engine.graphics.ModelBank;
import old.engine.graphics.TextureBank;
import old.engine.math.Vector3f;

public class test extends QuantumGame
{
    Scene1 scene1;
    
    DiffuseMaterial scene1mat;
    DiffuseMaterial scene2mat;
    
    SpecularMaterial specTestMat;

    public void init(){
        scene1mat = new DiffuseMaterial();
        scene1mat.name = "scene1";
        scene1mat.setProperty("BaseColor", new Vector3f(1f, 1f, 1f));
        MaterialBank.AddMaterial(scene1mat);

        scene2mat = new DiffuseMaterial();
        scene2mat.name = "scene2";
        scene2mat.setProperty("BaseColor", new Vector3f(1f, 0.3f, 0.5f));
        MaterialBank.AddMaterial(scene2mat);

        specTestMat = new SpecularMaterial();
        specTestMat.name = "specTest";
        specTestMat.setProperty("BaseColor", new Vector3f(0.3f, 0.3f, 0.3f));
        specTestMat.setProperty("specularPower", 64f);
        MaterialBank.AddMaterial(specTestMat);

        ModelBank.AddModel("TestScene.obj", "TestScene");

        ModelBank.AddModel("bunny.obj", "bunny");

        TextureBank.AddTexture("RandomNormal.png", "randomNormal");

        scene1 = new Scene1();
        SceneManager.loadScene(scene1);
    }
}
