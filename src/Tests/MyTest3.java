package Tests;

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

public class MyTest3 {
    @Test
    void MyTest3() {

        Scene scene = new Scene();
        scene.get_camera().setP0(new Point3D(0,0,400));//original z: 400 //z far:2000, z far from behind -1300
//        scene.get_camera().set_vTo(new Vector(0,0,1));//for behind

//        Quadrangle baseQ = new Quadrangle(/*A*/new Point3D(600,-900,400),/*B*/new Point3D(600,-200,400),
//                /*C*/new Point3D(600,-200,200),/*D*/new Point3D(600,-900,200));
//        Quadrangle leftQ = new Quadrangle(/*A*/new Point3D(600,-900,400),/*D*/new Point3D(600,-900,200),
//                /*E*/new Point3D(300,-900,200),/*F*/new Point3D(300,-900,400));
//        Quadrangle rightQ = new Quadrangle(/*B*/new Point3D(600,-200,400), /*C*/new Point3D(600,-200,200),
//                /*G*/new Point3D(300,-200,200),/*H*/new Point3D(300,-200,400));
//        Quadrangle topQ = new Quadrangle(/*F*/new Point3D(300,-900,400),  /*E*/new Point3D(300,-900,200),
//                /*G*/new Point3D(300,-200,200),/*H*/new Point3D(300,-200,400));
//        Quadrangle backQ = new Quadrangle(/*D*/new Point3D(600,-900,200), /*C*/new Point3D(600,-200,200),
//                /*G*/new Point3D(300,-200,200),/*E*/new Point3D(300,-900,200));

        Quadrangle front = new Quadrangle(new Point3D(-800,-1300,-200),new Point3D(-800,1300,-200),
                                          new Point3D(900,1300,-200),new Point3D(900,-1300,-200));
        front.getMaterial().setKr(1);
        front.getMaterial().setShininess(500);
        front.setEmmission(new Color(11, 14, 4));
        scene.addGeometry(front);

        Triangle back = new Triangle(new Point3D(1500,-2500,400),new Point3D(1500,2500,400),
                new Point3D(-1500,0,400));
        back.getMaterial().setKr(1);
//        back.setEmmission(new Color(19, 0, 66));
        scene.addGeometry(back);

        Sphere sphere = new Sphere(100,new Point3D(-350,-350,100));
        sphere.setEmmission(new Color(0, 226, 8));
        scene.addGeometry(sphere);

        Sphere sphere2 = new Sphere(100,new Point3D(250,-350,100));
        sphere2.setEmmission(new Color(226, 0, 7));
//        sphere2.getMaterial().setKr(0.8);
        scene.addGeometry(sphere2);

        Sphere sphere3 = new Sphere(50,new Point3D(350,300,100));
        sphere3.setEmmission(new Color(5, 0, 161));
        scene.addGeometry(sphere3);

        Sphere sphere4 = new Sphere(80,new Point3D(-300,400,100));
        sphere4.setEmmission(new Color(224, 226, 0));
//        sphere4.getMaterial().setKr(0.8);
        scene.addGeometry(sphere4);

        scene.addLight(new SpotLight(new Color(53, 107, 80), new Point3D(7000, 7000, 100),
                new Vector(-1,-1,0),0, 0.00005, 0.000000005));
        scene.addLight(new SpotLight(new Color(13, 1, 0), new Point3D(7000, -7000, 100),
                new Vector(-1,1,0),0, 0.00000000005, 0.000000001));


//        scene.addGeometry(sphere);
//        scene.addGeometry(sphere1);
//        scene.addGeometry(sphere2);


        ImageWriter imageWriter = new ImageWriter("MyTest3", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
