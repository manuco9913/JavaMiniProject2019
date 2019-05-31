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
        scene.get_camera().setP0(new Point3D(2000,-2000,1000));
        scene.get_camera().set_vTo(new Vector(0,-1,-1));

        Plane plane = new Plane(new Vector(1,0,1),new Point3D(0,0,0));
//        Triangle front = new Triangle(new Point3D())
        Sphere sphere = new Sphere(1200,new Point3D(2000,0,-350));

        //triangle 1
        Quadrangle base1 = new Quadrangle(new Point3D(500,-2000,-180),new Point3D(500,-2000,-520),
                                          new Point3D(500,2200,-520),new Point3D(500,2200,-180));
        Quadrangle left1 = new Quadrangle(new Point3D(500,-2000,-180),new Point3D(500,-2000,-520),
                                          new Point3D(4100,0,-520),new Point3D(4100,0,-180));
        Quadrangle right1 = new Quadrangle(new Point3D(4100,0,-520),new Point3D(4100,0,-180),
                                   new Point3D(500,2200,-520),new Point3D(500,2200,-180));

        //triangle 2
        Quadrangle base2 = new Quadrangle(new Point3D(3200,-2000,-180),new Point3D(3200,-2000,-520),
                                          new Point3D(3200,2200,-520),new Point3D(3200,2200,-180));
        Quadrangle left2 = new Quadrangle(new Point3D(3200,-2000,-180),new Point3D(3200,-2000,-520),
                                          new Point3D(-400,0,-520),new Point3D(-400,0,-180));
        Quadrangle right2 = new Quadrangle(new Point3D(-400,0,-520),new Point3D(-400,0,-180),
                                           new Point3D(3200,2200,-520),new Point3D(3200,2200,-180));


//        s.getMaterial().setShininess(1000);
        sphere.getMaterial().setKt(0.8);
        sphere.setEmmission(new Color(0, 0, 0));
        base1.setEmmission(new Color(0,0,255));
        left1.setEmmission(new Color(0,0,255));
        right1.setEmmission(new Color(0,0,255));
        base2.setEmmission(new Color(34, 92, 136));
        left2.setEmmission(new Color(34, 92, 136));
        right2.setEmmission(new Color(34, 92, 136));

        scene.addLight((new DirectionalLight(new Color(146, 237, 202), new Vector(-600, 500, -1400))));
//        scene.addLight(new SpotLight(new Color(31, 57, 201), new Point3D(1700,-3000,3500),
//                new Vector(1300,2000,-550), 0, 0.00000008, 0.000005));
        scene.addLight(new PointLight(new Color(201, 126, 134), new Point3D(2500,0,-500),
                0, 0.00000008, 0.000005));
        scene.addGeometry(plane);
//        scene.addGeometry(sphere);
        scene.addGeometry(base1);
        scene.addGeometry(left1);
        scene.addGeometry(right1);
        scene.addGeometry(base2);
        scene.addGeometry(left2);
        scene.addGeometry(right2);



        ImageWriter imageWriter = new ImageWriter("MyTest2", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }

}
