API design

Will be structured as function calls with variables built into constructors should be able to be a simple replacement for the text file input

x, y and z positions in all cases will be put within an array ffor the sake of consitency. 

Camera
Camera will be it’s own class where the constructor will be formatted as such : Camera({x,y,z},{x,y,z}) where the first set is the position of the camera and the second is the area for the camera to look at.

Light
2 kinds Directional and Abmient
LightAmbient({x,y,z})
	x,y,z coodrinates for the light source
LightDirectional({x,y,z},{x,y,z})
	First set is position of source second is are to point light towards

Shape
Shape can be limited into classes per shape that will extend a basic shape class.
ShapeSphere({x,y,z},r) will be used in place of what is already implemented
something like ShapeCube({x,y,z},L) should be easy enough to implement

Surface
Might have to implement this as a subclass to the shape object as it goes on top of a specific shape might not be too difficult to do. Surface class is extremelty difficult to decipher as it has 10 inputs.

RGB, transparency, diffuse reflect coefficient, specular reflect coefficient, specular exponent, reflectivity coefficient (in % of light reflected), transparency coefficient

Need to find a way to narrow that down to less inputs as it is too many right now

Maybe set a couple basic coefficients and provide methods to change them just to streamline the process for most users

Surface({R,G,B},)
