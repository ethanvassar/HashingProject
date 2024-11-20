// Dictonary using Quadratic Hashing Final Project

import java.util.*;     //import all packages
import java.io.*;


public class QuadProbing{

    //method for hash function
    public static void hash(List<String> words, int wordCount){
    
    //initialize varibales and hashtable
    int wordAmount = words.size();
    int tableSize = wordCount;    
    String[] hashTable = new String[tableSize];
    int totalProbes = 0;
    
    int maxCount=0;
    
    //add key values to hash function
    for(String word: words){    //hash function
        int hash = Math.abs(word.hashCode()) % tableSize;
        int i = 0;
        while(hashTable[hash +i] != null){ 
            i++;
            //rehash
            hash = (hash + i*i) % tableSize;
            totalProbes += 1;
        }
        hashTable[hash]=word;

        if(i>maxCount){
            maxCount=i;
        }
    }
    //track the time it takes for search
    long startTime = System.nanoTime();
    String value=hashTable[123456];
    System.out.println(value); 
    long endTime = System.nanoTime();
    long duration = endTime - startTime;

     double loadFactor = (double) wordCount / tableSize;
        // print out time tohash, amount of keys, load factor, number of probes, maximum times an index is hashed to
        System.out.println("time to hash: " + duration);
        System.out.println("Amount of keys: " + wordAmount);
        System.out.println("Load factor: "+ loadFactor);
        System.out.println("Total Number of Probes: " + totalProbes);
        System.out.println("Maximum number of times any position is hashed to: "+ maxCount);
        System.out.println(" ");
}
        
        
            
                // Driver code
                public static void main(String args[]) throws FileNotFoundException{
            
                    File file = new File("C:\\Mack\\CSC2820\\Assignment 4\\words_a-2.txt"); //path to the word txt file
            
                    Scanner sc = new Scanner(file); //scanner to read txt document
                    List<String> words= new ArrayList<>();  
            
            
                   
                    while(sc.hasNextLine()){    //to add words to array list
                        String line = sc.nextLine();
                        words.add(line);
                    }
                    sc.close(); //close out scanner
            
                    hash(words,933028);  //Load Factor = 0.5, non-prime table
                    hash(words,933047);  //Load Factor = 0.5, prime table
                    hash(words,606471);  //Load Factor = 0.7, non-prime table
                    hash(words,606479);  //Load Factor = 0.7, prime table

    }


}
