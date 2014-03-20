package quantum.test;

import engine.components.Component;
import engine.core.Input;
import engine.core.Time;
import engine.ext.GameObject;
import engine.ext.SceneManager;

public class Test1SceneSwap extends Component
{
    int currentScene = 1;
    long lastTime = 0;
    
    public Test1SceneSwap(GameObject gameObject)
    {
        super(gameObject, "Test1SceneSwap");
    }
    
    public void input(){
        if(Input.getKey(Input.KEY_SPACE) && Time.getTime()-lastTime >= 200000000){
            if(currentScene == 1){
                SceneManager.loadScene(new Scene2());
                currentScene = 2;
                lastTime = Time.getTime();
            }
            else
            {
                SceneManager.loadScene(new Scene1());
                currentScene = 1;
                lastTime = Time.getTime();
            }
        }
    }
    
}
