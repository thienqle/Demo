/**
* @author: Thien Le
*/

#ifndef OBJECT_H
#define OBJECT_H

/**
* Model of object of the game
*/
class Object
{
    public:
        Object();
        Object(int input_x,int input_y,int input_color,bool input_directionX,bool input_directionY,int input_speed);
        virtual ~Object();
        int getX();
        int getY();
        int getSpeed();
        bool getDirectionX();
        bool getDirectionY();
        int getColor();
        bool getStatus();
        void update();
        void setX(int input_x);
        void setY(int input_y);
        void setDirectionX(int input_x);
        void setDirectionY(int input_y);
        void setColor(int input_color);
        void setSpeed(int input_speed);
        void setStatus(bool input_status);

    protected:
        int x;
        int y;
        int color;
        int speed;
        bool directionX;
        bool directionY;
        bool status;

    private:


};

#endif // OBJECT_H
