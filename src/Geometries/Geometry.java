package Geometries;

import java.awt.*;
import java.util.List;

import Primitives.*;

public abstract class Geometry implements Intersectable
{
    private Material _material = new Material();
    private Color _emmission = new Color(0, 0, 0);

    // ***************** Getters/Setters ********************** //
    public Material getMaterial(){ return _material;}
    public Color getEmmission(){ return _emmission;}
    public void setMaterial(Material mat){_material = mat;}
    public void setEmmission(Color emmission){
        _emmission = emmission;
    }

    // ***************** Operations ******************** //
    public abstract List<Point3D> FindIntersections(Ray ray);
    public abstract Vector getNormal(Point3D point);
}
