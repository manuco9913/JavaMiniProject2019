package Geometries;

import primitives.*;
import java.util.List;

public class Cylinder extends RadialGeometry
{
    private Point3D _axisPoint;
    private Vector _axisDirection;

    // ***************** Constructors ********************** //
    public Cylinder(){
        super();
        _axisPoint = new Point3D();
        _axisDirection = new Vector();
    }
    public Cylinder(Cylinder cylinder){
        _radius = cylinder._radius;
        _axisPoint = cylinder._axisPoint;
        _axisDirection = cylinder._axisDirection;
    }
    public Cylinder(double radius, Point3D axisPoint, Vector axisDirection){
        this._radius = radius;
        _axisPoint = axisPoint;
        _axisDirection = axisDirection;
    }

    // ***************** Getters/Setters ********************** //
    public Point3D getAxisPoint(){ return _axisPoint;}
    public Vector getAxisDirection(){return _axisDirection;}
    public void setAxisPoint(Point3D axisPoint){_axisPoint = axisPoint;}
    public void setAxisDirection(Vector axisDirection){ _axisDirection = axisDirection;}

    // ***************** Operations ******************** //
    //@Override
    //public List<Point3D> FindIntersections(Ray ray);
    @Override
    public Vector getNormal(Point3D point){
        _axisDirection.normalize();//normalize the Cylinder vector
        Vector v2 = point.subtract(_axisPoint);//v2 is the "diagonal"
        double height = v2.dotProduct(_axisDirection);//height of "point" from "_axisPoint"
        Point3D p2 = _axisPoint.add(_axisDirection.scale(height));// p2 = axisPoint + [axisDirection(normalized) * height]
        Vector v3 = point.subtract(p2);
        v3.normalize();
        return v3;
    }
}
