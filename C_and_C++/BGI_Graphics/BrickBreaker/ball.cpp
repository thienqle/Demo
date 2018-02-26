/**
* @author: Thien Le
*/
#include "ball.h"
#include <graphics.h>
#include "brick.h"
#include "paddle.h"

/**
* constructs of ball
*/
Ball::Ball()
{
    //ctor
    radius = 0;
}

/**
* constructs of ball
*/

Ball::Ball(int input_x,int input_y,int input_color,bool input_directionX,bool input_directionY,int input_speed,int input_radius)
{
    //ctor
    x = input_x;
    y = input_y;
    color = input_color;
    directionX = input_directionX;
    directionY = input_directionY;
    speed = input_speed;
    radius = input_radius;
}

/**
* Accessor to variable radius
*/
int Ball::getRadius(){
    return radius;
}

/**
* Render ball to the screen
*/
void Ball::draw(){
    setcolor(color);
    setfillstyle(SOLID_FILL,color);
    circle(x,y,radius);
    floodfill(x,y,color);
}

/**
* Method that detects collision between ball and current Brick
*/
bool Ball::detectCollision(Brick brick){
    if(x>=(brick.getX()-brick.getWidth()/2) &&
       x<=(brick.getX()+brick.getWidth()/2) &&
       y>=(brick.getY()-brick.getHeight()/2) &&
       y<=(brick.getY()+brick.getHeight()/2)){
        return true;
       }
    return false;
}

/**
* Method that detects collision between ball and paddle
*/
bool Ball::detectCollision(Paddle paddle){
    if(x>=(paddle.getX()-paddle.getWidth()/2) &&
       x<=(paddle.getX()+paddle.getWidth()/2) &&
       y>=(paddle.getY()-paddle.getHeight()/2) &&
       y<=(paddle.getY()+paddle.getHeight()/2)){
        return true;
       }
       return false;
}

/**
* destructs of ball
*/
Ball::~Ball()
{
    //dtor
}
