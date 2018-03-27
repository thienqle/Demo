/**
 * @author: Thien Le
 * Model of an Object
 */
#include "object.h"
#include <math.h>

/**
 * Construct of object
 */
Object::Object(){
  numOfVertices = 0;
  numOfColors = 0;
  posX = 0;
  posY = 0;
  posZ = 0;
}

/**
 * Destruct of object
 */
Object::~Object(){
  numOfVertices = 0;
  numOfColors = 0;
  posX = 0;
  posY = 0;
  posZ = 0;
  vertices.clear();
  colors.clear();
}

Object::Object(const Object &input){
  numOfVertices = input.getNumOfVertices();
  numOfColors = input.getNumOfColors();
  posX = input.getPosX();
  posY = input.getPosY();
  posZ = input.getPosZ();
  
  int i;
  for(i=0;i<numOfVertices;i++){
    double *tmp = input.getVertex(i); 
    vertices.push_back(tmp[0]);
    vertices.push_back(tmp[1]);
    vertices.push_back(tmp[2]);
    delete tmp;
  }
  for(i=0;i<numOfColors;i++){
    double *tmp = input.getColor(i); 
    vertices.push_back(tmp[0]);
    vertices.push_back(tmp[1]);
    vertices.push_back(tmp[2]);
    delete tmp;
  }
}

Object& Object::operator=(const Object& input){
  numOfVertices = input.getNumOfVertices();
  numOfColors = input.getNumOfColors();
  posX = input.getPosX();
  posY = input.getPosY();
  posZ = input.getPosZ();
  
  int i;
  for(i=0;i<numOfVertices;i++){
    double *tmp = input.getVertex(i); 
    vertices.push_back(tmp[0]);
    vertices.push_back(tmp[1]);
    vertices.push_back(tmp[2]);
    delete tmp;
  }
  for(i=0;i<numOfColors;i++){
    double *tmp = input.getColor(i); 
    vertices.push_back(tmp[0]);
    vertices.push_back(tmp[1]);
    vertices.push_back(tmp[2]);
    delete tmp;
  }
  return *this;
}

/**
 * Method that added vertice to object
 */
void Object::addVertex(double x,double y,double z){
  vertices.push_back(x);
  vertices.push_back(y);
  vertices.push_back(z);
  numOfVertices++;
}

/**
 * Method that added vertice to object
 */
void Object::addColor(double x,double y,double z){
  colors.push_back(x);
  colors.push_back(y);
  colors.push_back(z);
  numOfColors++;
}

/**
 * Method that n vertice at given index
 * @param given index
 * @return vertex coordinate
 */ 
double* Object::getVertex(int index) const{
  double *output = new double[3]; 
  output[0] = vertices.at(index*3);
  output[1] = vertices.at(index*3+1);
  output[2] = vertices.at(index*3+2);
  return output; //Remember to free this
}

/**
 * Method that return color at given index
 * @param given index
 * @return vertex coordinate
 */ 
double* Object::getColor(int index) const{
  double *output = new double[3]; 
  output[0] = colors.at(index*3);
  output[1] = colors.at(index*3+1);
  output[2] = colors.at(index*3+2);
  return output; //Remember to free this
}

/**
 * Method that return number of vertices of object
 */ 
int Object::getNumOfVertices() const{
   return numOfVertices;
}


/**
 * Method that return number of colors of object
 */ 
int Object::getNumOfColors() const{
   return numOfColors;
}

/**
 * Accessor of posX
 * @return posX
 */
double Object::getPosX() const{
  return posX;
}

/**
 * Accessor of posY
 * @return posY
 */
double Object::getPosY() const{
  return posY;
}

/**
 * Accessor of posZ
 * @return posZ
 */
double Object::getPosZ() const{
  return posZ;
}

/**
 * Accessor of dirX
 * @return dirX
 */
double Object::getDirX() const{
  return dirX;
}

/**
 * Accessor of dirY
 * @return dirY
 */
double Object::getDirY() const{
  return dirY;
}

/**
 * Accessor of dirZ
 * @return dirZ
 */
double Object::getDirZ() const{
  return dirZ;
}


/**
 * Accessor of posZ
 * @return posZ
 */
double Object::getSize() const{
  return size;
}


/**
 * Set size of object
 */
void Object::setSize(double s){
  size = s;
}

/**
 * Set position of object
 */
void Object::setPosition(double x,double y,double z){
  posX = x;
  posY = y;
  posZ = z;
}

/**
 * Set position of object
 */
void Object::setDirection(double x,double y,double z){
  dirX = x;
  dirY = y;
  dirZ = z;
}

/**
 * Rotate around center point with given speed and radius
 * @param delta
 * @param radius
 */
void Object::update_rotation(double delta,double radius){
    if(angle>360){
      angle==0;
    }
    angle += delta;
    posX = radius*cos(angle);
    posZ = radius*sin(angle);
}
