package Geometries;

import jdk.internal.org.objectweb.asm.commons.RemappingAnnotationAdapter;
import primitives.*;

import java.beans.VetoableChangeListener;
import java.util.List;
import java.util.Map;

public class Sphere extends RadialGeometry
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
        _center = center;
    }
    //public Sphere(Map<String, String> attributes);

    // ***************** Getters/Setters ********************** //
    public Point3D getCenter(){return _center;}
    public void setCenter(Point3D center){ _center = center;}

    // ***************** Operations ******************** //
    //@Override
    //public List<Point3D> FindIntersections(Ray ray);
    @Override
    public Vector getNormal(Point3D point){
        Vector v1 = point.subtract(_center);
        v1.normalize();
        return v1;
    }
}
