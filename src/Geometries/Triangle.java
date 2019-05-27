package Geometries;

import Primitives.*;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends FlatGeometry implements Intersectable {
    private Point3D _p1;
    private Point3D _p2;
    private Point3D _p3;

    // ***************** Constructors ********************** //
    public Triangle() {
        _p1 = new Point3D(0, 0, 0);
        _p2 = new Point3D(0, 0, 0);
        _p3 = new Point3D(0, 0, 0);
    }
    public Triangle(Triangle triangle) {
        _p1 = triangle._p1;
        _p2 = triangle._p2;
        _p3 = triangle._p3;
    }
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        _p1 = p1;
        _p2 = p2;
        _p3 = p3;
    }

    // ***************** Getters/Setters ********************** //
    public Point3D getP1() {
        return _p1;
    }
    public Point3D getP2() {
        return _p2;
    }
    public Point3D getP3() {
        return _p3;
    }
    public void setP1(Point3D p1) {
        _p1 = p1;
    }
    public void setP2(Point3D p2) {
        _p2 = p2;
    }
    public void setP3(Point3D p3) {
        _p3 = p3;
    }

    // ***************** Operations ******************** //

    /*************************************************
     * FUNCTION
     *  getNormal
     * @param point Point3D value
     * @return the normal Vector of the specific geometry
     * MEANING
     *  it calculates the U and V, two vectors of the geometry and cross product them. the solution vector is then normalized and multiplied by 1
     **************************************************/
    @Override
    public Vector getNormal(Point3D point) {
        Vector v1 = new Vector(_p1, _p2);
        Vector v2 = new Vector(_p1, _p3);
        Vector n = v1.crossProduct(v2);
        n.normalize();
        n = n.scale(-1);
        return n;
    }
    /*************************************************
     * FUNCTION
     *  FindIntersections
     * @param ray Ray value
     * @return list of the points intersected by the sent ray
     * MEANING
     * by using the FindIntersection function of the plane, we find the intersection point with it (the pLane), and check if
     * it is inside the triangle area or not, if so, it returns the intersection point
     **************************************************/
  @Override
    public List<Point3D> FindIntersections(Ray ray) {
//works
      if (getNormal(_p2).length() == 0) {
          return new ArrayList<>();
      }
      Plane intersectionHelp = new Plane(getNormal(_p2), _p2);
      ArrayList<Point3D> intersectionPoints = (ArrayList<Point3D>) (intersectionHelp.FindIntersections(ray));
      if (intersectionPoints.isEmpty()) {
          return new ArrayList<>();
      }

      Point3D intersectionPoint3D = intersectionPoints.get(0);

      Vector n1 = new Vector(ray.getPOO(), _p1).crossProduct(new Vector(ray.getPOO(), _p2));
      Vector n2 = new Vector(ray.getPOO(), _p2).crossProduct(new Vector(ray.getPOO(), _p3));
      Vector n3 = new Vector(ray.getPOO(), _p3).crossProduct(new Vector(ray.getPOO(), _p1));
      n1.normalize();
      n2.normalize();
      n3.normalize();

      double d1 = (new Vector(intersectionPoint3D, ray.getPOO())).dotProduct(n1);
      double d2 = (new Vector(intersectionPoint3D, ray.getPOO())).dotProduct(n2);
      double d3 = (new Vector(intersectionPoint3D, ray.getPOO())).dotProduct(n3);

      if (!((d1 > 0 && d2 > 0 && d3 > 0) || (d1 < 0 && d2 < 0 && d3 < 0))) {
          intersectionPoints = new ArrayList<>();
      }

      return intersectionPoints;
  }
}

