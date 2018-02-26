/**
* @author: Thien Le
*/

#include "object.h"

#ifndef BRICK_H
#define BRICK_H

/**
* Model of brick object of the game
*/
class Brick : public Object
{
    public:
        Brick();
        Brick(int input_x,int input_y,int input_color,bool input_directionX,bool input_directionY,int input_speed,int input_width,int input_height);
        virtual ~Brick();
        int getWidth();
        int getHeight();
        void setWidth(int input_width);
        void setHeight(int input_height);
        void draw();
        void getControl();
        void update();
        void getNextLocation();

    protected:
        int width;
        int height;

    private:
};

#endif // BRICK_H
