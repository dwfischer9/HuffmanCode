import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;

/*
 * This class reads an input file specified by the user and encodes it using huffman encoding methods. 
 * It also generates the codes and frequencies of the characters in the input file as text files.
 */
public class encode {

    public static void main(String[] args) throws FileNotFoundException {
        getCodes(readFile());
    }

    /**
     * @param none
     * @RuntimeComplexity O(n), where n is the number of characters in the file.
     * @return HashMap of character and its frequency in the text file.
     * @throws FileNotFoundException
     */
    private static HashMap<Character, Integer> readFile() throws FileNotFoundException {
        final Scanner input = new Scanner(System.in);
        HashMap<Character, Integer> frequencies = new HashMap<Character, Integer>(); // create frequency map for the 39
                                                                                     // possible characters
        System.out.println("Enter the name of the file to encode: "); // get the name of the file
        final String fileName = input.nextLine(); // read input from the console
        input.close(); // close the input stream, it is not needed anymore
        final File infile = new File(fileName);
        final Scanner reader = new Scanner(infile); // create a reader for the input file
        reader.useDelimiter(""); // set the delimiter so that we can read character by character
        while (reader.hasNext()) {
            Character c = reader.next().toLowerCase().toCharArray()[0];
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1); // update the frequency of the current character.
        }
        reader.close();
        return frequencies;
    }

    private static void getCodes(HashMap<Character, Integer> frequencies) {
        PriorityQueue<HashMap.Entry<Character, Integer>> maxHeap = new PriorityQueue<>(
                (b, a) -> b.getValue() - a.getValue()); // enter the frequencies into a priority queue (max heap) sorted
                                                        // by descending frequency in the file.
        maxHeap.addAll(frequencies.entrySet());
        while (maxHeap.size() > 1) {
            HashMap.Entry<Character, Integer> leaf1 = maxHeap.poll();
            HashMap.Entry<Character, Integer> leaf2 = maxHeap.poll();
            Map.Entry<Character, Integer> entry = new AbstractMap.SimpleEntry<Character, Integer>('^',
                    leaf1.getValue() + leaf2.getValue());
            System.out.println(entry);
        }
        System.out.println(maxHeap);

    }

}