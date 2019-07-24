package Elements;

import Primitives.Point3D;
import Primitives.Vector;

import java.awt.*;

public class PointLight extends Light implements LightSource {
    Point3D _position;  // Light Position
    double _Kc, _Kl, _Kq;//intensity
// ***************** Constructors ********************** //

    /*******************************************************
     * Constructor
     * Set Light: Color, Position and Constants By given parameters
     *
     * @param color Color To set As this light color
     * @param position  Point3D to set as this light position
     * @param kc    double to set as light kc Constant
     * @param kl    double to set as light kl Constant
     * @param kq    double to set as light kq Constant
     */
    public PointLight(Color color, Point3D position, double kc, double kl, double kq) {
        _color = new Color(color.getRGB());
        _position = new Point3D(position);
        _Kc = kc;
        _Kl = kl;
        _Kq = kq;

    }
// ***************** Getters/Setters ********************** //

    public Point3D getPosition() {
        return _position;
    }

    @Override
    public Vector getL(Point3D point)
    {
        Vector lVector= new Vector(_position,point);
        lVector.normalize();
        return lVector;
    }
    /*************************************************
     * FUNCTION
     *  getIntensity
     * @param point Point3D To Check Color At
     * @return Color in the given Point
     * MEANING
     * gets Distance from Light To Point it then calculates the divider by _Kc+_Kl*d+_Kq*d*d and divides RGB Values of this Color
     * by it.
     **************************************************/
    @Override
    public Color getIntensity(Point3D point) {
        Vector v = new Vector(point);
        v.subtract(new Vector(_position));
        double d = v.length();
        double divider = _Kc + _Kl * d + _Kq * d * d;
        double r, g, b;
        r = _color.getRed() / divider;
        g = _color.getGreen() / divider;
        b = _color.getBlue() / divider;
        return new Color(r > 255 ? 255 : (r < 0 ? 0 : (int) r), g > 255 ? 255 : (g < 0 ? 0 : (int) g), b > 255 ? 255 : (b < 0 ? 0 : (int) b));
    }
}
