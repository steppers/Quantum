package GUI;

import engine.math.Vector3f;

public class Button extends GUIComponent
{
    private String text = "";
    private Vector3f outerColor;
    private Vector3f innerColor;
    
    public Button(String name, int x, int y, int width, int height, String text)
    {
        super(x, y, width, height);
        this.name = name;
        this.text = text;
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public void setOuterColor(Vector3f color){
        outerColor = color;
    }
    
    public void setInnerColor(Vector3f color){
        innerColor = color;
    }
}
