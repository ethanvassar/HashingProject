import java.io.*; // Import IO for file reading and writing
import java.util.*; // Import for Scanner, List, and other utility classes

public class FinalProject { // Main class for the project// used from other reviews and used from project 4
    public static void main(String[] args) throws Exception { // Main method
        // Specify the file containing words to be hashed
        File file = new File("C:\\Users\\anton\\Downloads\\words_a-2.txt");
        Scanner sc = new Scanner(file); // Create a Scanner to read the file
        List<String> words = new ArrayList<>(); // List to store words from the file

        // Read each line from the file and store non-empty lines in the list
        while (sc.hasNextLine()) { 
            String line = sc.nextLine(); // Get current line from file
            if (line.length() > 0) { // If line isn't empty, add to list
                words.add(line);
            }
        }
        sc.close(); // Close the scanner after reading file

        // Define table sizes for different configurations
        int primeTableSize50 = 933047;  // Prime table size for load factor 0.5
        int nonPrimeTableSize50 = 933028; // Non-prime table size for load factor 0.5
        int primeTableSize70 = 606479;  // Prime table size for load factor 0.7
        int nonPrimeTableSize70 = 606471; // Non-prime table size for load factor 0.7

        // Process each configuration with the hash table
        processHashing(words, primeTableSize50, 0.5, "PRIME_0.5_LoadFactor.csv", "Prime (0.5 Load Factor)");
        processHashing(words, nonPrimeTableSize50, 0.5, "NonPrime_0.5_LoadFactor.csv", "Non-Prime (0.5 Load Factor)");
        processHashing(words, primeTableSize70, 0.7, "PRIME_0.7_LoadFactor.csv", "Prime (0.7 Load Factor)");
        processHashing(words, nonPrimeTableSize70, 0.7, "NonPrime_0.7_LoadFactor.csv", "Non-Prime (0.7 Load Factor)");

        // Call searchMultipleWords for performance testing with different configurations
        searchMultipleWords(words, primeTableSize50, "Prime (0.5 Load Factor)", 1, 10, 20, 30, 40, 50);
        searchMultipleWords(words, nonPrimeTableSize50, "Non-Prime (0.5 Load Factor)", 1, 10, 20, 30, 40, 50);
        searchMultipleWords(words, primeTableSize70, "Prime (0.7 Load Factor)", 1, 10, 20, 30, 40, 50);
        searchMultipleWords(words, nonPrimeTableSize70, "Non-Prime (0.7 Load Factor)", 1, 10, 20, 30, 40, 50);
    }

    private static void processHashing(List<String> words, int tableSize, double loadFactor, String csvFileName, String tableType) throws IOException {
        String[] hashTable = new String[tableSize]; // Array to represent the hash table
        int[] probeCounts = new int[tableSize]; // Array to track probe counts per index

        // Create a BufferedWriter to write to the CSV file
        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName));
        writer.write("Index/Hash value,Count\n"); // Write CSV header

        // Insert all words into the hash table
        for (String word : words) {
            int hash = Math.abs(word.hashCode()) % tableSize; // Find the hash index
            int i = 0; // Probe counter starts at 0
            while (hashTable[(hash + i) % tableSize] != null) { // Check for collision using linear probing
                i++; // Move to next spot if occupied
            }
            hashTable[(hash + i) % tableSize] = word; // Place word in table
            probeCounts[(hash + i) % tableSize]++; // Update probe count for this index
        }

        // Write probe counts to the CSV file
        for (int i = 0; i < tableSize; i++) {
            writer.write(i + "," + probeCounts[i] + "\n"); // Save index and probe count
        }
        writer.close(); // Close the writer after writing file

        // Measure search performance
        long startTime = System.nanoTime(); // Start timing the search
        int maxHash = 0; // Keep track of max probes
        for (String word : words) {
            int hash = Math.abs(word.hashCode()) % tableSize; // Find index in hash table
            int i = 0; // Probe counter
            while (hashTable[(hash + i) % tableSize] != null &&
                    !hashTable[(hash + i) % tableSize].equals(word)) { // Look for the word
                i++;
            }
            if (i > maxHash) { // Update max probes if current is larger
                maxHash = i;
            }
        }
        long endTime = System.nanoTime(); // Stop timing the search
        double searchTime = (endTime - startTime) / 1e6; // Convert time to milliseconds

        // Count unused positions in the hash table
        int notHashedCount = 0;
        for (String value : hashTable) {
            if (value == null) { // Count empty slots in table
                notHashedCount++;
            }
        }

        // Print performance metrics
        System.out.println("Table Type: " + tableType); // Print table type
        System.out.println("Table Size: " + tableSize); // Print size of table
        System.out.printf("Load Factor: %.2f\n", loadFactor); // Print load factor
        System.out.println("Maximum number of probes: " + maxHash); // Print max probes
        System.out.println("Number of positions not hashed to: " + notHashedCount); // Print empty spots
        System.out.printf("Search time: %.3f ms\n", searchTime); // Print time in ms
        System.out.println("Data exported to: " + csvFileName); // Tell user where file was saved
        System.out.println("-----------------------------------------"); // Separator for readability

        // Print the first 20 words in the hash table (indices 1 through 20 inclusive)
        System.out.println("Words at indices 1-20 in hash table for " + tableType + ":"); // Info about specific table
        for (int i = 1; i <= 20; i++) { // Loop through indices 1-20
            System.out.println("Index " + i + ": " + (hashTable[i] != null ? hashTable[i] : "null")); // Print word or null
        }
        System.out.println("-----------------------------------------"); // Separator 

    }

    private static void searchMultipleWords(List<String> words, int tableSize, String tableType, int... wordCounts) {
        String[] hashTable = new String[tableSize]; // Array to represent the hash table
        for (String word : words) { // Insert words into hash table
            int hash = Math.abs(word.hashCode()) % tableSize; // Get hash index
            int i = 0; // Start probe count
            while (hashTable[(hash + i) % tableSize] != null) { // Handle collisions
                i++; // Increment probe count
            }
            hashTable[(hash + i) % tableSize] = word; // Store word
        }

        // Loop through specified word counts for search performance
        for (int wordCount : wordCounts) { 
            List<String> searchWords = words.subList(0, Math.min(wordCount, words.size())); // Get words to search
            long totalSearchTime = 0; // Initialize total search time

            for (String word : searchWords) { // Loop through words to search
                long startTime = System.nanoTime(); // Start search timing
                int hash = Math.abs(word.hashCode()) % tableSize; // Get hash index
                int i = 0; // Probe counter
                while (hashTable[(hash + i) % tableSize] != null && !hashTable[(hash + i) % tableSize].equals(word)) {
                    i++; // Increment probe if not found
                }
                totalSearchTime += System.nanoTime() - startTime; // Add search time
            }

            double avgSearchTime = totalSearchTime / (double) wordCount; // nanoseconds
            System.out.printf("Search in %s for %d words: Average search time: %.3f ns\n", tableType, wordCount, avgSearchTime); // Print average search time
        }
        System.out.println("-----------------------------------------"); // Separator 
    }
}
// end of program