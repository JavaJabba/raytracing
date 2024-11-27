package cs3318.raytracing;

/**
 * represents a light source in the ray tracing system
 * supports ambient, directional and point light types.
 */
enum LightType {
    ;

    public static final int AMBIENT = 0;
    public static final int DIRECTIONAL = 1;
    public static final int POINT = 2;
}
// All the public variables here are ugly, but I
// wanted Lights and Surfaces to be "friends"
class Light {

    private final int lightType;
    private Vector3D lvec;           // the position of a point light or
    // the direction to a directional light
    private final float ir;
    private final float ig;
    private final float ib;        // intensity of the light source

    /**
     * constructs a light source
     * @param type
     * @param v
     * @param r
     * @param g
     * @param b
     */
    public Light(int type, Vector3D v, float r, float g, float b) {
        this.lightType = type;
        this.ir = r;
        this.ig = g;
        this.ib = b;
        if (type != LightType.AMBIENT) {
            this.setLvec(v);
            if (type == LightType.DIRECTIONAL) {
                this.getLvec().normalize();
            }
        }
    }

    /**
     * getters for the light source
     * @return
     */


    /**
     * getters for the vector associated with the light
     * @return
     */


    /**
     * gets the intensity of the green channel of light
     * @return
     */

    /**
     * gets the intensity of the blue channel of the light
     * @return the blue intensity
     */


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Light{");
        sb.append("lightType=").append(getLightType());
        sb.append(", ir=").append(getIr());
        sb.append(", ig=").append(getIg());
        sb.append(", ib=").append(getIb());
        if (getLightType() == LightType.AMBIENT) {
            sb.append(", lightType=AMBIENT");
        }
        if (getLightType() == LightType.DIRECTIONAL) {
            sb.append(", lightType=DIRECTIONAL");
        }
        sb.append("}");

        return sb.toString();
    }

    public int getLightType() {
        return lightType;
    }

    public Vector3D getLvec() {
        return lvec;
    }

    public void setLvec(Vector3D lvec) {
        this.lvec = lvec;
    }

    public float getIr() {
        return ir;
    }

    public float getIg() {
        return ig;
    }

    public float getIb() {
        return ib;
    }
}
