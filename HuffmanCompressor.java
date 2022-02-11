import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException; 

/**
 * This is a HuffmanCompressor class that utilizes a HuffmanNode class to convert a text file to 0's and 1's in order 
 * to save space. This class uses a variety of helper methods in order to accomplish this task. The class first scans the input file,
 * places all the unique characters into a HashMap with their frequency, converts the HashMap into an ArrayList, Builds the Huffman
 * Tree from the ArrayList, creates an encoding table and transcribes the input file to the output file in the form of 0's and 1's. 
 * 
 * @author Alexander Pallozzi
 */
public class HuffmanCompressor {

	//Stores the list of HuffmanNodes.
	private ArrayList<HuffmanNode> list = new ArrayList<HuffmanNode>(); 
	//Stores the Character along with the frequency they appear in the input file. 
	private HashMap<Character, Integer> freqMap = new HashMap<Character, Integer>(); 
	//Stores the Character along with the 0 and 1 code that corresponds with the character. 
	private HashMap<Character, String> valueMap = new HashMap<Character, String>(); 
	//Stores the total space saved from the program. 
	private int spaceSaved; 

	/**
	 * Helper method used to escape the special characters within a string. 
	 * @param x passes in the string to which the special characters are to be escaped. 
	 * @return returns the string with all special characters escaped. 
	 */
	public static String escapeSpecialCharacter(String x) {
		StringBuilder sb = new StringBuilder();
		for(char c: x.toCharArray()) {
			if(c>=32 && c < 127) sb.append(c); 
			else sb.append("[0x" + Integer.toOctalString(c) + "]");
		}
		return sb.toString();
	}
	
	/**
	 * Method that functions to scan the input file, and place all the characters into a hashmap with their respective frequencies. 
	 * The method then places all the characters into HuffmanNodes that are then placed into an ArrayList unsorted. 
	 * @param fileName passes in the name of the file in order 
	 * @throws IOException
	 */
	public void scan(String fileName) throws IOException {
		File input = new File(fileName);
		FileReader reader = new FileReader(input);
		StringBuilder string = new StringBuilder(); 
		BufferedReader buff = new BufferedReader(reader);
		//String used to read through the file. 
		String s = buff.readLine();
		//while the reader still has more lines to read. 
		while(s != null) {
			//appends the string onto the StringBuilder while escaping special characters. 
			string.append(escapeSpecialCharacter(s));
			s = buff.readLine();
		}
		buff.close();
		//Converts the StringBuilder into a String. 
		String string1 = string.toString();
		/**
		 * Loop that traverses the string form of the input text file. If the character existed in the HashMap already 
		 * the frequency is incremented, otherwise it is added into the Hashmap. 
		 */
		 for(int i = 0; i < string1.length(); i++) {
			if(!freqMap.containsKey(string1.charAt(i))) {
				freqMap.put(string1.charAt(i), 1); 
			}
			else if(freqMap.containsKey((Character)string.charAt(i))) {
				freqMap.replace(string.charAt(i), freqMap.get(string.charAt(i)) + 1);
			}
		}
		Iterator<Character> itr = freqMap.keySet().iterator();
		Character iteration = itr.next();
		//Iterator that iterates through the HashMap and adds all of the key value pairs into an ArrayList of HuffmanNodes. 
		while(itr.hasNext()) {
			list.add(new HuffmanNode(iteration, freqMap.get(iteration)));
			iteration = itr.next();
		}
		list.add(new HuffmanNode(iteration, freqMap.get(iteration)));
	}
	
	/**
	 * Method that merges two nodes into one and receives the combined frequency of both. The nodes merged become the children of 
	 * the parent node. 
	 * @param node1 first HuffmanNode passed into the method for merging.
	 * @param node2 second HuffmanNode passed into the method for merging. 
	 * @return returns the merged HuffmanNode. 
	 */
	public HuffmanNode merge(HuffmanNode node1, HuffmanNode node2) {
		HuffmanNode newNode = new HuffmanNode(null, node1.getFrequency() + node2.getFrequency(), node1, node2);
		if(node1.getFrequency() < node2.getFrequency()) {
			newNode.setLeft(node1);
			newNode.setRight(node2);
		}
		else if(node1.getFrequency() >= node2.getFrequency()){
			newNode.setLeft(node2);
			newNode.setRight(node1);
		}
		return newNode; 
	}

	/**
	 * Method that functions to sort the HuffmanNodes by their frequency from least frequent to most frequent. This method is a 
	 * selection sort that traverses the ArrayList and finds the smallest frequency, and places the HuffmanNode with that smallest 
	 * frequency at the front and swaps with the HuffmanNode that was at the front in the first place. The outer loop then increments
	 * and sorts the array again looking for the smallest frequency value, but leaves the first element and all the elements that 
	 * are put towards the front of the ArrayList. 
	 */
	public void sortList() {
		for (int i = 0; i < list.size(); i++) {  
			int index = i;  
			for (int j = i + 1; j < list.size(); j++){  
				if (list.get(j).getFrequency() < list.get(index).getFrequency()){  
					index = j;  
				}  
			}  
			HuffmanNode temp = list.get(index); 
			list.set(index, list.get(i));
			list.set(i, temp);
		}
	}
	
	/**
	 * Method that builds the Huffman Tree. This is done by using a loop that executes while the list has more than two elements 
	 * inside of it. The method merges the first two HuffmanNodes at the beginning of the list, which happen to be the lowest 
	 * frequency Nodes because the list is sorted at the beginning of the loop. The merged node is then added to the end of the 
	 * list, and the two front nodes are removed. This continues until there are only two elements in the list, which are then merged 
	 * after the loop ends. 
	 * @return
	 */
	public HuffmanNode buildTree() {
		while(list.size() > 2) {
			sortList();
			HuffmanNode leftMerge = list.get(0);
			HuffmanNode rightMerge = list.get(1);
			HuffmanNode tempMerge = merge(leftMerge, rightMerge); 
			list.add(tempMerge);
			list.remove(0);
			list.remove(0);
		}
		HuffmanNode newRoot = merge(list.get(0), list.get(1));
		list.add(newRoot);
		list.remove(0);
		list.remove(0);
		return list.get(0);
	}
	
	/**
	 * Recursive method that functions to add the coded values to a HashMap where the coded value for a specific character is stored
	 * using the character as the key value. If the root's left and right children are null, the method prints the output value which
	 * is the character: frequency: value, and adds the value to the character in the HashMap. 
	 * @param root passes in the root node of the Huffman Tree. 
	 * @param value the coded value for a specific character that is later added to the HashMap. 
	 */
	public void encoder(HuffmanNode root, String value) {
		String output = ""; 
		if(root.getLeft() != null) 
			encoder(root.getLeft(), value + "0");
		if(root.getRight() != null)
			encoder(root.getRight(), value + "1");
		if(root.getLeft() == null && root.getRight() == null) {
			output = root.getInChar() + ":" + root.getFrequency() + ":" + value + "\n";
			valueMap.put(root.getInChar(), value); 
			System.out.print(output);
		}	
	}
	/**
	 * Method that functions to scan the input file again, and use the input file and the HashMap filled with the coded versions 
	 * of the characters to transcribe the the input file into 0's and 1's on the output file. This is done by using a fileWriter 
	 * and the coded portion of the HashMap is transcribed onto the output file for each character on the input file. The method also
	 * computes the savings of space by using the Huffman encoding method. 
	 * @param imputFile the input file to be encoded. 
	 * @param outputFile the file that the input file is transcribed on. 
	 * @throws IOException
	 */
	public void transcription(String imputFile, String outputFile) throws IOException {
		File input = new File(imputFile);
		FileReader reader = new FileReader(input);
		StringBuilder string = new StringBuilder(); 
		BufferedReader buff = new BufferedReader(reader); 
		String s = buff.readLine();
		//Input file is read again, while the special characters are escaped. 
		while(s != null) {
			string.append(escapeSpecialCharacter(s));
			s = buff.readLine();
		}
		buff.close();
		//Variables that store the space used before the encoding, and the space used after the encoding. 
		int spaceBefore = 0;
		int spaceAfter = 0;
		String string1 = string.toString();
		StringBuilder newString = new StringBuilder();
		//New FileWriter created to write encoded version of the input file. 
		FileWriter writer = new FileWriter(outputFile); 
		//Loops through the string that represents the input file
		for(int i = 0; i < string1.length(); i++) {
			//each character is 8 bits so for each character the space before encoding increases by 8bits. 
			spaceBefore += 8; 
			//gets the length of the coded version of the character to get the individual space taken up by that character.
			int length = String.valueOf(valueMap.get(string1.charAt(i))).length();
			//the individual space is then added to the total spaced used after the encoding. 
			spaceAfter += length;
			//Appends the coded version of the character on a new string builder. 
			newString.append(valueMap.get(string.charAt(i))+ "");
		}
		//That stringbuilder is then written using the FileWriter on the output file. 
		writer.write(newString.toString());
		writer.close();
		//Space is calculated from the difference in the space used before and after the encoding. 
		this.spaceSaved = spaceBefore - spaceAfter; 
		System.out.println("You saved" + " " + this.spaceSaved + " " + "space!");
	}
	
	/**
	 * Method that utilizes the helper methods above to actually transcribe on the output file. If there is a IOException, the 
	 * method catches it and returns "Input File Error", otherwise "OK" is returned and the file was successfully encoded. 
	 * @param inputFileName file to be encoded using the Huffman Tree. 
	 * @param outputFileName file that the input file is to be transcribed on in its coded version
	 * @return returns whether the file was encoded successfully. 
	 */
	public String huffmanCoder(String inputFileName, String outputFileName) {
		try {
			this.scan(inputFileName); 
			this.encoder(this.buildTree(), "");
			this.transcription(inputFileName, outputFileName);
		}
		catch(IOException e) {
			return "Input File Error";
		}
		return "Ok"; 
		
	}

	/**
	 * Method used to run the program using the command arguments of the two file names. 
	 * @param args the input and output file names are to be entered here. 
	 */
	public static void main(String[] args) {
		HuffmanCompressor h = new HuffmanCompressor(); 
		System.out.print(h.huffmanCoder(args[0], args[1]));
	}

}
