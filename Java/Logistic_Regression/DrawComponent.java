/**
 * Thien Le
 */
package logisticRegression;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

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
	public LogisticRegression linear;
		
	public DrawComponent(int width,int height,LogisticRegression linear) {
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
        g.setColor(Color.black);
        
        /**
         * Get size here since real size different with initialize
         */
        screenSize = this.getSize();
        marginX = screenSize.width/10;
        marginY = screenSize.height/10;
        pointSize = 3;
        unitSize = 3;
               
        Point root = convertPoint(0,0,0);
        int rightX = screenSize.width-2*marginX;
        int topY = screenSize.height-2*marginY;
        Point x_axis = convertPoint(rightX,0,0);
        Point y_axis = convertPoint(0,topY,0);
        g.drawLine((int)root.x1,(int)root.x2,(int)x_axis.x1,(int)x_axis.x2);
        g.drawLine((int)root.x1,(int)root.x2,(int)y_axis.x1,(int)y_axis.x2);
        
        g.setColor(Color.black);
        /* Screen infor */
        g.drawString("Screen width = " + Integer.toString(screenSize.width),0,15);
        g.drawString("Screen height = " + Integer.toString(screenSize.height),0,30);
        for(int i=(int)root.x1;i<x_axis.x1;i++) {
        	if(i%50==0) {
        		drawMarkX(i,(int)root.x2,g);
        		Point temp = convertPointBack(i,(int)root.x2,0);
        		g.drawString(Integer.toString((int)temp.x1),i,(int)root.x2+marginX/2);
        	}
        }
        
        for(int i=(int)root.x2;i>y_axis.x2;i--) {
        	if(i%50==0) {
        		drawMarkY((int)root.x1,i,g);	
        		Point temp = convertPointBack((int)root.x1,i,0);
        		g.drawString(Integer.toString((int)temp.x2),(int)root.x1-marginY/2,i);
        	}
        }
        
        
        
        /*Draw all the line of gradient descent*/
        //renderMinJ(0.0001,g,-1,rightX);
		g.setColor(Color.red);
		drawLinear(g,-1,rightX,linear.B0,linear.B1,linear.B2);
		drawLinear(g,-1,rightX,linear.B0,linear.B1,linear.B2);
        
        for(int i=0;i<linear.n;i++) {
        	Point tmp = convertPoint((int)linear.points[i].x1,(int)linear.points[i].x2,linear.points[i].y);
        	if(tmp.y==1) {
        		g.setColor(Color.red);
        		drawPoint((int)tmp.x1,(int) tmp.x2, g);
        	} else {
        		g.setColor(Color.blue);
        		drawPoint((int)tmp.x1,(int) tmp.x2, g);
        	}
        	
        }
        
	}
	
	/**
	 * Function that draws a linear with given slope and intercept
	 */
	public void drawLinear(Graphics g,int leftX,int rightX,double B0,double B1,double B2) {
		Point firstPoint = convertPoint(leftX,(int)(-leftX*B1/B2 - B0/B2),0);
        Point secondPoint = convertPoint(rightX,(int)(-rightX*B1/B2 - B0/B2),0);
        g.drawLine((int)firstPoint.x1,(int)firstPoint.x2,(int)secondPoint.x1,(int)secondPoint.x2);
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
	public Point convertPoint(int x1,int x2,int y) {
		int temp_x = (x1 + marginX);
		int temp_y = (screenSize.height -(x2 + marginY));
		Point output = new Point(temp_x,temp_y,y);
		return output;
	}
	
	/**
	 * convert point from pixel to descarte on screen
	 */
	public Point convertPointBack(int x1,int x2,int y) {
		int temp_x = x1 - marginX;
		int temp_y = (screenSize.height -(x2 + marginY));
		Point output = new Point(temp_x,temp_y,y);
		return output;
	}
	
}
