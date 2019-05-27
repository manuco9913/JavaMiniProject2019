package Geometries;

import Primitives.*;

public abstract class RadialGeometry extends Geometry
{
    protected double _radius;

    // ***************** Constructors ********************** //
    public RadialGeometry(){
        _radius = 0.0;
    }
    public RadialGeometry(double p) {
        _radius = p;
    }
    public RadialGeometry(RadialGeometry rg){
        this._radius = rg._radius;
    }

    // ***************** Getters/Setters ********************** //
    public double getRadius(){
        return _radius;
    }
    public void setRadius(double radius){
        _radius = radius;
    }

    // ***************** Operations ******************** //
    public abstract Vector getNormal(Point3D point);
}
