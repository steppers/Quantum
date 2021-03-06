package old.engine.core;

import old.engine.components.Component;
import old.engine.components.Transform;
import old.engine.math.Vector3f;
import old.engine.shaders.Shader;

import java.util.ArrayList;

public class GameObject {

    private boolean active = true;
    private boolean Static;
    private String tag = "";
    private ArrayList<Component> components = new ArrayList<>();
    private ArrayList<GameObject> children;
    private GameObject parent;
    private Transform transform;
    
    private boolean removeOnSceneLoad = true;

    public GameObject() {
        this(Vector3f.ZERO_VECTOR, Vector3f.ZERO_VECTOR, Vector3f.ONE_VECTOR, "");
    }

    public GameObject(Vector3f pos, Vector3f rot, Vector3f scale, String tag) {
        transform = new Transform(this, pos, rot, scale);
        this.tag = tag;
        children = new ArrayList<GameObject>();
    }

    public void input(float delta) {
        for (Component c : components) {
            if (c.isActive() && this.isActive()) {
                c.input(delta);
            }
        }
        for (GameObject g : children) {
            if (g.isActive()) {
                g.input(delta);
            }
        }
    }

    public void update(float delta) {
        for (Component c : components) {
            if (c.isActive() && this.isActive()) {
                c.update(delta);
            }
        }
        for (GameObject g : children) {
            if (g.isActive()) {
                g.update(delta);
            }
        }
    }

    public void render(Shader shader, boolean useMaterial) {
        for (Component c : components) {
            if (c.isActive() && this.isActive()) {
                c.render(transform, shader, useMaterial);
            }
        }
        for (GameObject g : children) {
            if (g.isActive()) {
                g.render(shader, useMaterial);
            }
        }
    }

    public void AddComponent(Component component) {
        for (Component c : components) {
            if (c.getType().equals(component.getType())) {
                return;
            }
        }
        components.add(component);
    }

    public Transform getTransform() {
        return transform;
    }

    public GameObject getChildWithTag(String tag) {
        for (GameObject g : children) {
            if (g.getTag().equals(tag)) {
                return g;
            }else{
                GameObject v = g.getChildWithTag(tag);
                if(v != null){
                    return v;
                }
            }
        }
        return null;
    }
    
    public ArrayList<GameObject> getChildrenWithTag(String tag) {
        ArrayList<GameObject> gos = new ArrayList<>();
        for (GameObject g : children) {
            if (g.getTag().equals(tag)) {
                gos.add(g);
            } else {
                gos.addAll(g.getChildrenWithTag(tag));
            }
        }
        return gos;
    }

    public GameObject getParent() {
        if(parent != null)
            return parent;
        else
            return null;
    }

    public void addChild(GameObject child) {
        child.setParent(this);
        children.add(child);
    }

    public void removeChild(GameObject child){
        if(children.contains(child)){
            children.remove(child);
        }else{
            for(GameObject c : children){
                c.removeChild(child);
            }
        }
    }

    public ArrayList<GameObject> getRemoveOnLoads(){
        ArrayList<GameObject> gos = new ArrayList<>();
        for (GameObject g : children) {
            if (g.removesOnSceneLoad()) {
                gos.add(g);
            } else {
                gos.addAll(g.getRemoveOnLoads());
            }
        }
        return gos;
    }

    public void resetAnimations(){
        getTransform().resetAnimations();
        for (GameObject g : children) {
            if (g.isActive()) {
                g.resetAnimations();
            }
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setActiveRecursively(boolean val) {
        active = val;
        for (GameObject g : children) {
            g.setActiveRecursively(val);
        }
    }

    public boolean isStatic() {
        return Static;
    }

    public void setStatic(boolean Static) {
        this.Static = Static;
    }
    
    public <T extends Component> T getComponent(Class<T> type){
        for (Component c : components) {
            if (c.getClass() == type) {
                return type.cast(c);
            }
        }
        return null;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }
    
    public void setRemoveOnSceneLoad(boolean val){
        removeOnSceneLoad = val;
    }
    
    public boolean removesOnSceneLoad(){
        return removeOnSceneLoad;
    }
    
    public void destroy(){
        for (Component c : components) {
            c.destroy();
        }
    }
}
