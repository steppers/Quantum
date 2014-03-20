package old.engine.graphics;

import java.util.ArrayList;

public class MaterialBank 
{
    private static ArrayList<Material> materials = new ArrayList<>();
    
    public static void AddMaterial(Material m){
        materials.add(m);
    }
    
    public static Material getMaterial(String name){
        for(Material m : materials){
            if(m.name.equals(name)){
                return m;
            }
        }
        return null;
    }
    
}
