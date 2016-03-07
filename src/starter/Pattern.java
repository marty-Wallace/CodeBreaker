package starter;


import java.util.Arrays;


/**
 * Pattern object with a random pattern function
 * 
 * @author Martin Wallace 
 * <p> Martin.V.Wallace@ieee.org 
 *
 */
public class Pattern {

	private Marble[] pattern; 
	private int size; // size of pattern; how many marbles 


	/**
	 * Creates random Pattern
	 */
	public Pattern(int size){
		this.size = size; 
		pattern = new Marble[size];
		for(int i = 0; i < size; i ++){
			pattern[i] = Marble.getRandomMarble();
		}
	}

	/**
	 * Constructor for a pattern with a string. Note that this does not handle any errors 
	 * @param patternString - String representation of the pattern you wish to create 
	 */
	public Pattern(String patternString){

		String[] parts = patternString.split(",");
		this.size = parts.length;
		
		pattern = new Marble[size];
		
		for(int i = 0; i < size; i++) {
			pattern[i] = Marble.valueOf(parts[i]);
		}
		
		
	}

	@Override
	public String toString(){
		String ret = "";
		for(int i = 0; i < pattern.length; i++){
			ret += pattern[i].toString() + ",";
		}
		return ret.substring(0, ret.length()-1);
	}

	/**
	 * Function to check how many (red pegs) colors are in the right position in the pattern compared to this pattern
	 * @param match - the pattern being compared to this pattern 
	 * @return - the number of correct colors in the right position (red pegs)
	 */
	public int howManyRed(Marble[] match){
		int numReds = 0;
		for(int i = 0; i < match.length; i++){
			if(pattern[i].equals(match[i])){
				numReds++;
			}
		}
		return numReds;
	}

	/**
	 * Function to check the number of (white pegs) right colors in the wrong position of a pattern compared to this pattern. 
	 * @param match - the pattern being compared to this pattern
	 * @return - the number of correct colors in the wrong position (white pegs)
	 */
	public int howManyWhites(Marble[] match) {
		int numWhites = 0;
		boolean[]duplicates1 = new boolean[match.length];
		boolean[]duplicates2 = new boolean[match.length];
		Arrays.fill(duplicates1, true);
		Arrays.fill(duplicates2, true);
		for(int i = 0; i < match.length; i++){
			for(int j = 0; j < match.length; j++){
				if(duplicates1[i] && duplicates2[j] && pattern[i].equals(match[j])){
					numWhites++;
					duplicates1[i] = false;
					duplicates2[j] = false;
					break;
				}
			}
		}
		return numWhites;
	}
	
	/**
	 * Returns the marble array
	 * @return - Marble array of this pattern 
	 */
	public Marble[] getPattern(){
		return this.pattern;
	}
}
