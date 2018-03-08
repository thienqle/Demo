/**
 * Thien Le
 */
package linearRegression;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

/**
 * To draw graphics line and rect we need Jcomponent which will be added to JFrame
 */
public class DrawComponent extends JComponent  {

	/**
	 * Margin of graph, and width,height of the screen
	 */
	public int marginX;
	public int marginY;
	public int width;
	public int height;
	public int pointSize;
	public int unitSize;
	Dimension screenSize;
	public LinearRegression linear;
		
	public DrawComponent(int width,int height,LinearRegression linear) {
		super();
        setPreferredSize(new Dimension(width,height));
        this.width=width;
        this.height=height;
        this.setBounds(0,0,width,height);
        this.linear= linear;
	}
	
	public void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        Dimension d = getPreferredSize();
        g.setColor(Color.black);
        
        /**
         * Get size here since real size different with initialize
         */
        screenSize = this.getSize();
        marginX = screenSize.width/10;
        marginY = screenSize.height/10;
        pointSize = 3;
        unitSize = 3;
        
        //g.drawString(Integer.toString(marginX) + " " + Integer.toString(width-2*marginX),marginX,marginY);
        //g.drawString(Integer.toString(marginY) + " " + Integer.toString(height-2*marginY),marginX,marginY+20);
        
               
        //drawLine(int x1, int y1, int x2, int y2)
        //g.drawLine(marginX,marginY,screenSize.width-marginX,screenSize.height-marginY);
        
        //drawRect(int x, int y, int width, int height)
        //g.drawRect(marginX,marginY,screenSize.width-2*marginX,screenSize.height-2*marginY);
        
        //drawOval(int x, int y, int width, int height)
        //g.drawOval(marginX,marginY,screenSize.width-2*marginX,screenSize.height-2*marginY);
        
        Point root = convertPoint(0,0);
        int rightX = screenSize.width-2*marginX;
        int topY = screenSize.height-2*marginY;
        Point x_axis = convertPoint(rightX,0);
        Point y_axis = convertPoint(0,topY);
        g.drawLine((int)root.x,(int)root.y,(int)x_axis.x,(int)x_axis.y);
        g.drawLine((int)root.x,(int)root.y,(int)y_axis.x,(int)y_axis.y);
        
        g.setColor(Color.black);
        /* Screen infor */
        g.drawString("Screen width = " + Integer.toString(screenSize.width),0,15);
        g.drawString("Screen height = " + Integer.toString(screenSize.height),0,30);
        for(int i=(int)root.x;i<x_axis.x;i++) {
        	if(i%50==0) {
        		drawMarkX(i,(int)root.y,g);
        		Point temp = convertPointBack(i,(int)root.y);
        		g.drawString(Integer.toString((int)temp.x),i,(int)root.y+marginX/2);
        	}
        }
        
        for(int i=(int)root.y;i>y_axis.y;i--) {
        	if(i%50==0) {
        		drawMarkY((int)root.x,i,g);	
        		Point temp = convertPointBack((int)root.x,i);
        		g.drawString(Integer.toString((int)temp.y),(int)root.x-marginY/2,i);
        	}
        }
        
        
        g.setColor(Color.red);
        drawLinear(g,-1,rightX,linear.getSlope_v1(),linear.getIntercept_v1());
     
        /*Draw all the line of gradient descent*/
        renderMinJ(0.0001,g,-1,rightX);
        
        g.setColor(Color.blue);
        for(int i=0;i<linear.n;i++) {
        	Point tmp = convertPoint((int)linear.points[i].x,(int)linear.points[i].y);
        	drawPoint((int)tmp.x,(int) tmp.y, g);
        }
        
	}
	
	/**
	 * Function that draws a linear with given slope and intercept
	 */
	public void drawLinear(Graphics g,int leftX,int rightX,double slope,double intercept) {
		Point firstPoint = convertPoint(leftX,(int)(leftX*slope + intercept));
        Point secondPoint = convertPoint(rightX,(int)(rightX*slope + intercept));
        g.drawLine((int)firstPoint.x,(int)firstPoint.y,(int)secondPoint.x,(int)secondPoint.y);
	}

	
	/**
	 * Function that draws a point on screen
	 */
	public void drawPoint(int x,int y,Graphics g) {
		g.fillOval(x-pointSize/2,y-pointSize/2,pointSize,pointSize);
		g.drawOval(x-pointSize/2,y-pointSize/2,pointSize,pointSize);
	}
	
	/**
	 * Function that draws a point on screen
	 */
	public void drawMarkX(int x,int y,Graphics g) {
		g.fillOval(x-pointSize/2,y-pointSize/2,0,pointSize);
		g.drawOval(x-pointSize/2,y-pointSize/2,0,pointSize);
	}

	/**
	 * Function that draws a point on screen
	 */
	public void drawMarkY(int x,int y,Graphics g) {
		g.fillOval(x-pointSize/2,y-pointSize/2,pointSize,0);
		g.drawOval(x-pointSize/2,y-pointSize/2,pointSize,0);
	}

	/**
	 * convert point from descarte to pixel a point on screen
	 */
	public Point convertPoint(int x,int y) {
		int temp_x = (x + marginX);
		int temp_y = (screenSize.height -(y + marginY));
		//temp_x = temp_x * unitSize;
		//temp_y = temp_y * unitSize;
		Point output = new Point(temp_x,temp_y);
		return output;
	}
	
	/**
	 * convert point from pixel to descarte on screen
	 */
	public Point convertPointBack(int x,int y) {
		int temp_x = x - marginX;
		int temp_y = (screenSize.height -(y + marginY));
		Point output = new Point(temp_x,temp_y);
		return output;
	}
	
	/**
	 * Function that render gradient descent
	 * @param alpha, learning rate (step to adjustment)
	 * @return value of slope and intercept that give minimum cost function
	 */
	public double[] renderMinJ(double alpha,Graphics g,int leftX,int rightX){
		double []output = new double[2]; 
		double intercept = 0.0;
		double slope = 0.0;
		double convergence = linear.J(slope,intercept);
		System.out.println("Convergence=" + convergence);
		double previous_covergence = convergence;
		while((convergence < -0.01 || convergence > 0.01)) {
			double derivative_on_interecept = linear.derrivative_on_J_on_intercept(slope,intercept); 
			double derivative_on_slope = linear.derrivative_on_J_on_slope(slope,intercept);
			double temp_intercept = intercept - alpha * (derivative_on_interecept);
			double temp_slope = slope - alpha *(derivative_on_slope);
			intercept = temp_intercept;
			slope = temp_slope;
			convergence = linear.J(slope,intercept);
			
	        Random rand = new Random();
	        float red = rand.nextFloat();
	        float green = rand.nextFloat();
	        float blue = rand.nextFloat();
	        Color randColor = new Color(red,green,blue);
	        g.setColor(randColor);
			drawLinear(g,leftX,rightX,slope,intercept);
	        if(previous_covergence==convergence) { //If cannot move any more
				break;
			} else {
				previous_covergence=convergence;
			}
		}
		output[0] = slope;
		output[1] = intercept;
		
		System.out.println("Result slope=" + slope);
		System.out.println("Result intercept=" +intercept);
		
		return output;
	}
}
