package old.engine.default_Materials;

import old.engine.default_shaders.DiffuseShader;
import old.engine.graphics.Material;
import old.engine.math.Vector3f;

public class DiffuseMaterial extends Material
{   
    public DiffuseMaterial()
    {
        super();
        this.setShader(DiffuseShader.getInstance());
        this.addProperty("eyePos", Vector3f.ZERO_VECTOR);
    }   
}
