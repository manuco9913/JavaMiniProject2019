package Elements;

import Primitives.*;

import java.awt.*;

public interface LightSource {

    Color getIntensity(Point3D point);
    Vector getL(Point3D point); // light to point vector
}
