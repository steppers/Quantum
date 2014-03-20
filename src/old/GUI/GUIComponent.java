package old.GUI;

public class GUIComponent 
{
    public int x, y, width, height;
    public String name;
    private GUIMesh mesh;
    
    public GUIComponent(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void input(){
        
    }
    
    public void update(){
        
    }
    
    public void render(){
        mesh.draw();
    }
}
