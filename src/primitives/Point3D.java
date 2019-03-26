package primitives;
import primitives.Vector;

public class Point3D extends Point2D// implements Comparable<Point3D>
{

    protected Coordinate _z;

    // ***************** Constructors ********************** //
    public Point3D() {
        _x = new Coordinate();
        _y = new Coordinate();
        _z = new Coordinate();
    }
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this._x = x;
        this._y = y;
        this._z = z;
    }
    public Point3D(double x, double y, double z){
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
        this._z = new Coordinate(z);
    }
    public Point3D(Point3D point3D){
        this._x = point3D._x;
        this._y = point3D._y;
        this._z = point3D._z;
    }

    // ***************** Getters/Setters ********************** //
    public Coordinate getZ(){ return _z;}
    public void setZ(Coordinate z){ this._z = z;}

    // ***************** Administration ******************** //
    /*************************************************
     * FUNCTION
     *  compareTo
     * PARAMETERS
     *  Point3D
     * RETURN VALUE
     *  An integer value
     * MEANING
     *  Function that compares between two Point3D objects:
     *      the function returns 0 if the Point3Ds are equal
     *      the function returns a value different from 0 if the two points are different
     **************************************************/
    public int compareTo(Point3D point3D){
        if(((Point2D)this).compareTo((Point2D)point3D) == 0)
            if(this._z.compareTo(point3D._z) == 0)
                return 0;
            return -1;
    }
    /*************************************************
     * FUNCTION
     *  toString
     * PARAMETERS
     *  none
     * RETURN VALUE
     *  A string value that represents the object of type Point3D
     * MEANING
     *  This functions is used for the convertion: Point3D -> String
     **************************************************/
    public String toString(){ return String.format("(%.2f, %.2f, %.2f)", _x._coordinate,_y._coordinate,_z._coordinate); }//(x,y,z)

    // ***************** Operations ******************** //
    /*************************************************
     * FUNCTION
     *  divide
     * PARAMETERS
     *  double div
     * RETURN VALUE
     *  none
     * MEANING
     *  This functions divides all the Coordinates of the Point3D by the div value
     **************************************************/
    public void divide(double div){
        this._x._coordinate /= div;
        this._y._coordinate /= div;
        this._z._coordinate /= div;
    }
    /*************************************************
     * FUNCTION
     *  add
     * PARAMETERS
     *  Vector vector
     * RETURN VALUE
     *  A Point3D value
     * MEANING
     *  This functions adds a Vector to the Point3D and creates a new Point3D with the result
     **************************************************/
    public Point3D add(Vector vector){
        Point3D toReturn = new Point3D();
        toReturn._x = this._x.add(vector.getHead()._x);
        toReturn._y = this._y.add(vector.getHead()._y);
        toReturn._z = this._z.add(vector.getHead()._z);
        return toReturn;
    }
    /*************************************************
     * FUNCTION
     *  subtract
     * PARAMETERS
     *  Vector vector
     * RETURN VALUE
     *  A Point3D value
     * MEANING
     *  This functions subtracts a Vector to the Point3D and creates a new Point3D with the result
     **************************************************/
    public Point3D subtract(Vector vector){
        Point3D toReturn = new Point3D();
        toReturn._x = this._x.subtract(vector.getHead()._x);
        toReturn._y = this._y.subtract(vector.getHead()._y);
        toReturn._z = this._z.subtract(vector.getHead()._z);
        return toReturn;
    }
    /*************************************************
     * FUNCTION
     *  distance
     * PARAMETERS
     *  Point3D point
     * RETURN VALUE
     *  A double value
     * MEANING
     *  This functions calculates the distance between 'this' point2D and point
     **************************************************/
    public double distance(Point3D point){
    return Math.sqrt(Math.pow(point._x.subtract(this._x)._coordinate,2) +
            Math.pow(point._y.subtract(this._y)._coordinate,2) + Math.pow(point._z.subtract(this._z)._coordinate,2));

}
}