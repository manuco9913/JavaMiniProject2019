package Tests;

import Elements.DirectionalLight;
import Elements.PointLight;
import Geometries.Plane;
import Geometries.Quadrangle;
import Geometries.Sphere;
import Primitives.Point3D;
import Primitives.Vector;
import Renderer.ImageWriter;
import Renderer.Render;
import Scene.Scene;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class MyTest3 {
    @Test
    void MyTest3() {

        Scene scene = new Scene();
        scene.get_camera().setP0(new Point3D(0,200,0));
        scene.get_camera().set_vTo(new Vector(0,-1,-1));

        Sphere sphere = new Sphere(100,new Point3D(0,0,-200));
//        //triangle 1
//        Quadrangle base1 = new Quadrangle(new Point3D(500,-2000,-180),new Point3D(500,-2000,-520),
//                new Point3D(500,2200,-520),new Point3D(500,2200,-180));
//        Quadrangle left1 = new Quadrangle(new Point3D(500,-2000,-180),new Point3D(500,-2000,-520),
//                new Point3D(4100,0,-520),new Point3D(4100,0,-180));
//        Quadrangle right1 = new Quadrangle(new Point3D(4100,0,-520),new Point3D(4100,0,-180),
//                new Point3D(500,2200,-520),new Point3D(500,2200,-180));
//
//        base1.setEmmission(new Color(0,0,255));
//        left1.setEmmission(new Color(0,0,255));
//        right1.setEmmission(new Color(0,0,255));
//
//        scene.addGeometry(base1);
//        scene.addGeometry(left1);
//        scene.addGeometry(right1);
        scene.addGeometry(sphere);


        ImageWriter imageWriter = new ImageWriter("MyTest3", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
