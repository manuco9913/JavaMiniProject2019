package Geometries;

import Primitives.*;
import com.sun.org.omg.CORBA.ExceptionDescription;

import java.util.ArrayList;
import java.util.List;

public class Plane extends FlatGeometry implements Intersectable
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
        _normal = new Vector(normal);
        _Q = new Point3D(q);
    }

    // ***************** Getters/Setters ********************** //
    @Override
    public Vector getNormal(Point3D point){
        Vector v = new Vector(this._Q,point);
        boolean b = _normal.dotProduct(v)==0;
        if(!b)
            throw new ArithmeticException();
        return _normal;
    }
    public Point3D getQ(){ return _Q;}
    public void setNormal(Vector normal){ _normal = new Vector(normal);}
    public void setQ(Point3D d){ _Q = d; }

    // ***************** Operations ******************** //
    /*************************************************
     * FUNCTION
     *  FindIntersections
     * @param ray Ray value
     * @return list of the points intersected by the sent ray
     * MEANING
     * We look for one intersection point with the plane, since it's a FlatGeometry.
     * The intersection point will be equal to the directional vector of the ray multiplied by a certain value that defines the
     * Vector between the point P0 from where the Ray is shot, and the intersection point P
     **************************************************/
   //elc
   @Override
   public List<Point3D> FindIntersections(Ray ray){

       List<Point3D> intersectionPoint = new ArrayList<>();
       Vector N = _normal;
       Vector V = ray.getDirection();

       if(N.dotProduct(V)==0)
           return intersectionPoint;

       double t = -N.dotProduct(new Vector(_Q, ray.getPOO()))/N.dotProduct(V);//

       if(t >= 0)
       {
           V = V.scale(t);
           Point3D P = ray.getPOO().add(V);
           intersectionPoint.add(P);
       }
       return intersectionPoint;
   }
}
