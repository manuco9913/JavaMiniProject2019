package Tests;

import Elements.DirectionalLight;
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

public class stam {
    @Test
    void stam() {

        Scene scene = new Scene();
//        scene.getCamera().setP0(new Point3D(0,0,1000));
//        scene.setCamera(new Camera(new Point3D(1000,0,1000),new Vector(0,0,-1),
//                new Vector(-1,0,0)));

        Sphere s = new Sphere(200, new Point3D());
        s.setEmmission(new Color(255,255,255));
        scene.addGeometry(s);
        scene.addLight(new SpotLight(new Color(94, 255, 46, 255), new Point3D(0, 200, 0),
                new Vector(0, 1000, -1), 0, 0.000005, 0.000008));

        ImageWriter imageWriter = new ImageWriter("stam", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
