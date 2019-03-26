package primitives;

import static primitives.Coordinate.*;

public class Point2D implements Comparable<Point2D>
{

    protected Coordinate _x;
    protected Coordinate _y;

    // ***************** Constructors ********************** //

    public Point2D(){
        this._x = ZERO;
        this._y = ZERO;
    }
    public Point2D(Coordinate x, Coordinate y){
        this._x = x;
        this._y = y;
    }
    public Point2D(Point2D point2D){
        this._x = point2D._x;
        this._y = point2D._y;
    }

    // ***************** Getters/Setters ********************** //
    public Coordinate getX(){ return this._x; }
    public Coordinate getY(){ return this._y; }
    public void setX(Coordinate x){ this._x = x;}
    public void setY(Coordinate y){ this._y = y;}

    // ***************** Administration ******************** //
    /*************************************************
     * FUNCTION
     *  compareTo
     * PARAMETERS
     *  Point2D
     * RETURN VALUE
     *  An integer value
     * MEANING
     *  Function that compares between two Point2D objects:
     *      the function returns 0 if the Point2Ds are equal
     *      the function returns a value different from 0 if the two points are different
     **************************************************/
    @Override
    public int compareTo(Point2D point){
        Point2D zeroPoint = new Point2D(ZERO,ZERO);
        if(this._x.equals(point._x) && this._y.equals(point._y))
            return 0;
        return 1;
    }

    /*************************************************
     * FUNCTION
     *  toString
     * PARAMETERS
     *  none
     * RETURN VALUE
     *  A string value that represents the object of type Point2D
     * MEANING
     *  This functions is used for the convertion: Point2D -> String
     **************************************************/
    @Override
    public String toString() { return String.format("(%.2f,%.2f)", _x._coordinate,_y._coordinate); }  //(x,y)

    // ***************** Operations ******************** //
    /*************************************************
     * FUNCTION
     *  distance
     * PARAMETERS
     *  Point2D secondPoint
     * RETURN VALUE
     *  A double value
     * MEANING
     *  This functions calculates the distance between 'this' point2D and secondPoint
     **************************************************/
    public double distance(Point2D secondPoint)
    {
        double dist = Math.sqrt(Math.pow(secondPoint._x.subtract(this._x)._coordinate,2) + Math.pow(secondPoint._y.subtract(this._y)._coordinate,2));
        return dist;
    }
}
