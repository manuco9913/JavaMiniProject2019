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

public class MyTest3 {
    @Test
    void MyTest3() {

        Scene scene = new Scene();
        scene.get_camera().setP0(new Point3D(0,0,400));
//        scene.get_camera().set_vTo(new Vector(-1,0,-1));

        Sphere sphere = new Sphere(150,new Point3D(500,0,-100));
        Sphere sphere1 = new Sphere(150,new Point3D(-400,-600,-100));
        Sphere sphere2 = new Sphere(150,new Point3D(-400,600,-100));

int j=0;
        for(int i = -400; j<5; j++, i = i+200)
        {
            Sphere iSphere = new Sphere(150,new Point3D(i,i-400,-100));
            scene.addGeometry(iSphere);
        }


        Triangle base = new Triangle(new Point3D(-800,-1300,-200),new Point3D(-800,1300,-200),
                new Point3D(900,0,-200));

        base.setEmmission(new Color(255, 231, 34));

        scene.addLight(new PointLight(new Color(74, 150, 109), new Point3D(100, -300, 100),
                0, 0.00005, 0.000008));

//        scene.addGeometry(sphere);
//        scene.addGeometry(sphere1);
//        scene.addGeometry(sphere2);
        scene.addGeometry(base);

        ImageWriter imageWriter = new ImageWriter("MyTest3", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
