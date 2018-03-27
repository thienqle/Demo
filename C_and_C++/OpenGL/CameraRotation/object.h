/**
 * @author: Thien Le
 */
#include <vector>
using namespace std;
 
/**
 * Model of an Object
 */
class Object
{
 public:
  Object();
  virtual ~Object();
  Object(const Object &input);
  Object& operator=(const Object& input);
  void addVertex(double x,double y,double z);
  void addColor(double x,double y,double z);
  double* getVertex(int index) const;
  double* getColor(int index) const;
  int getNumOfVertices() const;
  int getNumOfColors() const;
  double getPosX() const;
  double getPosY() const;
  double getPosZ() const;
  double getSize() const;
  double getDirX() const;
  double getDirY() const;
  double getDirZ() const;
  void setSize(double s);
  void setPosition(double x,double y,double z);
  void setDirection(double x,double y,double z);
  void update_rotation(double delta,double radius);

 protected:
  int numOfVertices;
  int numOfColors;
  double angle;
  double posX,posY,posZ;
  double dirX,dirY,dirZ;
  double speed_rotate;
  double size;
  vector<double> vertices; 
  vector<double> colors; 
};
