// Dictonary using Quadratic Hashing Final Project

import java.io.*;     //import all packages
import java.util.*;


public class QuadProbing1{

    //method for hash function
    public static String[] hash(List<String> words, int wordCount){
    
    //initialize varibales and hashtable
    int wordAmount = words.size();
    int tableSize = wordCount;    
    //create an array of the keys after being hashed 
    String[] hashTable = new String[tableSize];
    int[] positionCount = new int[wordCount];
    int[] probeCount = new int[wordCount];
    int totalProbes = 0;
    
    int maxCount=0;
    
    //add key values to hash function
    for(String word: words){    //hash function
        int hash = Math.abs(word.hashCode()) % tableSize;
        int i = 0;
        int index = 0;
        positionCount[hash]+=1;
        int ogHash= hash;
        while(hashTable[hash +i] != null){ 
            i++;
            //rehash
            hash = (hash + i*i) % tableSize;
            totalProbes += 1;
            
        }
        hashTable[hash]=word;

        probeCount[hash] = i;                                                                                                                                                                

        if(i>maxCount){
            maxCount=i;
        }
       
    }

     double loadFactor = (double) wordAmount / tableSize;
        // print out time tohash, amount of keys, load factor, number of probes, maximum times an index is hashed to
        System.out.println("Amount of keys: " + wordAmount);
        System.out.println("Load factor: "+ loadFactor);
        System.out.println("Total Number of Probes: " + totalProbes);
        System.out.println("Maximum number of times any position is hashed to: "+ maxCount);
        System.out.println(" ");

        String positionFile = "position_count_" + wordCount + ".csv";
        String probeFile = "probe_count_" + wordCount + ".csv";
        exportPositionCountToCSV(positionCount, positionFile);
        exportProbeCountToCSV(probeCount, probeFile);

        return hashTable;
}

    //export the index count to an external file 
    static void exportPositionCountToCSV(int[] positionCount, String filename){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            writer.write("Index, Count\n");

            for(int i=0; i<positionCount.length; i++){
                writer.write(i+"," + positionCount[i]+ "\n");
            }
        } catch(IOException e){
            System.err.println("Error writing to CSV: "+ e.getMessage());
        }
    }

    //export the number of probes to an external file
    static void exportProbeCountToCSV(int[] probeCount, String filename){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            writer.write("Word, Probes\n");

            for(int i=0; i<probeCount.length; i++){
                writer.write("key index: " + i +"," + probeCount[i]+ "\n");
            }
        } catch(IOException e){
            System.err.println("Error writing to CSV: "+ e.getMessage());
        }
    }

    static long search(int j, String[] hashTable){
        long totalstartTime = System.nanoTime();
        int[] timetable = new int[j];
        for(int i=0; i<j; i++){
        long startTime = System.nanoTime();
        String word= hashTable[i];
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        timetable[i]= (int)duration;
        }
        long totalendTime = System.nanoTime();
        long totalduration = totalendTime - totalstartTime;
        return totalduration;
    }
    static int[] searchEachTime(int j, String[] hashTable){
        int[] timetable = new int[j];
        for(int i=0; i<j; i++){
        long startTime = System.nanoTime();
        String word= hashTable[i];
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        timetable[i]= (int)duration;
        System.out.println(timetable[i]);//prints out array of each time 
        }
        
        return timetable;
    }  
        
    static void AllHashCase( String[] NonPrime5,String[] Prime5,String[] NonPrime7,String[] Prime7, int i){
        long duration1n5= search(i,NonPrime5);
        System.out.println("0.5 and nonprime: " + duration1n5);

        long duration1p5= search(i,Prime5);
        System.out.println("0.5 and prime: " +duration1p5);

        long duration1n7= search(i,NonPrime7);
        System.out.println("0.7 and nonprime: " +duration1n7);

        long duration1p7= search(i,Prime7);
        System.out.println("0.7 and prime: " +duration1p7);
        System.out.println(" ");

    }
            
                // Driver code
                public static void main(String args[]) throws FileNotFoundException{
            
                    File file = new File("C:\\Mack\\CSC2820\\Final Project\\words.txt"); //path to the word txt file
            
                    Scanner sc = new Scanner(file); //scanner to read txt document
                    List<String> words= new ArrayList<>();  

                    int wordCount = 933028;

                    int[] probeCount = new int[wordCount];
                    int[] positionCount = new int[wordCount];
                   
                    while(sc.hasNextLine()){    //to add words to array list
                        String line = sc.nextLine();
                        words.add(line);
                    }
                    sc.close(); //close out scanner
            
                    String[] NonPrime5= hash(words,933028);  //Load Factor = 0.5, non-prime table
                    String[] Prime5=hash(words,933047);  //Load Factor = 0.5, prime table
                    String[] NonPrime7=hash(words,606471);  //Load Factor = 0.7, non-prime table
                    String[] Prime7=hash(words,606479);  //Load Factor = 0.7, prime table
                    
                    System.out.println("One Word");    
                     //one word search
                    AllHashCase(NonPrime5,Prime5,NonPrime7,Prime7,1);
                    System.out.println("Ten Word");
                     //Ten word search
                    AllHashCase(NonPrime5,Prime5,NonPrime7,Prime7,10);
                    System.out.println("Twenty Word");
                    // Twenty word search
                    AllHashCase(NonPrime5,Prime5,NonPrime7,Prime7,20);
                    System.out.println("Forty Word");
                    //Forty word Search
                    AllHashCase(NonPrime5,Prime5,NonPrime7,Prime7,40);
                    System.out.println("Fifty Word");
                    // Fifty word Search
                    AllHashCase(NonPrime5,Prime5,NonPrime7,Prime7,50);
                    int[] duration50p5each = searchEachTime(50,Prime5);
    }

}
