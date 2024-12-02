package cs3318.raytracing;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class RayTraceAPI {
    // Define some variables
    int width, height;
    final static int CHUNKSIZE = 100;
    List<Object> objectList;
    List<Object> lightList;
    Surface currentSurface;
    Canvas canvas;
    GraphicsContext gc;

    Vector3D eye, lookat, up;
    Vector3D Du, Dv, Vp;

    float fov;
    Color background;

    // Rewriting the java file in its entirety in order to better understand the methodology and more effectively make changes in design philosophy
    public RayTraceAPI(int width, int height){
        this.width = width;
        this.height = height;
        this.background = Color.WHITE;

        // Define canvas area and fill with white
        canvas = new Canvas(this.width, this.height);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(background);
        gc.fillRect(0,0,width,height);

        // Initialise lists for management
        objectList = new ArrayList<>(CHUNKSIZE);
        lightList = new ArrayList<>(CHUNKSIZE);

        // Initialise default object values
        currentSurface = new Surface(0.8f, 0.2f, 0.9f, 0.2f, 0.4f, 0.4f, 10.0f, 0f, 0f, 1f);
        eye = new Vector3D(0,10,10);
        lookat = new Vector3D(0,0,0);
        up = new Vector3D(0,1,0);

        // Set a default field of view (the horizontal width of
        fov = 30;

        // Compute viewing matrix that maps screen coordinates to a ray direction
        Vector3D look = new Vector3D(lookat.x - eye.x, lookat.y - eye.y, lookat.z - eye.z);
        Du = Vector3D.normalize(look.cross(up));
        Dv = Vector3D.normalize(look.cross(Du));
        float fl = (float)(width / (2*Math.tan((0.5*fov)*Math.PI/180)));
        Vp = Vector3D.normalize(look);
        Vp.x = Vp.x*fl - 0.5f*(width*Du.x + height*Dv.x);
        Vp.y = Vp.y*fl - 0.5f*(width*Du.y + height*Dv.y);
        Vp.z = Vp.z*fl - 0.5f*(width*Du.z + height*Dv.z);
    }

    public void addSphere(float x, float y, float z, float r){
        Vector3D v = new Vector3D(x,y,z);
        objectList.add(new Sphere(currentSurface, v, r));
    }

    public void addSphere(float x, float y, float z, float r, Surface surface){
        Vector3D v = new Vector3D(x,y,z);
        objectList.add(new Sphere(surface, v, r));
    }

    public void addLight(int type, Vector3D v, float r, float b) {
	lightList.add(new Light(Light.type, v, r, b));
    }

    public void setSurface(Surface input){
        currentSurface = input;
    }

    public void setSurface(float r, float g, float b, float ambience, float diffuse, float specular, float phong, float reflectance, float transmission, float index){
        currentSurface = new Surface(r,g,b,ambience,diffuse,specular,phong,reflectance,transmission,index);
    }

    public void setCamera(Vector3D cameraPos, Vector3D target){
        eye = cameraPos;
        lookat = target;
    }

    public void setCamera(float camerax, float cameray, float cameraz, float targetx, float targety, float targetz){
        eye = new Vector3D(camerax,cameray,cameraz);
        lookat = new Vector3D(targetx,targety,targetz);
    }

    public void changeFov(float input){
        fov = input;
    }


}
