package Tests;
import Geometries.Plane;
import Geometries.Sphere;
import Geometries.Triangle;
import Primitives.*;
import Elements.*;
import Primitives.Point3D;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CameraTest {

    @Test
    void constructorsTest() {
        //Given
        Camera c = new Camera();
        Camera c2 = new Camera(c);

        //Then
        assertEquals(c, new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0)
                , new Vector(0, 0, -1)));
        assertEquals(c, c2);
    }

    @Test
    void toStringTest() {
        //Given
        Vector v1 = new Vector(45,32,34);
        Vector v2 = new Vector(9,2,1);
        Camera c =  new Camera(new Point3D(1,2,3),v2,v1);

        //When
        String s = c.toString();
        Vector v3 = v2.crossProduct(v1);

        //Then
        assertEquals("Vto: " + v1 + "\n" + "Vup: " + v2 + "\n" + "Vright:" + v3 + ".",c.toString());
    }

    @Test
    public void testRaysConstruction() {
        //Given
        final int WIDTH = 3;
        final int HEIGHT = 3;
        Point3D[][] screen = new Point3D[HEIGHT][WIDTH];
        Camera camera = new Camera(new Point3D(0.0, 0.0, 0.0),
                        new Vector(0.0, 1.0, 0.0),
                        new Vector(0.0, 0.0, -1.0));
        System.out.println("Camera:\n" + camera);

        //When
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++)
            {
                Ray ray = camera.constructRayThroughPixel(
                        WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
                screen[i][j] = ray.getPOO();
                System.out.print(screen[i][j]);
                System.out.println(ray.getDirection());

                //Then
                    // Checking z-coordinate
                assertTrue(Double.compare(screen[i][j].getZ().getCoordinate(), -1.0) == 0);
                    // Checking all options
                double x = screen[i][j].getX().getCoordinate();
                double y = screen[i][j].getY().getCoordinate();
                if (Double.compare(x, 3) == 0 ||
                        Double.compare(x, 0) == 0 ||
                        Double.compare(x, -3) == 0) {
                    if (Double.compare(y, 3) == 0 ||
                            Double.compare(y, 0) == 0 ||
                            Double.compare(y, -3) == 0) {
                        assertTrue(true);
                    } else
                        fail("Wrong y coordinate");
                } else
                    fail("Wrong x coordinate");
            }
            System.out.println("---");
        }
    }
}
