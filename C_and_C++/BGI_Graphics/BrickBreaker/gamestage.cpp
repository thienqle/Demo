/**
* @author: Thien Le
*/
#include <graphics.h>
#include <iostream>
#include "config.h"
#include "object.h"
#include "ball.h"
#include "paddle.h"
#include "brick.h"
#include "gamestage.h"

/**
* Construct of game stage
*/
GameStage::GameStage()
{
    //ctor
}

/**
* Construct of game stage
*/
GameStage::GameStage(int screen_width,int screen_height){

    /*Center point*/
    int x_ball = screen_width/2;
    int y_ball = screen_height/2;
    int x_paddle = screen_width/2;
    int y_paddle = 5*screen_height/6;
    int speed = 5;
    int sizeBrick = screen_width/8;
    SCREEN_WIDTH = screen_width;
    SCREEN_HEIGHT = screen_height;
    gameOver = false;

    ball = Ball(x_ball,y_ball,WHITE,0,0,speed,RADIUS);
    paddle = Paddle(x_paddle,y_paddle,YELLOW,0,0,0,BRICK_WIDTH*4,BRICK_WIDTH);

    for(int i=0;i<NO_ROW_BRICK;i++){
        for(int j=0;j<NO_COL_BRICK;j++){
            bricks[i][j] = Brick((1+j)*sizeBrick,(i+2)*2*BRICK_WIDTH,(i+2),0,0,0,BRICK_WIDTH*4,BRICK_WIDTH);
            bricks[i][j].setStatus(true);
        }
    }
}

/**
* Method that operate game
*/
void GameStage::update(){

    /*
    * Check boundaries of ball
    */
    if(ball.getY() >= SCREEN_HEIGHT-ball.getRadius() || ball.getY() <= ball.getRadius()){
         ball.setDirectionY(!ball.getDirectionY());
    }
    if(ball.getX() >= SCREEN_WIDTH-ball.getRadius() || ball.getX() <= ball.getRadius()){
         ball.setDirectionX(!ball.getDirectionX());
    }

    ball.update();
    paddle.update(SCREEN_WIDTH);
    for(int i=0;i<NO_ROW_BRICK;i++){
        for(int j=0;j<NO_COL_BRICK;j++){
            if(ball.detectCollision(bricks[i][j]) && bricks[i][j].getStatus()){
                bricks[i][j].setStatus(false);
                ball.setDirectionX(!ball.getDirectionX());
                ball.setDirectionY(!ball.getDirectionY());
            }
        }
    }

    /**
    * Ball hits paddle event
    */
    if(ball.detectCollision(paddle)){
        ball.setDirectionY(!ball.getDirectionY());
    }

    if(ball.getY()+ ball.getRadius() > SCREEN_HEIGHT){
        gameOver = true;
        return;
    }
}

/**
* Method that renders game objects
*/
void GameStage::draw(){
    if(gameOver){
        char *msg = "Game Over";
        outtextxy(SCREEN_WIDTH/2 - strlen(msg),SCREEN_HEIGHT/2,msg);
    } else {
        ball.draw();
        paddle.draw();
        drawBricks();
    }
}

/**
* Method that controls game objects
*/
void GameStage::control(){
    paddle.getControl();
}


/**
* Method that renders bricks of the game
*/
void GameStage::drawBricks(){
    for(int i=0;i<NO_ROW_BRICK;i++){
        for(int j=0;j<NO_COL_BRICK;j++){
            if(bricks[i][j].getStatus()) {
                bricks[i][j].draw();
            }
        }
    }
}

GameStage::~GameStage()
{
    //dtor
}
