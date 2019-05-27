package Tests;

import Elements.SpotLight;
import Geometries.Sphere;
import Geometries.Triangle;
import Primitives.Point3D;
import Primitives.Vector;
import Renderer.ImageWriter;
import Renderer.Render;
import Scene.Scene;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class FiveScenes {

    @Test
    public void MyTest1() {

        Scene scene = new Scene();
        scene.set_screenDistance(100);

        Sphere sphere = new Sphere(300, new Point3D(-550, -500, -1000));
        sphere.getMaterial().setShininess(20);
        sphere.setEmmission(new Color(0, 0, 100));
        sphere.getMaterial().setKt(0.5);
        scene.addGeometry(sphere);

        Sphere sphere2 = new Sphere(150, new Point3D(-550, -500, -1000));
        sphere2.getMaterial().setShininess(20);
        sphere2.setEmmission(new Color(100, 20, 20));
        sphere2.getMaterial().setKt(0);
        scene.addGeometry(sphere2);

        Triangle triangle = new Triangle(new Point3D(1000, -1000, -1000),
                new Point3D(-1500, 1500, -1500),
                new Point3D(200, 200, -375));

//        Triangle triangle2 = new Triangle(new Point3D(1500, -1500, -1500),
//                new Point3D(-1500, 1500, -1500),
//                new Point3D(-1500, -1500, -1500));

        triangle.setEmmission(new Color(20, 20, 20));
//        triangle2.setEmmission(new Color(20, 20, 20));
        triangle.getMaterial().setKr(1);
//        triangle2.getMaterial().setKr(0.5);
        scene.addGeometry(triangle);
//        scene.addGeometry(triangle2);

        scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -150),
                new Vector(-2, -2, -3), 0, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("MyTest1", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();

    }
}
