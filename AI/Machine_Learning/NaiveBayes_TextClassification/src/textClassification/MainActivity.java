package textClassification;

import java.util.Set;

public class MainActivity {
	/**
	 * Main method of application
	 */
	public static void main(String []args) {
		
		NaiveBayes naive = new NaiveBayes();
		
		//float training_result = naive.computeOverallAccuracy(true,true);
		//System.out.println("Training accuraccy = " + training_result);
		float training_result = naive.computeOverallAccuracy(true,false);
		System.out.println("Training accuraccy (BE) = " + training_result);
		
		//float testing_result = naive.computeOverallAccuracy(false,true);
		//System.out.println("Testing accuraccy MLE= " + testing_result);
		float testing_result = naive.computeOverallAccuracy(false,false);
		System.out.println("Testing accuraccy (BE)= " + testing_result);
		
		
		Set<Integer> categories = naive.getCaterogyList();
		Object []list_categories = categories.toArray();
		/*System.out.println("Class accuracy " + "MLE");
		for(int category:categories) {
			float result = naive.computeClassAccuracy(false,true,category);
			System.out.println("Group " + category + ": " + result);
		}
		*/
		//System.out.println("\n");
		System.out.println("Class accuracy " + "BE");
		for(int i=list_categories.length-1;i>=0;i--) {
			int category = (int)list_categories[i];
			float result = naive.computeClassAccuracy(true,false,category);
			System.out.println("Group " + category + ": " + result);
		}
		//System.out.println("11234 :" + naive.performNBClassify_BE(11234,true));
	}
}
