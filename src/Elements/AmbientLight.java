package Elements;

import java.awt.*;

public class AmbientLight extends Light
{
    private double _Ka;

    // ***************** Constructors ********************** //
    public AmbientLight(){
        super();
        _Ka = 0.1;
    }
    public AmbientLight(Color c, double k){
    _color = new Color(c.getRed(),c.getGreen(),c.getBlue());
    _Ka = k;
}
    public AmbientLight(int r, int g, int b){
        _color = new Color(r,g,b);
        _Ka = 0.1;
    }
    public AmbientLight(AmbientLight aLight){
        _color = new Color(aLight._color.getRed(),aLight._color.getGreen(),aLight._color.getBlue());
        _Ka = aLight._Ka;
    }

    // ***************** Getters/Setters ********************** //
    public Color getColor(){
        return _color;
    }
    public void setColor(Color c){_color = new Color(c.getRGB());}
    public double getKa(){return _Ka;}
    public Color getIntensity(){return new Color((int)(_color.getRed()*_Ka),(int)(_color.getGreen()*_Ka),(int)(_color.getBlue()*_Ka));}

    // ***************** Operations ********************** //
    /*************************************************
     * FUNCTION
     *  equals
     * @param obj Vector value
     * @return A boolean value
     * MEANING
     *  Function that checks if two AmbientLight objects are equal, if so it returns true
     **************************************************/
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof AmbientLight)) return false;
        return _color.equals(((AmbientLight) obj)._color)&& (_Ka == ((AmbientLight) obj)._Ka);
    }
    /*************************************************
     * FUNCTION
     *  toString
     * @param
     * @return A string value that represents the object of type AmbientLight
     * MEANING
     *  This functions is used for the convertion: AmbientLight -> String
     **************************************************/
    @Override
    public String toString(){
        return _color.toString()+" Ka: "+_Ka;
    }

}
