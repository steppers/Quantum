package old.engine.default_shaders;

import old.engine.components.Camera;
import old.engine.components.Transform;
import old.engine.core.RenderingEngine;
import old.engine.core.Texture;
import old.engine.ext.SceneManager;
import old.engine.graphics.LightModel;
import old.engine.graphics.Material;
import old.engine.math.Matrix4f;
import old.engine.math.Vector3f;
import old.engine.shaders.Shader;

public class SpecularShader extends Shader
{
    
    private static final SpecularShader instance = new SpecularShader();
    public static SpecularShader getInstance(){
        return instance;
    }
    
    public SpecularShader()
    {
        super();
        
        addVertexShaderFromFile("DefaultSpecular.vs");
        addFragmentShaderFromFile("DefaultSpecular.fs");
        compileShader();
        
        addUniform("transform");
        addUniform("transformView");
        addUniform("normalMatrix");
        addUniform("transformProjected");
        addUniform("BaseColor");   
        addUniform("ambientLight");
        
        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("eyePos");
        
        for (int i = 0; i < LightModel.MAX_DIR_LIGHTS; i++) {
            addUniform("directionalLights[" + i + "].base.color");
            addUniform("directionalLights[" + i + "].base.intensity");
            addUniform("directionalLights[" + i + "].direction");
        }
        
        for (int i = 0; i < LightModel.MAX_POINT_LIGHTS; i++) {
            addUniform("pointLights[" + i + "].base.color");
            addUniform("pointLights[" + i + "].base.intensity");
            addUniform("pointLights[" + i + "].atten.constant");
            addUniform("pointLights[" + i + "].atten.linear");
            addUniform("pointLights[" + i + "].atten.exponent");
            addUniform("pointLights[" + i + "].position");
            addUniform("pointLights[" + i + "].range");
        }

        for (int i = 0; i < LightModel.MAX_SPOT_LIGHTS; i++) {
            addUniform("spotLights[" + i + "].pointLight.base.color");
            addUniform("spotLights[" + i + "].pointLight.base.intensity");
            addUniform("spotLights[" + i + "].pointLight.atten.constant");
            addUniform("spotLights[" + i + "].pointLight.atten.linear");
            addUniform("spotLights[" + i + "].pointLight.atten.exponent");
            addUniform("spotLights[" + i + "].pointLight.position");
            addUniform("spotLights[" + i + "].pointLight.range");
            addUniform("spotLights[" + i + "].direction");
            addUniform("spotLights[" + i + "].cutoff");
        }
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
        setUniform("BaseColor", material.getProperty("BaseColor", Vector3f.class));
        
        setUniformf("specularIntensity", material.getProperty("specularIntensity", float.class));
        setUniformf("specularPower", material.getProperty("specularPower", float.class));
        setUniform("eyePos", getRenderingEngine().getCamera().getTransform().getPos());

        setUniform("ambientLight", LightModel.GLOBAL_AMBIENT_LIGHT);
        
        for (int i = 0; i < LightModel.getDirectionalLights().length; i++) {
            setUniform("directionalLights[" + i + "]", LightModel.getDirectionalLights()[i]);
        }

        for (int i = 0; i < LightModel.getPointLights().length; i++) {
            setUniform("pointLights[" + i + "]", LightModel.getPointLights()[i]);
        }

        for (int i = 0; i < LightModel.getSpotLights().length; i++) {
            setUniform("spotLights[" + i + "]", LightModel.getSpotLights()[i]);
        }
    }   
}
