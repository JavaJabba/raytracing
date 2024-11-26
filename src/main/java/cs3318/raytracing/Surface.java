package cs3318.raytracing;

import java.awt.*;
import java.util.List;

/*
ka =  ambient reflection coefficient
kd = diffuse reflection coefficient
ks = specular reflection coefficient
kt = transmission coefficient
kr = reflectance coefficient
ns phong exponent
 */
class Surface {
    private final float ir, ig, ib;        // surface's intrinsic color
    private final float ka, kd, ks, ns;    // constants for phong model
    private final float kt, kr, nt;
    private static final float TINY = 0.001f;
    private static final float I255 = 0.00392156f;  // 1/255

    /**
     * constructs the surface
     * @param rval red value
     * @param gval green value
     * @param bval blue values
     * @param ambiance ambiance refelction coefficient
     * @param diffuse diffuse reflection coefficient
     * @param specular specular reflection transmission
     * @param phong phong exponent
     * @param reflectance reflectance coefficient
     * @param transmission transmission coefficient
     * @param index index
     */
    public Surface(float rval, float gval, float bval, float ambiance, float diffuse, float specular, float phong, float reflectance, float transmission, float index) {
        this.ir = rval;
        this.ig = gval;
        this.ib = bval;
        this.ka = ambiance;
        this.kd = diffuse;
        this.ks = specular;
        this.ns = phong;
        this.kr = reflectance * I255; //Convert reflectance coefficient to scaled value
        this.kt = transmission;
        this.nt = index;
    }

    /**
     * computes the shaded colour at a point on the surface using the Phong illumination model.
     */
    public Color Shade(Vector3D p, Vector3D n, Vector3D v, java.util.List<Object> lights, List<Object> objects, Color background) {
        float r = 0;
        float g = 0;
        float b = 0;
        for (Object lightSources : lights) {
            Light light = (Light) lightSources;
            if (light.getLightType() == LightType.AMBIENT) {
                r += ka * ir * light.getIr();
                g += ka * ig * light.getIg();
                b += ka * ib * light.getIb();
            } else {
                Vector3D l;
                if (light.getLightType() == LightType.POINT) {
                    l = new Vector3D(light.getLvec().x - p.x, light.getLvec().y - p.y, light.getLvec().z - p.z);
                    l.normalize();
                } else {
                    l = new Vector3D(-light.getLvec().x, -light.getLvec().y, -light.getLvec().z);
                }

                // Check if the surface point is in shadow
                Vector3D poffset = new Vector3D(p.x + TINY * l.x, p.y + TINY * l.y, p.z + TINY * l.z);
                Ray shadowRay = new Ray(poffset, l);
                if (shadowRay.trace(objects))
                    break;

                float lambert = Vector3D.dot(n, l);
                if (lambert > 0) {
                    if (kd > 0) {
                        float diffuse = kd * lambert;
                        r += diffuse * ir * light.getIr();
                        g += diffuse * ig * light.getIg();
                        b += diffuse * ib * light.getIb();
                    }
                    if (ks > 0) {
                        lambert *= 2;
                        float spec = v.dot(lambert * n.x - l.x, lambert * n.y - l.y, lambert * n.z - l.z);
                        if (spec > 0) {
                            spec = ks * ((float) Math.pow((double) spec, (double) ns));
                            r += spec * light.getIr();
                            g += spec * light.getIg();
                            b += spec * light.getIb();
                        }
                    }
                }
            }
        }

        // Compute illumination due to reflection
        if (kr > 0) {
            float t = v.dot(n);
            if (t > 0) {
                t *= 2;
                Vector3D reflect = new Vector3D(t * n.x - v.x, t * n.y - v.y, t * n.z - v.z);
                Vector3D poffset = new Vector3D(p.x + TINY * reflect.x, p.y + TINY * reflect.y, p.z + TINY * reflect.z);
                Ray reflectedRay = new Ray(poffset, reflect);
                if (reflectedRay.trace(objects)) {
                    Color rcolor = reflectedRay.Shade(lights, objects, background);
                    r += kr * rcolor.getRed();
                    g += kr * rcolor.getGreen();
                    b += kr * rcolor.getBlue();
                } else {
                    r += kr * background.getRed();
                    g += kr * background.getGreen();
                    b += kr * background.getBlue();
                }
            }
        }

        // Add code for refraction here

        r = (r > 1f) ? 1f : r;
        g = (g > 1f) ? 1f : g;
        b = (b > 1f) ? 1f : b;

        r = (r < 0) ? 0 : r;
        g = (g < 0) ? 0 : g;
        b = (b < 0) ? 0 : b;

        return new Color(r, g, b);
    }
}
