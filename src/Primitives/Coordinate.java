package Primitives;

import static Primitives.Util.*;

/*
 * Coordinate class represents a coordinate on the axis.
 * it is used to define a 3DPoint and a 2DPoint
 *
 * PARAMETER
 * a double variable
 */
public final class Coordinate implements Comparable<Coordinate>
{
    protected double _coordinate;

    public static Coordinate ZERO = new Coordinate(0.0); //Origin of the axis

    // ********* Constructors ***********/
    public Coordinate(){ _coordinate = ZERO._coordinate; }
    public Coordinate(double coord) {
        // if it's too close to zero make it zero
        _coordinate = alignZero(coord);
    }
    public Coordinate(Coordinate coord) {
        _coordinate = coord.getCoordinate();
    }

    // ************* Getters/Setters *******/
    public double getCoordinate() {
        return _coordinate;
    }
    public void setCoordinate (double coord) { _coordinate = coord; }

    // ************** Administration *****************/

    /*************************************************
     * FUNCTION
     *  equals
     * @param obj to check if is equal
     * @return A boolean value that indicates whether the objects are equal
     * MEANING
     *  In this functions: if the subtraction of the two objects equals to zero, it returns true.
     *  function used to equalize two Coordinates.
     **************************************************/
    @Override
    public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof Coordinate)) return false;
            return usubtract(_coordinate, ((Coordinate)obj)._coordinate) == 0.0;
    }

    /*************************************************
     * FUNCTION
     *  toString
     * @param
     * @return A string value that represents the object of type Coordinate
     * MEANING
     *  This functions is used for the convertion: Coordinate -> String
     **************************************************/
    @Override
    public String toString() {
        return String.format("%.2f", _coordinate);
    }

    /*************************************************
     * FUNCTION
     *  compareTo
     * @param coord Coordinate value
     * @return
     *  the function returns 0 if the Coordinates have the same size
     *  the function returns a positive value if the coordinate is smaller than 'this'
     *  the function returns a negative value if the coordinate is bigger than 'this'
     * MEANING
     *  Function that compares between two coordinates.
     **************************************************/
    @Override
    public int compareTo(Coordinate coord){
        if(this.equals(coord)) return 0;
        double subtractionResult=usubtract(this._coordinate, coord._coordinate);
        if(subtractionResult > 0) return (int)subtractionResult;
        return (int)subtractionResult;
    }

    // ************* Operations ***************/
    /*************************************************
     * FUNCTION
     *  subtract
     * @param other Coordinate value
     * @return subtracted Coordinate
     * MEANING
     *  Function subtracts two Coordinates returning a new one with the corresponding value
     *  the function uses a Util.java function for a more specific subtraction
     **************************************************/
    public Coordinate subtract(Coordinate other) {
        return new Coordinate(usubtract(_coordinate, other._coordinate));
    }

    /*************************************************
     * FUNCTION
     *  add
     * @param other Coordinate value
     * @return added Coordinate
     * MEANING
     *  Function adds two Coordinates returning a new one with the corresponding value
     *  the function uses a Util.java function for a more specific addition
     **************************************************/
    public Coordinate add(Coordinate other) {
        return new Coordinate(uadd(_coordinate, other._coordinate));
    }

    /*************************************************
     * FUNCTION
     *  scale
     * @param num double value
     * @return scaled Coordinate
     * MEANING
     *  Function scales two Coordinates returning a new one with the corresponding value
     *  the function uses a Util.java function for a more specific scaling
     **************************************************/
    public Coordinate scale(double num) {
        return new Coordinate(uscale(_coordinate, num));
    }

    /*************************************************
     * FUNCTION
     *  multiply
     * @param other Coordinate value
     * @return multiplied Coordinate
     * MEANING
     *  Function multiplies two Coordinates returning a new one with the corresponding value
     *  the function uses a Util.java function for a more specific multiplication
     **************************************************/
    public Coordinate multiply(Coordinate other) {
        return new Coordinate(uscale(_coordinate, other._coordinate));
    }
}
