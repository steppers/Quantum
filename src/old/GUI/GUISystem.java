package old.GUI;

import java.util.ArrayList;

public class GUISystem 
{
    private static ArrayList<GUIComponent> components = new ArrayList<>();
    
    public static void AddComponent(GUIComponent c){
        components.add(c);
    }
    
    public static GUIComponent GetComponent(String name){
        for(GUIComponent c : components){
            if(c.name.equals(name))
                return c;
        }
        return null;
    }
    
    public static void RemoveComponent(String name){
        GUIComponent del = null;
        for(GUIComponent c : components){
            if(c.name.equals(name))
                del = c;
        }
        components.remove(del);
    }
    
    public static void input(){
        
    }
    
    public static void update(){
        
    }
    
    public static void render(){
        
    }
}
