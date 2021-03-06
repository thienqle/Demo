/**
 * Thien Le
 */
#include "object.h"

/**
 * Model of a 3D scene
 */
class Scene 
{
 public:
  Scene();
  ~Scene();
 
  //protected:
  Object ground;
  Object cube;
  Object ball;

 private:
  Scene(const Scene&);
  Scene& operator=(const Scene&);
};
