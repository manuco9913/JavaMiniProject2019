package primitives;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    DecimalFormat df = new DecimalFormat("#.##");

    @Test
    void add() {
        //Given
        Vector v1 = new Vector(1,1,1);
        Vector v2 = new Vector(2.345,12.22,6.981);

        //When
        Vector res1 = v1.add(v1); // [2,2,2]
        Vector res2 = v1.add(v2); // [3.345,13.22,7.981]

        //Then
        assertTrue(res1.equals(new Vector(2,2,2)));
        assertTrue(res2.equals(new Vector(1+2.345,1+12.22,1+6.981)));
    }

    @Test
    void subtract() {
        //Given
        Vector v1 = new Vector(1,1,1);
        Vector v2 = new Vector(2.345,12.22,6.981);

        //When
        Vector res1 = v1.subtract(v1); // [0,0,0]
        Vector res2 = v1.subtract(v2); // [-1.345,-11.22,-5.981]

        //Then
        assertTrue(res1.equals(new Vector(0,0,0)));
        assertTrue(res2.equals(new Vector(1-2.345,1-12.22,1-6.981)));
    }

    @Test
    void scale() {
        //Given
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(2.3,1.2,6);

        //When
        Vector res1 = v1.scale(5.987897);
        Vector res2 = v2.scale(32);

        //Then
        assertTrue(res1.equals(new Vector(5.987897,2*5.987897,3*5.987897)));
        assertTrue(res2.equals(new Vector(2.3*32,1.2*32,6*32)));
    }

    @Test
    void crossProduct() {
        //Given
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(2.3,1.2,6);

        //When
        Vector res1 = v1.crossProduct(v2);
        Vector res2 = v2.crossProduct(v1);

        //Then
        assertTrue(res1.equals(new Vector(8.4,0.9,-3.4)));
        assertTrue(res2.equals(new Vector(-8.4,-0.9,3.4)));
    }

    @Test
    void length() {
        //Given
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(2.3,1.2,6);
        Vector v3 = new Vector(3,1,2);

        //When
        double res1 = v1.length();
        double res2 = v2.length();
        double res3 = v3.length();

        //Then
        assertEquals((Double.parseDouble(df.format(Math.sqrt(14)))),res1);
        assertEquals((Double.parseDouble(df.format(6.53682))),res2);
        assertNotEquals(res1,res2);
    }

    @Test
    void normalize() {
        //Given
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(2.3,1.2,6);
        double lengthV1 = v1.length();
        double lengthV2 = v2.length();
        //When
        v1.normalize();
        v2.normalize();

        //Then
        assertTrue(v1.equals(new Vector(1/lengthV1,2/lengthV1,3/lengthV1)));
        assertTrue(v2.equals(new Vector(2.3/lengthV2,1.2/lengthV2,6/lengthV2)));
       }

    @Test
    void dotProduct() {
        //Given
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(2.3,1.2,6);

        //When
        double res1 = v1.dotProduct(v2);
        double res2 = v2.dotProduct(v1);
        double res3 = v2.dotProduct(new Vector(3,3,3));

        //Then
        assertEquals(22.7,res1);
        assertEquals(res1,res2);
        assertEquals(28.5,res3);

    }
}