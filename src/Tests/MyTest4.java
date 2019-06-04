package Tests;

import Elements.PointLight;
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
        scene.get_camera().setP0(new Point3D(0,0,400));
//        scene.get_camera().set_vTo(new Vector(-1,0,-1));

//        Plane base = new Plane(new Vector(1,0,1),new Point3D());
        Plane base = new Plane(new Vector(10000,0,3000),new Point3D(-1000,0,-1000));
        Plane roof = new Plane(new Vector(-500,0,-700),new Point3D(5000,0,0));
        Plane back = new Plane(new Vector(0,0,1),new Point3D(0,0,-20000));

        base.setEmmission(new Color(0, 98, 98));
        roof.setEmmission(new Color(37, 99, 25));
        back.setEmmission(new Color(181, 2,0));

        scene.addGeometry(base);
        scene.addGeometry(roof);
        scene.addGeometry(back);

        scene.addLight(new PointLight(new Color(74, 150, 109), new Point3D(0, 0, 0),
                0, 0.000005, 0.0000008));

        ImageWriter imageWriter = new ImageWriter("MyTest4", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
