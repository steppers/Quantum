package old.engine.components;

import old.engine.core.GameObject;
import old.engine.core.QuantumGame;
import old.engine.core.RenderingEngine;
import old.engine.ext.SceneManager;
import old.engine.graphics.Material;
import old.engine.math.Matrix4f;
import old.engine.shaders.Shader;

public class MeshRenderer extends Component
{
    private MeshFilter meshFilter;
    private Material material;
    
    public MeshRenderer(GameObject gameObject)
    {
        super(gameObject, "MeshRenderer");
        if(gameObject.getComponent(MeshFilter.class) != null) {
            meshFilter = (MeshFilter) gameObject.getComponent(MeshFilter.class);
        }else{
            meshFilter = new MeshFilter(gameObject);
            gameObject.AddComponent(meshFilter);
        }
    }
    
    @Override
    public void render(Transform transform, Shader shader, boolean useMaterial){
        if(useMaterial){
            material.getShader().bind();
            material.getShader().updateUniforms(transform,
                    RenderingEngine.getCamera().getComponent(Camera.class),
                    material);
        }else{
            shader.bind();
            shader.updateUniforms(transform,
                    RenderingEngine.getCamera().getComponent(Camera.class),
                    material);
        }
        meshFilter.getSharedMesh().draw();
    }  

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
