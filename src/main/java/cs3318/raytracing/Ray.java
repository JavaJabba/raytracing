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

    //getter for the ray's origin
    public Vector3D getOrigin() {
        return origin;
    }

    //getter for the ray's direction
    public Vector3D getDirection() {
        return direction;
    }

    //getter for the intersection parameter t
    public float getT() {
        return t;
    }

    //setter for the intersection parameter t
    public void setT(float t) {
        this.t = t;
    }

    //getter for the intersected object
    public Renderable getObject() {
        return object;
    }

    //setter for the intersected object
    public void setObject(Renderable object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return ("ray origin = " + origin + "  direction = " + direction + "  t = " + t);
    }
}
