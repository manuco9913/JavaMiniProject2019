package Elements;

import Primitives.Point3D;
import Primitives.Vector;

import java.awt.*;

public class DirectionalLight extends Light implements LightSource {

    private Vector _direction;  //Direction Of the light

    //***************** Constructors ********************** //
    public DirectionalLight(Color color, Vector direction) {
        _color = new Color(color.getRGB());
        _direction = new Vector(direction);
    }

    //***************** Getters/Setters ********************** //
    public Vector getDirection() {
        return _direction;
    }
    public void setDirection(Vector _direction) {
        this._direction = new Vector(_direction);
    }
    @Override
    public Color getIntensity(Point3D point) {
        return _color;
    }
    @Override
    public Vector getL(Point3D point)
    {
        Vector Vec = new Vector(_direction.getHead(),point);
        Vec.normalize();
        return Vec;
    }
}
