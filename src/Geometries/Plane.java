package Geometries;

import primitives.*;
import java.util.List;

public class Plane extends Geometry
{
    private Vector _normal;
    private Point3D _Q;
    // ***************** Constructors ********************** //
    public Plane(){
        _normal = new Vector();
        _Q = new Point3D();
    }
    public Plane (Plane plane){
        _normal = plane._normal;
        _Q = plane.getQ();
    }
    public Plane (Vector normal, Point3D q){
        _normal = normal;
        _Q = q;
    }
    // ***************** Getters/Setters ********************** //
    @Override
    public Vector getNormal(Point3D point){
        return _normal;
    }
    public Point3D getQ(){ return _Q;}
    public void setNormal(Vector normal){ _normal = normal;}
    public void setQ(Point3D d){ _Q = d; }
    // ***************** Operations ******************** //
    //public List<Point3D> FindIntersections(Ray ray);
}
