package Geometries;

import java.util.List;
import java.awt.*;
import primitives.*;

public abstract class Geometry
{
    private Material _material = new Material();
    private double _nShininess = 1;
    private Color _emmission = new Color(0, 0, 0);

    //public abstract List<Point3D> FindIntersections (Ray ray);
    public abstract Vector getNormal(Point3D point);

//    public double getShininess();
  //  public Material getMaterial();
   // public Color getEmmission();
    //public void setShininess(double n);
   // public void setMaterial(Material _material);
   // public void setEmmission(Color emmission);
   // public void setKs(double ks);
   // public void setKd(double kd);
   // public void setKr(double Kr);
    // public void setKt(double Kt);
}
