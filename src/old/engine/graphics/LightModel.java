package old.engine.graphics;

import old.engine.lights.*;
import old.engine.math.Vector3f;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.*;

public class LightModel 
{
    public static Vector3f GLOBAL_AMBIENT_LIGHT = new Vector3f(0.4f, 0.4f, 0.4f);
    
    public static final int MAX_DIR_LIGHTS = 4;
    public static final int MAX_POINT_LIGHTS = 8;
    public static final int MAX_SPOT_LIGHTS = 8;
    
    public static ArrayList<PointLight> pointLights = new ArrayList<>();
    public static ArrayList<SpotLight> spotLights = new ArrayList<>();
    public static ArrayList<DirectionalLight> dirLights = new ArrayList<>();
    
    public static void AddPointLight(PointLight light){
        pointLights.add(light);
    }
    
    public static void AddSpotLight(SpotLight light){
        spotLights.add(light);
    }
    
    public static void AddDirectionalLight(DirectionalLight light){
        dirLights.add(light);
    }
    
    public static PointLight[] getPointLights(){
        PointLight[] lights = new PointLight[pointLights.size()];
        return pointLights.toArray(lights);
    }
    
    public static SpotLight[] getSpotLights(){
        SpotLight[] lights = new SpotLight[spotLights.size()];
        return spotLights.toArray(lights);
    }
    
    public static DirectionalLight[] getDirectionalLights(){
        DirectionalLight[] lights = new DirectionalLight[dirLights.size()];
        return dirLights.toArray(lights);
    }
    
    public static void RemovePointLight(PointLight light){
        pointLights.remove(light);
    }
    
    public static void RemoveSpotLight(SpotLight light){
        spotLights.remove(light);
    }
    
    public static void RemoveDirectionalLight(DirectionalLight light){
        dirLights.remove(light);
    }
    
}
