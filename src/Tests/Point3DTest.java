package Tests;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;
import org.junit.jupiter.api.*;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    DecimalFormat df = new DecimalFormat("#.##");
    //Double.parseDouble(df.format(val));// used for getting a number only with two digits after the comma

    @Test
    void compareTo() {
        //Given
        Coordinate c1 = new Coordinate(0.0);
        Coordinate c2 = new Coordinate(1.0);
        Coordinate c3 = new Coordinate(2.0);
        Coordinate c4 = new Coordinate(3.0);

        //When
        Point3D p1 = new Point3D(c1, c1, c1);
        Point3D p2 = new Point3D(c2, c3, c4);
        Point3D p3 = new Point3D(c2, c2, c1);
        Point3D p4 = new Point3D(1.0, 2.0, 3.0);

        //Then
        int one = p1.compareTo(p2); //false = -1
        int two = p2.compareTo(p4); //true = 0
        int three = p3.compareTo(p2); //false = -1
        int four = p3.compareTo(p3); //true = 0

        assertEquals(-1, one);
        assertEquals(0, two);
        assertEquals(-1, three);
        assertEquals(0, four);
    }

    @Test
    void testToString() {
        //Given
        Coordinate c1 = new Coordinate(1.0);
        Point3D p1 = new Point3D(c1, c1, c1);
        Point3D p2 = new Point3D(1.0, 2.999, 3.8);

        //When
        String str1Test = p1.toString();
        String str2Test = p2.toString();

        //Then
        assertEquals("(1.00, 1.00, 1.00)", str1Test);
        assertEquals("(1.00, 3.00, 3.80)", str2Test);
    }

    @Test
    void add() {
        //Given
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(1.87, 22, 1);
        Vector vec1Test = new Vector(p1);
        Vector vec2Test = new Vector(1.999, 2.0, 6.54);

        //When
        Point3D res1 = p1.add(vec1Test);
        Point3D res2 = p1.add(vec2Test);
        Point3D res3 = p2.add(vec1Test);
        Point3D res4 = p2.add(vec2Test);

        //Then
        assertTrue(res1.compareTo(new Point3D(2, 4, 6)) == 0);
        assertTrue(res2.compareTo(new Point3D(2.999, 4.0, 9.54)) == 0);
        assertTrue(res3.compareTo(new Point3D(2.87, 24, 4)) == 0);
        assertTrue(res4.compareTo(new Point3D(1.87 + 1.999, 24, 7.54)) == 0);
    }

    @Test
    void subtract() {
        //Given
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(1.87, 22, 1);
        Vector vec1Test = new Vector(p1);
        Vector vec2Test = new Vector(1.999, 2.0, 6.54);

        //When
        Point3D res1 = p1.subtract(vec1Test);
        Point3D res2 = p1.subtract(vec2Test);
        Point3D res3 = p2.subtract(vec1Test);
        Point3D res4 = p2.subtract(vec2Test);

        //Then
        assertTrue(res1.compareTo(new Point3D(0, 0, 0)) == 0);
        assertTrue(res2.compareTo(new Point3D(1 - 1.999, 0, 3 - 6.54)) == 0);
        assertTrue(res3.compareTo(new Point3D(0.87, 20, -2)) == 0);
        assertTrue(res4.compareTo(new Point3D(1.87 - 1.999, 20, 1 - 6.54)) == 0);
    }

    @Test
    void distance() {
        //Given
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(-1, -1, -1);
        Point3D p3 = new Point3D(2, 1, 2);

        //When
        double res1 = p1.distance(new Point3D(0, 0, 0));
        double res2 = p2.distance(new Point3D(0, 0, 0));
        double res3 = p1.distance(p2);

        //Then
        assertEquals(res1, res2);
        assertEquals(3.4641016151377544, res3);
    }
}

//region David Tests
/*public class Point3DTest
 {
 DecimalFormat df = new DecimalFormat("#.##");
// ************************************** Point3D tests *************************************************************
 @Test
    public void Test01() {
        System.out.println("Test01: Point3D compareTo");
        Point3D point3D = new Point3D(2.0, -7.5, 9.25);
        Point3D instance = new Point3D(2.0, -7.5, 9.25);
        int expResult = 0;
        int result = instance.compareTo(point3D);
        assertEquals(expResult, result);
    }

   @Test
   public void Test02() {
       System.out.println("Test02: Point3D toString");
       Point3D instance = new Point3D(1.123, 2.569, 3.999);
       String expResult = "(1.12, 2.57, 4.00)";
       String result = instance.toString();
       assertEquals(expResult, result);
   }

  @Test
   public void Test03() {
       System.out.println("Test03: Point3D add");
       Vector vector = new Vector(1.25, -2.0, 3.0);
       Point3D instance1 = new Point3D(4.75, -5.0, 6.0);
       Point3D instance = instance1.add(vector);
       assertTrue(instance.compareTo(new Point3D(6.0, -7.0, 9.0)) == 0, "Add failed! ");
   }

  @Test
   public void Test04() {
       System.out.println("Test04: Point3D subtract");
       Vector vector = new Vector(1.0, 2.0, 3.0);
       Point3D instance1 = new Point3D(4.0, 5.0, 6.0);
       Point3D instance = instance1.subtract(vector);
       assertTrue(instance.compareTo(new Point3D(3.0, 3.0, 3.0)) == 0, "Substruct failed! ");
   }

  @Test
   public void Test05() {
       System.out.println("Test05: Point3D distance");
       Point3D point = new Point3D(-20.5, 55, 9.25);
       Point3D instance = new Point3D(75, -10, -100);
       double expResult = 159.0;
       double result = instance.distance(point);
       assertEquals(expResult, result, 0.01, "Worng distance");
   }
   //************************************* Vector tests *************************************************************
   @Test
   public void Test06(){
       System.out.println("Test06: Vector Add test");

  Vector v1 = new Vector(1.0, 1.0, 1.0);
       Vector v2 = new Vector(-1.0, -1.0, -1.0);

  Vector vt = v1.add(v2);
       assertTrue(vt.compareTo(new Vector(0.0,0.0,0.0)) == 0);

  Vector vtt = v2.add(v1);
       assertTrue(vtt.compareTo(vtt) == 0);
   }

  @Test
   public void Test07(){
       System.out.println("Test07: Vector Substruct test");

 Vector v1 = new Vector(1.0, 1.0, 1.0);
       Vector v2 = new Vector(-1.0, -1.0, -1.0);

  Vector vt = v1.subtract(v2);
       assertTrue(vt.compareTo(new Vector(2.0,2.0,2.0)) == 0);

  Vector vtt = v2.subtract(vt);
       assertTrue(vtt.compareTo(new Vector(-3.0,-3.0,-3.0)) == 0);
   }

  @Test
   public void Test08(){
       System.out.println("Test08: Vector Scaling test");

  Vector v1 = new Vector(1.0, 1.0, 1.0);

  Vector vt = v1.scale(1);
       assertTrue(vt.compareTo(vt) == 0);

  Vector vtt = vt.scale(2);
       assertTrue(vtt.compareTo(new Vector(2.0,2.0,2.0)) == 0);

  Vector vttt = vtt.scale(-2);
       assertTrue(vttt.compareTo(new Vector(-4.0,-4.0,-4.0)) == 0);
   }

  @Test
   public void Test09(){
       System.out.println("Test09: Vector Dot product test");


  Vector v1 = new Vector(3.5,-5,10);
       Vector v2 = new Vector(2.5,7,0.5);

  assertTrue(Double.compare(v1.dotProduct(v2), (8.75 + -35 + 5)) == 0);

 }

  @Test
   public void Test10() {
       System.out.println("Test10: Vector Length test");

  Vector v = new Vector(3.5,-5,10);
       assertTrue(v.length() ==  (12.25 + 25 + 100));
   }

  @Test
   public void Test11(){
       System.out.printf("Test11: Vector Normalize test -> ");

  Vector v = new Vector(100,-60.781,0.0001);
       System.out.printf("Length = %f  ", v.length());
       v.normalize();
       System.out.printf("Length = %f\n", v.length());

  assertEquals(1, v.length(), 1e-10, "Incorrect length after normalize! ");

  v = new Vector(0,0,0);

  try {
           v.normalize();
           fail("Didn't throw divide by zero exception!");
       } catch (ArithmeticException e) {
           assertTrue(true);
       }

  }

  @Test
   public void Test12(){
       System.out.println("Test12: Vector Cross product test");

  Vector v1 = new Vector(3.5, -5.0, 10.0);
       Vector v2 = new Vector(2.5, 7, 0.5);
       Vector v3 = v1.crossProduct(v2);


  assertEquals(0, v3.dotProduct(v2), 1e-10);
       assertEquals(0, v3.dotProduct(v1), 1e-10);

  Vector v4 = v2.crossProduct(v1);
       assertEquals( 0, vt.length(), 1e-10);
   }


}*/
//endregion
