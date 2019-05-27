package Geometries;

import java.util.ArrayList;
import java.util.List;
import Primitives.*;

public class Quadrangle extends FlatGeometry{

    private Triangle _tri1;
    private Triangle _tri2;

    public Quadrangle(Point3D P1, Point3D P2, Point3D P3, Point3D P4) {
        _tri1 = new Triangle(P1, P2, P4);
        _tri2 = new Triangle(P2, P3, P4);
    }
    /*************************************************
     * FUNCTION
     *  FindIntersections
     * @param ray Ray value
     * @return list of the points intersected by the sent ray
     * MEANING
     * We look for one intersection point with the Quadrangle, since it's a FlatGeometry.
     * The intersection point will be equal to the intersection of the ray with one of the two triangles
     * that defines the Quadrangle
     **************************************************/
    @Override
    public List<Point3D> FindIntersections(Ray ray) {
        List<Point3D> intersectionPoints = new ArrayList<>();
        intersectionPoints.addAll(_tri1.FindIntersections(ray));
        intersectionPoints.addAll(_tri2.FindIntersections(ray));
        return intersectionPoints;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _tri2.getNormal(point);
    }
}
