//WordSeg.java - Optimal segmentation of a string based on word quality
//See Kleinberg & Tardos pgs. 316 - 317 for a problem definition.
//Code By: Matthew Fritter, November 2017
import java.util.ArrayList;

public class WordSeg {

	/**
	 * Main method - Uses dynamic programming to determine the optimal segmentation
	 * of an input string based on the "quality" of each segment
	 */
	public static void main(String[] args) {
		//Input string
		String str = "soitgoes";
		//Arrays for optimal sub-solutions and segment pointers (string length + 1 for base case 0)
		int[] opt = new int[str.length()+1];
		int[] pointer = new int[str.length()+1];
		//Base case for an empty string
		opt[0] = 0;
		//For every character in the string
		for(int i=1; i <= str.length(); i++) {
			//Base optimal value - string is all characters from 0 to i
			opt[i] = quality(str.substring(0, i));
			//For every character from 1<=z<=i
			for(int z=1; z <= i; z++) {
				//Value of previous optimal segment (1 .. z-1) plus quality of segment (z .. i)
				int recurrence = opt[z-1] + quality(str.substring((z-1),i));
				//If the recurrence is greater than current opt[i]
				if(recurrence > opt[i]) {
					//Set opt[i] to the recurrence
					opt[i] = recurrence;
					//Set the pointer to z-1 to indicate where to segment starts
					pointer[i] = z-1;
				}
			}
		}
		//Call the traceback to get the actual optimal segmentation
		ArrayList<String> words = traceback(pointer, str);
		//Print optimal value and segments
		System.out.println(opt[str.length()]);
		System.out.println(words.toString());
	}
	
	/**
	 * Basic Quality Function
	 * Returns integer value equal to string length for valid (defined) words,
	 * Otherwise returns -1;
	 * @param str - String to be checked
	 * @return - integer "quality" of input string
	 */
	public static int quality(String str) {
		switch(str) {
			case "so": return 2;
			case "it": return 2;
			case "goes": return 4;
			case "go": return 2;
			case "i": return 1;
			default: return -1;
		}
	}
	
	/**
	 * Traceback Function - Uses pointers from main method to determine segmentation of string
	 * @param pointer - Array of pointer values to check
	 * @param str - The original string that is being segmented
	 * @return - ArrayList of string segments (words)
	 */
	public static ArrayList<String> traceback(int[] pointer, String str) {
		ArrayList<String> words = new ArrayList<String>();
		for(int i = str.length(); i > 0; i++) {
			words.add(0, str.substring(pointer[i], i));
			i = pointer[i] - 1;
		}
		return words;
	}
	
}
