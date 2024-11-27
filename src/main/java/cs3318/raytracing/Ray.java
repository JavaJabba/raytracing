package cs3318.raytracing;

import java.awt.*;
import java.util.List;

/**
 * class Ray
 */
class Ray {
    public static final float MAX_T = Float.MAX_VALUE;
    private final Vector3D origin;
    private final Vector3D direction;
    private float t;
    private Renderable object;

    /**
     * consdtructor for Ray
     * @param eye eye
     * @param dir direction
     */
    public Ray(Vector3D eye, Vector3D dir) {
        this.origin = new Vector3D(eye);
        this.direction = Vector3D.normalize(dir);
    }

    /**
     * trace method to determine if the ray intersects any objects
     * @param objects objects
     * @return object not null
     */
    public boolean trace(List<Object> objects) {
        this.t = MAX_T;
        this.object = null;
        for (Object objList : objects) {
            Renderable object = (Renderable) objList;
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

    /**
     * shade method to compute the colour of the object intersected by the ray
     * @param lights List<Object>
     * @param objects List<Object>
     * @param background Colour
     * @return object
     */
    public final Color Shade(List<Object> lights, List<Object> objects, Color background) {
        return object.Shade(this, lights, objects, background);
    }

    /**
     * getter for the ray's origin
     * @return origin
     */
    public Vector3D getOrigin() {
        return origin;
    }

    /**
     * getter for the ray's direction
     * @return direction
     */
    public Vector3D getDirection() {
        return direction;
    }

    /**
     * getter for the intersection parameter
     * @return t
     */
    public float getT() {
        return t;
    }

    /**
     * setter for the intersection parameter t
     * @param t
     */
    public void setT(float t) {
        this.t = t;
    }

    /**
     * getter for the intersected object
     * @return object
     */
    public Renderable getObject() {
        return object;
    }

    /**
     * setter for the intersected object
     * @param object
     */
    public void setObject(Renderable object) {
        this.object = object;
    }

    /**
     * string method
     * @return string
     */
    @Override
    public String toString() {
        return ("ray origin = " + origin + "  direction = " + direction + "  t = " + t);
    }
}
