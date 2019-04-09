package Geometries;

import primitives.*;

public abstract class RadialGeometry extends Geometry
{
    protected double _radius;
    public RadialGeometry(){
        _radius = 0.0;
    }
    public RadialGeometry(double p) {
        _radius = p;
    }
    public RadialGeometry(RadialGeometry rg){
        this._radius = rg._radius;
    }

    public double getRadius(){
        return _radius;
    }
    public void setRadius(double radius){
        _radius = radius;
    }

    public abstract Vector getNormal(Point3D point);
}
