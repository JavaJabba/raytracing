# Raytracing Software

## Overview

This program is a raytracing engine designed with a programmatic approach, providing the user a flexible API for defining scenes. Using the API, you can create and configure objects, lights, and surfaces, and add them to the scene that will be rendered.

### Features

- Create and add objects to an objectList
- Define and add lights to a lightList
- Configure surfaces with detailed properties (color, reflectance, transmisskon etc)

The overloading methods within provide convenience for direct object creation or reusing default existing values

This raytracing engine will 

- Handle lighting calculations, including ambient, diffuse and specular components
- Support reflection, transmission, and refraction effects
- Implement shading for realistic surface rendering

### How to use

You can define surface properties and apply them to objects :

```
RayTraceAPI.setSurface(r, g, b, ambience, diffuse, specular, phong, reflectance, transmission, index);
RayTraceAPI.setSurface(currentSurface);
```

You can instantiate objects and add them to the objectList :

```
RayTraceAPI.addSphere(x, y, z, r, surface);
```

You can instantiate light objects and add them to the lightList:

```
RayTraceAPI.addLight(type, v, r, b);
```

You can set the camera :

```
RayTraceAPI.setCamera(camerax, cameray, cameraz, targetx, targety, targetz);
RayTraceAPI.setCamera(cameraPos, target);
```

You can change the FOV :

```
RayTraceAPI.changeFOV(input);
```
