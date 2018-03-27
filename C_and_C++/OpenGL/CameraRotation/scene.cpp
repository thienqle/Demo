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
  ground.addVertex(-2.5, 0.0, -2.5f);
  ground.addVertex(-2.5f, 0.0f, 2.5f);
  ground.addVertex( 2.5f, 0.0f, 2.5f);
  ground.addVertex( 2.5f, 0.0f, -2.5f);
  for(i=0;i<4;i++){
    ground.addColor(1.0,1.0,1.0);
  }
 
  cube.setSize(0.3);
  cube.addColor(1,0,0);
  cube.setPosition(0,0.2,0);

  ball.setSize(0.2);
  ball.addColor(0,0,1);
  ball.setPosition(0,0.2,1);
}

/**
 * desstruct of a scene
 */
Scene::~Scene(){
  
}
