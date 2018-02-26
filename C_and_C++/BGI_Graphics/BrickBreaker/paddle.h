/**
* @author: Thien Le
*/
#include "object.h"
#ifndef PADDLE_H
#define PADDLE_H


class Paddle  : public Object
{
    public:
        Paddle();
        Paddle(int input_x,int input_y,int input_color,bool input_directionX,bool input_directionY,int input_speed,int input_width,int input_height);
        virtual ~Paddle();
        int getWidth();
        int getHeight();
        int getStatus();
        void setWidth(int input_width);
        void setHeight(int input_height);
        void setStatus();
        void draw();
        void getControl();
        void update(int screen_width);
        void getNextLocation();

    protected:
        int width;
        int height;

    private:
};

#endif // PADDLE_H
