/*
* Thien Le
*/
import java.util.Random;
import java.util.BitSet;
import java.lang.Object;

public class BloomFilterRan{

    private int setSize;
    private int filterSize;
    private BitSet bitSetFilter;
    private int dataSize;
    private int numHashes;
    private int[] a;
    private int[] b;
   
    /*Create a Bloom filter that can store a set S of cardinality setSize*/
    /*The size of the Filter should be random a prime p that is bigger than N./
    /*The number of hash functions should be the optimal choice which is ln2*filterSize/setSize.*/
    BloomFilterRan(int setSize, int bitsPerElement){
	this.setSize = setSize;
	//int p = RandomPrimeNumberGenerator(setSize,bitsPerElement*setSize);
	int p = NextPrimeNumber(setSize);
	this.filterSize = p; //size of Filter is p
	//this.numHashes = (int)(Math.log(2)*filterSize/setSize);
	this.numHashes = (int)(Math.log(2)*bitsPerElement);
	this.dataSize = 0;
	this.bitSetFilter = new BitSet(this.filterSize);
	this.a = new int[numHashes];
	this.b = new int[numHashes];
	
	for(int i=0;i<numHashes;i++){
	    a[i] = RandomNumberGenerator(0,p-1,a);
	}

	for(int i=0;i<numHashes;i++){
	    b[i] = RandomNumberGeneratorNoDiff(0,p-1);
	}
	
    } 

    public int getSetSize(){
	return setSize;
    }

    public BitSet getBloomFilter(){
	return this.bitSetFilter;
    }
    
    
    /*Add string s to the filter*/
    /*This is case sensitive method*/
    public void add(String s){
	/*Run through all k hash function to update the Bloom Filter*/
	s = s.toLowerCase();
	for(int i=0;i<numHashes;i++){
	    this.bitSetFilter.set(hash(s,a[i],b[i]));
	}
	this.dataSize++;
    }

    /*Returns true if s appears in the Filter; otherwise returns false.*/
    public boolean appears(String s){
	/*Run through all k hash function to check the Bloom Filter*/
	s = s.toLowerCase();
	for(int i=0;i<numHashes;i++){
	    if(!this.bitSetFilter.get(hash(s,a[i],b[i]))){
		    return false;
	    }
	}

	return true;
    }

     /*Create a single hash function*/
     /*h(x) = (ax + b)%p.*/
    /* Since hashCode could return negative value, turn off sign bit of result */
    public int hash(String s,int a, int b){
	s = s.toLowerCase();
	int x = s.hashCode();
	int h = (a*x + b)%this.filterSize;
	h = h & 0x7FFFFFFF;
	return h;
    }
    
    
    /*Return the size of (Filter) table*/
    public int filterSize(){
	return this.filterSize;
    }

    /*Returns the number of elements added to the filter*/
    public int dataSize(){
	return this.dataSize;
    }

    /*Return the number of hash function used*/
    public int numHashes(){
	return this.numHashes;
    }
    
    /*Implement basic prime number checking from https://en.wikipedia.org/wiki/Primality_test */
    public boolean PrimeCheck(int n){
	if(n<=1) return false;
	else if (n>1 && n<=3) return true;
	else if (n%2==0 || n%3 ==0) return false;
	else {
	    int i = 5;
	    while(i*i<=n) {
		if(n % i == 0 || n % (i+2)==0 ) return false;
		i = i + 6;
	    }
	    return true;
	}
    }

  

    public int RandomPrimeNumberGenerator(int min,int max){
	    Random aRandom = new Random();
	    boolean run = true;
	    while(run){
		int prime = aRandom.nextInt()+min;
		if(PrimeCheck(prime)){
		    if(prime > min && prime <= max){
			run = false;
			return prime;
		    }
		}
	    }
	    return min; //default smallest prime
    }

	public int NextPrimeNumber(int n){
	    Random aRandom = new Random();
	    boolean run = true;
	    while(run){
			n = n + 1;
			if(PrimeCheck(n)){
				run = false;
				return prime;
			}
	    }
	    return n;
    }

    
     public int RandomNumberGenerator(int from,int to,int[] diff){
	Random aRandom = new Random();
	boolean run = true;
	while(run){
	    int number = aRandom.nextInt(to)+from;
	    boolean satified = true;
	    for(int i = 0;i<diff.length;i++){
		if(number==diff[i]){
		    satified = false;
		} 
	    }
	    if(satified){
		run = false;
		return number;
	    }
	}
	return 0; //default
    }

     public int RandomNumberGeneratorNoDiff(int from,int to){
	Random aRandom = new Random();
	return aRandom.nextInt(to)+from;
    }

    
}
