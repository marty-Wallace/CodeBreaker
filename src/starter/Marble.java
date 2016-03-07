package starter;

import java.util.Random;

/**
 * Enumeration of the available colours for CodeBreaker patterns 
 *  
 * @author Martin Wallace
 * <p> Martin.V.Wallace@ieee.org
 *
 */
public enum Marble {

	BLACK(0),
	BROWN(1),
	BLUE(2),
	RED(3), 
	ORANGE(4),
	PINK(5), 
	GREEN(6), 
	YELLOW(7),
	WHITE(8),
	;
	
	private final int code;
	private final static Random random = new Random();
	private final static int SIZE = Marble.values().length;
	
	private Marble(int code){
		this.code = code;
	}
	
	public int getCode(){
		return this.code;
	}
	
	public static Marble getRandomMarble(){
		return Marble.values()[random.nextInt(SIZE-1)];
	}
	
	public String toString(){
		return this.name();
	}
	
	
	public boolean equals(Marble m){
		if(m.getCode() == this.code){
			return true;
		}
		return false;
	}
}
