package Tests;

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

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;

public class MyTest5 {
    @Test
    void MyTest5() {

        Scene scene = new Scene();

//        Sphere sphere = new Sphere(100,new Point3D(-200,-100,00));
//        scene.addGeometry(sphere);

        Quadrangle base = new Quadrangle(new Point3D(-200,-100,0),new Point3D(-200,100,0),
                new Point3D(0,150,-100),new Point3D(0,-50,-100));

        scene.addGeometry(base);

        ImageWriter imageWriter = new ImageWriter("MyTest5", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
