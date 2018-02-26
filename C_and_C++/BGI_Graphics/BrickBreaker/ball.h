#include "object.h"
#include "brick.h"
#include "paddle.h"

#ifndef BALL_H
#define BALL_H


class Ball : public Object
{
    public:
        Ball();
        Ball(int input_x,int input_y,int input_color,bool input_directionX,bool input_directionY,int input_speed,int input_radius);
        virtual ~Ball();
        void draw();
        void setRadius(int input_radius);
        int getRadius();
        bool detectCollision(Brick brick);
        bool detectCollision(Paddle paddle);

    protected:
        int radius;
    private:
};

#endif // BALL_H
