package Tests;

import Elements.SpotLight;
import Geometries.Plane;
import Geometries.Sphere;
import Geometries.Triangle;
import Primitives.Point3D;
import Primitives.Vector;
import Renderer.ImageWriter;
import Renderer.Render;
import Scene.Scene;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class MyTest4 {

    @Test
    void MyTest4() {

        Scene scene = new Scene();
        scene.getCamera().setP0(new Point3D(/*-50*/100,/*-500*/0,/*50*/400));//z:300
//        scene.getCamera().set_vTo(new Vector(0,1,-1));

        Plane back = new Plane(new Vector(0,0,1),new Point3D(0,0,-200));
        back.getMaterial().setShininess(1000);
        Triangle tLeft = new Triangle(new Point3D(/*450-300*/150,-330,-100),new Point3D(/*250-300*/0,-150,50),
                new Point3D(-900-300,0,-100));
        Triangle tCentralLeft = new Triangle(new Point3D(/*250-300*/0,-150,50),new Point3D(/*170-300*/-40,0,120),
                new Point3D(-900-300,0,-100));
        Triangle tCentralRight = new Triangle(new Point3D(/*170-300*/-40,0,120),new Point3D(/*250-300*/0,150,50),
                new Point3D(-900-300,0,-100));
        Triangle tRight = new Triangle(new Point3D(/*250-300*/0,150,50),new Point3D(/*450-300*/150,330,-100),
                new Point3D(-900-300,0,-100));
        Triangle tBackLeft = new Triangle(new Point3D(/*450-300*/150,-330,-100),new Point3D(/*400-300*/200,-150,0),
                new Point3D(-900-300,0,-100));
        Triangle tBackRight = new Triangle(new Point3D(/*400-300*/200,150,0),new Point3D(/*450-300*/150,330,-100),
                new Point3D(-900-300,0,-100));


        /////////
        Sphere s1 = new Sphere(230,new Point3D(400-300,-30,-30));
        s1.setEmmission(new Color(197, 109, 99));
        scene.addGeometry(s1);
//        Sphere s2 = new Sphere(50,new Point3D(500-300,0,140));
//        s2.setEmmission(new Color(83, 6, 0));
//        scene.addGeometry(s2);
        Sphere s3 = new Sphere(270,new Point3D(530-300,110/*60*/,-120));
        s3.setEmmission(new Color(65, 30, 40));
        scene.addGeometry(s3);
        Sphere s4 = new Sphere(270,new Point3D(510-300,-120/*-60*/,-140));
        s4.setEmmission(new Color(141, 23, 0));
        scene.addGeometry(s4);
        //////////
        tLeft.setEmmission(new Color(139, 101, 57));
        tCentralLeft.setEmmission(new Color(139, 101, 57));
        tCentralRight.setEmmission(new Color(139, 101, 57));
        tRight.setEmmission(new Color(139, 101, 57));
        tBackLeft.setEmmission(new Color(139, 101, 57));
        tBackRight.setEmmission(new Color(139, 101, 57));

        back.setEmmission(new Color(106, 86, 77));
        back.getMaterial().setShininess(200);

        scene.addGeometry(back);
        scene.addGeometry(tLeft);
        scene.addGeometry(tCentralLeft);
        scene.addGeometry(tCentralRight);
        scene.addGeometry(tRight);
        scene.addGeometry(tBackLeft);
        scene.addGeometry(tBackRight);

//        scene.addLight(new PointLight(new Color(70, 248, 255),new Point3D(0,200,500),
//                0, 0.00005, 0.0000008));
        scene.addLight(new SpotLight(new Color(106, 103, 104), new Point3D(-800,1200,1150),
                new Vector(1,-1,-1),0.0000008, 0.00000008, 0.0000008));
        scene.addLight(new SpotLight(new Color(83, 106, 83), new Point3D(-800,-1700,1150),
                new Vector(1,1,-1),0.0000008, 0.0000000008, 0.0000008));
//        scene.addLight(new DirectionalLight(new Color(28, 54, 195),new Vector(0,200,100)));


        ImageWriter imageWriter = new ImageWriter("MyTest4", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
