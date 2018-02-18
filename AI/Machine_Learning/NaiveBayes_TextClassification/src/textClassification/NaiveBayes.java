package textClassification;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/*
 * Model that for operation Naive Bayes classification on input data
 */
public class NaiveBayes {

	/*
	 * Hashtable that store trained data value using MLE
	 * Hashtable<wordID,Hashtable<category,NaiveBayes estimate>>
	 */
	private Hashtable<Integer,Hashtable<Integer,Float>> trained_data_MLE;
	
	/*
	 * Hashtable that store trained data value using BE
	 * Hashtable<wordID,Hashtable<category,NaiveBayes estimate>>
	 */
	private Hashtable<Integer,Hashtable<Integer,Float>> trained_data_BE;
	
	/*
	 * Hashtable that store total number of word of in all docutment in a category
	 * Hashtable<category,wordcount>
	 */
	private Hashtable<Integer,Integer> wordcount;
	
	
	/**
	 * All of file input 
	 */
	private static String pathFile = "E:\\ISU study\\STUDY\\Spring2018\\COMS 474 - Machine\\Lab\\20newsgroups\\20newsgroups";
	private static String vocabulary = "vocabulary.txt";
	private static String map = "map.csv";
	private static String training_label = "train_label.csv";
	private static String training_data = "train_data.csv";
	private static String testing_label = "test_label.csv";
	private static String testing_data = "test_data.csv";
	
	/**
	 * All of file content 
	 */
	private TextFile vocabularyFile;
	private TextFile mapFile;
	private TextFile trainingData;
	private TextFile trainingLabel;
	private TextFile testingData;
	private TextFile testingLabel;
	private Map map_File;
	private Vocabulary vocab;
	
	/**
	 * Data and label of training set
	 */
	private Data training_Data;
	private Label training_Label;
	
	/**
	 * Data and label of testing set
	 */
	private Data testing_Data;
	private Label testing_Label;
	
	
	
	/**
	 * Construct NaiveBayes instance
	 */
	public NaiveBayes() {
		/*
		 * Initialize for variables
		 */
		vocabularyFile = new TextFile(pathFile + "\\" + vocabulary);
		mapFile = new TextFile(pathFile + "\\" + map);
		trainingData = new TextFile(pathFile + "\\" + training_data);
		trainingLabel = new TextFile(pathFile + "\\" + training_label);
		testingData = new TextFile(pathFile + "\\" + testing_data);
		testingLabel = new TextFile(pathFile + "\\" + testing_label);
		
		testing_Data = new Data(testingData.getFileContent());
		testing_Label = new Label(testingLabel.getFileContent());
		training_Data = new Data(trainingData.getFileContent());
		training_Label = new Label(trainingLabel.getFileContent());
		map_File = new Map(mapFile.getFileContent());
		vocab = new Vocabulary(vocabularyFile.getFileContent());
		
		trained_data_MLE = new Hashtable<Integer,Hashtable<Integer,Float>>();
		trained_data_BE = new Hashtable<Integer,Hashtable<Integer,Float>>();
		wordcount = new Hashtable<Integer,Integer>();
		//Train data
		this.computeWordCount();
		this.training();
	}
	
	/**
	 * Function that compute class prior
	 */
	public void displayPrior() {
		/*Compute and print class prior*/
		Set<Integer> keys = map_File.getCategories();
		for(int w:keys) {
			/*Calculate class prior P(wj)*/
			float classPrior = training_Label.computeClassPrior(w);
			System.out.println("P(Omega = " + w + ") = " + classPrior);
		}
	}
	
	/**
	 * Function that compute total number of word in all document of a class (category)
	 */
	public void computeWordCount() {
		Set<Integer> categories = this.map_File.getCategories();
		for(int category:categories) {
			int count = 0;
			ArrayList<Integer> filelist = training_Label.getFileList(category);
			for(int doc : filelist) {
				count+= training_Data.getWordCount1Doc(doc);
			}
			this.wordcount.put(category,count);
		}
	}
	
	/**
	 * Function that estimate maximum likelihood of a class with given word
	 * @param category,word
	 * 	given word and category
	 * @return float
	 * 	Maximum likelyhood estimator
	 */
	public float computeMLE(int word,int category) {
		/*Selection between tranining and testing data*/
		int occurence = 0;
		int total = this.wordcount.get(category);
		ArrayList<Integer> filelist = training_Label.getFileList(category);
		for(int doc : filelist) {
			occurence += training_Data.getWordCount(doc,word);
		}
		return (float)occurence/(float)total;			
	}
	
	/**
	 * Function that compute Bayesian estimator of a class with given word
	 * @param category,word
	 * 	given word and category
	 * @return float
	 * 	Bayesian estimator
	 */
	public float computeBE(int word,int category) {
		/*Selection between tranining and testing data*/
		int total = this.wordcount.get(category);
		int occurence = 0;
		ArrayList<Integer> filelist = training_Label.getFileList(category);
		for(int doc : filelist) {
			occurence += training_Data.getWordCount(doc,word); 
		}
		return (float)(occurence+1)/(float)(total + this.vocab.getTotal());
	}
	
	
	/*
	 * This function perform Naive Bayes classify on an document using Laplace estimate.
	 * @param doc,training
	 * @return category
	 * 	return category of document
	 */
	public int performNBClassify_BE(int doc,boolean training) {
		/*Selection between training and testing data*/
		Data data;
		if(training) {
			data = training_Data;
		} else {
			data = testing_Data;
		}
		
		Set<Integer> words = data.getWordList(doc); //word list
		Set<Integer> key = map_File.getCategories();	//Category list
		float max = Float.MIN_VALUE;
		int classified = 0;
		for(int w:key) {
			float w_nb = (float)Math.log(training_Label.computeClassPrior(w));
			for(int wd:words) {
				float be = getBE(wd,w);
				w_nb += Math.log(be);
			}
		
			if(max < w_nb || max == Float.MIN_VALUE) {
				classified = w;
				max = w_nb;
			}
		}
		return classified;
	}
	
	/*
	 * This function perform Naive Bayes classify on an document using Maximum Likelihood estimator.
	 * @param doc,training
	 * @return category
	 * 	return category of document
	 */
	public int performNBClassify_MLE(int doc,boolean training) {
		/*Selection between training and testing data*/
		Data data;
		if(training) {
			data = training_Data;
		} else {
			data = testing_Data;
		}
		
		Set<Integer> words = data.getWordList(doc); //word list
		Set<Integer> key = map_File.getCategories();	//Category list
		float max = Float.MIN_VALUE;
		int classified = 0;
		for(int w:key) {
			float w_nb = (float)Math.log(training_Label.computeClassPrior(w));
			for(int wd:words) {
				float mle = getMLE(wd,w);
				w_nb *= mle;
			}
			
			if(max < w_nb || max == Float.MIN_VALUE) {
				classified = w;
				max = w_nb;
			}
		}
		return classified;
	}
	
	/**
	 * This function perform measure overall accuracy
	 * @param training,mle
	 * 	training for selecting between training and testing data (if it is true, it is training)
	 *  mle of selecting between Maximum likelihood estimate and Laplace smoothing
	 * @return category
	 * 	return category of document
	 */
	public float computeOverallAccuracy(boolean training, boolean mle) {
		/*Selection between training and testing data*/
		Label label;
		if(training) {
			label = training_Label;
		} else {
			label = testing_Label;
		}
		
		int correct = 0;
		int numberOfFile = 0;
		Set<Integer> key = map_File.getCategories();	//Category list
		for(int category : key) {
			ArrayList<Integer> filelist = label.getFileList(category); //fileList
			for(int f:filelist) {
				int classifed;
				if(mle) {
					classifed = performNBClassify_MLE(f,training);
				} else {
					classifed = performNBClassify_BE(f,training);
				}
				if(label.checkFileCategory(f,classifed)) {
					correct++;
				}
				numberOfFile++;
				/*if(numberOfFile%50==0) {
					System.out.println(training + ": " + numberOfFile + " ;accuracy = " + (float)correct/numberOfFile);
				}*/
			}
		}
		return (float)correct/(float)numberOfFile;
	}
	
	/*
	 * This function perform measure accuracy of given class (category)
	 * @return category
	 * 	return category of document
	 */
	public float computeClassAccuracy(boolean training, boolean mle,int category) {
		/*Selection between training and testing data*/
		Label label;
		if(training) {
			label = training_Label;
		} else {
			label = testing_Label;
		}
		int correct = 0;
		int numberOfFile = 0;
		
		ArrayList<Integer> filelist = label.getFileList(category); //fileList
		for(int f:filelist) {
			int classifed;
			if(mle) {
				classifed = performNBClassify_MLE(f,training);
			} else { 
				classifed = performNBClassify_BE(f,training);
			}
			if(label.checkFileCategory(f,classifed)) {
				correct++;
			}
			numberOfFile++;
		}
		return (float)correct/(float)numberOfFile;
	}
	
	/**
	 * This function perform training data
	 */
	public void training() {
		Set<Integer> key = map_File.getMap().keySet();
		for(int c:key) { //For each category
			Set<Integer> words = vocab.getVocabulary().keySet();
			for(int w:words) {
				float mle = computeMLE(w,c);
				float be = computeBE(w,c);
				if(this.trained_data_BE.containsKey(w)) {
					this.trained_data_BE.get(w).put(c,be);
				} else {
					Hashtable<Integer,Float> tmp = new Hashtable<Integer,Float>();
					this.trained_data_BE.put(w,tmp);
				}
			
				if(this.trained_data_MLE.containsKey(w)) {
					this.trained_data_MLE.get(w).put(c,mle);
				} else {
					Hashtable<Integer,Float> tmp = new Hashtable<Integer,Float>();
					this.trained_data_MLE.put(w,tmp);
				}
			}
		}
	}
	
	/**
	 * This function return BE training data value
	 * @param word,category
	 * @return
	 * 	BE value of that word in catgory
	 */
	public float getBE(int word,int category) {
		if(this.trained_data_BE.containsKey(word)) {
			if(this.trained_data_BE.get(word).containsKey(category)) {
				return this.trained_data_BE.get(word).get(category);
			} else {
				return (float)0;
			}
		} else {
			return (float)0;
		}
	
	}
	
	/**
	 * This function return MLE training data value
	 * @param word,category
	 * @return
	 * 	BE value of that word in catgory
	 */
	public float getMLE(int word,int category) {
		if(this.trained_data_MLE.containsKey(word)) {
			if(this.trained_data_MLE.get(word).containsKey(category)) {
				return this.trained_data_MLE.get(word).get(category);
			} else {
				return (float)0;
			}
		} else {
			return (float)0;
		}
	
	}
	
	/**
	 * This function that displays MLE training value
	 */
	public void printMLEs() {
		Set<Integer> words = this.vocab.getVocabulary().keySet();
		for(int word: words) {
			if(this.trained_data_MLE.containsKey(word)) {
				System.out.println(this.trained_data_MLE.containsKey(word) + " = " + this.trained_data_MLE.get(word));
			}
		}
	}
	
	/**
	 * This function the list of category of training data
	 * @return array list of categories
	 */
	public Set<Integer> getCaterogyList() {
		return this.map_File.getMap().keySet();
	}
}
