#include <GL/glut.h>
#include "scene.h"

#define GRID_SPACING 0.25

/**
 * Construct of a scene
 */
Scene::Scene(){
  int i;
  /*Initial objects*/
  ground.setPosition(0,0,0);
  ground.addVertex(-50, 0, -50);
  ground.addVertex(-50, 0, 50);
  ground.addVertex( 50, 0, 50);
  ground.addVertex( 50, 0, -50);
  for(i=0;i<4;i++){
    ground.addColor(0.0,0.5,0.0);
  }
  
  int j;
  int count=0;
  for(i=-5;i<5;i++){
    for(j=-5;j<5;j++){
      if(count>99){
	break;
      }
      cube[count].setSize(2);
      cube[count].addColor(0.4*i+0.4,0,0.4*j+0.2);
      cube[count].setPosition(i*5,cube[count].getSize()/2,j*5);
      count++;
    }
  }
}

/**
 * desstruct of a scene
 */
Scene::~Scene(){
  
}
