package Geometries;

import primitives.*;
import java.util.List;
import java.util.Map;

public class Triangle extends Geometry
{
    private Point3D _p1;
    private Point3D _p2;
    private Point3D _p3;

    // ***************** Constructors ********************** //
    public Triangle(){
        _p1 = new Point3D(0,0,0);
        _p2 = new Point3D(0,0,0);
        _p3 = new Point3D(0,0,0);
    }
    public Triangle(Triangle triangle){
        _p1 = triangle._p1;
        _p2 = triangle._p2;
        _p3 = triangle._p3;
    }
    public Triangle(Point3D p1, Point3D p2, Point3D p3){
        _p1 = p1;
        _p2 = p2;
        _p3 = p3;
    }
    //public Triangle(Map<String, String> attributes);

    // ***************** Getters/Setters ********************** //
    public Point3D getP1(){ return _p1; }
    public Point3D getP2(){ return _p2; }
    public Point3D getP3(){ return _p3; }
    public void setP1(Point3D p1){_p1 = p1;}
    public void setP2(Point3D p2){_p2 = p2;}
    public void setP3(Point3D p3){_p3 = p3;}

    // ***************** Operations ******************** //
    @Override
    public Vector getNormal(Point3D point){
        Vector v1 = _p2.subtract(_p1);
        Vector v2 = _p3.subtract(_p1);
        Vector normal = v1.crossProduct(v2);
        normal.normalize();
        return normal;
    }
    //public List<Point3D> FindIntersections(Ray ray);
}
