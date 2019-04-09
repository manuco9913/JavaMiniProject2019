package primitives;

public class Ray implements Comparable<Ray>
{
    // Point of origin: first point
    private Point3D _POO;
    // Ray direction: second point
    private Vector _direction;

    // ***************** Constructors ********************** //
    public Ray(){
        _POO = new Point3D();
        _direction = new Vector();
    }
    public Ray(Ray ray){
        this._POO = ray._POO;
        this._direction = ray._direction;
    }
    public Ray(Point3D poo, Vector direction){
        this._POO = poo;
        _direction = direction;
    }

    // ***************** Getters/Setters ********************** //
    public void setPOO(Point3D POO) { this._POO = POO; }
    public void setDirection(Vector direction){ this._direction = direction; }
    public Vector getDirection(){ return _direction; }
    public Point3D getPOO(){ return _POO; }

    // ***************** Administration ******************** //
    /*************************************************
     * FUNCTION
     *  compareTo
     * PARAMETERS
     *  Ray ray
     * RETURN VALUE
     *  An integer value
     * MEANING
     *  Function that compares between the length two Ray objects:
     *      the function returns 0 if the Rays are equal
     *      the function returns a positive value if the first object is bigger than the second
     *      the function returns a negative value if the first object is smaller than the second
     **************************************************/
    @Override
    public int compareTo(Ray ray){//todo: compareTo function in ray its not logically right, to fix also the 'MEANING' of the func
        if(this._direction.length() > ray._direction.length() )
            return 1;
        if(this._direction.length() < ray._direction.length() )
            return -1;
        return 0; // in case they are equally big
    }
    /*************************************************
     * FUNCTION
     *  toString
     * PARAMETERS
     *  none
     * RETURN VALUE
     *  A string value that represents the object of type Ray
     * MEANING
     *  This functions is used for the convertion: Ray -> String
     **************************************************/
    @Override
    public String toString(){
        return "Origin:" + this._POO + " Direction:" + this._direction;
    }
}