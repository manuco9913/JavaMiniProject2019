package Renderer;
import java.awt.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.List;

import Elements.*;
import Geometries.*;
import Primitives.*;
import Primitives.Vector;
import Scene.Scene;

public class Render {
    private Scene _scene;
    private ImageWriter _imageWriter;
    private final int RECURSION_LEVEL = 3;
    private static final double EPSILON = 1.0 ;

    // ***************** Constructors ********************** //
    public Render(ImageWriter imageWriter, Scene scene) {
        this._scene = new Scene(scene);
        this._imageWriter = new ImageWriter(imageWriter);
    }
    public Render(Render render) {
        this._scene = render._scene;
        this._imageWriter = render._imageWriter;
    }

    // ***************** Getters/Setters ******************** //
    public Scene get_scene() {
        return _scene;
    }
    public ImageWriter get_imageWriter() {
        return _imageWriter;
    }
    public void set_scene(Scene _scene) {
        this._scene = _scene;
    }
    public void set_imageWriter(ImageWriter _imageWriter) {
        this._imageWriter = _imageWriter;
    }

    // ***************** Operations ******************** //
/*************************************************
     * FUNCTION
     *  equals
     * @param obj Vector value
     * @return A boolean value
     * MEANING
     *  Function that checks if two Render objects are equal, if so it returns true
     **************************************************/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Render)) return false;
        Render render = (Render) obj;
        return _scene.equals(render._scene) &&
                _imageWriter.equals(render._imageWriter);
    }
/*************************************************
     * FUNCTION
     *  toString
     * @param
     * @return A string value that represents the object of type Point2D
     * MEANING
     *  This functions is used for the convertion: Render -> String
     **************************************************/

    @Override
    public String toString() {
        return "Render{" +
                "_scene=" + _scene +
                ", _imageWriter=" + _imageWriter +
                '}';
    }
    public void renderImage() {
//recent
        int HEIGHT = _imageWriter.getHeight(), WIDTH = _imageWriter.getWidth();

        for (int i = 0; i < _imageWriter.getHeight(); i++)
            for (int j = 0; j < _imageWriter.getWidth(); j++) {
                Ray ray = _scene.get_camera().constructRayThroughPixel(_imageWriter.getNx(),
                        _imageWriter.getNy(), j, i, _scene.get_screenDistance(),
                        _imageWriter.getWidth(), _imageWriter.getHeight());
                Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);
                if (intersectionPoints.isEmpty())
                    _imageWriter.writePixel(j, i, _scene.get_background());
                else {
                    Entry<Geometry, Point3D> closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j, i, calcColor(closestPoint.getKey(), closestPoint.getValue(), ray));
                }
            }
    }
/*************************************************
     * FUNCTION
     *  printGrid
     * @param interval the integer that indicates how many pixels to jump in order to color the grid
     * MEANING
     *  The function prints a white pixel at every "interval"
     **************************************************/

    public void printGrid(int interval) {
        for (int i = 0; i < _imageWriter.getHeight(); i++) {
            for (int j = 0; j < _imageWriter.getWidth(); j++) {
                if (i % interval == 0 || j % interval == 0)
                    _imageWriter.writePixel(j, i, 255, 255, 255); // White
            }
        }
    }
/*************************************************
     * FUNCTION
     *  writeToImage
     * @param
     * @return
     * MEANING
     *  starts the function writeToImage of the _imageWriter
     **************************************************/

    public void writeToImage() {
        _imageWriter.writeToimage();
    }
/*************************************************
     * FUNCTION
     *  calcColor
     * @param geometry the geometry to get its emission light
     * @param point P
     * @return color value
     * MEANING
     *  calculates the color to print in the operation writeToImage()
     **************************************************/

    private Color calcColor(Geometry geometry, Point3D point) {
        Color ambientLight = _scene.get_ambientLight().getIntensity();
        Color emissionLight = geometry.getEmmission();

        int red = Math.max(0, Math.min(255, ambientLight.getRed() + emissionLight.getRed()));
        int green = Math.max(0, Math.min(255, ambientLight.getGreen() + emissionLight.getGreen()));
        int blue = Math.max(0, Math.min(255, ambientLight.getBlue() + emissionLight.getBlue()));
        return new Color(red, green, blue);
    }
/*************************************************
     * FUNCTION
     *  calcColor
     * @param geometry the geometry to get its emission light
     * @param point P
     * @return color value
     * MEANING
     *  calculates the color to print in the operation writeToImage(), calls the recursive function at level 0
     **************************************************/

    private Color calcColor(Geometry geometry, Point3D point, Ray ray) {
        return calcColor(geometry, point, ray, 0);
    }
/*************************************************
     * FUNCTION
     *  calcColor
     * @param geometry the geometry to get its emission light
     * @param point P
     * @return color value
     * MEANING
     *  calculates the color to print in the operation writeToImage() RECURSIVELY
     **************************************************/

    private Color calcColor(Geometry geometry, Point3D point, Ray inRay, int level) {
//fig///////////
        if (level == RECURSION_LEVEL) return new Color(0, 0, 0);

        int r, g, b;
        Color am = _scene.get_ambientLight().getIntensity();

        //int r,g,b;
        //r=(int)(am.getRed()*_scene.getAmbientLight().getKa());
        //g=(int) (am.getGreen()*_scene.getAmbientLight().getKa());
        //b=(int) (am.getBlue()*_scene.getAmbientLight().getKa());
        //am = Tools.giveColor(r, g, b);

        Color geoEm = geometry.getEmmission();
        Iterator<LightSource> lightIt = _scene.getLightsIterator();
        Color difLight = new Color(0, 0, 0);
        Color speLight = new Color(0, 0, 0);

        while (lightIt.hasNext()) {
            LightSource light = lightIt.next();
            if (!occluded(light, point, geometry)) {
                difLight = addColors(difLight, calcDiffusiveComp(geometry.getMaterial().getKd(), geometry.getNormal(point), light.getL(point), light.getIntensity(point)));

                speLight = addColors(speLight, calcSpecularComp(geometry.getMaterial().getKs(), new Vector(point, _scene.get_camera().getP0()),
                        geometry.getNormal(point), light.getL(point), geometry.getMaterial().getShininess(), light.getIntensity(point)));
            }
        }


        Color reflectedLight = new Color(0, 0, 0);
        Color refractedLight = new Color(0, 0, 0);

        Ray reflectedRay = constructReflectedRay(geometry.getNormal(point), point, inRay);
        Entry<Geometry, Point3D> reflectedEntry = findClosesntIntersection(reflectedRay);

        if (reflectedEntry != null) {
            Color reflectedColor = calcColor(reflectedEntry.getKey(), reflectedEntry.getValue(), reflectedRay, level + 1);

            double kr = geometry.getMaterial().getKr();

            r = (int) (kr * reflectedColor.getRed());
            g = (int) (kr * reflectedColor.getGreen());
            b = (int) (kr * reflectedColor.getBlue());

            reflectedLight = new Color(r > 255 ? 255 : (r < 0 ? 0 : r), g > 255 ? 255 : (g < 0 ? 0 : g), b > 255 ? 255 : (b < 0 ? 0 : b));
        }

        Ray refractedRay = constructRefractedRay(geometry, point, inRay);
        Entry<Geometry, Point3D> refractedEntry = findClosesntIntersection(refractedRay);
        if (refractedEntry != null) {
            Color refractedColor = calcColor(refractedEntry.getKey(), refractedEntry.getValue(), refractedRay, level + 1);
            double kt = geometry.getMaterial().getKt();
            r = (int) (kt * refractedColor.getRed());
            g = (int) (kt * refractedColor.getGreen());
            b = (int) (kt * refractedColor.getBlue());

            refractedLight = new Color(r > 255 ? 255 : (r < 0 ? 0 : r), g > 255 ? 255 : (g < 0 ? 0 : g), b > 255 ? 255 : (b < 0 ? 0 : b));
        }


        return addColors(addColors(addColors(am, geoEm), addColors(difLight, speLight)), addColors(reflectedLight, refractedLight));
    }
/*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param geometry we are working on
     * @param point specific point on the image
     * @param inRay the ray
     * @return Ray value
     * MEANING
     *  the function constructs the refraction ray by the formula
     **************************************************/

    private Ray constructRefractedRay(Geometry geometry, Point3D point, Ray inRay){
        // avi
//        return new Ray(point.add(geometry.getNormal(point).scale(-EPSILON)), inRay.getDirection());
//        mine
        Point3D P = new Point3D(point);
        Vector direction = new Vector(inRay.getDirection());
        direction = direction.scale(2);
        P = P.add(direction);
        direction = direction.scale(0.5);
        return new Ray(P,direction);
        // nerya epsilon
//        Point3D rayPoint = new Point3D(point);
//        Vector epsVector = inRay.getDirection();
//        epsVector.normalize();
//        epsVector = epsVector.scale(2);
//        rayPoint = rayPoint.add(epsVector);
//        inRay.getDirection().normalize();
//        return new Ray(rayPoint, inRay.getDirection());

    }

/*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param normal N
     * @param point RayP0
     * @param inRay Ray direction
     * @return Ray value
     * MEANING
     *  calculates the ray that's reflected from the geometry by the formula
     **************************************************/
    private Ray constructReflectedRay(Vector normal, Point3D point, Ray inRay) {
//sad
//Vector n = new Vector(normal);
//        Vector ray = new Vector(inRay.getDirection());
//
//        try
//        {
//            n.normalize();
//            ray.normalize();
//        }
//        catch (Exception e) { }
//
//        double dot = ray.dotProduct(normal);
//
//        if(dot>0)
//            n = n.scale(-1);
//
//        Vector epsVector = new Vector(n);
//        epsVector = epsVector.scale(0.0001);
//
//        n = n.scale(2 * ray.dotProduct(n));
//        ray = ray.subtract(n);
//        Point3D p = new Point3D(point);
//        p = p.add(epsVector);
//        try
//        {
//            ray.normalize();
//        }
//        catch (Exception e) { }
//        return new Ray(p, ray);

        //        mine
        Vector N = new Vector(normal);
        Vector ray = new Vector(inRay.getDirection());

        N.normalize();
        ray.normalize();

        if (ray.dotProduct(normal) > 0)
            N = N.scale(-1);

        Vector tempVector = new Vector(N);
        tempVector = tempVector.scale(2);

        N = N.scale(2 *ray.dotProduct(N));
        ray = ray.subtract(N);
        Point3D rayPoint = new Point3D(point);
        rayPoint = rayPoint.add(tempVector);
        ray.normalize();
        return new Ray(rayPoint, ray);
        //nerya
/*inRay.getDirection().normalize();
        Vector D = inRay.getDirection();
        normal.normalize();
        normal = normal.scale(-2 * D.dotProduct(normal));
        D = D.add(normal);

//        double D_dot_N = D.dotProduct(N);

//        if(D_dot_N < 0){
//            N = N.scale(-1);
//            D_dot_N = uscale(-1,D_dot_N);
//        }

        // creating the R vector
        Vector R = new Vector(D);
        R.normalize();
        Point3D point3D = point.add(normal);
//        N = N.scale(uscale(2,D_dot_N));
//        R = R.subtract(N);
        return new Ray(point3D, R);*/

    }
/*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param geometry we are working on
     * @param point P
     * @param light source of light
     * @return color value
     * MEANING
     *  checks if between the light and the object there's another object
     **************************************************/

    private boolean occluded(LightSource light, Point3D point, Geometry geometry){
        //      mine
        Vector lightDirection = light.getL(point);
        lightDirection = lightDirection.scale(-1);
        Point3D geometryPoint = new Point3D(point);
        Vector tempVector = new Vector(geometry.getNormal(point));
        tempVector = tempVector.scale(2);
        geometryPoint = geometryPoint.add(tempVector);

        Ray lightRay = new Ray(geometryPoint, lightDirection);
        Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(lightRay);

        // Flat geometry cannot self intersect
        if (geometry instanceof FlatGeometry){
            intersectionPoints.remove(geometry);
        }
        for (Entry<Geometry, List<Point3D>> entry: intersectionPoints.entrySet())
            if (entry.getKey().getMaterial().getKt() == 0)
                return true;
        return false;
  //nerya
//        Vector lightDirection = light.getL(point);
//        lightDirection.normalize();
//        lightDirection = lightDirection.scale(-1);
//
//        Vector N = new Vector(geometry.getNormal(point));
//        Vector epsVector = N.scale(N.dotProduct(lightDirection) > 0 ? 1.0 : -1.0);
//        epsVector = epsVector.scale(2);
//        Point3D geometryPoint = new Point3D(point).add(epsVector);
//        Ray lightRay = new Ray(geometryPoint, lightDirection);
//        Map<Geometry, List<Point3D>> intersections = getSceneRayIntersections(lightRay);
////        // Flat geometry cannot self intersect
////        if (geometry instanceof FlatGeometry){
////            intersections.remove(geometry);
////        }
//        return !intersections.isEmpty();

    }
/*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param ks attenuation factor
     * @param l l
     * @param normal N
     * @param v vector
     * @param shininess of the geometry
     * @param lightIntensity of the specific light
     * @return color value
     * MEANING
     *  calculates the specular light of the specific light
     **************************************************/

    private Color calcSpecularComp(double ks, Vector v, Vector normal, Vector l, double shininess, Color lightIntensity) {

        v.normalize();
        normal.normalize();
        l.normalize();

        if (l.dotProduct(normal) > 0)
            normal = normal.scale(-1);

        normal = normal.scale(2 * l.dotProduct(normal));
        l = l.subtract(normal);
        double factor = ks * Math.pow(v.dotProduct(l), shininess);
        double r = lightIntensity.getRed() * factor;
        double g = lightIntensity.getGreen() * factor;
        double b = lightIntensity.getBlue() * factor;
        return new Color(r > 255 ? 255 : (r < 0 ? 0 : (int) r), g > 255 ? 255 : (g < 0 ? 0 : (int) g), b > 255 ? 255 : (b < 0 ? 0 : (int) b));
        //return new Color((int) (r * factor), (int) (g * factor), (int) (b * factor));
        //remember maybe its the right one... return Tools.giveColor(r*factor, g*factor, b*factor);
    }
/*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param kd attenuation factor
     * @param normal N
     * @param l L
     * @param lightIntensity if the specific light
     * @return color value
     * MEANING
     *  calculates the diffusion of the light on the touched area
     **************************************************/

    private Color calcDiffusiveComp(double kd, Vector normal, Vector l, Color lightIntensity) {

        normal.normalize();
        l.normalize();

        if (l.dotProduct(normal) > 0)
            normal = normal.scale(-1);

        double intensity = kd * (normal.dotProduct(l));
        intensity = Math.abs(intensity);
        double r = lightIntensity.getRed()* intensity;
        double g = lightIntensity.getGreen()* intensity;
        double b = lightIntensity.getBlue()* intensity;
        return new Color(r > 255 ? 255 : (r < 0 ? 0 : (int) r), g > 255 ? 255 : (g < 0 ? 0 : (int) g), b > 255 ? 255 : (b < 0 ? 0 : (int) b));
        //return new Color((int) (r * intensity), (int) (g * intensity), (int) (b * intensity));
        //remember maybe its the right one... return Tools.giveColor(intensity*r, intensity*g, intensity*b);
    }
/*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param intersectionPoints list
     * @return Entry<Geometry, Point3D> value
     * MEANING
     *  returns the closest point between the intersection points
     **************************************************/

    private Entry<Geometry, Point3D> getClosestPoint(Map<Geometry, List<Point3D>> intersectionPoints) {

        double distance = Double.MAX_VALUE;
        Point3D P0 = _scene.get_camera().getP0();
        Map<Geometry, Point3D> minDistancePoint = new HashMap<>();

        for (Entry<Geometry, List<Point3D>> entry:intersectionPoints.entrySet())
            for (Point3D point: entry.getValue())
                if(P0.distance(point) < distance)
                {
                    minDistancePoint.clear();
                    minDistancePoint.put(entry.getKey(), new Point3D(point));
                    distance = P0.distance(point);
                }
        return minDistancePoint.entrySet().iterator().next();
    }
/*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param ray Ray
     * @return Entry<Geometry, Point3D> value
     * MEANING
     *  finding the first intersection that this ray meets in the scene
     **************************************************/

    private Entry<Geometry, Point3D> findClosesntIntersection(Ray ray){
        Map<Geometry, List<Point3D>> intersections = getSceneRayIntersections(ray);
        double distance = Double.MAX_VALUE;
        Point3D P0 = new Point3D(ray.getPOO());
        Map<Geometry, Point3D> closestIntersection = new HashMap<>();

        for (Entry<Geometry, List<Point3D>> entry : intersections.entrySet()) {
            for (Point3D point : entry.getValue()) {
                if (P0.distance(point) < distance) {
                    closestIntersection.clear();
                    closestIntersection.put(entry.getKey(), point);
                    distance = P0.distance(point);
                }
            }
        }

        if (closestIntersection.isEmpty())
            return null;
        return closestIntersection.entrySet().iterator().next();
    }
/*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param ray Ray
     * @return Map<Geometry, List<Point3D>> value
     * MEANING
     *  gets all the intersection points with the elements in the scene
     **************************************************/

    private Map<Geometry, List<Point3D>> getSceneRayIntersections(Ray ray) {

        Iterator<Geometry> geometries = _scene.getGeometriesIterator();
        Map<Geometry, List<Point3D>> intersectionPoints = new HashMap<>();

        while(geometries.hasNext())
        {
            Geometry geometry = geometries.next();
            ArrayList<Point3D> geometryIntersectionPoints = (ArrayList<Point3D>) geometry.FindIntersections(ray);
            if(!(geometryIntersectionPoints.isEmpty()))
                intersectionPoints.put(geometry, geometryIntersectionPoints);
        }
        return intersectionPoints;
    }
/*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param a first color
     * @param b second color
     * @return color value
     * MEANING
     *  calculates the color to print in the operation writeToImage()
     **************************************************/

    private Color addColors(Color a, Color b) {

        int R = a.getRed() + b.getRed();
        if (R > 255) R = 255;

        int G = a.getGreen() + b.getGreen();
        if (G > 255) G = 255;

        int B = a.getBlue() + b.getBlue();
        if (B > 255) B = 255;

        return new Color(R, G, B);

    }
}

/*
package Renderer;

import Elements.*;
import Primitives.*;
import Primitives.Vector;
import Scene.Scene;
import Geometries.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.pow;
import static Primitives.Util.*;

public class Render {

    final int RECURSION_LEVEL = 2;
    private Scene _scene; // the scene that we wont to render
    private ImageWriter _imageWriter; // make the scene to image

    */
/************* Constructors ******************//*


    //CTOR
    public Render(ImageWriter _imageWriter, Scene _scene) {
        // checking if the given parameters are not null
        setScene(_scene != null ? _scene : new Scene());
        setImageWriter(_imageWriter != null ? _imageWriter : new ImageWriter("Default Image", 500, 500, 500, 500));
    }

    //default CTOR
    public Render() {
        setScene(new Scene());
        setImageWriter(new ImageWriter("Default Image", 500, 500, 500, 500));
    }

    // copy CTOR
    public Render(Render r) {
        if (r != null) {
            setScene(r.getScene());
            setImageWriter(r.getImageWriter());
        }
    }

    */
/************* Getters/Setters ******************//*


    public Scene getScene() { return new Scene(_scene); }

    public void setScene(Scene _scene) {
        if (_scene != null) {
            this._scene = new Scene(_scene);
        }
    }

    public ImageWriter getImageWriter() { return _imageWriter; }

    public void setImageWriter(ImageWriter _imageWriter) {
        if (_imageWriter != null) {
            this._imageWriter = _imageWriter;
        }
    }

    */
/************* Operations ****************//*


    */
/*************************************************
     * FUNCTION
     * renderImage
     * PARAMETERS
     * no parameters
     * RETURN VALUE
     * no return value
     *
     * MEANING
     * make a image from all rey intersections that we find and
     * calculate the closest ona to calc the color according this element.
     **************************************************//*

    public void renderImage() {

        // running on all the pixels on the view plane
        for (int i = 0; i < getImageWriter().getWidth(); i++) {

            for (int j = 0; j < getImageWriter().getHeight(); j++) {

                // creating a ray to the current pixel
                Ray r = getScene().get_camera().constructRayThroughPixel
                        (getImageWriter().getWidth(), getImageWriter().getHeight(), i, j,
                                getScene().get_screenDistance(), getImageWriter().getNx(),
                                getImageWriter().getNy());

                // getting all the intersection points through the current pixel
                Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(r);

                // if there is not an intersection point through the current pixel
                if (intersectionPoints.isEmpty()) {
                    _imageWriter.writePixel(j, i, getScene().get_background());
                }
                // otherwise
                else {
                    // getting the closest point which the ray interacts
                    Map<Geometry, Point3D> closestPoint = getClosestPoint(intersectionPoints, getScene().get_camera().getP0());
                    Map.Entry<Geometry, Point3D> entry = null;
                    for (Map.Entry<Geometry, Point3D> zug : closestPoint.entrySet()) {
                        entry = zug;
                    }

                    _imageWriter.writePixel(j, i, calcColor(entry.getKey(), entry.getValue(), r));
                }
            }
        }
    }

    */
/*************************************************
     * FUNCTION
     * getSceneRayIntersections
     * PARAMETERS
     * ray
     * RETURN VALUE
     * map with all the intersection points
     *
     * MEANING
     * getting a ray and returning all the intersection points
     * of the ray with all the geometries in the scene.
     **************************************************//*

    private Map<Geometry, List<Point3D>> getSceneRayIntersections(Ray ray) {

        Iterator<Geometry> geometries = _scene.getGeometriesIterator();
        Map<Geometry, List<Point3D>> intersectionPoints = new HashMap<Geometry, List<Point3D>>();

        while (geometries.hasNext()) {
            Geometry geometry = geometries.next();
            List<Point3D> geometryIntersectionPoints = geometry.FindIntersections(ray);
            //add geometryIntersectionPoints to intersectionPoints
            if (!geometryIntersectionPoints.isEmpty()) {
                intersectionPoints.put(geometry, geometryIntersectionPoints);
            }
        }
        return intersectionPoints;
    }

    */
/*************************************************
     * FUNCTION
     * getClosestPoint
     * PARAMETERS
     * Map<Geometry, List<Point3D>>
     * Point3D
     * RETURN VALUE
     * map with the closest intersection point
     *
     * MEANING
     * getting the closest point to the camera.
     **************************************************//*

    private Map<Geometry, Point3D> getClosestPoint(Map<Geometry, List<Point3D>> intersectionPoints, Point3D P0) {

        double distance = Double.MAX_VALUE;
        Map<Geometry, Point3D> minDistancePoint = new HashMap<Geometry, Point3D>();

        for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet()) {
            for (Point3D point : entry.getValue()) {
                if (P0.distance(point) < distance) {
                    minDistancePoint.clear();
                    minDistancePoint.put(entry.getKey(), new Point3D(point));
                    distance = P0.distance(point);
                }
            }
        }
        return minDistancePoint;
    }

    */
/*************************************************
     * FUNCTION
     * calcColor
     * PARAMETERS
     * geometry
     * Point3D
     * Ray
     * RETURN VALUE
     * the pixel color
     *
     * MEANING
     * calculating the color at the current point.
     **************************************************//*

    // calculating the color at the current point
    private Color calcColor(Geometry geometry, Point3D point, Ray inRay){
        return calcColor(geometry, point, inRay, 0);
    }

    // calculating the color at the current point - recursive
    private Color calcColor(Geometry geometry, Point3D point, Ray inRay, int level) {

        if (level == RECURSION_LEVEL) {
            return Color.BLACK;
        }

        // getting the normal through the point
        Vector N = geometry.getNormal(point);

        // checking if the point was on the geometry
        if (N == null) {
            return getScene().get_background();
        }

        // calculating the ambient and the emission light
        Color ambientLight = getScene().get_ambientLight().getIntensity(point);
        Color emissionLight = geometry.getEmmission();

        Color l0 = addColor(ambientLight, emissionLight);

        // running over all the lights
        Iterator<LightSource> lights = _scene.getLightsIterator();
        while (lights.hasNext()) {

            // checking if we have reached the white color
            if (l0.getRGB() == Color.WHITE.getRGB()) {
                return l0;
            }

            // promoting the iterator
            LightSource lightSource = lights.next();

            if (!occluded(lightSource, point, geometry)) {
                Vector L = lightSource.getL(point);
                point.vector(getScene().get_camera().getP0()).normalize();
                Vector V = point.vector(getScene().get_camera().getP0())
                if (L.dotProduct(N) * V.dotProduct(N) > 0) {
                    Color lightIntensity = lightSource.getIntensity(point);

                    // calculating the diffuse light
                    Color diffuseLight = calcDiffusiveComp(geometry.getMaterial().getKd(), N, L, lightIntensity);

                    V = V.scale(-1);

                    // calculating the specular light
                    Color specularLight = calcSpecularComp(geometry.getMaterial().getKs(), V, N, L,
                            geometry.getMaterial().getN(), lightIntensity);

                    // l0 += diffuseLight + specularLight
                    l0 = addColor(l0, diffuseLight, specularLight);
                }
            }

        }


        // Recursive call for a reflected ray
        Ray reflectedRay = constructReflectedRay(geometry.getNormal(point), point, inRay);

        // getting the closest intersection point to the reflected ray
        Map.Entry<Geometry,Point3D> reflectedEntry = null;
        Map<Geometry,Point3D> reflected_closestPoint = getClosestPoint(getSceneRayIntersections(reflectedRay),reflectedRay.getPOO());

        Color reflectedColor = Color.BLACK;

        for(Map.Entry<Geometry,Point3D> zug : reflected_closestPoint.entrySet()){
            reflectedEntry = zug;
        }

        if(reflectedEntry != null){
            reflectedColor = calcColor(reflectedEntry.getKey(), reflectedEntry.getValue(), reflectedRay,level+1);
        }

        double kr = geometry.getMaterial().getKr();
        Color reflectedLight = multColor(reflectedColor,kr);

        // Recursive call for a refracted ray
        Ray refractedRay = constructRefractedRay(point, inRay);

        // getting the closest intersection point to the refracted ray
        Map.Entry<Geometry,Point3D> refractedEntry = null;
        Map<Geometry,Point3D> refracted_closestPoint = getClosestPoint(getSceneRayIntersections(refractedRay),refractedRay.getPOO());

        Color refractedColor = Color.black;


        for(Map.Entry<Geometry,Point3D> zug : refracted_closestPoint.entrySet()){
            refractedEntry = zug;
        }
        if(refractedEntry != null) {
            refractedColor = calcColor(refractedEntry.getKey(), refractedEntry.getValue(), refractedRay, level + 1);
        }

        double kt = geometry.getMaterial().getKt();
        Color refractedLight = multColor(refractedColor,kt);

        l0 = addColor(l0, reflectedLight,refractedLight);

        return l0;
    }


    private boolean occluded(LightSource light, Point3D point, Geometry geometry){
        light.getL(point).normalize();
        Vector lightDirection = light.getL(point);
        lightDirection = lightDirection.scale(-1);

        Vector N = new Vector(geometry.getNormal(point));
        Vector epsVector = N.scale(N.dotProduct(lightDirection) > 0 ? 1.0 : -1.0);
        epsVector = epsVector.scale(2);
        Point3D geometryPoint = new Point3D(point).add(epsVector);
        Ray lightRay = new Ray(geometryPoint, lightDirection);
        Map<Geometry, List<Point3D>> intersections = getSceneRayIntersections(lightRay);
//        // Flat geometry cannot self intersect
//        if (geometry instanceof FlatGeometry){
//            intersections.remove(geometry);
//        }
        return !intersections.isEmpty();
    }

    private Color calcSpecularComp(double ks, Vector v, Vector normal, Vector d, double shininess, Color lightIntensity){

        double dn = normal.dotProduct(d)*2;
        if(dn < 0){
            normal = normal.scale(-1);
            dn = uscale(dn, -1);
        }
        Vector R = new Vector(d);
        normal = normal.scale(dn);
        R = R.subtract(normal);
        R.normalize();
        double res = Math.pow(v.dotProduct(R),shininess);
        double mekadem = uscale(ks,res);

        int r = Integer.min((int)(mekadem*lightIntensity.getRed()),255);
        int g = Integer.min((int)(mekadem*lightIntensity.getGreen()),255);
        int b = Integer.min((int)(mekadem*lightIntensity.getBlue()),255);

        return new Color(Integer.max(r,0),Integer.max(g,0),Integer.max(b,0));

    }

    private Color calcDiffusiveComp(double kd, Vector normal, Vector l, Color lightIntensity){
        double KdNL = kd*Math.abs(normal.dotProduct(l));
        int r = Integer.min((int)(KdNL*lightIntensity.getRed()), 255);
        int g = Integer.min((int)(KdNL*lightIntensity.getGreen()), 255);
        int b = Integer.min((int)(KdNL*lightIntensity.getBlue()), 255);
        return new Color(Integer.max(r,0),Integer.max(g,0),Integer.max(b,0));
    }

    // printing lines on the grid
    public void printGrid(int interval) {

        if (interval > 0) {
            for (int i = 0; i < _imageWriter.getHeight(); i++) {
                for (int j = 0; j < _imageWriter.getWidth(); j++) {

                    if (i % interval == 0 || j % interval == 0)
                        _imageWriter.writePixel(j, i, 255, 255, 255);

                }
            }
        }
    }

    public void writeToImage() {
        _imageWriter.writeToimage();
    }

    // construct Reflected Ray
    private Ray constructReflectedRay(Vector N, Point3D p, Ray ray){
        ray.getDirection().normalize();
        Vector D = ray.getDirection();
        N.normalize();
        N = N.scale(-2 * D.dotProduct(N));
        D = D.add(N);

//        double D_dot_N = D.dotProduct(N);

//        if(D_dot_N < 0){
//            N = N.scale(-1);
//            D_dot_N = uscale(-1,D_dot_N);
//        }

        // creating the R vector
        D.normalize();
        Vector R = new Vector(D);
        Point3D point3D = p.add(N);
//        N = N.scale(uscale(2,D_dot_N));
//        R = R.subtract(N);
        return new Ray(point3D, R);
    }

    // construct Refracted Ray
    private Ray constructRefractedRay(Point3D p, Ray ray){

        // epsilon
        Point3D rayPoint = new Point3D(p);
        ray.getDirection().normalize();
        Vector epsVector = ray.getDirection();
        epsVector = epsVector.scale(2);
        rayPoint = rayPoint.add(epsVector);

        return new Ray(rayPoint, ray.getDirection());
    }
}



*/
