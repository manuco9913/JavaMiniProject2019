package primitives;

import static primitives.Util.*;

public final class Coordinate
{
    protected double _coordinate;

    public static Coordinate ZERO = new Coordinate(0.0);

    /********** Constructors ***********/
    public Coordinate(double coord) {
        // if it's too close to zero make it zero
        _coordinate = alignZero(coord);
    }

    public Coordinate(Coordinate other) {
        _coordinate = other._coordinate;
    }

    /************** Getters/Setters *******/
    public double get() {
        return _coordinate;
    }

    /*************** Admin *****************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Coordinate)) return false;
        return usubtract(_coordinate, ((Coordinate)obj)._coordinate) == 0.0;
    }

    @Override
    public String toString() {
        return "" + _coordinate;
    }

    /************** Operations ***************/
    public Coordinate subtract(Coordinate other) {
        return new Coordinate(usubtract(_coordinate, other._coordinate));
    }

    public Coordinate add(Coordinate other) {
        return new Coordinate(uadd(_coordinate, other._coordinate));
    }

    public Coordinate scale(double num) {
        return new Coordinate(uscale(_coordinate, num));
    }

    public Coordinate multiply(Coordinate other) {
        return new Coordinate(uscale(_coordinate, other._coordinate));
    }

}
