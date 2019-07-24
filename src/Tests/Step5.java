package Tests;

import Elements.*;
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

public class Step5 {
    @Test
    void Step5() {

        Scene scene = new Scene();
        scene.getCamera().setP0(new Point3D(0, 0, 300));

        Quadrangle base = new Quadrangle(new Point3D(1000, -4000, -2000), new Point3D(1000, 4000, -2000),
                new Point3D(-1000, 1500, 400), new Point3D(-1000, -1500, 400));
        base.setEmmission(new Color(143,0, 6, 188));
        scene.addGeometry(base);

        Quadrangle littleBase = new Quadrangle(new Point3D(300, -200, 200), new Point3D(300, 200, 200),
                new Point3D(-200, 200, 200), new Point3D(-200, -200, 200));
        littleBase.setEmmission(new Color(50, 54, 57));
        littleBase.getMaterial().setKt(1);
        scene.addGeometry(littleBase);

        Sphere q = new Sphere(200,new Point3D(0,0,0));
        q.setEmmission(new Color(0, 224, 1));
        scene.addGeometry(q);

        Sphere p = new Sphere(200,new Point3D(-100,-100,100));
        p.setEmmission(new Color(55, 49, 224));
        scene.addGeometry(p);


        scene.addLight(new SpotLight(new Color(0,0,0),new Point3D(1000,500,-1200),new Vector(-1,-1,1),
                0,0.00005,0.00000000005));


//********** Rendering  ************ //
        ImageWriter imageWriter = new ImageWriter("Step5", 1000, 1000, 1000, 1000);
        Render render = new Render(imageWriter, scene);

//		render.setMoreReflectedON(true);
//		render.setMoreRefractedON(true);
        render.renderImage();
        render.writeToImage();
    }
}




/*   Scene scene = new Scene();
        scene.setCamera(new Camera(new Point3D(0,100,400),
                new Vector(0,1,0),
                new Vector(0,0,1)));
//	********** Background  ************   	//

        Quadrangle qFloor = new Quadrangle(new Point3D(-600,-200, 200), // left-down corner
                new Point3D( 600, -200, 200),//right-down corner
                new Point3D( 200, -140, 500), // right-up corner
                new Point3D( -200, -140, 500));// left-up corner
        qFloor.setEmmission(new Color(100,60,20));
        qFloor.getMaterial().setShininess(80);
        qFloor.getMaterial().setKr(1);
        qFloor.getMaterial().setKt(0);

        Quadrangle qGlass = new Quadrangle(new Point3D(-400,-200, 200), // left-down corner
                new Point3D( 400, -200, 200),//right-down corner
                new Point3D( 200, -115, 320), // right-up-Opp corner
                new Point3D( -200, -115, 320));// left-up-Opp corner
        qGlass.setEmmission(new Color(40,40,40));
        qGlass.getMaterial().setShininess(80);
        qGlass.getMaterial().setKr(0.7);
        qGlass.getMaterial().setKt(0.5);
        qGlass.getMaterial().setKd(0);
        qGlass.getMaterial().setKs(0);


        // ממול קיר wall
        Quadrangle qOppWall=new Quadrangle(new Point3D( -200, 200, 500), // left-up corner
                new Point3D( -240, -300, 600),// Left-down-Far corner
                new Point3D( 240,  -300, 600), // right-down-Far corner
                new Point3D(  200,  200, 500));// left-up corner
        qOppWall.setEmmission(new Color(30,30,30));
        qOppWall.getMaterial().setShininess(10);
        qOppWall.getMaterial().setKr(1);
        qOppWall.getMaterial().setKt(0.6);

        scene.addGeometry(qGlass);
        scene.addGeometry(qFloor);
        scene.addGeometry(qOppWall);

//********** Spheres  ************ //


        // mirror sphere 2 - left up
        Sphere rightRightBack = new Sphere(60, new Point3D(130,-100,350));
        rightRightBack.setEmmission(new Color (140,30,180));
        rightRightBack.getMaterial().setShininess(10);
        rightRightBack.getMaterial().setKr(0.2);
        rightRightBack.getMaterial().setKt(0.2);


        // main blue circle
        Sphere blueBig = new Sphere(60, new Point3D(0, -100, 350));
        blueBig.setEmmission(new Color(0, 0, 150));
        blueBig.getMaterial().setShininess(20);
        blueBig.getMaterial().setKr(0.8);
        blueBig.getMaterial().setKt(0.2);

        // Inner little sphere
        Sphere leftLittleBlue = new Sphere(60, new Point3D(-130, -100, 350));
        leftLittleBlue.setEmmission(new Color(30, 60, 160));
        leftLittleBlue.getMaterial().setShininess(40);
        leftLittleBlue.getMaterial().setKr(0.2);
        leftLittleBlue.getMaterial().setKt(0.2);

        scene.addGeometry(leftLittleBlue);
//        scene.addGeometry(blueBig);
//        scene.addGeometry(rightRightBack);

//************ Lights  ************* //

        LightSource dir = new DirectionalLight(new Color(40,20,20), new Vector(1,1.5,1));

        SpotLight spotLeftMiddle = new SpotLight(new Color(90,20,20), new Point3D(-200,100,450),
                new Vector(1,-1,-1), 0, 0.00001, 0.000005);

        SpotLight spotRightBack = new SpotLight(new Color(40,40,20), new Point3D(200,100,250),
                new Vector(-1,-1,1), 0, 0.00001, 0.00005);

        PointLight up = new PointLight(new Color(100,30,30), new Point3D(0,100,300), 0, 0.00001, 0.000005);
        scene.addLight(spotRightBack);
        scene.addLight(up);
//		scene.addLight(spotLeftMiddle);
        //scene.addLight(pointLeftUP);
//		scene.addLight(dir);

//************* Triangles  ************ //

        // base triangle
        Triangle tBetweenCameraAndGeometries = new Triangle(new Point3D(1000,  -1000, 50),
                new Point3D( -1000, -1000, 50),
                new Point3D( 1000, 1000, 50));
        tBetweenCameraAndGeometries.setEmmission(new Color(20, 20, 20));
        tBetweenCameraAndGeometries.getMaterial().setKd(0);
        tBetweenCameraAndGeometries.getMaterial().setKs(0);
        tBetweenCameraAndGeometries.getMaterial().setKr(0);
        tBetweenCameraAndGeometries.getMaterial().setKt(1);

//		scene.addGeometry(tBetweenCameraAndGeometries);

//********** Rendering  ************ //
        ImageWriter imageWriter = new ImageWriter("Step5", 1000, 1000, 1000, 1000);
        Render render = new Render(imageWriter,scene);

//		render.setMoreReflectedON(true);
//		render.setMoreRefractedON(true);
        render.renderImage();
        render.writeToImage();
*/