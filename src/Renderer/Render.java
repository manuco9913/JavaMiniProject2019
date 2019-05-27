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
     * @return
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
     * @param point
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
     * @param point
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
     * @param point
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
     *  the
     **************************************************/
    private Ray constructRefractedRay(Geometry geometry, Point3D point, Ray inRay){
        Point3D P = new Point3D(point);
        Vector direction = new Vector(inRay.getDirection());
        direction = direction.scale(2);
        P = P.add(direction);
        direction = direction.scale(0.5);
        return new Ray(P,direction);
    }
    /*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param normal
     * @param point
     * @param inRay
     * @return Ray value
     * MEANING
     *  calculates the ray that's reflected from the geometry
     **************************************************/
    private Ray constructReflectedRay(Vector normal, Point3D point, Ray inRay) {
        Vector N = new Vector(normal);
        Vector ray = new Vector(inRay.getDirection());

        N.normalize();
        ray.normalize();

        if (ray.dotProduct(normal) > 0)
            N = N.scale(-1);

        Vector tempVector = new Vector(N);
        tempVector = tempVector.scale(0.0001);

        N = N.scale(2 *ray.dotProduct(N));
        ray = ray.subtract(N);
        Point3D rayPoint = new Point3D(point);
        rayPoint = rayPoint.add(tempVector);
        ray.normalize();
        return new Ray(rayPoint, ray);
    }
    /*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param geometry
     * @param point
     * @param light
     * @return color value
     * MEANING
     *  calculates the color to print in the operation writeToImage()
     **************************************************/
    private boolean occluded(LightSource light, Point3D point, Geometry geometry){
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
    }
    /*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param ks
     * @param l
     * @param normal
     * @param v
     * @param shininess
     * @param lightIntensity
     * @return color value
     * MEANING
     *  calculates the color to print in the operation writeToImage()
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
     * @param kd
     * @param normal
     * @param l
     * @param lightIntensity
     * @return color value
     * MEANING
     *  calculates the color to print in the operation writeToImage()
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