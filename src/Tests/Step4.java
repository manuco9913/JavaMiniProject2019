package Tests;

import Elements.*;
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

    public class Step4 {
        @Test
        void Step4() {

            Scene scene = new Scene();
            scene.getCamera().setP0(new Point3D(0, 0, 200));
//            scene.setScreenDistance(400);

            Plane plane = new Plane(new Vector(0, 0, 1), new Point3D(-270, 0, -500));
            plane.setEmmission(new Color(86, 66, 226));
            plane.getMaterial().setKr(0.5);
//            scene.addGeometry(plane);

            Quadrangle floor = new Quadrangle(/*A*/new Point3D(-500, -900, 300), /*B*/new Point3D(-500, 900, 300),
                    /*C*/new Point3D(-200, 900, -500), /*D*/new Point3D(-200, -900, -500));
            floor.setEmmission(new Color(99, 99, 99));
            floor.getMaterial().setKr(0.5);
//            scene.addGeometry(floor);

            Sphere sphere = new Sphere(70, new Point3D(-150, -80, 50));
            sphere.setEmmission(new Color(135, 21, 16));
            sphere.getMaterial().setKd(0);
            sphere.getMaterial().setKs(1);
            scene.addGeometry(sphere);

            Quadrangle base = new Quadrangle(/*A*/new Point3D(-300, -150-150, -250+200), /*B*/new Point3D(-300, 50+300, -250+200),
                    /*C*/new Point3D(-300, 50+300, -50+200), /*D*/new Point3D(-300, -150-150, -50+200));
            Quadrangle left = new Quadrangle(/*A*/new Point3D(-300, -150-150, -250+200), /*D*/new Point3D(-300, -150-150, -50+200),
                    /*E*/new Point3D(300,-150-150,-50+200), /*F*/new Point3D(300,-150-150,-250+200));
            Quadrangle right = new Quadrangle(/*B*/new Point3D(-300, 50+300, -250+200), /*C*/new Point3D(-300, 50+300, -50+200),
                    /*G*/new Point3D(300,50+300,-50+200), /*H*/new Point3D(300,50+300,-250+200));
            Quadrangle top = new Quadrangle(/*F*/new Point3D(300,-150-150,-250+200), /*E*/new Point3D(300,-150-150,-50+200),
                    /*G*/new Point3D(300,50+300,-50+200), /*H*/new Point3D(300,50+300,-250+200));
            Quadrangle front = new Quadrangle(/*D*/new Point3D(-300, -250+100-150, -50+200), /*C*/new Point3D(-300, 50, -50+200),
                    /*G*/new Point3D(300,50,-50+200), /*E*/new Point3D(0,-150-150,-50+200));
            Quadrangle back = new Quadrangle(/*A*/new Point3D(-300, -150-150, -250+200), /*B*/new Point3D(-300, 50+300, -250+200),
                    /*H*/new Point3D(300,50+300,-250+200), /*F*/new Point3D(300,-150-150,-250+200));




            Quadrangle fffront =  new Quadrangle(/*D*/new Point3D(-50,-50,-100), /*C*/new Point3D(-50,50,-100),
                    /*G*/new Point3D(50,50,-100), /*E*/new Point3D(50,-50,-100));
            fffront.getMaterial().setKt(0.9);
            fffront.setEmmission(new Color(24,0, 5));
//            scene.addGeometry(fffront);

            base.setEmmission(new Color(0, 224, 1));
            left.setEmmission(new Color(51, 87, 191));
            front.setEmmission(new Color(5, 11, 5));
            right.setEmmission(new Color(12, 25, 8));
            top.setEmmission(new Color(56, 23, 23));
            back.setEmmission(new Color(14, 15, 28));

//            base.getMaterial().setKt(1);
//            left.getMaterial().setKt(1);
//            front.getMaterial().setKt(1);
//            right.getMaterial().setKt(1);
//            top.getMaterial().setKt(1);
            back.getMaterial().setKr(1);
//            back.getMaterial().setShininess(1000);

//            scene.addGeometry(front);

            scene.addGeometry(base);
            scene.addGeometry(left);
            scene.addGeometry(right);
            scene.addGeometry(top);
            scene.addGeometry(back);

            ////
//        Quadrangle stam = new Quadrangle(new Point3D())
            ////
            scene.addLight((new PointLight(new Color(12, 25, 8), new Point3D(-200, 290, 150),
                    0, 0.00005, 0.0000005)));
            scene.addLight((new PointLight(new Color(52, 3, 42), new Point3D(-200, -290, 150),
                    0, 0.00005, 0.0000005)));

            ImageWriter imageWriter = new ImageWriter("Step4", 500, 500, 500, 500);
            Render render = new Render(imageWriter, scene);
//            render.setMoreRefractedON(true);
//            render.setMoreReflectedON(true);
            render.renderImage();
            render.writeToImage();


        }
    }

