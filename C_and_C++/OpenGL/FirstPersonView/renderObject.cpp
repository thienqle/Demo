/**
 * Thien Le
 * Application that render a 3d scene
 */
#include <GL/glut.h>
#include "scene.h"
#include <math.h> 
#include <iostream>
using namespace std;

#define GRID_SPACING 0.25
#define PI 3.14
#define SCREEN_WIDTH 800
#define SCREEN_HEIGHT 600

// Rotation for the camera direction
float angle=0;

// Direction of the camera
float dirX=0;
float dirZ=-1;

// Position of the camera
float posX=3;
float posZ=6;

Scene scene;

void renderGridLine(); /*Draw grid lane with giving spacing */
void initPerspetive();
void display(); /* Function that render a scene */
void renderObject(Object &a,int type); /*Helper function that render an object*/
void renderScene();
void renderPrimitiveObject(Object &a,int type);
void exitKeyListener(unsigned char key, int x, int y);
void controlKeysLitener(int key, int x, int y);
void renderGround();
void depth_buffer_setup();
void depth_buffer_clearing();
void lighting_setup();

int main(int argc, char** argv)
{
  glutInit(&argc, argv);
  //glutInitDisplayMode(GLUT_SINGLE); //Init with single buffer window
  /* Init with double buffer window (helps to smooth animation)
   * Init with GLUT_DEPTH for depth
   */
  glutInitDisplayMode(GLUT_DOUBLE | GLUT_DEPTH); 
  glutInitWindowSize(SCREEN_WIDTH,SCREEN_HEIGHT);     //Init window size
  glutInitWindowPosition(100, 100); //Init window poistion on the screen
  glutCreateWindow("Render object");

  depth_buffer_setup();
  lighting_setup();
  glutDisplayFunc(renderScene);
  glutKeyboardFunc(exitKeyListener);
  glutSpecialFunc(controlKeysLitener);
  glutMainLoop();
  return 0;
}


/**
 * Function that renders the scene
 */
void renderScene(){
  depth_buffer_clearing(); //Without clearing old rendering of amination still there
  glLoadIdentity();
  // camera control affects here
  gluLookAt(posX,1,posZ,posX+dirX,1,posZ+dirZ,0,1,0);
  renderObject(scene.ground,GL_QUADS); 
  int i;
  for(i=0;i<100;i++){
    renderPrimitiveObject(scene.cube[i],1);
  }
  initPerspetive(); 
  glutSwapBuffers();
  glutPostRedisplay(); //Animation instead of glFlush
  //glFlush();
}

/**
 * Depth buffer setup
 */
void depth_buffer_setup(){
  //glDepthMask(GL_TRUE);
  glEnable(GL_DEPTH_TEST);
  //glDepthFunc(GL_LEQUAL);
}

/**
 * Depth buffer clearing
 */
void depth_buffer_clearing(){
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
}

/**
 * lightning setup
 */
void lighting_setup(){
  GLfloat diffuse[] = {0.5,0.5,0.5, 1.0};
  GLfloat position_light[] = {1.0, 1.0, 1.0, 0.0};
  GLfloat specular[] = { 1.0, 1.0, 1.0, 1.0 };
  GLfloat ambient[] = { 0.5, 0.5,0.5,0.0 };
  //Lightning
  glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuse);
  glLightfv(GL_LIGHT0, GL_POSITION, position_light);
  glLightModelfv(GL_LIGHT_MODEL_AMBIENT,ambient);

  
   //Enable lightning
  glEnable(GL_LIGHT0);
  glEnable(GL_LIGHTING);

}


/**
 * Function that renders a object with given vertex and color
 * @param given object
 * @param type of render
 */
void renderObject(Object &a,int type){
  int i=0;
  glTranslatef(a.getPosX(),a.getPosY(),a.getPosZ()); 
  glBegin(type);
  for(i=0;i<a.getNumOfVertices();i++){
    if(type==GL_QUADS){
      if(i%4==0 || i==0){
	double *tmp_color = a.getColor(i);
	GLfloat color_diffuse[] = { tmp_color[0],tmp_color[1],tmp_color[2], 1.0 };
	glMaterialfv(GL_FRONT, GL_AMBIENT,color_diffuse);
	glEnable(GL_COLOR_MATERIAL);
	glColorMaterial(GL_FRONT, GL_DIFFUSE);
	glColor3f(tmp_color[0],tmp_color[1],tmp_color[2]);
	delete(tmp_color);
      }
    } else if (type==GL_POLYGON){
      if(i%3==0 || i==0){
	double *tmp_color = a.getColor(i);
	GLfloat color_diffuse[] = { tmp_color[0],tmp_color[1],tmp_color[2], 1.0 };
	glMaterialfv(GL_FRONT, GL_AMBIENT,color_diffuse);
	glEnable(GL_COLOR_MATERIAL);
	glColorMaterial(GL_FRONT, GL_DIFFUSE);
	glColor3f(tmp_color[0],tmp_color[1],tmp_color[2]);
	delete(tmp_color);
      }
    }
    double *tmp_vertice = a.getVertex(i);
    glVertex3f(tmp_vertice[0],tmp_vertice[1],tmp_vertice[2]);
    delete(tmp_vertice);
  }
  glEnd();
}

/**
 * Function that renders a basic OPenGL object such as cone or sphere
 * @param given object
 * @param type of render
 */
void renderPrimitiveObject(Object &a,int type){
  glPushMatrix();
  if(type==0){
    int i;
    for(i=0;i<a.getNumOfColors();i++){
      double *tmp_color = a.getColor(i);
      GLfloat color_diffuse[] = { tmp_color[0],tmp_color[1],tmp_color[2], 1.0 };
      glMaterialfv(GL_FRONT, GL_AMBIENT,color_diffuse);
      glEnable(GL_COLOR_MATERIAL);
      glColorMaterial(GL_FRONT, GL_DIFFUSE);
      glColor3f(tmp_color[0],tmp_color[1],tmp_color[2]);
      delete(tmp_color);
    }
    glTranslatef(a.getPosX(),a.getPosY(),a.getPosZ()); 
    glutSolidSphere(a.getSize(),20,20);
  } else if(type==1){
    int i;
    for(i=0;i<a.getNumOfColors();i++){
      double *tmp_color = a.getColor(i);
      GLfloat color_diffuse[] = { tmp_color[0],tmp_color[1],tmp_color[2], 1.0 };
      glMaterialfv(GL_FRONT, GL_AMBIENT,color_diffuse);
      glEnable(GL_COLOR_MATERIAL);
      glColorMaterial(GL_FRONT, GL_DIFFUSE);
      glColor3f(tmp_color[0],tmp_color[1],tmp_color[2]);
    }
    glTranslatef(a.getPosX(),a.getPosY(),a.getPosZ()); 
    glutSolidCube(a.getSize());
  } else if(type==2){
    int i;
    for(i=0;i<a.getNumOfColors();i++){
      double *tmp_color = a.getColor(i);
      glColor3f(tmp_color[0],tmp_color[1],tmp_color[2]);
      delete(tmp_color);
    }
    glTranslatef(a.getPosX(),a.getPosY(),a.getPosZ()); 
    glutSolidCone(a.getSize(),a.getSize(),10,2);
  }
  glPopMatrix();

}


void exitKeyListener(unsigned char key, int x, int y){
  switch (key) {
  case 27: 
  case 'q': 
  case 'Q':
    exit(0);
   break;
  }
}

void controlKeysLitener(int key, int x, int y){
  float speed_move = 0.05f;
  float speed_rotate = 0.01f;
  switch (key) {
  case 8:
  case GLUT_KEY_UP :
    posX+= dirX * speed_move;
    posZ+= dirZ * speed_move;
    break;
  case 2:
  case 5:
  case GLUT_KEY_DOWN :
    posX -= dirX * speed_move; //
    posZ -= dirZ * speed_move;
    break;
  case 4:
  case GLUT_KEY_LEFT :
    if(angle<-360){
      angle==0;
    }
    angle -= speed_rotate;
    dirX = sin(angle); //dir part of vector being rotate
    dirZ = -cos(angle);//dir as part of vector being rotate
    break;
  case 6:
  case GLUT_KEY_RIGHT :
    if(angle>360){
      angle==0;
    }
    angle += speed_rotate;
    dirX = sin(angle);
    dirZ = -cos(angle);
    break;
  }
}

void initPerspetive(){
  double ratio = (double)SCREEN_WIDTH/SCREEN_HEIGHT;
  glViewport(0, 0,SCREEN_WIDTH,SCREEN_HEIGHT);
  glMatrixMode(GL_PROJECTION); /*For clipping*/
  glLoadIdentity();
  //glFrustum(-1,1,-1,1,1,50);
  //glFrustum(-1*ratio,1*ratio,-1,1,1.5,20); //do not set far end to far, wierd happens
  gluPerspective(45.0f,ratio,0.1,100);
  glMatrixMode(GL_MODELVIEW);
}


