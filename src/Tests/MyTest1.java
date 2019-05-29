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

class MyTest1 {

    @Test
    void MyTest1() {

        Scene scene = new Scene();
        scene.get_camera().setP0(new Point3D(-200, 0, 500));

        Sphere sphere = new Sphere(200, new Point3D(300, -500, -250));
        Sphere sphere2 = new Sphere(300, new Point3D(200, -100, -600));
        Sphere sphere3 = new Sphere(300, new Point3D(700, 400, -800));

        Quadrangle base = new Quadrangle(new Point3D(2000, 1500, -2000), new Point3D(-1000, 700, 100),
                new Point3D(-1000, -700, 100), new Point3D(2000, -1500, -2000));
        Quadrangle leftSide = new Quadrangle(new Point3D(4000, -1500, -2000), new Point3D(2000, -1500, -2000),
                new Point3D(-1000, -700, 100), new Point3D(300, -700, 100));
        Quadrangle rightSide = new Quadrangle(new Point3D(4000, 1500, -2000), new Point3D(2000, 1500, -2000),
                new Point3D(-1000, 700, 100), new Point3D(0, 700, 100));
        Quadrangle back = new Quadrangle(new Point3D(4000, 1500, -2000), new Point3D(2000, 1500, -2000),
                new Point3D(2000, -1500, -2000), new Point3D(4000, -1500, -2000));
        Quadrangle roof = new Quadrangle(new Point3D(4000, -1500, -2000), new Point3D(4000, 1500, -2000),
                new Point3D(0, 700, 100), new Point3D(0, -700, 100));

//        back.setEmmission(new Color(104, 116, 112));
        roof.setEmmission(new Color(95, 99, 88));
        base.setEmmission(new Color(0, 0, 0));
        leftSide.setEmmission(new Color(0, 0, 0));
        rightSide.setEmmission(new Color(0, 0, 0));
        rightSide.getMaterial().setKr(0.7);
        leftSide.getMaterial().setKr(0.7);
        back.getMaterial().setKr(0);

//        sphere2.getMaterial().setKt(0.5);
        sphere.getMaterial().setKr(0.5);
        sphere2.getMaterial().setShininess(1000);
        sphere.setEmmission(new Color(37, 26, 164));
        sphere2.setEmmission(new Color(144, 150, 146, 207));
        sphere3.setEmmission(new Color(255, 69, 91));

//        Plane plane = new Plane(new Vector(3000,0,3000),new Point3D(-1000,0,-1000));

        scene.addGeometry(sphere);
        scene.addGeometry(sphere2);
        scene.addGeometry(sphere3);
        scene.addGeometry(base);
        scene.addGeometry(back);
        scene.addGeometry(leftSide);
        scene.addGeometry(rightSide);
//        scene.addGeometry(roof);

        scene.addLight(new PointLight(new Color(74, 150, 109), new Point3D(1000, -300, 100),
                0, 0.00005, 0.000008));
//        scene.addLight(new SpotLight(new Color(92, 201, 122),new Point3D(2500,-1000,-1500),
//                new Vector(-1000, 700, 100), 0, 0.00000001, 0.0000005));
//        scene.addLight((new DirectionalLight(new Color(111, 114, 4), new Vector(-1, 0, -1))));
//
        ImageWriter imageWriter = new ImageWriter("MyTest1", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}