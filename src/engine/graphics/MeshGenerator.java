package engine.graphics;

import engine.core.Mesh;
import engine.core.Vertex;
import engine.math.Vector2f;
import engine.math.Vector3f;

public class MeshGenerator 
{
    
    public static Mesh NewPlane(float width, float height){
        Mesh m;
        
        Vertex[] vert = new Vertex[4];
        int[] ind = new int[]{0, 1, 2, 2, 3, 0};
        
        vert[0] = new Vertex(new Vector3f(-width/2, 0, -height/2), new Vector2f(0,0));
        vert[1] = new Vertex(new Vector3f(-width/2, 0, height/2), new Vector2f(0,1));
        vert[2] = new Vertex(new Vector3f(width/2, 0, height/2), new Vector2f(1,1));
        vert[3] = new Vertex(new Vector3f(width/2, 0, -height/2), new Vector2f(1,0));
        
        m = new Mesh(vert, ind, true);        
        return m;
    }
    
    public static Mesh NewBox(float width, float height, float depth){
        Mesh m;
        
        Vertex[] vert = new Vertex[24];
        
        //Top
        vert[0] = new Vertex(new Vector3f(-width/2, height/2, -depth/2), new Vector2f(0,0));
        vert[1] = new Vertex(new Vector3f(-width/2, height/2, depth/2), new Vector2f(0,1));
        vert[2] = new Vertex(new Vector3f(width/2, height/2, depth/2), new Vector2f(1,1));
        vert[3] = new Vertex(new Vector3f(width/2, height/2, -depth/2), new Vector2f(1,0));
        //Bottom
        vert[4] = new Vertex(new Vector3f(-width/2, -height/2, -depth/2), new Vector2f(0,0));
        vert[5] = new Vertex(new Vector3f(-width/2, -height/2, depth/2), new Vector2f(0,1));
        vert[6] = new Vertex(new Vector3f(width/2, -height/2, depth/2), new Vector2f(1,1));
        vert[7] = new Vertex(new Vector3f(width/2, -height/2, -depth/2), new Vector2f(1,0));
        //Left
        vert[8] = new Vertex(new Vector3f(-width/2, -height/2, depth/2), new Vector2f(0,0));
        vert[9] = new Vertex(new Vector3f(-width/2, height/2, depth/2), new Vector2f(0,1));
        vert[10] = new Vertex(new Vector3f(-width/2, height/2, -depth/2), new Vector2f(1,1));
        vert[11] = new Vertex(new Vector3f(-width/2, -height/2, -depth/2), new Vector2f(1,0));
        //Right
        vert[12] = new Vertex(new Vector3f(width/2, -height/2, depth/2), new Vector2f(0,0));
        vert[13] = new Vertex(new Vector3f(width/2, height/2, depth/2), new Vector2f(0,1));
        vert[14] = new Vertex(new Vector3f(width/2, height/2, -depth/2), new Vector2f(1,1));
        vert[15] = new Vertex(new Vector3f(width/2, -height/2, -depth/2), new Vector2f(1,0));
        //Front
        vert[16] = new Vertex(new Vector3f(-width/2, -height/2, -depth/2), new Vector2f(0,0));
        vert[17] = new Vertex(new Vector3f(-width/2, height/2, -depth/2), new Vector2f(0,1));
        vert[18] = new Vertex(new Vector3f(width/2, height/2, -depth/2), new Vector2f(1,1));
        vert[19] = new Vertex(new Vector3f(width/2, -height/2, -depth/2), new Vector2f(1,0));
        //Back
        vert[20] = new Vertex(new Vector3f(-width/2, -height/2, depth/2), new Vector2f(0,0));
        vert[21] = new Vertex(new Vector3f(-width/2, height/2, depth/2), new Vector2f(0,1));
        vert[22] = new Vertex(new Vector3f(width/2, height/2, depth/2), new Vector2f(1,1));
        vert[23] = new Vertex(new Vector3f(width/2, -height/2, depth/2), new Vector2f(1,0));
        
        int[] ind = new int[]{0, 1, 2, 2, 3, 0,
                                6, 5, 4, 4, 7, 6,
                                8, 9, 10, 10, 11, 8,
                                14, 13, 12, 12, 15, 14,
                                16, 17, 18, 18, 19, 16,
                                22, 21, 20, 20, 23, 22,};
        
        m = new Mesh(vert, ind, true);
        return m;
    }
    
    public static Mesh NewSphere(float radius, int hDivisions, int vDivisions){
        Mesh m;
        
        Vertex[] vert = new Vertex[4];
        
        int[] ind = new int[]{0, 1, 2, 2, 3, 0};
        
        m = new Mesh(vert, ind, true);       
        return m;
    }
    
}
