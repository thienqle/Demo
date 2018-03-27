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
  virtual ~Scene();
 
  //protected:
  Object ground;
  Object cube[100];

 private:
  Scene(const Scene&);
  Scene& operator=(const Scene&);
};
