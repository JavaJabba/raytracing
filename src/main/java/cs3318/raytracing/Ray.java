package cs3318.raytracing;

import java.awt.*;
import java.util.List;

class Ray {
    public static final float MAX_T = Float.MAX_VALUE;
    Vector3D origin;
    Vector3D direction;
    float t;
    Renderable object;

    public Ray(Vector3D eye, Vector3D dir) {
        this.origin = new Vector3D(eye);
        this.direction = Vector3D.normalize(dir);
    }

    public boolean trace(List<Object> objects) {
        this.t = MAX_T;
        this.object = null;
        for (Object objList : objects) {
            Object object = (Object) objList;
            object.intersect(this);
        }
        return (object != null);
    }

    // The following method is not strictly needed, and most likely
    // adds unnecessary overhead, but I prefered the syntax
    //
    //            ray.Shade(...)
    // to
    //            ray.object.Shade(ray, ...)
    //
    public final Color Shade(List<Object> lights, List<Object> objects, Color background) {
        return object.Shade(this, lights, objects, background);
    }

    public String toString() {
        return ("ray origin = " + origin + "  direction = " + direction + "  t = " + t);
    }
}
