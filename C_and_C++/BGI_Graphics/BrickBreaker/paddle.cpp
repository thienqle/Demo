#include "paddle.h"
#include "object.h"
#include "config.h"
#include <conio.h>
#include <stdio.h>
#include <graphics.h>
#include <string.h>
#include <iostream>

using namespace std;

/**
* Construct paddle
*/
Paddle::Paddle()
{
    //ctor
    width = 0;
    height = 0;
}

/**
* Construct paddle with given inputs
*/
Paddle::Paddle(int input_x,int input_y,int input_color,bool input_directionX,bool input_directionY,int input_speed,int input_width,int input_height)
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

Paddle::~Paddle()
{
    //dtor
}

/**
* accessor of variable height
*/
int Paddle::getHeight(){
    return height;
}

/**
* accessor of variable width
*/
int Paddle::getWidth(){
    return width;
}

/**
* mutator of variable height
*/
void Paddle::setHeight(int input_height){
    height = input_height;
}

/**
* mutator of variable width
*/
void Paddle::setWidth(int input_width){
    width = input_width;
}

/**
* Render paddle to the screen
*/
void Paddle::draw(){
    setcolor(color);
    setfillstyle(SOLID_FILL,color);
    rectangle(x-width/2,y-height/2,x+width/2,y+height/2);
    floodfill(x,y,color);
}

/**
* Render paddle to the screen (override base class)
*/
void Paddle::update(int screen_width){
    if(directionX && (x+width/2) < screen_width){
         x = x + speed;
     } else if (!directionX && (x-width/2) > 0){
         x = x - speed;
     }
     speed = 0;
}

/**
* Render paddle to the screen
*/
void Paddle::getControl(){
    if(kbhit()){
        int key = getch();
        switch (key) {
            case '6':
            case KEY_RIGHT:
              directionX = true;
              speed = PADDLE_SPEED;
              //update();
              break;
            case '4':
            case KEY_LEFT:
              directionX = false;
              speed = PADDLE_SPEED;
              //update();
              break;
            case 'Q':
            case 27:
                exit(0);
              break;
            default:
                speed = 0;
        }
    }
}
