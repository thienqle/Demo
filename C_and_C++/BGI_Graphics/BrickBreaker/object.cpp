/**
* @author: Thien Le
*/

#include "object.h"
#include <graphics.h>

/**
* constructs object
*/
Object::Object()
{
    //ctor
    x = 0;
    y = 0;
    color = YELLOW;
    directionX = 0;
    directionY = 0;
    speed = 0;
}

/**
* constructs object with given input
*/
Object::Object(int input_x,int input_y,int input_color,bool input_directionX,bool input_directionY,int input_speed)
{
    //ctor
    x = input_x;
    y = input_y;
    color = YELLOW;
    directionX = input_directionX;
    directionY = input_directionY;
    speed = input_speed;
}

/**
* destructs object
*/
Object::~Object()
{
    //dtor
    x = 0;
    y = 0;
}

/**
* Accessor of variable coordinate x
*/
int Object::getX(){
    return x;
}

/**
* Accessor of variable coordinate y
*/
int Object::getY(){
    return y;
}

/**
* Accessor of variable color
*/
int Object::getColor(){
    return color;
}

/**
* Accessor of variable direction X
*/
bool Object::getDirectionX(){
    return directionX;
}

/**
* Accessor of variable direction Y
*/
bool Object::getDirectionY(){
    return directionY;
}

/**
* Accessor of variable speed
*/
int Object::getSpeed(){
    return speed;
}

/**
* Accessor of variable status
*/
bool Object::getStatus(){
    return status;
}

/**
* mutator of variable coordinate x
*/
void Object::setX(int input_x){
    x = input_x;
}

/**
* mutator of variable coordinate y
*/
void Object::setY(int input_y){
    y = input_y;
}

/**
* mutator of variable color
*/
void Object::setColor(int input_color){
    color = input_color;
}

/**
* mutator of variable direction on x-axis
*/
void Object::setDirectionX(int input_x){
    directionX = input_x;
}

/**
* mutator of variable direction on y-axis
*/
void Object::setDirectionY(int input_y){
    directionY = input_y;
}

/**
* mutator of variable speed
*/
void Object::setSpeed(int input_speed){
    speed = input_speed;
}


/**
* mutator of variable status
*/
void Object::setStatus(bool input_status){
    status = input_status;
}

/**
* get next location of current status
*/
void Object::update(){
    if(directionX){
         x = x + speed;
     } else {
         x = x - speed;
     }
     if(directionY){
         y = y + speed;
     } else {
         y = y - speed;
     }
}
