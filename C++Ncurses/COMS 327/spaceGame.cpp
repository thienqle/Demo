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

class spaceship;

class unit {
public:
  int x;
  int y;
  inline unit(){
    x =0;y=0;
    //printf("Create Unit call\n");
  }
  inline ~unit(){
    x = 0;
    y = 0;
    //printf("Delete Unit call\n");
  }
};


class spaceship {
public:
  //unit* part[3][3];
  unit* part[2][3];
  int color;
  int speed;
  int symbol;
  int status;
  int timing;
  inline spaceship(){
    int i,j;
    for(i=0;i<2;i++){
      for(j=0;j<3;j++){
	//printf("Create spaceship call\n");
	part[i][j] = new unit();
	part[i][j]->x = 0;
	part[i][j]->y = 0;
      }
    }
  }
  inline ~spaceship(){
    int i,j;
    for(i=0;i<2;i++){
      for(j=0;j<3;j++){
	delete part[i][j];
	//printf("Delete spaceship call\n");
      }
    }
  }

  inline void setPosition(int input_y,int input_x){
    int i,j;
    for(i=0;i<3;i++){
      for(j=0;j<2;j++){
	part[j][i]->x = input_x+i;
	part[j][i]->y = input_y+j;
      }
    }
  }
  inline void printSpaceShip(){
    int i,j;
    for(i=0;i<2;i++){
      for(j=0;j<3;j++){
	  attron (COLOR_PAIR(color));
	  mvaddch(part[i][j]->y,part[i][j]->x,symbol);
	  attroff (COLOR_PAIR(color));
      }
    }
  }

  inline void moveLeft(){
    if(part[0][0]->x>1+speed){
      setPosition(part[0][0]->y,part[0][0]->x-speed);
    }
  }

  inline void moveRight(){
     if(part[0][0]->x<MAX_X-3-speed){
       setPosition(part[0][0]->y,part[0][0]->x+speed);
     }
  }
  
  /*This method is refer to color flickering method of professor in class*/
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
  inline int collision(spaceship *A){
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


class bullet{
public:
  unit *pos;
  int speed;
  int symbol;
  int status; /*0 - attach, 1- shoot*/
  inline bullet(){
    //printf("construct bullet\n");
    pos = new unit();
  }

  inline ~bullet(){
    //printf("delete bullet\n");
    delete pos;
  }

  inline void setPosition(int y,int x){
    pos->x = x;
    pos->y = y;
  }

  inline void shoot(int dir){
    if(dir == 0){
      pos->y =pos->y - speed;
    } else {
      pos->y =pos->y+ speed;
    }
  }

  inline void print(){
    attron (COLOR_PAIR(COLOR_YELLOW));
    mvaddch(pos->y,pos->x,'|');
    attroff (COLOR_PAIR(COLOR_YELLOW));
  }

  inline int hit(spaceship *A){
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


class alien : public spaceship {
public:
  bullet *aBullet;
  inline void printAlien(){
    attron (COLOR_PAIR(color));
    mvaddch(part[0][0]->y,part[0][0]->x,symbol);
    mvaddch(part[0][1]->y,part[0][1]->x,symbol);
    mvaddch(part[0][2]->y,part[0][2]->x,symbol);
    mvaddch(part[1][1]->y,part[1][1]->x,symbol);
    attroff (COLOR_PAIR(color));
  }

  inline void shoot(){
    aBullet->status = 1;
    if(aBullet->pos->y<MAX_Y){
	aBullet->shoot(1); //shoot down
	aBullet->print();
    } else {
	aBullet->status = 0;
	status = 0;
	aBullet->setPosition(this->part[1][1]->y,this->part[1][1]->x);
    }
    
  }

  inline void shoot_v2(){
    if(aBullet->status ==0){
       int r = rand()%24;
       if(r==1){
	 aBullet->status = 1;
       }
    } else {
      if(aBullet->pos->y<MAX_Y){
	aBullet->shoot(1); //shoot down
	aBullet->print();
      } else {
	aBullet->status = 0;
	status = 0;
	aBullet->setPosition(this->part[1][1]->y,this->part[1][1]->x);
      }
    }
  }

  inline void shoot_v1(int &bullet_status){
    aBullet->status = 1;
      if(aBullet->pos->y<MAX_Y){
	aBullet->shoot(1); //Shoot down
	aBullet->print();
	//bullet_status =1;
      } else {
	aBullet->status = 0;
	bullet_status =0;
	status = 0;
	aBullet->setPosition(this->part[1][1]->y,this->part[1][1]->x);
      }
  }
  
  inline void moveForward(int pos_y){
    if(part[0][0]->y<=pos_y){
      setPosition(part[0][0]->y+1,part[0][0]->x);
    }
  }

};

class pc : public spaceship {
public:
  bullet *aBullet;
  inline void printPC(){
    attron (COLOR_PAIR(color));
    mvaddch(part[0][1]->y,part[0][1]->x,symbol);
    mvaddch(part[1][0]->y,part[1][0]->x,symbol);
    mvaddch(part[1][1]->y,part[1][1]->x,symbol);
    mvaddch(part[1][2]->y,part[1][2]->x,symbol);
    attroff (COLOR_PAIR(color));
  }
  inline void shoot(){
    aBullet->status = 1;
    if(aBullet->pos->y>MIN_Y){
      aBullet->shoot(0); //Shoot up*
      aBullet->print();
    } else {
      aBullet->status = 0;
      status = 0;
      aBullet->setPosition(this->part[0][1]->y,this->part[0][1]->x);
    }
  }

  inline void shoot_v1(){
    if(aBullet->status == 1){
      if(aBullet->pos->y>MIN_Y){
	aBullet->shoot(0); //Shoot up
	aBullet->print();
      } else {
	aBullet->status = 0;
	status = 0;
	aBullet->setPosition(this->part[0][1]->y,this->part[0][1]->x);
      }
    }
  }

  inline void loading(){
    aBullet->status = 0;
     status = 0;
    aBullet->setPosition(this->part[0][1]->y,this->part[0][1]->x);
  }


};



class gameStage{
public:
  pc *aPC;
  vector<alien *>aliens;
  int current_shoot_index;
  int random_shoot_index;
  bullet *freeBullet;
  int game_status;
  int bullet_status;
  int direction;
  int numAlien;
  inline gameStage(){
    // printf("Create PC\n");
    bullet_status=0;
    current_shoot_index = -1;
    game_status = 0;
    aPC = new pc();
    aPC->speed = 2;
    aPC->symbol='0';
    aPC->color=1;
    aPC->setPosition(MAX_Y-3,MAX_X/2);
    aPC->aBullet = new bullet();
    aPC->aBullet->speed = 1;
    aPC->aBullet->setPosition(aPC->part[0][1]->y,aPC->part[0][1]->x);
    
    numAlien = 24;

    int i,j;
    for(i=0;i<6;i++){
      for(j=0;j<4;j++){
	alien * temp;
	temp = new alien();
	temp->speed = 1;
	temp->symbol='V';
	int c = rand()%2;
	if (c==0) { temp->color= 2; }
	else { temp->color= 6; }
	temp->setPosition(j*3+1,MAX_X/4 +1+5*i);
	temp->aBullet = new bullet();
	temp->aBullet->speed = 1;
	temp->aBullet->setPosition(temp->part[0][1]->y,temp->part[0][1]->x);
	aliens.push_back(temp);
       }
    }
    random_shoot_index = rand()%int(aliens.size());
    // freeBullet = new bullet();
    // freeBullet->status = 0;
    direction = 1;
  }

  inline gameStage(int numOfAlien){
    
  }

  inline ~gameStage(){
    //printf("Delete PC\n");
    delete aPC->aBullet;
    delete aPC;
    //delete freeBullet;
    int i;
    for(i=0;i<int(aliens.size());i++){
      delete aliens.at(i)->aBullet;
      delete aliens.at(i);
    }
  }
  
  inline int LeftMostAlien(){
    int i;
    int leftMost = MAX_X-1;
    for(i=0;i<int(aliens.size());i++){
      if(leftMost > aliens.at(i)->part[0][0]->x){
	leftMost = aliens.at(i)->part[0][0]->x;
      }
    }
    return leftMost;
  }

  inline int RightMostAlien(){
    int i;
    int rightMost = 1;
    for(i=0;i<int(aliens.size());i++){
      if(rightMost < aliens.at(i)->part[0][2]->x){
	rightMost = aliens.at(i)->part[0][2]->x;
      }
    }
    return rightMost;
  }

  inline int ShootingAlien(){
    int i;
    int index=-1;
    int bottom = 0;
    for(i=int(aliens.size())-1;i>=0;i--){
      if(aliens.at(i)->part[1][1]->x >= aPC->part[0][0]->x &&
	 aliens.at(i)->part[1][1]->x <= aPC->part[0][2]->x){
	if(bottom <= aliens.at(i)->part[1][1]->y){
	  bottom = aliens.at(i)->part[1][1]->y;
	  index = i;
	}
      }
    }
    return index;
  }

};

void animation_render(gameStage *aLevel);
void init_terminal();
void display(gameStage *aLevel);
void print_screen();
void background(gameStage *aLevel);
void nextMoveAlien(gameStage *aLevel,int type);
void nextMoveAlien(gameStage *aLevel,int type,int &direction);
void gameEvent(gameStage *aLevel);
void gameOver(gameStage *aLevel);


void nextMoveAlien(gameStage *aLevel,int type){
  /*0 random move*/
   if(type==0){
    int dir = rand()%2;
    if(dir==0){
         int i;
	 for(i=0;i<int(aLevel->aliens.size());i++){
	   aLevel->aliens.at(i)->moveLeft();
	 }
    } else {
         int i;
	 for(i=0;i<int(aLevel->aliens.size());i++){
	   aLevel->aliens.at(i)->moveRight();
	 }

    }
  } else {

  }
}

void nextMoveAlien(gameStage *aLevel,int type,int &direction){
  if(direction == 1){
    if(aLevel->RightMostAlien()>=MAX_X-2){
      int i;
      for(i=0;i<int(aLevel->aliens.size());i++){
	aLevel->aliens.at(i)->moveForward(aLevel->aPC->part[0][0]->y);
       }
      direction = 0;
    }
  } else {
    if(aLevel->LeftMostAlien()<=MIN_X+5){
      int i;
      for(i=0;i<int(aLevel->aliens.size());i++){
	aLevel->aliens.at(i)->moveForward(aLevel->aPC->part[0][0]->y);
       }
      direction = 1;
    }
  }
    if(direction==0){
         int i;
	 for(i=0;i<int(aLevel->aliens.size());i++){
	   aLevel->aliens.at(i)->moveLeft();
	 }
    } else {
         int i;
	 for(i=0;i<int(aLevel->aliens.size());i++){
	   aLevel->aliens.at(i)->moveRight();
	 }
    }
}


/*This method is refer to method of professor in class*/
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

/*This method is refer to color flickering method of professor in class*/
void handle_input(gameStage *aLevel)
{
  int run = 1;
  int key;
  do {
    animation_render(aLevel);
    switch (key = getch()) {
    case '6':
    case 'l':
    case KEY_RIGHT:
      aLevel->aPC->moveRight();
      if(aLevel->game_status == -1) {
	run = 0; } 
      else {
	run = 1; 
      }
      break;
    case '4':
    case 'h':
    case KEY_LEFT:
      aLevel->aPC->moveLeft();
      if(aLevel->game_status == -1) {
	run = 0; } 
      else {
	run = 1; 
      }
      break;
    case ' ':
    case 's':
      if(aLevel->aPC->aBullet->status==0){
	aLevel->aPC->aBullet->status=1;
      }
      break;

    case 'Q':
    case 27:
      run = 0;
      break;
    default:
      mvprintw(0, 0, "Wrong key press: %#o ", key);
      if(aLevel->game_status == -1) {
	run = 0; } 
      else {
	run = 1; 
      }
    }
  } while (run);
}

void random_point(int &x,int &y){
  /* x must be > MIN_X and < MAX_X */
  x = rand()% (MAX_X-MIN_X-1)+ MIN_X+1;
  /* y must be > MIN_Y and < MAX_Y */
  y = rand()% (MAX_Y-MIN_Y-1)+ MIN_Y+1;
}

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


void display(gameStage *aLevel){
  clear();
  // print_screen();
  background(aLevel); 
  aLevel->aPC->printPC(); 
  //aLevel->aPC->shoot();
  aLevel->aPC->shoot_v1();

  if(aLevel->current_shoot_index==-1 && aLevel->bullet_status==0){
    int chosen_alien = aLevel->ShootingAlien();
    if(chosen_alien!=-1){
      aLevel->current_shoot_index=chosen_alien;
      aLevel->bullet_status = 1;
    }
  } else {
      aLevel->aliens.at(aLevel->current_shoot_index)->shoot_v1(aLevel->bullet_status);
  }
  
  if(aLevel->random_shoot_index < int(aLevel->aliens.size())){
    if(aLevel->aliens.at(aLevel->random_shoot_index)->status!=0){
      aLevel->aliens.at(aLevel->random_shoot_index)->shoot();
    } else {
      aLevel->random_shoot_index = rand()%int(aLevel->aliens.size());
    }
  }
  
  

  int i;
  for(i=0;i<int(aLevel->aliens.size());i++){
    // aLevel->aliens.at(i)->shoot(); 
    aLevel->aliens.at(i)->printAlien(); 
  }
  if(aLevel->game_status == -1) {  return;}

  /*int chosen_alien = aLevel->ShootingAlien();
   if(aLevel->bullet_status==0){
      if(chosen_alien!=-1){
	aLevel->current_shoot_index = chosen_alien;
	aLevel->bullet_status=1;
	aLevel->aliens.at(aLevel->current_shoot_index)->aBullet->status=1;
	//aLevel->aliens.at(aLevel->current_shoot_index)->shoot_v1(aLevel->bullet_status); 
	//mvprintw(0, 0,"%d",aLevel->current_shoot_index);
      }
    } else {
      if(aLevel->current_shoot_index!=-1){
	//mvprintw(1, 0,"%d",aLevel->current_shoot_index);
	aLevel->aliens.at(aLevel->current_shoot_index)->aBullet->status=1;
	aLevel->aliens.at(aLevel->current_shoot_index)->shoot_v1(aLevel->bullet_status); 
      }
    }

  */
  
}

void background(gameStage *aLevel){
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

   mvprintw(0,0, "Score : <%04d>",aLevel->numAlien-aLevel->aliens.size());
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

void gameEvent(gameStage *aLevel){
  int i;
   nextMoveAlien(aLevel,0,aLevel->direction); /*Type does not matter*/
   if(int(aLevel->aliens.size())==0){
     attron (COLOR_PAIR(COLOR_GREEN));
     mvprintw(MAX_Y/2,MAX_X/2, "You won!");	
     attroff(COLOR_PAIR(COLOR_GREEN));
   }
  for(i=0;i<int(aLevel->aliens.size());i++){
    if(aLevel->aPC->aBullet->status==0){
      aLevel->aPC->loading();
    }
    if(aLevel->aPC->aBullet->hit((spaceship *)aLevel->aliens.at(i))){
      aLevel->aPC->loading();
      // mvprintw(0, 0, "hit alien");
    } 
    if(aLevel->aliens.at(i)->collision((spaceship *)aLevel->aPC)){
      aLevel->game_status = -1;
      // mvprintw(0, 0, "hit PC");
      gameOver(aLevel);
      return;

    } 
    if(aLevel->aliens.at(i)->aBullet->hit((spaceship *)aLevel->aPC)){
      aLevel->game_status = -1;
      // mvprintw(0, 0, "hit PC");
      gameOver(aLevel);
      return;
    }
    if(aLevel->aliens.at(i)->status == 4){
      alien * temp =  aLevel->aliens.at(i);
      aLevel->aliens.erase(aLevel->aliens.begin() + i);
      delete temp->aBullet;
      delete temp;
    }
    if(aLevel->aliens.size()<2){
      aLevel->aliens.at(i)->speed += 4;
    }
   
  }
}

void gameOver(gameStage *aLevel){
  
   attron (COLOR_PAIR(COLOR_RED));
   mvprintw(MAX_Y/2,MAX_X/2, "GAME OVER");	
   attroff(COLOR_PAIR(COLOR_RED));
   return;
}

void animation_render(gameStage *aLevel){
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
      display(aLevel);
      gameEvent(aLevel);
      /*End animation here*/

    } while (!select(STDIN_FILENO + 1, &readfs, NULL, NULL, &tv));
 
}



int main(int arc, char *argv[]){
  srand(time(NULL));
  gameStage aLevel;
  

  init_terminal();
  print_screen();
  handle_input(&aLevel);

  refresh();
  attron (COLOR_PAIR(COLOR_RED));
  mvprintw(MAX_Y/2,MAX_X/2, "GAME OVER");	
  attroff(COLOR_PAIR(COLOR_RED));
  getch();
  clear();
  endwin();

}
