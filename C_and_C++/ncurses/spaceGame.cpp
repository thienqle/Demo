#include<iostream>
#include <unistd.h>
#include <ncurses.h>
#include <ctype.h>
#include <stdlib.h>
#include <vector>

using namespace std;

#define MIN_X 0
#define MIN_Y 0
#define MAX_X 60
#define MAX_Y 28

/**
* Model of a Unit of the game
*/
class Unit {

/**
* Coordinate x,y of a Unit
*/
public:
  int x;
  int y;

	/**
	* constructs a Unit
	*/
  inline Unit(){
    x =0;y=0;
  }

	/**
	* destruct a Unit
	*/
  inline ~Unit(){
    x = 0;
    y = 0;
  }
};


/**
* Model for spaceship
*/
class Spaceship {
public:
  
  /**
  * Each spaceship is 2 y-units and 3 x-units
  */		
  Unit* part[2][3];

  /**
  * color of spaceship
  */			
  int color;

  /**
  * symbol that represents spaceship
  */
  int symbol;
 
  /**
  * speed, status, and timing of spaceship
  */
  int speed;
  int status;
  int timing;

  /**
   * construct a spaceship
   */
  inline Spaceship(){
    int i,j;
    for(i=0;i<2;i++){
      for(j=0;j<3;j++){
	part[i][j] = new Unit();
	part[i][j]->x = 0;
	part[i][j]->y = 0;
      }
    }
  }

  /**
   * destruct a spaceship
   */
  inline ~Spaceship(){
    int i,j;
    for(i=0;i<2;i++){
      for(j=0;j<3;j++){
	delete part[i][j];
      }
    }
  }

 /**
  * Method that sets position a spaceship on screen
  * @param input_y the desired y coordinator
  * @param input_x the desired x coordinator
  */
  inline void setPosition(int input_y,int input_x){
    int i,j;
    for(i=0;i<3;i++){
      for(j=0;j<2;j++){
	part[j][i]->x = input_x+i;
	part[j][i]->y = input_y+j;
      }
    }
  }

 /**
  * Method that prints position a spaceship to the screen
  */
  inline void printSpaceship(){
    int i,j;
    for(i=0;i<2;i++){
      for(j=0;j<3;j++){
	  attron (COLOR_PAIR(color));
	  mvaddch(part[i][j]->y,part[i][j]->x,symbol);
	  attroff (COLOR_PAIR(color));
      }
    }
  }

	/**
	* Method that moves spaceship to the left
	*/
  inline void moveLeft(){
    if(part[0][0]->x>1+speed){
      setPosition(part[0][0]->y,part[0][0]->x-speed);
    }
  }

	/**
	* Method that moves spaceship to the right
	*/
  inline void moveRight(){
     if(part[0][0]->x<MAX_X-3-speed){
       setPosition(part[0][0]->y,part[0][0]->x+speed);
     }
  }
  

  /**
   * Method that shows shapeship exploring in space
   */
  /*This method is refer to color flickering method of professor in ISU class*/
  inline void fired(){
    fd_set readfs;
      FD_ZERO(&readfs);
      FD_SET(STDIN_FILENO, &readfs);

      int x1,y1,x2,y2,x3,y3,color_t;
      x1 = rand()% 3 + part[0][0]->x;
      y1 = rand()% 2 + part[0][0]->y;

      x2 = rand()% 3 + part[0][0]->x;
      y2 = rand()% 2 + part[0][0]->y;

      x3 = rand()% 3 + part[0][0]->x;
      y3 = rand()% 2 + part[0][0]->y;


      color_t = rand()% 7;
      attron (COLOR_PAIR(color_t));
      mvaddch(y1,x1,'*');
      mvaddch(y2,x2,'*');
      mvaddch(y3,x3,'*');
      attroff (COLOR_PAIR(color_t));
  } 

  /**
   * Method that detects collision with other spaceship
   * @param other spaceship
   * @return a collision result
   *	return 1 if it is a collision detected
   *	return 0 if it is no collision
   */
  inline int collision(Spaceship *A){
    int y,x;
    for(y=0;y<2;y++){
      for(x=0;x<3;x++){
	if(part[1][1]->x == A->part[y][x]->x && part[1][1]->y == A->part[y][x]->y){
	  A->fired();
	  A->status = 4;
	  return 1;
	}
	if(part[0][1]->x == A->part[y][x]->x && part[0][1]->y == A->part[y][x]->y){
	  A->fired();
	  A->status = 4;
	  return 1;
	}
	if(part[0][2]->x == A->part[y][x]->x && part[0][2]->y == A->part[y][x]->y){
	  A->fired();
	  A->status = 4;
	  return 1;
	}


      }
    }
    return 0;
  }  


};


/**
* Model for Bullet in game
*/
class Bullet{
public:

  /**
  * Unit of Bullet
  */
  Unit *pos;

  /**
  * speed of Bullet
  */
  int speed;

  /**
  * symbol of Bullet
  */
  int symbol;

  /**
  * status of Bullet
  * 0 - idle, 1- shoot
  */
  int status; 

  /**
   * Construct a Bullet
   */
  inline Bullet(){
    pos = new Unit();
  }

  /**
   * destruct a Bullet
   */
  inline ~Bullet(){
    delete pos;
  }

	/**
	* Method that sets position a Bullet on screen
	* @param input_y the desired y coordinator
	* @param input_x the desired x coordinator
	*/
  inline void setPosition(int y,int x){
    pos->x = x;
    pos->y = y;
  }

	/**
	* Method performs shooting a Bullet as given direction
	* @param dir: direction
	*/
  inline void shoot(int dir){
    if(dir == 0){
      pos->y =pos->y - speed;
    } else {
      pos->y =pos->y+ speed;
    }
  }

	/**
	* Method displays shooting a Bullet on screen
	*/
  inline void print(){
    attron (COLOR_PAIR(COLOR_YELLOW));
    mvaddch(pos->y,pos->x,'|');
    attroff (COLOR_PAIR(COLOR_YELLOW));
  }

	/**
	* Method that detects collision between Bullet with other spaceship
	* @param other spaceship
	* @return a collision result
	*	return 1 if it a hit
	*	return 0 if it a miss
	*/
  inline int hit(Spaceship *A){
    int y,x;
    for(y=0;y<2;y++){
      for(x=0;x<3;x++){
	if(pos->x == A->part[y][x]->x && pos->y == A->part[y][x]->y){
	  A->fired();
	  A->status = 4;
	  return 1;
	}
      }
    }
    return 0;
  }  
};


/**
*	Model of a Alien which is-a spaceship
*/
class Alien : public Spaceship {
public:

  /**
  * An alien spaceship which has-a bullet  
  */
  Bullet *bullet;
  
  /**
  * Method displays alien spaceship on screen
  */
  inline void printAlien(){
    attron (COLOR_PAIR(color));
    mvaddch(part[0][0]->y,part[0][0]->x,symbol);
    mvaddch(part[0][1]->y,part[0][1]->x,symbol);
    mvaddch(part[0][2]->y,part[0][2]->x,symbol);
    mvaddch(part[1][1]->y,part[1][1]->x,symbol);
    attroff (COLOR_PAIR(color));
  }

  /**
  * Method performs alien shoot bullet
  */
  inline void shoot(){
    bullet->status = 1;
    if(bullet->pos->y<MAX_Y){
	bullet->shoot(1); //shoot down
	bullet->print();
    } else {
	bullet->status = 0;
	status = 0;
	bullet->setPosition(this->part[1][1]->y,this->part[1][1]->x);
    }
    
  }

  /**
  * Method performs alien shoot bullet 
  */
  inline void shoot_v2(){
    if(bullet->status ==0){
       int r = rand()%24;
       if(r==1){
	 bullet->status = 1;
       }
    } else {
      if(bullet->pos->y<MAX_Y){
	bullet->shoot(1); //shoot down
	bullet->print();
      } else {
	bullet->status = 0;
	status = 0;
	bullet->setPosition(this->part[1][1]->y,this->part[1][1]->x);
      }
    }
  }

  /**
  * Method performs alien shoot bullet. It can effects other bull status of a game
  * @param bullet_status	
  */
  inline void shoot_v1(int &bullet_status){
    bullet->status = 1;
      if(bullet->pos->y<MAX_Y){
	bullet->shoot(1); //Shoot down
	bullet->print();
	    } else {
	bullet->status = 0;
	bullet_status =0;
	status = 0;
	bullet->setPosition(this->part[1][1]->y,this->part[1][1]->x);
      }
  }
  
  /**
  * Method performs alien movement. Alien always move forward one unit everytime
  * @param y coordinate the limitation of this movement	
  */
  inline void moveForward(int pos_y){
    if(part[0][0]->y<=pos_y){
      setPosition(part[0][0]->y+1,part[0][0]->x);
    }
  }

};

/**
*	Model of a pc(main player) which is-a spaceship
*/
class PC : public Spaceship {
public:

  /**
  * PC spaceship which has-a bullet  
  */
  Bullet *bullet;

  /**
  * Construct PC spaceship
  */
  inline void printPC(){
    attron (COLOR_PAIR(color));
    mvaddch(part[0][1]->y,part[0][1]->x,symbol);
    mvaddch(part[1][0]->y,part[1][0]->x,symbol);
    mvaddch(part[1][1]->y,part[1][1]->x,symbol);
    mvaddch(part[1][2]->y,part[1][2]->x,symbol);
    attroff (COLOR_PAIR(color));
  }

  /**
  * Method performs PC spaceship shooting
  */
  inline void shoot(){
    bullet->status = 1;
    if(bullet->pos->y>MIN_Y){
      bullet->shoot(0); //Shoot up*
      bullet->print();
    } else {
      bullet->status = 0;
      status = 0;
      bullet->setPosition(this->part[0][1]->y,this->part[0][1]->x);
    }
  }

  /**
  * Method performs PC spaceship shooting. It is a wrapper method
  */
  inline void shoot_v1(){
    if(bullet->status == 1){
      if(bullet->pos->y>MIN_Y){
	bullet->shoot(0); //Shoot up
	bullet->print();
      } else {
	bullet->status = 0;
	status = 0;
	bullet->setPosition(this->part[0][1]->y,this->part[0][1]->x);
      }
    }
  }

  /**
  * Method performs PC spaceship loading bullet
  */
  inline void loading(){
    bullet->status = 0;
     status = 0;
    bullet->setPosition(this->part[0][1]->y,this->part[0][1]->x);
  }


};


/**
* Model of a game stage
*/
class GameStage{
public:
	
  /**
   * GameStage has a PC
   */
  PC *pc;

  /**
   * GameStage has-a list of Aliens
   */
  vector<Alien *>aliens;
  int numAlien;
	
  /**
   * Variables that indicate shooting index
   */
  int current_shoot_index;
  int random_shoot_index;

  /**
   * Variable that manage bullet flying on the screen
   */
  Bullet *freeBullet;
  int direction;
	
  /**
   * Variable that indicates status of game objects
   */
  int game_status;
  int bullet_status;
  
  
  /**
   * Constructs a game stage
   */
  inline GameStage(){
    // printf("Create PC\n");
    bullet_status=0;
    current_shoot_index = -1;
    game_status = 0;
    pc = new PC();
    pc->speed = 2;
    pc->symbol='0';
    pc->color=1;
    pc->setPosition(MAX_Y-3,MAX_X/2);
    pc->bullet = new Bullet();
    pc->bullet->speed = 1;
    pc->bullet->setPosition(pc->part[0][1]->y,pc->part[0][1]->x);
    
    numAlien = 24;

    int i,j;
    for(i=0;i<6;i++){
      for(j=0;j<4;j++){
	Alien * temp;
	temp = new Alien();
	temp->speed = 1;
	temp->symbol='V';
	int c = rand()%2;
	if (c==0) { temp->color= 2; }
	else { temp->color= 6; }
	temp->setPosition(j*3+1,MAX_X/4 +1+5*i);
	temp->bullet = new Bullet();
	temp->bullet->speed = 1;
	temp->bullet->setPosition(temp->part[0][1]->y,temp->part[0][1]->x);
	aliens.push_back(temp);
       }
    }
    random_shoot_index = rand()%int(aliens.size());
    direction = 1;
  }


  /**
   * Destructs a game stage
   */
  inline ~GameStage(){
    delete pc->bullet;
    delete pc;
    int i;
    for(i=0;i<int(aliens.size());i++){
      delete aliens.at(i)->bullet;
      delete aliens.at(i);
    }
  }
  
  /**
   * Method returns left most alien spaceship on the screen
   * @return the index of left most alien spaceship
   */
  inline int getLeftMostAlien(){
    int i;
    int leftMost = MAX_X-1;
    for(i=0;i<int(aliens.size());i++){
      if(leftMost > aliens.at(i)->part[0][0]->x){
	leftMost = aliens.at(i)->part[0][0]->x;
      }
    }
    return leftMost;
  }

  /**
   * Method returns left most alien spaceship on the screen
   * @return the index of right most alien spaceship
   */
  inline int getRightMostAlien(){
    int i;
    int rightMost = 1;
    for(i=0;i<int(aliens.size());i++){
      if(rightMost < aliens.at(i)->part[0][2]->x){
	rightMost = aliens.at(i)->part[0][2]->x;
      }
    }
    return rightMost;
  }

  /**
   * Method returns the alien spaceship who will shoot out the bullet
   * This method choose alien which is align with PC spaceship
   * @return the index of alien will shoot out bullet
   */
  inline int ShootingAlien(){
    int i;
    int index=-1;
    int bottom = 0;
    for(i=int(aliens.size())-1;i>=0;i--){
      if(aliens.at(i)->part[1][1]->x >= pc->part[0][0]->x &&
	 aliens.at(i)->part[1][1]->x <= pc->part[0][2]->x){
	if(bottom <= aliens.at(i)->part[1][1]->y){
	  bottom = aliens.at(i)->part[1][1]->y;
	  index = i;
	}
      }
    }
    return index;
  }

};

/**
* Declaration of needed methods for game loop
*/
void render_animation(GameStage *gamePlay);
void init_terminal();
void display(GameStage *gamePlay);
void print_screen();
void print_backGround(GameStage *gamePlay);
void nextMoveAlien(GameStage *gamePlay,int type);
void nextMoveAlien(GameStage *gamePlay,int type,int &direction);
void operate_GameEvent(GameStage *gamePlay);
void operate_GameOver(GameStage *gamePlay);


/**
* Function that compute next movement of alien spaceships
* Alien will move toward to PC ships in coordinate x 
* Alien will keep moving the left or right. They will change direction if they hit the edge of the screen
* @param GameStage
* @param type of move
*/
void nextMoveAlien(GameStage *gamePlay,int type){
  /*0 random move*/
   if(type==0){
    int dir = rand()%2;
    if(dir==0){
         int i;
	 for(i=0;i<int(gamePlay->aliens.size());i++){
	   gamePlay->aliens.at(i)->moveLeft();
	 }
    } else {
         int i;
	 for(i=0;i<int(gamePlay->aliens.size());i++){
	   gamePlay->aliens.at(i)->moveRight();
	 }

    }
  } else {

  }
}

/**
* Function that compute next movement of alien spaceships with provide direction
* @param GameStage
* @param type of movement
* @param direction of movement
*/
void nextMoveAlien(GameStage *gamePlay,int type,int &direction){
  if(direction == 1){
    if(gamePlay->getRightMostAlien()>=MAX_X-2){
      int i;
      for(i=0;i<int(gamePlay->aliens.size());i++){
	gamePlay->aliens.at(i)->moveForward(gamePlay->pc->part[0][0]->y);
       }
      direction = 0;
    }
  } else {
    if(gamePlay->getLeftMostAlien()<=MIN_X+5){
      int i;
      for(i=0;i<int(gamePlay->aliens.size());i++){
	gamePlay->aliens.at(i)->moveForward(gamePlay->pc->part[0][0]->y);
       }
      direction = 1;
    }
  }
    if(direction==0){
         int i;
	 for(i=0;i<int(gamePlay->aliens.size());i++){
	   gamePlay->aliens.at(i)->moveLeft();
	 }
    } else {
         int i;
	 for(i=0;i<int(gamePlay->aliens.size());i++){
	   gamePlay->aliens.at(i)->moveRight();
	 }
    }
}


/**
* Function that initializes the game screen terminal
*/
void init_terminal()
{
  initscr();
  raw();
  noecho();
  curs_set(0);
  keypad(stdscr, TRUE);
  start_color();
  init_pair(COLOR_RED, COLOR_RED, COLOR_BLACK);
  init_pair(COLOR_GREEN, COLOR_GREEN, COLOR_BLACK);
  init_pair(COLOR_YELLOW, COLOR_YELLOW, COLOR_BLACK);
  init_pair(COLOR_BLUE, COLOR_BLUE, COLOR_BLACK);
  init_pair(COLOR_MAGENTA, COLOR_MAGENTA, COLOR_BLACK);
  init_pair(COLOR_CYAN, COLOR_CYAN, COLOR_BLACK);
  init_pair(COLOR_WHITE, COLOR_WHITE, COLOR_BLACK);
}

/**
* Function that handles the input of users and control game play
* @param GameStage current gamePlay to be controlled
*/
void handle_input(GameStage *gamePlay)
{
  int run = 1;
  int key;
  do {
    render_animation(gamePlay);
    switch (key = getch()) {
    case '6':
    case 'l':
    case KEY_RIGHT:
      gamePlay->pc->moveRight();
      if(gamePlay->game_status == -1) {
	run = 0; } 
      else {
	run = 1; 
      }
      break;
    case '4':
    case 'h':
    case KEY_LEFT:
      gamePlay->pc->moveLeft();
      if(gamePlay->game_status == -1) {
	run = 0; } 
      else {
	run = 1; 
      }
      break;
    case ' ':
    case 's':
      if(gamePlay->pc->bullet->status==0){
	gamePlay->pc->bullet->status=1;
      }
      break;

    case 'Q':
    case 27:
      run = 0;
      break;
    default:
      mvprintw(0, 0, "Wrong key press: %#o ", key);
      if(gamePlay->game_status == -1) {
	run = 0; } 
      else {
	run = 1; 
      }
    }
  } while (run);
}

/**
* Function that generates a random point
* @param x coordinate of the point
* @param y coordinate of the point
*/
void random_point(int &x,int &y){
  /* x must be > MIN_X and < MAX_X */
  x = rand()% (MAX_X-MIN_X-1)+ MIN_X+1;
  /* y must be > MIN_Y and < MAX_Y */
  y = rand()% (MAX_Y-MIN_Y-1)+ MIN_Y+1;
}

/**
* Function that displays a print_backGround game screen
*/
void print_screen(){
  int x,y;
  for(y=MIN_Y;y<MAX_Y;y++){
      mvaddch(y,MIN_X,'|');
      mvaddch(y,MAX_X,'|');
  }
  for(x=MIN_X;x<MAX_X+1;x++){
      mvaddch(MIN_Y,x,'-');
      mvaddch(MAX_Y,x,'-');
  }
}

/**
* Function that displays a game stage. Old objects of game stage will be displayed to the screen
*/
void display(GameStage *gamePlay){
  clear();
  
  print_backGround(gamePlay); 
  gamePlay->pc->printPC(); 
  gamePlay->pc->shoot_v1();

  if(gamePlay->current_shoot_index==-1 && gamePlay->bullet_status==0){
    int chosen_Alien = gamePlay->ShootingAlien();
    if(chosen_Alien!=-1){
      gamePlay->current_shoot_index=chosen_Alien;
      gamePlay->bullet_status = 1;
    }
  } else {
      gamePlay->aliens.at(gamePlay->current_shoot_index)->shoot_v1(gamePlay->bullet_status);
  }
  
  if(gamePlay->random_shoot_index < int(gamePlay->aliens.size())){
    if(gamePlay->aliens.at(gamePlay->random_shoot_index)->status!=0){
      gamePlay->aliens.at(gamePlay->random_shoot_index)->shoot();
    } else {
      gamePlay->random_shoot_index = rand()%int(gamePlay->aliens.size());
    }
  }
  
  

  int i;
  for(i=0;i<int(gamePlay->aliens.size());i++){
    gamePlay->aliens.at(i)->printAlien(); 
  }
  if(gamePlay->game_status == -1) {  return;}
 
}


/**
* Method that renders back ground of the game play
* @param GameStage game play
*/
void print_backGround(GameStage *gamePlay){
   int x,y;
   mvaddch(MAX_Y/2,MAX_X/2,'.');
   mvaddch(MAX_Y/4,MAX_X/2,'.');
   mvaddch(MAX_Y/2,MAX_Y/4,'.');

   mvaddch(MAX_Y/3,MAX_X/3,'.');
   mvaddch(MAX_Y/5,MAX_X/3,'.');
   mvaddch(MAX_Y/3,MAX_X/5,'.');

   mvaddch(MAX_Y/3+1,MAX_X/3+1,'.');
   mvaddch(MAX_Y/3+1,MAX_X/3-1,'.');
   mvaddch(MAX_Y/5-1,MAX_X/3-1,'.');
   mvaddch(MAX_Y/3-1,MAX_X/5-1,'.');

   mvaddch(MAX_Y/7+1,MAX_X/4+1,'.');
   mvaddch(MAX_Y/7+1,MAX_X/4-1,'.');
   mvaddch(MAX_Y/7-1,MAX_X/7-1,'.');
   mvaddch(MAX_Y/4-1,MAX_X/4-1,'.');


   mvaddch(MAX_Y-MAX_Y/4,MAX_X/2,'.');
   mvaddch(MAX_Y/2,MAX_Y-MAX_Y/4,'.');

   mvaddch(MAX_Y-MAX_Y/3,MAX_X-MAX_X/3,'.');
   mvaddch(MAX_Y-MAX_Y/5,MAX_X-MAX_X/3,'.');
   mvaddch(MAX_Y-MAX_Y/3,MAX_X -MAX_X/5,'.');

   mvaddch(MAX_Y-MAX_Y/3+1,MAX_X-MAX_X/3+1,'.');
   mvaddch(MAX_Y-MAX_Y/3+1,MAX_X-MAX_X/3-1,'.');
   mvaddch(MAX_Y-MAX_Y/5-1,MAX_X-MAX_X/3-1,'.');
   mvaddch(MAX_Y-MAX_Y/3-1,MAX_X-MAX_X/5-1,'.');

   mvaddch(MAX_Y-MAX_Y/7+1,MAX_X-MAX_X/4+1,'.');
   mvaddch(MAX_Y-MAX_Y/7+1,MAX_X-MAX_X/4-1,'.');
   mvaddch(MAX_Y-MAX_Y/7-1,MAX_X-MAX_X/7-1,'.');
   mvaddch(MAX_Y-MAX_Y/4-1,MAX_X-MAX_X/4-1,'.');

   mvprintw(0,0, "Score : <%04d>",gamePlay->numAlien-gamePlay->aliens.size());
   mvprintw(0,MAX_X+2, "<<INSTRUCTIONS>>");
   mvprintw(1,MAX_X+2,"4,h or <-: Left");
   mvprintw(2,MAX_X+2,"6,l or ->: Right");
   mvprintw(3,MAX_X+2,"spacebar : shoot");
   mvprintw(4,MAX_X+2,"Q or Esc : Quit");
   random_point(x,y);
   attron (COLOR_PAIR(COLOR_WHITE));
   mvaddch(y,x,'.');
   attroff (COLOR_PAIR(COLOR_WHITE));
}

/**
* Method that handles game event of game play
* @param GameStage game play
*/
void operate_GameEvent(GameStage *gamePlay){
  int i;
   nextMoveAlien(gamePlay,0,gamePlay->direction); /*Type does not matter*/
   if(int(gamePlay->aliens.size())==0){
     attron (COLOR_PAIR(COLOR_GREEN));
     mvprintw(MAX_Y/2,MAX_X/2, "You won!");	
     attroff(COLOR_PAIR(COLOR_GREEN));
   }
  for(i=0;i<int(gamePlay->aliens.size());i++){
    if(gamePlay->pc->bullet->status==0){
      gamePlay->pc->loading();
    }
    if(gamePlay->pc->bullet->hit((Spaceship *)gamePlay->aliens.at(i))){
      gamePlay->pc->loading();
    } 
    if(gamePlay->aliens.at(i)->collision((Spaceship *)gamePlay->pc)){
      gamePlay->game_status = -1;
      operate_GameOver(gamePlay);
      return;

    } 
    if(gamePlay->aliens.at(i)->bullet->hit((Spaceship *)gamePlay->pc)){
      gamePlay->game_status = -1;
      operate_GameOver(gamePlay);
      return;
    }
    if(gamePlay->aliens.at(i)->status == 4){
      Alien * temp =  gamePlay->aliens.at(i);
      gamePlay->aliens.erase(gamePlay->aliens.begin() + i);
      delete temp->bullet;
      delete temp;
    }
    if(gamePlay->aliens.size()<2){
      gamePlay->aliens.at(i)->speed += 4;
    }
   
  }
}

/**
* Method that displayes game over event
* @param GameStage game play
*/
void operate_GameOver(GameStage *gamePlay){
  
   attron (COLOR_PAIR(COLOR_RED));
   mvprintw(MAX_Y/2,MAX_X/2, "GAME OVER");	
   attroff(COLOR_PAIR(COLOR_RED));
   return;
}

/**
* Method that displayes animation of game play
* Method contains main loop of the game
* @param GameStage game play
*/
void render_animation(GameStage *gamePlay){
  fd_set readfs;
  struct timeval tv;
  do {
      FD_ZERO(&readfs);
      FD_SET(STDIN_FILENO, &readfs);

      tv.tv_sec = 0;
      //tv.tv_usec = 125000; /*1/8 second */
      tv.tv_usec = 250000;
      
      /*Start Animation here */
      refresh();    
      display(gamePlay);
      operate_GameEvent(gamePlay);
      /*End animation here*/

    } while (!select(STDIN_FILENO + 1, &readfs, NULL, NULL, &tv));
 
}


/**
* Main Method
*/
int main(int arc, char *argv[]){
  srand(time(NULL));
  GameStage gamePlay;
  

  init_terminal();
  print_screen();
  handle_input(&gamePlay);

  refresh();
  attron (COLOR_PAIR(COLOR_RED));
  mvprintw(MAX_Y/2,MAX_X/2, "GAME OVER");	
  attroff(COLOR_PAIR(COLOR_RED));
  getch();
  clear();
  endwin();

}
