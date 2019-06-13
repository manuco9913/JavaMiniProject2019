package Primitives;

import java.text.DecimalFormat;

public class Vector implements Comparable<Vector>
{
    private Point3D _head;

    // ***************** Constructors ********************** //
    public Vector(){
        _head = new Point3D();
    }
    public Vector(Point3D head){
        _head = head;
    }
    public Vector(Vector vector){
        this._head = vector.getHead();
    }
    public Vector(double xHead, double yHead, double zHead){
        this._head = new Point3D();
        this._head._x.setCoordinate(xHead);
        this._head._y.setCoordinate(yHead);
        this._head._z.setCoordinate(zHead);
    }
    public Vector(Point3D p1, Point3D p2) {
        if (p1.equals(p2))
            throw new IllegalArgumentException("pt1 == pt2!");
        this.setHead(new Point3D(
                p2.getX().subtract(p1.getX()),
                p2.getY().subtract(p1.getY()),
                p2.getZ().subtract(p1.getZ())));
    }


    // ***************** Getters/Setters ********************** //
    public Point3D getHead(){ return _head; }
    public void setHead(Point3D head) { _head = head; }

    // ***************** Administration ******************** //
    /*************************************************
     * FUNCTION
     *  compareTo
     * @param vector Vector value
     * @return An integer value
     * MEANING
     *  Function that compares between two Vectors objects:
     *      the function returns 0 if the Vectors are equal
     *      the function returns a negative value if the first object is smaller than the second
     *      the function returns a positive value if the first object is bigger than the second
     **************************************************/
    @Override
    public int compareTo(Vector vector){
        if(this.length() > vector.length() )
            return 1;
        if(this.length() < vector.length() )
            return -1;
        return 0; // in case they are equal
    }
    /*************************************************
     * FUNCTION
     *  toString
     * @param
     * @return A string value that represents the object of type Vector
     * MEANING
     *  This functions is used for the convertion: Vector -> String
     **************************************************/
    @Override
    public String toString(){
        return "(" + _head._x + ", " + _head._y + ", " + _head._z + ")";
    } //[x,y,z]
    /*************************************************
     * FUNCTION
     *  equals
     * @param obj Vector value
     * @return A boolean value
     * MEANING
     *  Function that checks if two Vectors are equal, if so it returns true
     **************************************************/
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;

        if(_head._x.equals(((Vector)obj)._head._x) && _head._y.equals(((Vector)obj)._head._y) && _head._z.equals(((Vector)obj)._head._z))
            return true;
        return false;
    }

    // ***************** Operations ******************** //
    /*************************************************
     * FUNCTION
     *  add
     * @param vector Vector value
     * @return A Vector value
     * MEANING
     *  This functions adds a Vector to the Vector and creates a new Vector with the result
     **************************************************/
    public Vector add (Vector vector ){
        Vector toReturn = new Vector();
        toReturn._head._x = _head._x.add(vector.getHead()._x);
        toReturn._head._y = _head._y.add(vector.getHead()._y);
        toReturn._head._z = _head._z.add(vector.getHead()._z);
        return toReturn;
    }
    /*************************************************
     * FUNCTION
     *  subtract
     * @param vector Vector value
     * @return A Vector value
     * MEANING
     *  This functions subtracts a Vector to the Vector and creates a new Vector with the result
     **************************************************/
    public Vector subtract (Vector vector){
        Vector toReturn = new Vector();
        toReturn._head._x = _head._x.subtract(vector.getHead()._x);
        toReturn._head._y = _head._y.subtract(vector.getHead()._y);
        toReturn._head._z = _head._z.subtract(vector.getHead()._z);
        return toReturn;
    }
    /*************************************************
     * FUNCTION
     *  scale
     * @param scalingFactor double value
     * @return A Vector value
     * MEANING
     *  This functions scales a double to the Vector and creates a new Vector with the result
     **************************************************/
    public Vector scale(double scalingFactor){
        Vector toReturn = new Vector();
        Coordinate scale = new Coordinate(scalingFactor);
        toReturn._head._x = _head._x.multiply(scale);
        toReturn._head._y = _head._y.multiply(scale);
        toReturn._head._z = _head._z.multiply(scale);
        return toReturn;
    }
    /*************************************************
     * FUNCTION
     *  crossProduct
     * @param vector to cross product
     * @return A Vector value
     * MEANING
     *  This functions does crossProduct to the Vector and the parameter Vector and creates a new Vector with the result
     **************************************************/
    public Vector crossProduct(Vector vector){
        Vector toReturn = new Vector();
        toReturn._head._x = _head._y.multiply(vector._head._z).subtract(
                _head._z.multiply(vector._head._y));
        toReturn._head._y = _head._z.multiply(vector._head._x).subtract(
                _head._x.multiply(vector._head._z));
        toReturn._head._z = _head._x.multiply(vector._head._y).subtract(
                _head._y.multiply(vector._head._x));
        return toReturn;
    }
    /*************************************************
     * FUNCTION
     *  length
     *
     * @return A double value
     * MEANING
     *  This functions calculates the length of the vector
     **************************************************/
    public double length(){
        DecimalFormat df = new DecimalFormat("#.##");
        double val = Math.sqrt(Math.pow((this._head._x)._coordinate,2) + Math.pow((this._head._y)._coordinate,2) +
                Math.pow((this._head._z)._coordinate,2));
        return Double.parseDouble(df.format(val));
    }
    /*************************************************
     * FUNCTION
     *  normalize
     * MEANING
     *  This functions normalizes the vector so that its size will be 1
     **************************************************/
    public void normalize() { // Throws exception if length = 0
        if (this.length() == 0)
            throw new ArithmeticException("Cannot normalize a vector of length 0!");
        else
            this._head.divide(this.length());
    }
    /*************************************************
     * FUNCTION
     *  dotProduct
     * @param vector Vector value
     * @return A double value
     * MEANING
     *  This functions does dotProduct to the Vector and the parameter Vector and returns the double value as a result
     **************************************************/
    public double dotProduct(Vector vector){
        double result = _head._x.multiply(vector._head._x).add(
                        _head._y.multiply(vector._head._y)).add(
                        _head._z.multiply(vector._head._z)).getCoordinate();
        return result;
    }

/*
    public void headIsSubtractPoints(Point3D pt1, Point3D pt2) {

        if (pt1.equals(pt2))
            throw new IllegalArgumentException("pt1 == pt2!");
        this.setHead(new Point3D(
                pt2.getX().subtract(pt1.getX()),
                pt2.getY().subtract(pt1.getY()),
                pt2.getZ().subtract(pt1.getZ())));
    }
*/
}
