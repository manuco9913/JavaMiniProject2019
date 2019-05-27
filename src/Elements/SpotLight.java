package Elements;

import Primitives.Point3D;
import Primitives.Vector;

import java.awt.*;

public class SpotLight extends PointLight {
    private Vector _direction;
// ***************** Constructor ********************** //

    /**
     * Constractor
     * Set Light: Color, Position, direction and Constants By given parameters.
     *
     * @param color     Color To set as  light color
     * @param position  Point3D to set as light position
     * @param direction Vector To set as light Direction
     * @param kc        double to set as light kc Constant
     * @param kl        double to set as light kl Constant
     * @param kq        double to set as light kq Constant
     */
    public SpotLight(Color color, Point3D position, Vector direction, double kc, double kl, double kq) {
        super(color, position, kc, kl, kq);
        _direction = new Vector(direction);
    }
// ***************** Getters/Setters ********************** //

    /*************************************************
     * FUNCTION
     *  getIntensity
     * @param point Point3D To Check Color At
     * @return Color in the given Point
     * MEANING
     * gets Distance from Light To Point, it then calculates the divider by _Kc+_Kl*d+_Kq*d*d.
     * it sets multi to be the dot product of Vector from light to point and the light direction and multiplies the
     * RGB Values of this Color by it.
     **************************************************/
    @Override
    public Color getIntensity(Point3D point) {
        Vector v = new Vector(_position, point);
        double d = v.length();
        double div = _Kc + _Kl * d + _Kq * d * d;
        Vector directionCpy = new Vector(_direction);
        directionCpy.normalize();
        v.normalize();
        double r, g, b;
        double x = v.dotProduct(directionCpy);
        r = _color.getRed() * x / div;
        g = _color.getGreen() * x / div;
        b = _color.getBlue() * x / div;

        return new Color(r > 255 ? 255 : (r < 0 ? 0 : (int) r), g > 255 ? 255 : (g < 0 ? 0 : (int) g), b > 255 ? 255 : (b < 0 ? 0 : (int) b));
    }
}
