package Scene;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Elements.*;
import Geometries.Geometry;
import groovy.transform.ToString;

public class Scene {
    private String _sceneName;
    private Color _background;
    private AmbientLight _ambientLight;
    private Camera _camera;
    private double _screenDistance;
    private List<Geometry> _geometries = new ArrayList<>();
    private List<LightSource> _lights = new ArrayList<>();


    // ***************** Constructors ********************** //
    public Scene(){
        _background = new Color(0,0,0);
        _screenDistance = 100.0;
        _geometries = new ArrayList<>();
        _lights = new ArrayList<>();
        _ambientLight = new AmbientLight();
        _camera = new Camera();
        _sceneName = "scene";
    }
    public Scene(Scene scene) {
        _background = scene._background;
        _screenDistance = scene._screenDistance;
        _geometries = scene._geometries;
        _lights = scene._lights;
        _ambientLight = scene._ambientLight;
        _camera = scene._camera;
        _sceneName = scene._sceneName;
    }
    public Scene(AmbientLight aLight, Color background, Camera camera, double screenDistance) {
        _background = background;
        _screenDistance = screenDistance;
        _geometries = new ArrayList<>();
        _lights = new ArrayList<>();
        _ambientLight = aLight;
        _camera = camera;
        _sceneName = "scene";
    }

    // ***************** Getters/Setters ********************** //
    public List<Geometry> get_geometries() {
        return _geometries;
    }
    public double get_screenDistance() {
        return _screenDistance;
    }
    public Camera get_camera() {
        return _camera;
    }
    public Color get_background() {
        return _background;
    }
    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }
    public String get_sceneName() {
        return _sceneName;
    }
    public void set_geometries(List<Geometry> _geometries) {
        this._geometries = _geometries;
    }
    public void set_screenDistance(double _screenDistance) {
        this._screenDistance = _screenDistance;
    }
    public void set_camera(Camera _camera) {
        this._camera = _camera;
    }
    public void set_background(Color _background) {
        this._background = _background;
    }
    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }
    public void set_sceneName(String _sceneName) {
        this._sceneName = _sceneName;
    }

    // ***************** Operations ******************** //
    public void addGeometry(Geometry geometry){
        _geometries.add(geometry);
    }
    public void addLight(LightSource light){
        _lights.add(light);
    }
    public Iterator<Geometry> getGeometriesIterator(){
        return _geometries.iterator();
    }
    public Iterator<LightSource> getLightsIterator(){return _lights.iterator();}

    /*************************************************
     * FUNCTION
     *  toString
     * @param
     * @return A string value that represents the object of type Point2D
     * MEANING
     *  This functions is used for the convertion: Scene -> String
     **************************************************/
    @Override
    public String toString() {
        return "Scene{" +
                "_sceneName='" + _sceneName + '\'' +
                ", _background=" + _background +
                ", _ambientLight=" + _ambientLight +
                ", _camera=" + _camera +
                ", _screenDistance=" + _screenDistance +
                ", _geometries=" + _geometries +
                '}';
    }
    /*************************************************
     * FUNCTION
     *  equals
     * @param obj Vector value
     * @return A boolean value
     * MEANING
     *  Function that checks if two Scene objects are equal, if so it returns true
     **************************************************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Scene)) return false;
        Scene scene = (Scene) obj;
        return (_sceneName.compareTo(((Scene) obj)._sceneName)==0 &&
                _background.equals(((Scene) obj)._background) &&
                _ambientLight.equals(((Scene) obj)._ambientLight) &&
                _camera.equals(((Scene) obj)._camera) &&
                _screenDistance == ((Scene) obj)._screenDistance &&
                _geometries.equals(((Scene) obj)._geometries)
                );
    }

}