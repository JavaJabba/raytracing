package cs3318.raytracing;

import java.awt.*;
import java.util.List;

// An example "Renderable" object
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
        float dx = center.x - ray.origin.x;
        float dy = center.y - ray.origin.y;
        float dz = center.z - ray.origin.z;
        float v = ray.direction.dot(dx, dy, dz);

        // Do the following quick check to see if there is even a chance
        // that an intersection here might be closer than a previous one
        if (v - radius > ray.t)
            return false;

        // Test if the ray actually intersects the sphere
        float discriminant = radSqr + v * v - dx * dx - dy * dy - dz * dz;
        if (discriminant < 0)
            return false;

        // Test if the intersection is in the positive
        // ray direction and it is the closest so far
        discriminant = v - ((float) Math.sqrt(discriminant));
        if ((discriminant > ray.t) || (discriminant < 0))
            return false;

        ray.t = discriminant;
        ray.object = this;
        return true;
    }

    /**
     * shade of the sphere
     * @param ray Ray
     * @param lights java.util.List<Oblect>
     * @param objects List<Object>
     * @param background Colour
     * @return surface
     */
    @Override
    public Color Shade(Ray ray, java.util.List<Object> lights, List<Object> objects, Color background) {
        // An object shader doesn't really do too much other than
        // supply a few critical bits of geometric information
        // for a surface shader. It must must compute:
        //
        //   1. the point of intersection (p)
        //   2. a unit-length surface normal (n)
        //   3. a unit-length vector towards the ray's origin (v)
        //
        float px = ray.origin.x + ray.t * ray.direction.x;
        float py = ray.origin.y + ray.t * ray.direction.y;
        float pz = ray.origin.z + ray.t * ray.direction.z;

        Vector3D p = new Vector3D(px, py, pz);
        Vector3D v = new Vector3D(-ray.direction.x, -ray.direction.y, -ray.direction.z);
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
