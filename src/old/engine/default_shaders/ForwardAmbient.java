package old.engine.default_shaders;

import old.engine.components.Camera;
import old.engine.components.Transform;
import old.engine.core.Texture;
import old.engine.graphics.LightModel;
import old.engine.graphics.Material;
import old.engine.math.Matrix4f;
import old.engine.math.Vector3f;
import old.engine.shaders.Shader;

/**
 * Created by Ollie on 21/03/14.
 */
public class ForwardAmbient extends Shader{

    private static final ForwardAmbient instance = new ForwardAmbient();
    public static ForwardAmbient getInstance(){
        return instance;
    }

    private ForwardAmbient()
    {
        super();

        addVertexShaderFromFile("forward-ambient.vs");
        addFragmentShaderFromFile("forward-ambient.fs");
        compileShader();

        addUniform("transform");
        addUniform("transformView");
        addUniform("normalMatrix");
        addUniform("transformProjected");
        addUniform("ambientIntensity");
    }

    @Override
    public void updateUniforms(Transform transform, Camera camera, Material material) {

        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = camera.getViewProjection().mul(worldMatrix);
        material.getProperty("BaseTexture", Texture.class).bind();

        setUniform("normalMatrix", camera.getNormalViewMatrix(worldMatrix));
        setUniform("transformView", camera.getViewMatrix(worldMatrix));
        setUniform("transformProjected", projectedMatrix);
        setUniform("transform", worldMatrix);
        setUniform("ambientIntensity", getRenderingEngine().getAmbientLight());
    }
}
