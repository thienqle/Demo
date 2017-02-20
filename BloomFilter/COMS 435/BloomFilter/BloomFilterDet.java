/*
* Thien Le
*/
import java.util.BitSet;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class BloomFilterDet{

    private int setSize;
    private int filterSize;
    private BitSet bitSetFilter;
    private int dataSize;
    private int numHashes;


    public static final long FNV64PRIME = 1099511628211L;
    public static final long FNV64INIT = 0xcbf29ce484222325L;

    public int getSetSize(){
	return setSize;
    }

    public BitSet getBloomFilter(){
	return this.bitSetFilter;
    }
    
    /*Create a Bloom filter that can store a set S of cardinality setSize*/
    /*The size of the Filter should approximately be setSize * bitsPerElement.*/
    /*The number of hash functions should be the optimal choice which is ln 2*filterSize/setSize.*/
    BloomFilterDet(int setSize, int bitsPerElement){
	this.setSize = setSize;
	this.filterSize = setSize* bitsPerElement;
	this.numHashes =  (int)(Math.log(2)*filterSize/setSize);
	this.dataSize = 0;
	this.bitSetFilter = new BitSet(this.filterSize);
    }

    /*Add string s to the filter*/
    public void add(String s){
	s = s.toLowerCase();
	long h1 = hash1(s);
	long h2 = hash2(s);
	for(int i=0;i<numHashes;i++){
	    //this.bitSetFilter.set((int)(Math.abs(h % filterSize)*i+s.hashCode())%filterSize));
	    // this.bitSetFilter.set((int)(Math.abs(h)%filterSize));
	    this.bitSetFilter.set((int)(Math.abs(h1 + i*h2)%filterSize));
	}
	this.dataSize++;
    }


    /*Create a hash function*/
    /* public long hash(String s, int seed){
	long h = FNV64INIT;
	for(int i=0;i<s.length();i++){
	    // h = h XOR s[i];
	    h = h ^ s.charAt(i)*seed;
	    h = (h * FNV64PRIME)%(long)Math.pow(2,64);
	}
	return h; 
	}*/

    /**gi(x) = h1(x) + i * h2(x)*,*/
    /*Approach from article : http://zhen.org/blog/benchmarking-bloom-filters-and-hash-functions-in-go/ */
    public long hash1(String s){
	long h = FNV64INIT;
	for(int i=0;i<s.length()/2;i++){
	    h = h ^ s.charAt(i);
	    h = (h * FNV64PRIME)%(long)Math.pow(2,64);
	}
	return h; 
    }

    public long hash2(String s){
	long h = FNV64INIT;
	for(int i=s.length()/2;i<s.length();i++){
	    h = h ^ s.charAt(i);
	    h = (h * FNV64PRIME)%(long)Math.pow(2,64);
	}
	return h; 
    }


       
    /*Returns true if s appears in the Filter; otherwise returns false.*/
    public boolean appears(String s){
	s = s.toLowerCase();
	long h1 = hash1(s);
	long h2 = hash2(s);
	for(int i=0;i<numHashes;i++){
	    if(!this.bitSetFilter.get((int)(Math.abs(h1 + i*h2)%filterSize))){
		    return false;
	    }
	    
	}
	return true;
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
}
