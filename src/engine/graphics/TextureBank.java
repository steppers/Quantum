package engine.graphics;

import engine.core.Texture;
import java.util.ArrayList;

public class TextureBank 
{    
    private static ArrayList<Texture> textures = new ArrayList<>();
    
    public static void AddTexture(String fileName, String name){
        Texture t = new Texture(fileName);
        t.name = name;
        textures.add(t);
    }
    
    public static int GetTexture(String name){
        for(Texture t : textures){
            if(t.name.equals(name))
                return t.getID();
        }
        return 0;
    }   
}
