package old.engine.default_Materials;

import old.engine.default_shaders.SpecularShader;
import old.engine.graphics.Material;
import old.engine.math.Vector3f;

public class SpecularMaterial extends Material
{
    
    public SpecularMaterial()
    {
        super();
        this.setShader(SpecularShader.getInstance());
        this.addProperty("specularIntensity", 2f);
        this.addProperty("specularPower", 32f);
        this.addProperty("eyePos", Vector3f.ZERO_VECTOR);
    }
    
}
