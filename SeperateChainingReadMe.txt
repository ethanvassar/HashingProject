SeperateChaining.java

This project reads a list of words from a file, puts them into a hash table using separate 
chaining, has a function to search for words, and measures how long it took to search for the 
words. It also calculates the number of probes and writes this into a CSV file. 

You will need to download words.txt and change C:\\Users\\adamw\\Downloads\\words_a-2.txt
to the path where it is downloaded on your computer.
Enter words into line 44 where it says List<String> searchWords = Arrays.asList(//PUT HERE);.

The output will print the total search time for each word. It will also write the number if probes for each word in a CSV file.