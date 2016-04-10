package starterBot;

/**
 * Pattern object with a random pattern function
 * 
 * @author Martin Wallace 
 * <p> Martin.V.Wallace@ieee.org 
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
	 * Returns the marble array
	 * @return - Marble array of this pattern 
	 */
	public Marble[] getPattern(){
		return this.pattern;
	}
}
