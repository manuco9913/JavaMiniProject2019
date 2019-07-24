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
    private boolean moreRefractedON = false;
    private boolean moreReflectedON = false;

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
    public void setMoreRefractedON(boolean moreRefractedON) {
        this.moreRefractedON = moreRefractedON;
    }
    public void setMoreReflectedON(boolean moreReflectedON) {
        this.moreReflectedON = moreReflectedON;
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

    /*************************************************
     * FUNCTION
     *  renderImage
     * @param
     * @return void
     * MEANING
     *  This functions builds one ray for every pixel with the color calculated by calcColor
     **************************************************/
    public void renderImage() {
//recent

        for (int i = 0; i < _imageWriter.getHeight(); i++)
            for (int j = 0; j < _imageWriter.getWidth(); j++) {
                Ray ray = _scene.getCamera().constructRayThroughPixel(_imageWriter.getNx(),
                        _imageWriter.getNy(), j, i, _scene.getScreenDistance(),
                        _imageWriter.getWidth(), _imageWriter.getHeight());
                Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);
                if (intersectionPoints.isEmpty())
                    _imageWriter.writePixel(j, i, _scene.getBackground());
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

        if (level == RECURSION_LEVEL) return new Color(0, 0, 0);

        int r, g, b;
        Color am = _scene.getAmbientLight().getIntensity();

        Color geoEm = geometry.getEmmission();
        Iterator<LightSource> lightIt = _scene.getLightsIterator();
        Color difLight = new Color(0, 0, 0);
        Color speLight = new Color(0, 0, 0);

        while (lightIt.hasNext()) {
            LightSource light = lightIt.next();
            if (!occluded(light, point, geometry)) {
                difLight = addColors(difLight, calcDiffusiveComp(geometry.getMaterial().getKd(), geometry.getNormal(point), light.getL(point), light.getIntensity(point)));

                speLight = addColors(speLight, calcSpecularComp(geometry.getMaterial().getKs(), new Vector(point, _scene.getCamera().getP0()),
                        geometry.getNormal(point), light.getL(point), geometry.getMaterial().getShininess(), light.getIntensity(point)));
            }
        }


        Color reflectedLight = new Color(0, 0, 0);
        Color refractedLight = new Color(0, 0, 0);

        if(moreReflectedON) {
            // Getting a List of Rays created by constructReflectedRayList function
            List<Ray> reflectedRays = constructReflectedRayList(geometry.getNormal(point), point, inRay);
            // iterating the list, adding the color of each ray's intersections points
            for(Ray reflectedRay: reflectedRays)
            {
                Entry<Geometry, Point3D> reflectedEntry = findClosestIntersection(reflectedRay);

                if (reflectedEntry != null) {
                    Color reflectedColor = calcColor(reflectedEntry.getKey(), reflectedEntry.getValue(), reflectedRay, level + 1);
                    double kr = geometry.getMaterial().getKr();
                    kr = Math.pow(kr, level);
                    double distance = point.distance(reflectedEntry.getValue());
                    double distEffect = Math.log(Math.log(Math.log(Math.log(Math.log(distance + 1) + 1) + 1) + 1) + 1);
                    r = (int) (kr * reflectedColor.getRed() / reflectedRays.size() * distEffect);
                    g = (int) (kr * reflectedColor.getGreen() / reflectedRays.size() * distEffect);
                    b = (int) (kr * reflectedColor.getBlue() / reflectedRays.size() * distEffect);

                    Color reflectedRGB = new Color(r > 255 ? 255 : (r < 0 ? 0 : r), g > 255 ? 255 : (g < 0 ? 0 : g), b > 255 ? 255 : (b < 0 ? 0 : b));
                    reflectedLight = addColors(reflectedLight, reflectedRGB);
                }
            }
        }
        else {
            Ray reflectedRay = constructReflectedRay(geometry.getNormal(point), point, inRay);
            Entry<Geometry, Point3D> reflectedEntry = findClosestIntersection(reflectedRay);

            if (reflectedEntry != null) {
                Color reflectedColor = calcColor(reflectedEntry.getKey(), reflectedEntry.getValue(), reflectedRay, level + 1);

                double kr = geometry.getMaterial().getKr();

                r = (int) (kr * reflectedColor.getRed());
                g = (int) (kr * reflectedColor.getGreen());
                b = (int) (kr * reflectedColor.getBlue());

                reflectedLight = new Color(r > 255 ? 255 : (r < 0 ? 0 : r), g > 255 ? 255 : (g < 0 ? 0 : g), b > 255 ? 255 : (b < 0 ? 0 : b));
            }
        }

        if(moreRefractedON)
            {
                // Getting a List of Rays created by constructRefractedRayList function
                List<Ray> refractedRays = constructRefractedRayList(geometry, point, inRay);
                // iterating the list, adding the color of each ray's intersections points
                for(Ray refractedRay: refractedRays)
                {
                    Entry<Geometry, Point3D> refractedEntry = findClosestIntersection(refractedRay);

                    if (refractedEntry != null) {
                        Color refractedColor = calcColor(refractedEntry.getKey(), refractedEntry.getValue(), refractedRay, level + 1);
                        double kt = geometry.getMaterial().getKt();
                        kt = Math.pow(kt, level);
                        r = (int) (kt * refractedColor.getRed() / refractedRays.size());
                        g = (int) (kt * refractedColor.getGreen() / refractedRays.size());
                        b = (int) (kt * refractedColor.getBlue() / refractedRays.size());

                        Color refractedRGB = new Color(r > 255 ? 255 : (r < 0 ? 0 : r), g > 255 ? 255 : (g < 0 ? 0 : g), b > 255 ? 255 : (b < 0 ? 0 : b));
                        refractedLight = addColors(refractedLight, refractedRGB);
                    }
                }
            }
            else
            {
                Ray refractedRay = constructRefractedRay(geometry, point, inRay);
                Entry<Geometry, Point3D> refractedEntry = findClosestIntersection(refractedRay);
                if (refractedEntry != null) {
                    Color refractedColor = calcColor(refractedEntry.getKey(), refractedEntry.getValue(), refractedRay, level + 1);
                    double kt = geometry.getMaterial().getKt();
                    r = (int) (kt * refractedColor.getRed());
                    g = (int) (kt * refractedColor.getGreen());
                    b = (int) (kt * refractedColor.getBlue());

                    refractedLight = new Color(r > 255 ? 255 : (r < 0 ? 0 : r), g > 255 ? 255 : (g < 0 ? 0 : g), b > 255 ? 255 : (b < 0 ? 0 : b));
                }
            }

        return addColors(addColors(addColors(am, geoEm), addColors(difLight, speLight)), addColors(reflectedLight, refractedLight));
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
//recent
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
    }


    private List<Ray> constructReflectedRayList(Vector normal, Point3D point, Ray inRay)
    {

        List<Ray> raysToReturn = new ArrayList<>();

        Vector v = inRay.getDirection();
        v.normalize();

        double angle = v.dotProduct(normal);
        Vector eps = normal.scale(-2 * angle);
        v = v.add(eps);

        Vector R = new Vector(v);
        R.normalize();
        Point3D p = point.add(R.scale(2));


        Ray centralRay =  new Ray(p, R);
//        List<Ray> raysToReturn = new ArrayList<>();
//
//        Vector N = new Vector(normal);
//        Vector ray = new Vector(inRay.getDirection());
//
//        N.normalize();
//        ray.normalize();
//
//        if (ray.dotProduct(normal) > 0)
//            N = N.scale(-1);
//
//        Vector tempVector = new Vector(N);
//        tempVector = tempVector.scale(2);
//
//        N = N.scale(2 *ray.dotProduct(N));
//        ray = ray.subtract(N);
//        Point3D p = new Point3D(point);
//        p = p.add(tempVector);
//        ray.normalize();
//        Ray centralRay = new Ray(p, ray);

//        Ray centralRay = constructReflectedRay(normal, point, inRay);
//        Point3D p = new Point3D(centralRay.getPOO());/////////////////////////???????????????????????????
        raysToReturn.add(centralRay);
        raysToReturn.add(new Ray(p, inRay.getDirection().add(new Vector(RandomNum(), RandomNum(), 0))));
        raysToReturn.add(new Ray(p, inRay.getDirection().add(new Vector(RandomNum(), RandomNum(), 0))));
        raysToReturn.add(new Ray(p, inRay.getDirection().add(new Vector(RandomNum(), RandomNum(), 0))));
        raysToReturn.add(new Ray(p, inRay.getDirection().add(new Vector(RandomNum(), RandomNum(), 0))));
        return raysToReturn;
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
        //recent
        Point3D P = new Point3D(point);
        Vector direction = new Vector(inRay.getDirection());
        direction = direction.scale(2);
        P = P.add(direction);
        direction = direction.scale(0.5);
        return new Ray(P,direction);
    }

    /*************************************************
     * FUNCTION
     *  constructRefractedRayList
     * @param geometry we are working on
     * @param point specific point on the image
     * @param inRay the ray
     * @return List<Ray> value
     * MEANING
     *  the function constructs the refraction ray by the formula,
     *  and builds 4 other Rays with a little movement on the sides
     **************************************************/
    private List<Ray> constructRefractedRayList(Geometry geometry, Point3D point, Ray inRay) {

        List<Ray> raysToReturn = new ArrayList<>();
        Vector eps = inRay.getDirection();
        Point3D p = point.add(eps.scale(2));
        Ray centralRay = new Ray(p, inRay.getDirection());
        raysToReturn.add(centralRay);
        raysToReturn.add(new Ray(p, inRay.getDirection().add(new Vector(RandomNum(), RandomNum(), 0 /*RandomNum()*/))));
        raysToReturn.add(new Ray(p, inRay.getDirection().add(new Vector(RandomNum(), RandomNum(), 0/*RandomNum()*/))));
        raysToReturn.add(new Ray(p, inRay.getDirection().add(new Vector(RandomNum(), RandomNum(), 0/*RandomNum()*/))));
        raysToReturn.add(new Ray(p, inRay.getDirection().add(new Vector(RandomNum(), RandomNum(), 0/*RandomNum()*/))));
        return raysToReturn;
    }

    private double RandomNum(){
        Random random = new Random();
        return (random.nextDouble()*(0.05*2))-0.05;
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
    }

    /*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param ks attenuation factor
     * @param l l
     * @param normal N
     * @param v vector
     * @param shininess of the geometry
    //     * @param lightIntensity of the specific light
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
    }

    /*************************************************
     * FUNCTION
     *  constructRefractedRay
     * @param kd attenuation factor
     * @param normal N
     * @param l L
//     * @param lightIntensity if the specific light
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
        Point3D P0 = _scene.getCamera().getP0();
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
    private Entry<Geometry, Point3D> findClosestIntersection(Ray ray){
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
