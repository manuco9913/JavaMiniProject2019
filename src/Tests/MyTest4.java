package Tests;

import Elements.DirectionalLight;
import Elements.PointLight;
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

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;

public class MyTest4 {

    @Test
    void MyTest4() {

        Scene scene = new Scene();
        scene.get_camera().setP0(new Point3D(0,0,1000));
//        scene.get_camera().set_vTo(new Vector(-1,0,-1));

        Plane back = new Plane(new Vector(0,0,1),new Point3D(0,0,-500));
        Triangle tLeft = new Triangle(new Point3D(450,-330,-100),new Point3D(300,-150,0),
                new Point3D(-900,0,-100));
        Triangle tCentralLeft = new Triangle(new Point3D(300,-150,0),new Point3D(140,0,200),
                new Point3D(-900,0,-100));
        Triangle tCentralRight = new Triangle(new Point3D(140,0,200),new Point3D(300,150,0),
                new Point3D(-900,0,-100));
        Triangle tRight = new Triangle(new Point3D(300,150,0),new Point3D(450,330,-100),
                new Point3D(-900,0,-100));


        /////////
        Sphere s = new Sphere(200,new Point3D(140,0,200));
        s.setEmmission(new Color(255,0,0));
        scene.addGeometry(s);
        //////////
        tLeft.setEmmission(new Color(7, 16, 7));
        tCentralLeft.setEmmission(new Color(48, 180, 30));
        tCentralRight.setEmmission(new Color(14, 29, 14));
        tRight.setEmmission(new Color(180, 140, 63));

        back.setEmmission(new Color(255, 255, 255));

        scene.addGeometry(back);
        scene.addGeometry(tLeft);
        scene.addGeometry(tCentralLeft);
        scene.addGeometry(tCentralRight);
        scene.addGeometry(tRight);

        scene.addLight(new SpotLight(new Color(255, 252, 14), new Point3D(400, 1000, 500),
                new Vector(-100,-100,-800),0, 0.000005, 0.00008));
        scene.addLight(new DirectionalLight(new Color(28, 54, 195),new Vector(0,-100,-100)));


        ImageWriter imageWriter = new ImageWriter("MyTest4", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
