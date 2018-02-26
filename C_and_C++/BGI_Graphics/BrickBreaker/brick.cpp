/**
* @author: Thien Le
*/
#include "brick.h"
#include "object.h"
#include <graphics.h>

Brick::Brick()
{
    //ctor
}

/**
* Construct paddle with given inputs
*/
Brick::Brick(int input_x,int input_y,int input_color,bool input_directionX,bool input_directionY,int input_speed,int input_width,int input_height)
{
    //ctor
    x = input_x;
    y = input_y;
    color = input_color;
    directionX = input_directionX;
    directionY = input_directionY;
    speed = input_speed;
    width = input_width;
    height = input_height;
}


Brick::~Brick()
{
    //dtor
}

/**
* accessor of variable height
*/
int Brick::getHeight(){
    return height;
}

/**
* accessor of variable width
*/
int Brick::getWidth(){
    return width;
}

/**
* mutator of variable height
*/
void Brick::setHeight(int input_height){
    height = input_height;
}

/**
* mutator of variable width
*/
void Brick::setWidth(int input_width){
    width = input_width;
}

/**
* Render paddle to the screen
*/
void Brick::draw(){
    if(status){
        setcolor(color);
        setfillstyle(SOLID_FILL,color);
        rectangle(x-width/2,y-height/2,x+width/2,y+height/2);
        floodfill(x,y,color);
    }
}

/**
* Render paddle to the screen (override base class)
*/
void Brick::update(){
    if(directionX){
         x = x + speed;
     } else {
         x = x - speed;
     }
     speed = 0;
}

