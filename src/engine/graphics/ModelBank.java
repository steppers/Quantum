package engine.graphics;

import engine.core.Mesh;
import java.util.ArrayList;

public class ModelBank 
{
    
    private static ArrayList<Mesh> models = new ArrayList<>();
    
    public static void AddModel(String fileName, String name){
        Mesh m = new Mesh(fileName);
        m.name = name;
        models.add(m);
    }
    
    public static Mesh GetModel(String name){
        for(Mesh m : models){
            if(m.name.equals(name))
                return m;
        }
        return null;
    }
    
    public static void RemoveMesh(String name){
        Mesh del = null;
        for(Mesh m : models){
            if(m.name.equals(name)){
                del = m;
                break;
            }
        }
        if(del != null){
            models.remove(del);
        }
    }
}
