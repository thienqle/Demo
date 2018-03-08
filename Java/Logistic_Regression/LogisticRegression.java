/**
 * Thien Le
 */
package logisticRegression;


/**
 * Model of linear regression 
 */
public class LogisticRegression {

	/**
	 * Array of point
	 */
	Point []points;
	double B0;
	double B1;
	double B2;
	
	/**
	 * There are 2 formula to find linear line
	 * Formula for first method
	 */
	int sum_Y;
	int sum_X;
	int sum_square_X;
	int sum_XY;
	int n;
	
	/**
	 * Formula for second method
	 */
	double mean_x;
	double mean_y;
	
	/**
	 * Construct of Logistic regression model
	 * @param x
	 * @param y
	 */
	public LogisticRegression(int []x1,int []x2,int []y) {
		int size = x1.length;
		points = new Point[size];
		for (int i=0;i<size;i++) {
			points[i] = new Point(x1[i],x2[i],y[i]);
		}
		
		sum_Y = 0;
		sum_X = 0;
		sum_square_X = 0;
		sum_XY = 0;
		n = size;
		for(int i=0;i<size;i++) {
			sum_Y+=points[i].x2;
			sum_X+=points[i].x1;
			sum_XY+= points[i].x1 * points[i].x2;
			sum_square_X+= points[i].x1 * points[i].x1;
		}
		mean_x = (double)sum_X/n;
		mean_y = (double)sum_Y/n;
		
		B0 = 0;
		B1 = 0;
		B2 = 0;
	}
	
	/**
	 * Function that computes slope of linear regression
	 * @return
	 */
	public double getSlope() {
		
		return (double)((sum_Y * sum_square_X) - (sum_X * sum_XY))/(n*sum_square_X - sum_X * sum_X);
	}
	
	/**
	 * Function that computes intercept of linear regression
	 * @return
	 */
	public double getIntercept() {
		return (double)(n * sum_XY - sum_X * sum_Y) / (n * (sum_square_X) - sum_X * sum_X);
	}
	
	/**
	 * Function that computes slope of linear regression
	 * @return
	 */
	public double getSlope_v1() {
		double Sum_each_minus_mean = 0f;
		double Sum_square_x_minus_meanX = 0f;
		for(int i=0;i<n;i++) {
			double x_minus_meanX =(points[i].x1 - mean_x);
			double y_minus_meanY =(points[i].x2 - mean_y);
			Sum_each_minus_mean+=x_minus_meanX*y_minus_meanY;
			Sum_square_x_minus_meanX += x_minus_meanX * x_minus_meanX;
		}
		return Sum_each_minus_mean/Sum_square_x_minus_meanX;
	}
	
	/**
	 * Function that computes intercept of linear regression
	 * @return
	 */
	public double getIntercept_v1() {
		double slope = getSlope_v1();
		return (sum_Y - slope * sum_X) / n;
	}
	
	/**
	 * Function that computes y based on x
	 * @return
	 */
	public double findY(int x) {		
		return (this.getSlope_v1() * x) + getIntercept_v1();
	}
	
	
	/**
	 * Gradient descent Part 
	 */
	
	/**
	 * Hypothesis (which is a logistic regression equation) - which means in hypothesis we can find y by given equation
	 * Instead of using linear equation, we use sigmoid function or logistic function (sigmoid and logistic are same thing)
	 * @param B0
	 * @param B1
	 * @return value y of hypothesis based on given x 
	 */
	public double h(double B0, double B1,double B2,double x1,double x2) {
		//return (intercept + slope * x); //Linear regression
		//Math.exp(n) is e^n 
		return 1/(1+Math.exp(-1*(B0 + B1*x1 + B2*x2)));
	}
	
	
	
	
	/**
	 * sum of cost function which computes costs of a hypothesis on data 
	 * @param B0
	 * @param B1
	 * @param B2
	 * @return value of cost function 
	 */
	public double J(double B0, double B1,double B2) {
		double sum_of_cost = (double)0;
		for(int i=0;i<n;i++) {
			double singular_cost;
			singular_cost = cost(B0,B1,B2,i,points[i].y);
			sum_of_cost += singular_cost;
		}
		return (double)(-1*(sum_of_cost/n));
	}
	
	
	/**
	 * a cost function which computes costs of a hypothesis on a point
	 * if y=1 cost will be -log(h(x))
	 * if y=0 cost will be -log(1-h(x))
	 * @param B0
	 * @param B1
	 * @param B2
	 * @return value of cost function 
	 */
	public double cost(double B0, double B1,double B2,int index,int y) {
		return -(y*Math.log(h(B0,B1,B2,points[index].x1,points[index].x2)) 
				+ (1-y)*Math.log(1-h(B0,B1,B2,points[index].x1,points[index].x2))); 
	}
	
	/**
	 * function which computes derivative on slope value of a hypothesis on data 
	 * @param B0
	 * @param B1
	 * @param B2
	 * @return value of cost function 
	 */
	public double[] derrivative_on_all_paramater(double B0, double B1,double B2) {
		double []output = new double[3];
		double sum_of_error_B0 = (double)0;
		double sum_of_error_B1 = (double)0;
		double sum_of_error_B2 = (double)0;
		for(int i=0;i<n;i++) {
			double point_error;
			double point_error_B0;
			double point_error_B1;
			double point_error_B2;
			double hypothesis = h(B0,B1,B2,points[i].x1,points[i].x2); 
			point_error = hypothesis - points[i].y;
			point_error_B0 = point_error;
			point_error_B1 = point_error * points[i].x1;
			point_error_B2 = point_error * points[i].x2;
			sum_of_error_B0+= point_error_B0;
			sum_of_error_B1+= point_error_B1;
			sum_of_error_B2+= point_error_B2;
		}
		output[0] = sum_of_error_B0/n;
		output[1] = sum_of_error_B1/n;
		output[2] = sum_of_error_B2/n;
		return output;
	}
	
	/**
	 * Function that find minimum cost function J
	 * @param alpha, learning rate (step to adjustment)
	 * @return value of B0,B1,B2 that give minimum cost function
	 */
	public double[] minJ(double alpha){
		double []output = new double[3]; 
		double convergence = J(B0,B1,B2);
		int count =0;
		double previous_covergence = convergence;
		while(convergence < -0.05 || convergence > 0.05) {
			double []costs = derrivative_on_all_paramater(B0,B1,B2);
			double derivative_on_B0 = costs[0]; 
			double derivative_on_B1 = costs[1];
			double derivative_on_B2 = costs[2];
			double temp_B0 = B0 - alpha * (derivative_on_B0);
			double temp_B1 = B1 - alpha * (derivative_on_B1);
			double temp_B2 = B2 - alpha * (derivative_on_B2);
			B0 = temp_B0;
			B1 = temp_B1;
			B2 = temp_B2;
			convergence = J(B0,B1,B2);
			count++;
			if(count%500000 == 0) {
				System.out.println("coverge =" + convergence + " , B0=" + B0 + ",B1 =" + B1 + ",B2 =" + B2);
			}
			if(previous_covergence==convergence) { //If cannot move any more
				break;
			} else {
				previous_covergence=convergence;
			}
		}
		output[0] = B0;
		output[1] = B1;
		output[2] = B2;
		return output;
	}
	
}
