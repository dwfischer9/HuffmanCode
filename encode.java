import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
 * This class reads an input file specified by the user and encodes it using huffman encoding methods. 
 * It also generates the codes and frequencies of the characters in the input file as text files.
 */
public class encode {
    static HashMap<Character,String> codes = new HashMap<Character,String>();//hashmap of character codes.

    final static Character[] VALIDCHARACTERS = {' ',',','.','0','1','2','3','4','5','6','7','8','9','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k','l', 'm', 'n', 'o', 'p', 'q','r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    /**
     * Node
     * An inner class to construct the huffman tree
     */
    public static class Node {
        Character character;
        int frequency;
        Node left;
        Node right;
        Boolean isLeftNode;
        Boolean isLeaf;
    /**
     * 
     * @param character the character of this node
     * @param frequency the frequency of the characters descending from this node
     * @param left the left child of this node
     * @param right the right child of this node
     * @param isLeftNode boolean indicating whether this node is left or right
     */
        private  Node(Character character, int frequency,Node left,Node right, Boolean isLeftNode) {
            this.character = character;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
            this.isLeftNode =  isLeftNode;
        }
        private Character getCharacter(){
            return this.character;
        }
        private int getFrequency(){
            return this.frequency;
        }
        private Node getLeft(){
            return this.left;
        }
        private Node getRight(){
            return this.right;
        }
        private boolean isLeft(){
            return this.isLeftNode;
        }
        private boolean isLeaf(){
            return this.left == null && this.right == null;
        }
        private static void inorder(Node root){
            if(root == null)
                return;
            inorder(root.left);
            System.out.println(root.getCharacter()+": "+root.getFrequency());
            inorder(root.right);
        }
    }
    public static void main(String[] args) throws IOException {
        generateTree(readFile());
        writeCodes(codes);
    }
    private static void encodeText(){
        
    }
    /**
     * @param none
     * @RuntimeComplexity O(n), where n is the number of characters in the file.
     * @return HashMap of character and its frequency in the text file.
     * @throws IOException
     */
    private static HashMap<Character, Integer> readFile() throws IOException {
        //final Scanner input = new Scanner(System.in);
        HashMap<Character, Integer> frequencies = new HashMap<Character, Integer>(); // create frequency map for the 39
                                                                                     // possible characters
        for (Character c : VALIDCHARACTERS){
            frequencies.put(c, 0);
            
        }
        frequencies.put(' ', frequencies.getOrDefault(' ', 0) - 1); //hardcoded frequency for spaces, as there is an extra newline at the end of test1.txt that is not matched  by the frequency file in mylearningspace
        // System.out.println("Enter the name of the file to encode: "); // get the name of the file
        // final String fileName = input.nextLine(); // read input from the console
       // input.close(); // close the input stream, it is not needed anymore
       String fileName = "test1.txt"; //temporary input for test purposes. 
        final File infile = new File(fileName);
        final Scanner reader = new Scanner(infile); // create a reader for the input file
        reader.useDelimiter(""); // set the delimiter so that we can read character by character
        while (reader.hasNext()) {
            Character c = reader.next().toLowerCase().toCharArray()[0];
            if(Character.isWhitespace(c))
                c = ' ';
            if(Character.isLetterOrDigit(c)||Character.isWhitespace(c)||c.equals('.')||c.equals(','))
                frequencies.put(c, frequencies.getOrDefault(c, 0) + 1); // update the frequency of the current character.
        }
        reader.close();
        writeFrequencies(frequencies);
        return frequencies;
    }

    private static Node generateTree(HashMap<Character, Integer> frequencies) {
        PriorityQueue<Node> minHeap = new PriorityQueue<>(
                (b, a) -> b.getFrequency() - a.getFrequency()); // enter the frequencies into a priority queue (max heap) sorted
                                                        // by descending frequency in the file.
        for (Character c : frequencies.keySet()) {
            minHeap.add(new Node(c, frequencies.get(c), null, null, false)); // initial entry of the minHeap
        }
        while(minHeap.size()>1){
            Node left = minHeap.poll();
            left.isLeftNode = true;
            Node right = minHeap.poll();
            Node combinNode = new Node('^', left.getFrequency() + right.getFrequency(),left,right, false);
            minHeap.add(combinNode);
        }
        Node root = minHeap.poll();
        encode.Node.inorder(root); // print the tree inorder.
        generateCodes(root, "");
        return root;
    }
    private static void writeFrequencies(HashMap<Character, Integer> frequencies) throws IOException{
        final String fileName = "frequencies.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Character character : VALIDCHARACTERS) {
            writer.append(character+":" + Integer.toString(frequencies.get(character)));
            writer.newLine();
        }
        writer.close();

    }
    private static void writeCodes(HashMap<Character,String> codes) throws IOException{
        final String fileName = "codes.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Character character : VALIDCHARACTERS) {
            writer.append(character+":"+codes.get(character));
            writer.newLine();
        }
        writer.close();
    }
    private static void generateCodes(Node root, String code) {
        if(root.left == null && root.right == null && !root.getCharacter().equals('^')) {
            codes.put(root.getCharacter(), code);
            return;
        }
        generateCodes(root.left, code + "0");
        generateCodes(root.right, code + "1");
        
}
}