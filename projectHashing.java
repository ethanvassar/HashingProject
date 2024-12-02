import java.util.*;
import java.io.*;

class projectHashing
{
    static class hashTable
    {
        String [] Htable;
        int [] count;
        int [] probes;
        int size;
        int wordCount;

        hashTable(int s)
        {
            size = s;
            Htable = new String[size];
            count = new int [size];
            probes = new int [size];
            wordCount = 0;
        }

        int functionOne(String word)
        {
            int answer = Math.abs(word.hashCode()) % size;
            return answer;
        }

        int functionTwo(String word)
        {
            int answer = 7 - (Math.abs(word.hashCode()) % 7);
            return answer;
        }

        void doubleHashing(String word)
        {
            int i = functionOne(word);
            int probe = 0;
            count[i] = count[i] + 1;

            while(Htable[i] != null)
            {
                probe = probe + 1;
                i = (i + functionTwo(word)) % size;
            }

            Htable[i] = word;
            probes[i] = probe;
            wordCount = wordCount + 1;
        }

        void search(String word)
        {
            int index = functionOne(word);
            int start = index;
            int probes = 0;
            
            while(Htable[index] != null)
            {
                if(Htable[index].equals(word))
                {
                    String i = Integer.toString(index);
                    System.out.println(word + " has been found at index " + i);
                }
                
                index = (index + functionTwo(word)) % size;
                probes = probes + 1;
                
                if(index == start)
                {
                    break;
                }
            }
            System.out.println("The search used " + probes + " probes");
            
        }
        
        
        void display(PrintWriter writer)
        {
            for(int i = 0; i < 1001; i++)
            {
                writer.println("Index " + i + ": " + Htable[i] + " Count: " + count[i] + " Probes: " + probes[i]);
            }
        }
    }

    public static void main(String [] args)
    {
        String filePath = "C:\\Users\\vassa\\Downloads\\words_a-2.txt";

        ArrayList <String> wordList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String [] words = line.split("\\s+");
                wordList.addAll(Arrays.asList(words));
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        String [] words = wordList.toArray(new String [0]);
        
        String outputPath = "C:\\Users\\vassa\\OneDrive\\Desktop\\Singly Linked List\\.vscode\\Circular Queue Using Arrays\\.vscode\\doubleendedqueue.java\\.vscode\\Visual Studio Code\\ProjectHashing\\output.txt";

        try(PrintWriter writer = new PrintWriter(new FileWriter(outputPath)))
        {
        
        //case where tablesize is non-prime and load factor is 0.5
        hashTable h1 = new hashTable (words.length * 2);
        for(String word: words)
        {
            h1.doubleHashing(word);
        }
        
        long startTime = System.nanoTime();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("The search took " + duration + " milliseconds");

        //case where tablesize is prime and load factor is 0.5
        hashTable h2 = new hashTable ((words.length * 2) - 9);
        for(String word: words)
        {
            h2.doubleHashing(word);
        }
        
        
        //case where tablesize is non-prime and load factor is 0.7
        hashTable h3 = new hashTable (666501);
        for(String word: words)
        {
            h3.doubleHashing(word);
        }
        
        
        //case where tablesize is prime and load factor is 0.7
        hashTable h4 = new hashTable(666511);
        for(String word: words)
        {
            h4.doubleHashing(word);
        }
        h4.display(writer);
    }

    catch(IOException e)
    {
        e.printStackTrace();
    }
    }
}