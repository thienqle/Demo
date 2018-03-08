/**
 * Thien Le
 */
package linearRegression;

/**
 * Model of linear regression 
 */
public class LinearRegression {

	/**
	 * Array of point
	 */
	Point []points;
	
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
	 * Construct of LeanerRegression model
	 * @param x
	 * @param y
	 */
	public LinearRegression(int []x,int []y) {
		int size = x.length;
		points = new Point[size];
		for (int i=0;i<size;i++) {
			points[i] = new Point(x[i],y[i]);
		}
		
		/*for (int i=0;i<size;i++) {
			double xn = (double)(x[i] - MinX)/(MaxX-MinX);
			double yn = (double)(y[i] - MinY)/(MaxY-MinY);
			points[i] = new Point(xn,yn);
			//System.out.println(points[i].toString());
		}*/
		
		sum_Y = 0;
		sum_X = 0;
		sum_square_X = 0;
		sum_XY = 0;
		n = size;
		for(int i=0;i<size;i++) {
			sum_Y+=points[i].y;
			sum_X+=points[i].x;
			sum_XY+= points[i].x * points[i].y;
			sum_square_X+= points[i].x * points[i].x;
		}
		mean_x = (double)sum_X/n;
		mean_y = (double)sum_Y/n;
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
			double x_minus_meanX =(points[i].x - mean_x);
			double y_minus_meanY =(points[i].y - mean_y);
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
	 * Hypothesis (which is a linear equation 
	 * @param slope
	 * @param intercept
	 * @return value y of hypothesis based on given x 
	 */
	public double h(double slope, double intercept,double x) {
		/*System.out.println("intecept=" + intercept);
		System.out.println("slope=" + slope);
		System.out.println("x=" + x);
		System.out.println("h=" + (intercept + slope * x));
		Sytem.out.println("========");*/
		return (intercept + slope * x);
	}
	
	/**
	 * Cost function which computes costs of a hypothesis on data 
	 * @param slope
	 * @param intercept
	 * @return value of cost function 
	 */
	public double J(double slope,double intercept) {
		double sum_of_error = (double)0;
		for(int i=0;i<n;i++) {
			double point_square_error;
			point_square_error = h(slope,intercept,points[i].x) - points[i].y;
			point_square_error = point_square_error * point_square_error; //Spare the value to make its square
			sum_of_error += point_square_error;
		}
		return (double)sum_of_error/(2*n);
	}
	
	/**
	 * function which computes derivative on slope value of a hypothesis on data 
	 * @param slope
	 * @param intercept
	 * @return value of cost function 
	 */
	public double derrivative_on_J_on_slope(double slope,double intercept) {
		double sum_of_error = (double)0;
		for(int i=0;i<n;i++) {
			double point_error;
			point_error = h(slope,intercept,points[i].x) - points[i].y;
			point_error = point_error * points[i].x;
			sum_of_error+= point_error;
		}
		return (double)sum_of_error/n;
	}
	
	/**
	 * function which computes derivative on intercept value of a hypothesis on data 
	 * @param slope
	 * @param intercept
	 * @return value of cost function 
	 */
	public double derrivative_on_J_on_intercept(double slope,double intercept) {
		double sum_of_error = (double)0;
		for(int i=0;i<n;i++) {
			double point_error;
			point_error = h(slope,intercept,points[i].x) - points[i].y;
			sum_of_error+= point_error;
		}
		return (double)sum_of_error/n;
	}
	
	/**
	 * Function that find minimum cost function J
	 * @param alpha, learning rate (step to adjustment)
	 * @return value of slope and intercept that give minimum cost function
	 */
	public double[] minJ(double alpha){
		double []output = new double[2]; 
		double intercept = 0.0;
		double slope = 0.0;
		double convergence = J(slope,intercept);
		int count =0;
		double previous_covergence = convergence;
		while(convergence < -0.05 || convergence > 0.05) {
			double derivative_on_interecept = derrivative_on_J_on_intercept(slope,intercept); 
			double derivative_on_slope = derrivative_on_J_on_slope(slope,intercept);
			double temp_intercept = intercept - alpha * (derivative_on_interecept);
			double temp_slope = slope - alpha *(derivative_on_slope);
			intercept = temp_intercept;
			slope = temp_slope;
			convergence = J(slope,intercept);
			count++;
			if(count%500000 == 0) {
				System.out.println("coverge =" + convergence + " , Slope=" + slope + ",intercept =" + intercept);
			}
			if(previous_covergence==convergence) { //If cannot move any more
				break;
			} else {
				previous_covergence=convergence;
			}
		}
		output[0] = slope;
		output[1] = intercept;
		return output;
	}
	
}
