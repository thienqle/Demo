package linearRegression;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 *	The model for mainFrame of the system 
 */
public class MainFrame extends JFrame{
	
	/**
	 * width,height of the screen
	 */
	public int width;
	public int height;
	public LinearRegression linear;
	
	public MainFrame(int width,int height,LinearRegression linear) {
		this.width = width;
		this.height = height;
		this.linear = linear;
		this.setPreferredSize(new Dimension(width,height));
		this.setBounds(0,0,width,height);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setSize(new Dimension(width,height));
    	setResizable(false);
	    setTitle("Linear Regression");
	    setLayout(new BorderLayout());
	    
	    DrawComponent drawComponent = new DrawComponent(this.getWidth(),this.getHeight(),linear);
	    this.add(drawComponent);
	}
}
