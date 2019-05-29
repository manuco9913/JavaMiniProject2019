package Tests;

import Elements.DirectionalLight;
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

class MyTest2 {

    @Test
    void MyTest2() {

        Scene scene = new Scene();
        scene.get_camera().setP0(new Point3D(2000,0,1000));
//        scene.get_camera().set_vUp(new Vector(3000,0,0));

        Plane plane = new Plane(new Vector(1,0,1),new Point3D(0,0,0));
//        Triangle front = new Triangle(new Point3D())
        Sphere s = new Sphere(1000,new Point3D(1500,0,-550));


        s.getMaterial().setShininess(1000);
        s.setEmmission(new Color(8, 8, 8));

        scene.addLight((new DirectionalLight(new Color(146, 237, 202), new Vector(-600, 500, -1500))));
        scene.addLight(new SpotLight(new Color(201, 122, 137), new Point3D(3000,-1000,4000),
                new Vector(1500,0,-550), 0, 0.0000008, 0.00000002));


        scene.addGeometry(plane);
        scene.addGeometry(s);



        ImageWriter imageWriter = new ImageWriter("MyTest2", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }

}
