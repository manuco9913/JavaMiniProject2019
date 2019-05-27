package Elements;

import Primitives.*;

import java.util.Map;
import java.util.Objects;

public class Camera {
    //Eye point of the camera
    private Point3D _P0;
    private Vector _vUp;
    private Vector _vToward;
    //Should be calculated as the cross product if vUp and vTo
    private Vector _vRight;

    // ***************** Constructors ********************** //
    public Camera() {
        _P0 = new Point3D(0, 0, 10);
        _vUp = new Vector(1, 0, 0);
        _vToward = new Vector(0, 0, -1);
        _vRight = new Vector(_vUp.crossProduct(_vToward));//(1,0,0)

        _vRight.normalize();
        _vUp.normalize();
        _vToward.normalize();
    }

    public Camera(Camera camera) {
        _P0 = camera._P0;
        _vUp = camera._vUp;
        _vToward = camera._vToward;
        _vRight = camera._vRight;
    }

    public Camera(Point3D P0, Vector vUp, Vector vTow) {
        _P0 = new Point3D(P0);
        _vUp = new Vector(vUp);
        _vToward = new Vector(vTow);
        _vRight = _vUp.crossProduct(_vToward);
    }
    //public Camera(Map<String, String> attributes);

    // ***************** Getters/Setters ********************** //
    public Vector get_vUp() {
        return _vUp;
    }
    public void set_vUp(Vector vUp) {
        _vUp = vUp;
    }
    public Vector get_vTo() {
        return _vToward;
    }
    public void set_vTo(Vector vTo) {
        _vToward = vTo;
    }
    public Point3D getP0() {
        return _P0;
    }
    public void setP0(Point3D P0) {
        _P0 = P0;
    }
    public Vector get_vRight() {
        return _vRight;
    }

    // ***************** Administration ********************** //
    @Override
    public String toString() {
        return "Vto: " + _vToward + "\n" + "Vup: " + _vUp + "\n" + "Vright:" + _vRight + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Camera)) return false;
        Camera camera = (Camera) o;
        return (_P0.compareTo(camera._P0) == 0 &&
                _vUp.compareTo(camera._vUp) == 0 &&
                _vToward.compareTo(camera._vToward) == 0 &&
                _vRight.compareTo(camera._vRight) == 0);
    }

    // ***************** Operations ******************** //

    /*************************************************
     * FUNCTION
     *  constructRayThroughPixel
     * @param Nx number of pixels on X axis
     * @param Ny number of pixels on Y axis
     * @param x pixel's position on X axis
     * @param y pixel's position on Y axis
     * @param screenDist distance of the screen from the camera
     * @param screenWidth screen width
     * @param screenHeight screen height
     * @return A Ray value
     * MEANING
     * building a Ray from p0 to the point P, which is in position (x,y) of the screen
     * P is a certain distance far from the Pc
     **************************************************/
    public Ray constructRayThroughPixel(int Nx, int Ny, double x, double y,
                                        double screenDist,
                                        double screenWidth,
                                        double screenHeight) {
        // Calculating the image center
        Vector vToward = new Vector(_vToward);
        Vector vRight = new Vector(_vRight);
        Vector vUP = new Vector(_vUp);

        vToward.normalize();
        vRight.normalize();
        vUP.normalize();

        vToward = vToward.scale(screenDist);

        //pc = point center of the screen
        Point3D Pc = getP0().add(vToward);

        // Calculating x-y ratios
        double Rx = screenWidth / Nx;
        double Ry = screenHeight / Ny;

        // Calculating P - the intersection point
        vRight = vRight.scale(((x - (Nx / 2.0)) * Rx + (Rx / 2.0)));
        vUP = vUP.scale(((y - (Ny / 2.0)) * Ry + (Ry / 2.0)));


        Point3D P = Pc.add(vRight.subtract(vUP));
//        Point3D P = (Pc.add(vRight)).add(vUP);

        // returning the constructed ray between P0 and the intersection point
        Vector vector = new Vector(_P0, P);
        vector.normalize();
        return (new Ray(P, vector));
    }
}