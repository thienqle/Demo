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

using namespace std;


/**
* Main function of the game
*/
int main()
{
   initwindow(MAX_HEIGHT,MAX_WIDTH, "Brick Breaker");
    GameStage game = GameStage(getmaxx(),getmaxy());

    /**
    * Main loop of a game
    */
    bool run = true;
    while(run)
    {
         game.draw();
         game.control();
         game.update();
         delay(50); //animation
         cleardevice(); //refresh screen to redraw


    }
    return 0;
}


