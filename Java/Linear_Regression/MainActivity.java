package linearRegression;

import java.util.Random;

import javax.swing.JFrame;

public class MainActivity {
	
	final static int SCREEN_WIDTH = 505 + 16; //(-16 real screen)(-6 real screen)
	final static int SCREEN_HEIGHT = 500 + 29; //(-39 real screen)(-29 real screen)
	final static int NUMBER_OF_POINT = 200;
	
	public static void main(String []args) {
		
		int []x = {103,103,101,93,84,73,84,79,76,76,69,74,81,70,65,58};
		int []y = {184,183,175,174,173,173,173,170,175,178,188,203,230,258,310,330};
		
		
		/*int []x = new int[NUMBER_OF_POINT];
		int []y = new int[NUMBER_OF_POINT];
		
		Random rand = new Random();
		for(int i=0;i<NUMBER_OF_POINT;i++) {
			int rand_x = rand.nextInt(300);
			int rand_y = rand.nextInt(100);
			x[i] = rand_x;
			y[i] = rand_y;
		}*/
		
		LinearRegression linear = new LinearRegression(x,y);
		MainFrame mainFrame =  new MainFrame(SCREEN_WIDTH,SCREEN_HEIGHT,linear);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setBounds(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
		
		
		System.out.println(linear.getSlope());
		System.out.println(linear.getIntercept());
		System.out.println("slope = " + linear.getSlope_v1());
		System.out.println("intercept ="+ linear.getIntercept_v1());
		System.out.println("Convergence of correct paramater=" + linear.J(linear.getSlope_v1(), linear.getIntercept_v1()));
		//double []gd= linear.minJ(0.001);
		//System.out.println(gd[0]);
		//System.out.println(gd[1]);
		
	}
}
