package Geometries;

import Primitives.*;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.pow;

public class Sphere extends RadialGeometry implements Intersectable
{
    private Point3D _center;

    // ***************** Constructors ********************** //
    public Sphere(){
        super();
        _center = new Point3D();
    }
    public Sphere (Sphere sphere){
        _radius = sphere._radius;
        _center = sphere._center;
    }
    public Sphere(double radius, Point3D center){
        _radius = radius;
        _center = new Point3D(center);
    }

    // ***************** Getters/Setters ********************** //
    public Point3D getCenter(){return _center;}
    public void setCenter(Point3D center){ _center = center;}

    // ***************** Operations ******************** //
    /*************************************************
     * FUNCTION
     *  getNormal
     * @param point Point3D value
     * @return the normal Vector of the specific geometry
     * MEANING
     * calculates and returns the vector from the given point to the center of the Sphere, normalized
     **************************************************/
    @Override
    public Vector getNormal(Point3D point){
        Vector v1 = new Vector(_center,point);
        v1.normalize();
        return v1;
    }
    /*************************************************
     * FUNCTION
     *  FindIntersections
     * @param ray Ray value
     * @return list of the points intersected by the sent ray
     * MEANING
     * - P1 is the first intersection point, it equals to: P0 + t1*V, where t1 is the difference between tm (length of
     *      the projection of the L Vector on V) and th (distance from the intersection point of V with the sphere and
     *      the intersection of the projection of the L Vector on V with V)
     * - P2 is th + tm
     **************************************************/
    @Override
    public List<Point3D> FindIntersections(Ray ray) {
        List<Point3D> intersectionPoints= new ArrayList<>(2);

        Vector u = new Vector(ray.getPOO(), this.getCenter());
        double tm = u.dotProduct(ray.getDirection());
        double d = Math.sqrt((u.length()*u.length()) - (tm*tm));

        if (d > this.getRadius())
            return intersectionPoints; // return null;

        double th = Math.sqrt((this.getRadius()*this.getRadius()) - (d*d));

        double t1 = tm - th;
        double t2 = tm + th;

        if (t1 >= 0){
            Vector V = ray.getDirection();
            V = V.scale(t1);
            Point3D p = ray.getPOO();
            Point3D P1 = p.add(V);
            intersectionPoints.add(P1);
        }

        if (t2 >= 0){
            Vector V = ray.getDirection();
            V = V.scale(t2);
            Point3D p = ray.getPOO();
            Point3D P2 = p.add(V);
            intersectionPoints.add(P2);
        }

        return intersectionPoints;
    }
}
