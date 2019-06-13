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

class MyTest2 {

    @Test
    void MyTest2() {

        Scene scene = new Scene();
        scene.get_camera().setP0(new Point3D(3000,0,1500));
//        scene.get_camera().set_vTo(new Vector(0,100,-100));
//        scene.get_camera().set_vUp(new Vector(1,1,0));

        Plane plane = new Plane(new Vector(1,0,1),new Point3D(0,0,0));
//        Triangle front = new Triangle(new Point3D())
        Sphere sphere = new Sphere(1620,new Point3D(2900,0,-350));

        //triangle 1
        Quadrangle base1 = new Quadrangle(new Point3D(1300,-2000,100),new Point3D(1300,-2000,-800),
                                          new Point3D(1300,2200,-800),new Point3D(1300,2200,100));
        Quadrangle left1 = new Quadrangle(new Point3D(1300,-2000,100),new Point3D(1300,-2000,-800),
                                          new Point3D(4900,0,-800),new Point3D(4900,0,100));
        Quadrangle right1 = new Quadrangle(new Point3D(4900,0,-800),new Point3D(4900,0,100),
                                           new Point3D(1300,2200,100),new Point3D(1300,2200,-800));

        //triangle 2
        Quadrangle base2 = new Quadrangle(new Point3D(4000,-2000,100),new Point3D(4000,-2000,-800),
                                          new Point3D(4000,2200,-800),new Point3D(4000,2200,100));
        Quadrangle left2 = new Quadrangle(new Point3D(4000,-2000,100),new Point3D(4000,-2000,-800),
                                          new Point3D(400,0,-800),new Point3D(400,0,100));
        Quadrangle right2 = new Quadrangle(new Point3D(400,0,100),new Point3D(400,0,-800),
                                           new Point3D(4000,2200,-800),new Point3D(4000,2200,100));

        //rectangle
        Quadrangle baseQ = new Quadrangle(new Point3D(350,-2500,150),new Point3D(350,-2500,-1100),
                new Point3D(350,2500,-1100),new Point3D(350,2500,150));
        Quadrangle backQ = new Quadrangle(new Point3D(350,-2500,-1100), new Point3D(350,2500,-1100),
                new Point3D(5100,2500,-1100),new Point3D(5100,-2500,-1100));
        Quadrangle frontQ = new Quadrangle(new Point3D(350,-2500,150), new Point3D(350,2500,150),
                new Point3D(5100,2500,150),new Point3D(5100,-2500,150));
        Quadrangle rightQ = new Quadrangle(new Point3D(350,2500,-1100),new Point3D(350,2500,150),
                new Point3D(5100,2500,150),new Point3D(5100,2500,-1100));
        Quadrangle leftQ = new Quadrangle(new Point3D(350,-2500,150),new Point3D(350,-2500,-1100),
                new Point3D(5100,-2500,-1100),new Point3D(5100,-2500,150));
        //pyramid1
//        Triangle front1 = new Triangle(new Point3D(1350,-3000,))

        Sphere sphere1 = new Sphere(300,new Point3D(2500,0,-100));
        sphere1.getMaterial().setKr(0.8);


//        s.getMaterial().setShininess(1000);
//        sphere.getMaterial().setKt(0.8);
        sphere.setEmmission(new Color(0, 0, 0));
        base1.setEmmission(new Color(0,0,255));
        left1.setEmmission(new Color(0,0,255));
        right1.setEmmission(new Color(0,0,255));
        base2.setEmmission(new Color(34, 92, 136));
        left2.setEmmission(new Color(34, 92, 136));
        right2.setEmmission(new Color(34, 92, 136));
        baseQ.setEmmission(new Color(103, 103, 103));
        backQ.setEmmission(new Color(103, 103, 103));
//        backQ.getMaterial().setKr(0.9);
        frontQ.getMaterial().setKt(0.9);
//        frontQ.getMaterial().setKr(0.9);
        leftQ.getMaterial().setKt(0.9);
//        leftQ.getMaterial().setKr(0.9);
        rightQ.getMaterial().setKt(0.9);
//        rightQ.getMaterial().setKr(0.9);

        base1.getMaterial().setKr(0.9);
//        left1.getMaterial().setKr(0.9);
//        right1.getMaterial().setKr(0.9);
        base2.getMaterial().setKr(0.9);
//        left2.getMaterial().setKr(0.9);
//        right2.getMaterial().setKr(0.9);

        scene.addLight((new DirectionalLight(new Color(37, 99, 25), new Vector(-600, 500, -1400))));
//        scene.addLight(new SpotLight(new Color(31, 57, 201), new Point3D(1700,-3000,3500),
//                new Vector(1300,2000,-550), 0, 0.00000008, 0.000005));
        scene.addLight(new PointLight(new Color(201, 126, 134), new Point3D(7000,0,700),
                0, 0.000005, 0.0000005));

        scene.addGeometry(plane);
        scene.addGeometry(sphere1);
        scene.addGeometry(base1);
        scene.addGeometry(left1);
        scene.addGeometry(right1);
        scene.addGeometry(base2);
        scene.addGeometry(left2);
        scene.addGeometry(right2);
        scene.addGeometry(baseQ);
        scene.addGeometry(backQ);
//        scene.addGeometry(frontQ);
//        scene.addGeometry(leftQ);
//        scene.addGeometry(rightQ);


        ImageWriter imageWriter = new ImageWriter("MyTest2-without", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }

}
