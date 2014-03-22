package old.engine.default_shaders;

import old.engine.components.Camera;
import old.engine.components.Transform;
import old.engine.core.Texture;
import old.engine.graphics.LightModel;
import old.engine.graphics.Material;
import old.engine.lights.DirectionalLight;
import old.engine.math.Matrix4f;
import old.engine.math.Vector3f;
import old.engine.shaders.Shader;

/**
 * Created by Ollie on 22/03/14.
 */
public class ForwardDirectional extends Shader {

    private static final ForwardDirectional instance = new ForwardDirectional();
    public static ForwardDirectional getInstance(){
        return instance;
    }

    private ForwardDirectional()
    {
        super();

        addVertexShaderFromFile("forward-directional.vs");
        addFragmentShaderFromFile("forward-directional.fs");
        compileShader();

        addUniform("transform");
        addUniform("transformProjected");
        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("eyePos");

        addUniform("directionalLight.base.color");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");
    }

    @Override
    public void updateUniforms(Transform transform, Camera camera, Material material) {

        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = camera.getViewProjection().mul(worldMatrix);
        material.getProperty("BaseTexture", Texture.class).bind();

        setUniform("transformProjected", projectedMatrix);
        setUniform("transform", worldMatrix);

        setUniformf("specularIntensity", material.getProperty("specularIntensity", float.class));
        setUniformf("specularPower", material.getProperty("specularPower", float.class));
        setUniform("eyePos", getRenderingEngine().getCamera().getTransform().getPos());

        setUniform("directionalLight", getRenderingEngine().getDirectionalLight());
    }
}
