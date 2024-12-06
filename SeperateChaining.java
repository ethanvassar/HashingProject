import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
//Main class
public class SeperateChaining {
//Main methid
public static void main(String[] args) throws Exception {
//Create a file object
File file = new File("C:\\Users\\adamw\\Downloads\\words_a-2.txt");
//Read words from file
List<String> words = read(file);
//Remove word limit
List<String> limitWords = words;
//Set table size
int tSize = 446514;
//Create main hash table
List<String>[] mainTable = new List[tSize];
//Create collision table
int[] collisionTable = new int[tSize];
//Initialize hash table with empty lists
for (int i = 0; i < tSize; i++) {
//new empty list
mainTable[i] = new ArrayList<>();
}
//Track number of probes
Map<String, Integer> wordProbes = new LinkedHashMap<>();
//Fill hash table and count probes
for (String word : limitWords){
//Calculate hash index at current word
int index = hashFunction(word, tSize);
//Count number of probes
int probes = mainTable[index].size();
//Store probe count for current word
wordProbes.put(word, probes);
//Add the word to the hash table
mainTable[index].add(word);
//Increment collision count
collisionTable[index]++;
}
//Put words and their probe counts into a CSV file
writeCSV(wordProbes, "C:\\Users\\adamw\\Downloads\\output_words.csv");
//List of words to search
List<String> searchWords = Arrays.asList( //PUT HERE
"a", "a'", "a-", "A&M", "A&P", "A.", "A.A.A.", "A.B.", "A.B.A.", "A.C.",
"A.D.", "A.D.C.", "A.F.", "A.F.A.M.", "A.G.", "A.H.", "A.I.", "A.I.A.", "A.I.D.", "A.L.",
"A.L.P.", "A.M.", "A.M.A.", "A.M.D.G.", "A.N.", "a.p.", "a.r.", "A.R.C.S.", "A.U.", "A.U.C.",
"A.V.", "a.w.", "A.W.O.L.", "A/C", "A/F", "A/O", "A/P", "A/V", "A1", "A-1",
"A4", "A5", "AA", "AAA", "AAAA", "AAAAAA", "AAAL", "AAAS", "Aaberg", "Aachen"
);
//Measure total search time
long searchTime = performSearches(wordProbes, searchWords);

//Print total search time and confirmation of CSV file
System.out.println("Total search time for all words: " + searchTime + " nanoseconds");
System.out.println("Words and probes written to output_words.csv");
}
//Hash function to compute index of a word
public static int hashFunction(String word, int tSize) {
//Compute hash index using hashCode and table size
return Math.abs(word.hashCode() % tSize);
}
//Read all lines from file
public static List<String> read(File file) throws Exception {
//Read all lines from file and return as list of strings
return Files.readAllLines(Paths.get(file.getPath()));
}
//Write words and their probe counts to a CSV file
public static void writeCSV(Map<String, Integer> wordProbes, String filePath) throws IOException {
//Use a BufferedWriter to write to s specific file
try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
{
//Headers for CSV file
writer.write("Word,Probes\n");
//Write each word and its probe count to the CSV file
for (Map.Entry<String, Integer> entry : wordProbes.entrySet()) {
writer.write(entry.getKey() + "," + entry.getValue() + "\n");
}}}

//Method for total search time
public static long performSearches(Map<String, Integer> wordProbes,
List<String> searchWords) {
//Initialize total search time
long searchTime = 0;
//Search for each word
for (String searchWord : searchWords) {
//Start search time before search
long startTime = System.nanoTime();
//Search if word exists
boolean found = wordProbes.containsKey(searchWord);
//Stop search time after search
long endTime = System.nanoTime();
//Calculate the time it took to search
searchTime += (endTime - startTime);
}
//Return total search time
return searchTime;
}
}