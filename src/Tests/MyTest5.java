package Tests;

import Elements.Camera;
import Elements.DirectionalLight;
import Elements.PointLight;
import Elements.SpotLight;
import Geometries.Plane;
import Geometries.Quadrangle;
import Geometries.Sphere;
import Geometries.Triangle;
import Primitives.Point3D;
import Primitives.Vector;
import Renderer.ImageWriter;
import Renderer.Render;
import Scene.Scene;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class MyTest5 {
    @Test
    void MyTest5() {

        Scene scene = new Scene();
        scene.get_camera().setP0(new Point3D(0,0,1000));
//        scene.set_camera(new Camera(new Point3D(1000,0,1000),new Vector(0,0,-1),
//                new Vector(-1,0,0)));

        Sphere sphere = new Sphere(100,new Point3D(-100,95,-15));
        scene.addGeometry(sphere);


        Plane plane = new Plane(new Vector(10000,0,3000),new Point3D(-1000,0,-1000));
        plane.getMaterial().setKt(0.5);
//
        Quadrangle base = new Quadrangle(new Point3D(-1000,-1000,-100),new Point3D(-1000,-1000,-130),
                new Point3D(0,2900,-130),new Point3D(-1000,2000,-100));
        base.setEmmission(new Color(255,0,0));
//        Triangle front = new Triangle(new Point3D(-100,-100,0),new Point3D(-100,200,0),
//                new Point3D(100,0,-200));
//        front.getMaterial().setKt(0.5);
//        front.setEmmission(new Color(0, 255, 255));
//
//
        scene.addGeometry(plane);
        scene.addGeometry(base);
//        scene.addGeometry(front);

        scene.addLight(new DirectionalLight(new Color(255, 226, 80),
                new Vector(0,1000,-1)));

        ImageWriter imageWriter = new ImageWriter("MyTest5", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
