package field;

import java.util.Arrays;

import codebreaker.CodeBreaker;

public class Pattern {

	private Marble[] pattern;
	private static final int SIZE = CodeBreaker.COLS; // size of pattern

	/**
	 * Creates random Pattern
	 */
	public Pattern(){
		pattern = new Marble[SIZE];
		for(int i = 0; i < SIZE; i ++){
			pattern[i] = Marble.getRandomMarble();
		}
	}

	public Pattern(String patternString){

		pattern = new Marble[SIZE];
		
		String[] patternArray = patternString.split(",");
		
		if(patternArray.length != SIZE){
			for(int i = 0; i < patternArray.length; i++){
				try{
					pattern[i] = Marble.valueOf(patternArray[i].toUpperCase());
				}catch(Exception e) {
					pattern[i] = Marble.NULL;
				} 
			}
			for(int i = patternArray.length; i < CodeBreaker.COLS; i++) {
				pattern[i] = Marble.NULL;
			}
		}else{
			for(int i = 0; i < SIZE; i++){
				try{
					pattern[i] = Marble.valueOf(patternArray[i].toUpperCase());
				}catch(Exception e) { 
					pattern[i] = Marble.NULL;
				}
			}
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
	 * Function to check the number of (white pegs) right colors in the wrong position of a pattern compared to this pattern
	 * 
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

	public Marble[] getPattern(){
		return this.pattern;
	}
}
