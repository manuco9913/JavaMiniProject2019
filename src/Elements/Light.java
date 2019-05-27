package Elements;

import java.awt.*;

public abstract class Light
{
    protected Color _color;

    // ***************** Constructors ********************** //
    public Light(){
        _color = new Color(255, 255, 255);
    }
    public Light (Color color){
        color = _color;
    }

    // ***************** Getters/Setters ********************** //
    public Color getIntensity(){ return _color;}
}
