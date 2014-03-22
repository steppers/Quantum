package old.Animation;

import old.engine.components.Component;
import old.engine.core.GameObject;
import java.util.ArrayList;

public class Animator extends Component
{
    private ArrayList<Animation> animations = new ArrayList<>();
    private ArrayList<Animation> playing = new ArrayList<>();
    
    public Animator(GameObject gameObject)
    {
        super(gameObject, "Animator");
    }
    
    @Override
    public void update(float delta){
        for(Animation a : playing){
            if(!a.paused && !a.isFinished(delta)){
                process(a.getFrame());
            }
        }
        for(Animation a : animations){
            if(a.isFinished(delta)){
                playing.remove(a);
                a.isPlaying = false;
                a.paused = false;
                a.setTotalTime(0);
            }
        }
    }
    
    public void addAnimation(String name, String fileName){
        animations.add(new Animation(name, fileName));
    }
    
    public void setLooping(String name, boolean looping){
        for(Animation a : animations){
            if(a.getName().equals(name)){
                a.setLooping(looping);
                break;
            }
        }
    }
    
    public void setSpeed(String name, float speed){
        for(Animation a : animations){
            if(a.getName().equals(name)){
                a.setSpeed(speed);
                break;
            }
        }
    }
    
    public void play(String name){
        for(Animation a : animations){
            if(a.getName().equals(name)){
                if(!a.isPlaying){
                    a.isPlaying = true;
                    playing.add(a);
                    break;
                }
            }
        }
    }
    
    public void stop(String name){
        for(Animation a : animations){
            if(a.getName().equals(name)){
                if(!a.isPlaying){
                    playing.remove(a);
                    a.isPlaying = false;
                    a.paused = false;
                    a.setTotalTime(0);
                    break;
                }
            }
        }
    }
    
    public void pause(String name){
        for(Animation a : animations){
            if(a.getName().equals(name)){
                a.paused = true;
                break;
            }
        }
    }
    
    public void unpause(String name){
        for(Animation a : animations){
            if(a.getName().equals(name)){
                a.paused = false;
                break;
            }
        }
    }
    
    private void process(AnimationFrame a){
        parent.getTransform().setAnimPos(a.getPosition());
        parent.getTransform().setAnimRot(a.getRotation());
        parent.getTransform().setAnimScale(a.getScale());
    }
    
}
