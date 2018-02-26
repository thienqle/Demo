/**
* @author: Thien Le
*/
#ifndef GAMESTAGE_H
#define GAMESTAGE_H

#include <graphics.h>
#include <iostream>
#include "config.h"
#include "object.h"
#include "ball.h"
#include "paddle.h"
#include "brick.h"


/**
* Model of a Game Stage
*/
class GameStage
{
    public:
        GameStage();
        GameStage(int screen_width,int screen_height);
        virtual ~GameStage();
        void drawBricks();
        void update();
        void draw();
        void control();

    protected:
        Ball ball;
        Paddle paddle;
        Brick bricks[NO_ROW_BRICK][NO_COL_BRICK];
        int SCREEN_WIDTH;
        int SCREEN_HEIGHT;
        bool gameOver;

    private:
};

#endif // GAMESTAGE_H
