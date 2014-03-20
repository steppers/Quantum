package engine.default_Materials;

import engine.default_shaders.DiffuseShader;
import engine.graphics.Material;
import engine.math.Vector3f;

public class DiffuseMaterial extends Material
{   
    public DiffuseMaterial()
    {
        super();
        this.setShader(DiffuseShader.getInstance());
        this.addProperty("eyePos", Vector3f.ZERO_VECTOR);
    }   
}
