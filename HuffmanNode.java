/**
 * Class used to create a Node that stores the character and frequency of the character within the file. Used also to create the Huffman Tree as 
 * each node stores the left and right child. 
 *  
 * @author Alex Pallozzi
 */
public class HuffmanNode {
	
	//Stores the character value.
	private Character inChar; 
	//Stores the frequency of the character.
	private int frequency; 
	//Stores the left child.
	private HuffmanNode left; 
	//Stores the right child. 
	private HuffmanNode right; 
	
	/**
	 * Constructor for the HuffmanNode that includes 
	 * @param inChar character used for construction
	 * @param frequency used for construction
	 * @param left child used for construction
	 * @param right child used for construction
	 */
	public HuffmanNode(Character inChar, int frequency, HuffmanNode left, HuffmanNode right) {
		this.inChar = inChar; 
		this.frequency = frequency; 
		this.left = left; 
		this.right = right; 
	}
	
	/**
	 * Constructor for the HuffmanNode that includes 
	 * @param inChar character used for construction
	 * @param frequency used for construction
	 */
	public HuffmanNode(Character inChar, int frequency) {
		this.inChar = inChar; 
		this.frequency = frequency; 
		this.left = null; 
		this.right = null; 
	}
	

	/**
	 * @return the inChar
	 */
	public Character getInChar() {
		return inChar;
	}

	/**
	 * @param inChar the inChar to set
	 */
	public void setInChar(Character inChar) {
		this.inChar = inChar;
	}

	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the left child
	 */
	public HuffmanNode getLeft() {
		return left;
	}

	/**
	 * @param left the left child to set
	 */
	public void setLeft(HuffmanNode left) {
		this.left = left;
	}

	/**
	 * @return the right child
	 */
	public HuffmanNode getRight() {
		return right;
	}

	/**
	 * @param right the right child to set
	 */
	public void setRight(HuffmanNode right) {
		this.right = right;
	}

	/**
	 * A method that returns the String representation of the HuffmanNode.
	 * @return returns the string representation of the HuffmanNode. 
	 */
	public String toString() {
		return this.inChar + " " + this.frequency;
	}
}
