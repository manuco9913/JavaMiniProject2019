package Tests;

import Geometries.*;
import Primitives.*;
import Renderer.*;
import Scene.Scene;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;

public class RenderTest {
    final String IMAGES_TEST_DIR = "src/test/image";

    @Test
    public void emmissionTest() {
        Scene scene = new Scene();
        scene.setScreenDistance(50);

        Sphere sphere = new Sphere(150, new Point3D(0.0, 0.0, -50));
        Triangle triangle1 = new Triangle(new Point3D(100, 0, -50),
                new Point3D(0, 100, -50),
                new Point3D(100, 100, -50));

        Triangle triangle2 = new Triangle(new Point3D(100, 0, -50),
                new Point3D(0, -100, -50),
                new Point3D(100, -100, -50));

        Triangle triangle3 = new Triangle(new Point3D(-100, 0, -50),
                new Point3D(0, 100, -50),
                new Point3D(-100, 100, -50));

        Triangle triangle4 = new Triangle(new Point3D(-100, 0, -50),
                new Point3D(0, -100, -50),
                new Point3D(-100, -100, -50));

        sphere.setEmmission(new Color(255, 31, 26));
        triangle1.setEmmission(new Color(64, 59, 255));
        triangle2.setEmmission(new Color(255, 228, 60));
        triangle3.setEmmission(new Color(255, 121, 40));
        triangle4.setEmmission(new Color(147, 255, 221));


        scene.addGeometry(triangle1);
        scene.addGeometry(triangle2);
        scene.addGeometry(triangle3);
        scene.addGeometry(triangle4);
        scene.addGeometry(sphere);

        ImageWriter imageWriter = new ImageWriter("Emission test 444", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();
    }

    @Test
    public void basicRendering() {

        new File(IMAGES_TEST_DIR);
        Scene scene = new Scene();
        scene.setScreenDistance(50);

        /*scene.setCamera(new Camera(
                new Point3D(0,0,10),
                new Vector(0,0,-1),
                new Vector(0,1,0)));
           scene.setBackground(new Color(0.3f ,0.5f, 0.74f));*/
//         scene.setAmbientLight(new AmbientLight(Color.BLACK);

        Sphere sphere = new Sphere(50, new Point3D(0.0, 0.0, -50));

        Triangle triangle1 = new Triangle(
                new Point3D(100, 0, -49),
                new Point3D(0, 100, -49),
                new Point3D(100, 100, -49));

        Triangle triangle2 = new Triangle(
                new Point3D(100, 0, -49),
                new Point3D(0, -100, -49),
                new Point3D(100, -100, -49));

        Triangle triangle3 = new Triangle(
                new Point3D(-100, 0, -49),
                new Point3D(0, 100, -49),
                new Point3D(-100, 100, -49));

        Triangle triangle4 = new Triangle(
                new Point3D(-100, 0, -49),
                new Point3D(0, -100, -49),
                new Point3D(-100, -100, -49));

        sphere.setEmmission( new Color(172, 255, 133));
        triangle1.setEmmission( new Color(111, 75, 81));
        triangle2.setEmmission( new Color(255, 125, 37));
        triangle3.setEmmission( new Color(255, 135, 189));
        triangle4.setEmmission( new Color(20, 255, 44));

        scene.addGeometry(sphere);
        scene.addGeometry(triangle1);
        scene.addGeometry(triangle2);
        scene.addGeometry(triangle3);
        scene.addGeometry(triangle4);

        ImageWriter imageWriter = new ImageWriter( "Render test 3", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();

    }
}