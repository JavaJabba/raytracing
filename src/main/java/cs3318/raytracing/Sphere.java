package cs3318.raytracing;

import javafx.scene.paint.Color;
import java.util.List;

// An example of a "Renderable" object
class Sphere implements Renderable {
    private final Surface surface;
    private final Vector3D center;
    private final float radius;
    private final float radSqr;

    /**
     *construct the Sphere
     * @param surface surface of sphere
     * @param center center of sphere
     * @param radius radius of sphere
     */
    public Sphere(Surface surface, Vector3D center, float radius) {
        this.surface = surface;
        this.center = center;
        this.radius = radius;
        this.radSqr = radius * radius;
    }

    /**
     * intersection of the spheres
     * @param ray ray
     * @return Boolean
     */
    @Override
    public boolean intersect(Ray ray) {
        float dx = center.x - ray.getOrigin().x;
        float dy = center.y - ray.getOrigin().y;
        float dz = center.z - ray.getOrigin().z;
        float v = ray.getDirection().dot(dx, dy, dz);

        // Do the following quick check to see if there is even a chance
        // that an intersection here might be closer than a previous one
        if (v - radius > ray.getT())
            return false;

        // Test if the ray actually intersects the sphere
        float discriminant = radSqr + v * v - dx * dx - dy * dy - dz * dz;
        if (discriminant < 0)
            return false;

        // Test if the intersection is in the positive
        // ray direction, and it is the closest so far
        discriminant = v - ((float) Math.sqrt(discriminant));
        if ((discriminant > ray.getT()) || (discriminant < 0))
            return false;

        ray.setT(discriminant);
        ray.setObject(this);
        return true;
    }

    /**
     * shade of the sphere
     * @param ray Ray
     * @param lights java.util.List<Object>
     * @param objects List<Object>
     * @param background Colour
     * @return surface
     */
    @Override
    public Color Shade(Ray ray, java.util.List<Object> lights, List<Object> objects, Color background) {
        // An object shader doesn't really do too much other than
        // supply a few critical bits of geometric information
        // for a surface shader. It must compute:
        //
        //   1. the point of intersection (p)
        //   2. a unit-length surface normal (n)
        //   3. a unit-length vector towards the ray's origin (v)
        //
        float px = ray.getOrigin().x + ray.getT() * ray.getDirection().x;
        float py = ray.getOrigin().y + ray.getT() * ray.getDirection().y;
        float pz = ray.getOrigin().z + ray.getT() * ray.getDirection().z;

        Vector3D p = new Vector3D(px, py, pz);
        Vector3D v = new Vector3D(-ray.getDirection().x, -ray.getDirection().y, -ray.getDirection().z);
        Vector3D n = new Vector3D(px - center.x, py - center.y, pz - center.z);
        n.normalize();

        // The illumination model is applied
        // by the surface's Shade() method
        return surface.Shade(p, n, v, lights, objects, background);
    }

    /**
     * string
     * @return string
     */
    public String toString() {
        return ("sphere " + center + " " + radius);
    }
}
