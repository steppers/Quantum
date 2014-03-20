package old.engine.components;

import old.engine.core.Mesh;
import old.engine.ext.GameObject;

public class MeshFilter extends Component
{
    private Mesh sharedMesh;
    
    public MeshFilter(GameObject gameObject)
    {
        super(gameObject, "MeshFilter");
    }

    public Mesh getSharedMesh() {
        return sharedMesh;
    }

    public void setSharedMesh(Mesh mesh) {
        this.sharedMesh = mesh;
    }
    
    public void destroy(){
        sharedMesh = null;
    }
}
