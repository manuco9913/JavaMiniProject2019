package Geometries;

import Primitives.*;
import java.util.List;

public class Cylinder extends Tube {

    private double _height;

    // ***************** Constructors ********************** //
    public Cylinder() {
        super();
        _height = 0.0;
    }

    public Cylinder(Cylinder cylinder) {
        _radius = cylinder._radius;
        _axisPoint = cylinder._axisPoint;
        _axisDirection = cylinder._axisDirection;
        _height = cylinder._height;
    }

    public Cylinder(double radius, Point3D axisPoint, Vector axisDirection, double height) {
        this._radius = radius;
        _axisPoint = axisPoint;
        _axisDirection = axisDirection;
        _height = height;
    }

    // ***************** Getters/Setters ********************** //
    public double getHeight() {
        return _height;
    }

    public void setHeight(double h) {
        _height = h;
    }

    // ***************** Operations ******************** //

    /*************************************************
     * FUNCTION
     *  FindIntersections
     * @param ray Ray value
     * @return list of the points intersected by the sent ray
     * MEANING
     * todo: meaning
     **************************************************/
    @Override
    public List<Point3D> FindIntersections(Ray ray){return null;}
}
