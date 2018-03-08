package logisticRegression;

import java.util.Random;

import javax.swing.JFrame;

public class MainActivity {
	
	final static int SCREEN_WIDTH = 505 + 6; //(-16 from real screen)(-6 from real screen)
	final static int SCREEN_HEIGHT = 500 + 29; //(-39 from real scree)(-29 from real screen)
	final static int NUMBER_OF_POINT = 50;
	
	public static void main(String []args) {
		
		//int []x1 = {43,21,25,42,57,59};
		//int []x2 = {99,65,79,75,87,81};
		//int []y = {0,0,0,1,1,1};
		
		//int []x1 = {1,2,5,100,150,200};
		//int []x2 = {1,2,5,100,150,200};
		//int []y = {0,0,0,1,1,1};
		
		//int []x1 = {10,300};
		//int []x2 = {10,300};
		//int []y = {0,1};
		
		//int []x1 = {4,2,3,4,5,6};
		//int []x2 = {10,7,8,8,9,8};
		//int []y = {0,0,0,1,1,1};
		
		//int []x1 = {0,50,60,70,160,180,190};
		//int []x2 = {100,200,90,80,20,50,80};
		//int []y = {1,1,1,1,0,0,0,0};
		
		//int []x1 = {103,103,101,93,84,73,84,79,76,76,69,74,81,70,65,58};
		//int []x2 = {184,183,175,174,173,173,173,170,175,178,188,203,230,258,310,330};
		//int []y = {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1};
		
		
		int []x1 = new int[NUMBER_OF_POINT];
		int []x2 = new int[NUMBER_OF_POINT];
		int []y = new int[NUMBER_OF_POINT];
		
		Random rand = new Random();
		for(int i=0;i<NUMBER_OF_POINT;i++) {
			int rand_x = rand.nextInt(300);
			int rand_y = rand.nextInt(100)+50;
			x1[i] = rand_x;
			x2[i] = rand_y;
			if(x1[i] > 200) {
				y[i]=0;
			} else {
				y[i]=1;
			}
		}
		
		LogisticRegression linear = new LogisticRegression(x1,x2,y);
		linear.minJ(0.001);
		MainFrame mainFrame =  new MainFrame(SCREEN_WIDTH,SCREEN_HEIGHT,linear);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setBounds(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);

		
	}
}
