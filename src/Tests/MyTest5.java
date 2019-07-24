package Tests;

import Elements.DirectionalLight;
import Elements.PointLight;
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
        scene.getCamera().setP0(new Point3D(0,0,1000));
//        scene.setCamera(new Camera(new Point3D(1000,0,1000),new Vector(0,0,-1),
//                new Vector(-1,0,0)));

        Sphere sphere = new Sphere(100,new Point3D(-100,95,-15));
        Sphere sphere1 = new Sphere(300,new Point3D(-100,-400,100));
        Sphere sphere2 = new Sphere(400,new Point3D(500,500,-1000));
        Sphere sphere3 = new Sphere(100,new Point3D(0,200,500));
        Sphere sphere4 = new Sphere(80,new Point3D(-600,400,0));
        Sphere sphere5 = new Sphere(500,new Point3D(2000,3000,-1500));
        Sphere sphere6 = new Sphere(100,new Point3D(-1000,-300,300));

        Quadrangle top = new Quadrangle(/*A*/new Point3D(600,-900,400),/*B*/new Point3D(600,-200,400),
                                         /*C*/new Point3D(600,-200,200),/*D*/new Point3D(600,-900,200));
        Quadrangle left = new Quadrangle(/*A*/new Point3D(600,-900,400),/*D*/new Point3D(600,-900,200),
                                         /*E*/new Point3D(300,-900,200),/*F*/new Point3D(300,-900,400));
        Quadrangle right = new Quadrangle(/*B*/new Point3D(600,-200,400), /*C*/new Point3D(600,-200,200),
                                          /*G*/new Point3D(300,-200,200),/*H*/new Point3D(300,-200,400));
        Quadrangle base = new Quadrangle(/*F*/new Point3D(300,-900,400),  /*E*/new Point3D(300,-900,200),
                                        /*G*/new Point3D(300,-200,200),/*H*/new Point3D(300,-200,400));
        Quadrangle back = new Quadrangle(/*D*/new Point3D(600,-900,200), /*C*/new Point3D(600,-200,200),
                                         /*G*/new Point3D(300,-200,200),/*E*/new Point3D(300,-900,200));


        base.setEmmission(new Color(97, 67, 128, 222));
        left.setEmmission(new Color(97, 67, 128, 222));
        right.setEmmission(new Color(97, 67, 128, 222));
        top.setEmmission(new Color(97, 67, 128, 222));
        back.setEmmission(new Color(20, 48, 160, 222));

        sphere.setEmmission(new Color(44, 255, 28));
        sphere1.setEmmission(new Color(0, 229, 255));
        sphere1.getMaterial().setKr(0.9);
        sphere2.setEmmission(new Color(255,0, 231));
        sphere3.setEmmission(new Color(0, 98, 98));
        sphere3.getMaterial().setKr(0.9);
        sphere4.setEmmission(new Color(0, 98, 98));
        sphere5.setEmmission(new Color(0,0, 255));
        sphere5.getMaterial().setShininess(1000);
        sphere6.setEmmission(new Color(136, 2,0));
        sphere6.getMaterial().setShininess(1000);
        scene.addGeometry(sphere);
        scene.addGeometry(sphere1);
        scene.addGeometry(sphere2);
        scene.addGeometry(sphere3);
        scene.addGeometry(sphere4);
        scene.addGeometry(sphere5);
        scene.addGeometry(sphere6);
//        scene.addGeometry(base);
        scene.addGeometry(left);
        scene.addGeometry(right);
        scene.addGeometry(top);
//        scene.addGeometry(back);

        Plane plane = new Plane(new Vector(10000,0,3000),new Point3D(-1000,0,-1000));
        plane.getMaterial().setKt(0.5);
//
//        Quadrangle baseQ = new Quadrangle(new Point3D(-1000,-1000,-100),new Point3D(-1000,-1000,-130),
//                new Point3D(0,2900,-130),new Point3D(-1000,2000,-100));
        Triangle triangle = new Triangle(new Point3D(-1000,-1000,-100), new Point3D(0,2900,-200),
                new Point3D(-1000,2000,-100));
        triangle.setEmmission(new Color(255,0,0));
        Sphere sphere7 = new Sphere(100,new Point3D(-950,2900,-400));
        sphere7.setEmmission(new Color(255,255,255));

        scene.addGeometry(sphere7);
        scene.addGeometry(plane);
        scene.addGeometry(triangle);

        scene.addLight(new DirectionalLight(new Color(139, 101, 57, 255),
                new Vector(0,1000,-1)));
        scene.addLight(new PointLight(new Color(94, 255, 46),new Point3D(300,-550,450),0,0.00005,0.000005));
        scene.addLight(new DirectionalLight(new Color(0, 29, 10, 243),
                new Vector(0,-1000,1)));

        ImageWriter imageWriter = new ImageWriter("MyTest5", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
