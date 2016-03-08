package codebreaker;

import field.Pattern;


/**
 * A Random AI that simply returns a random pattern every time a move is requested  
 * 
 * @author Martin Wallace
 * <p> Martin.V.Wallace@ieee.org 
 *
 */
public class RNBot implements BotLogic{
	
	private int count = 0; 

	@Override
	public void updateSettings(String setting) {
		
	}

	@Override
	public void updateGame(String update) {
		
	}

	@Override
	public String requestMove(String moveType, int timeLimit) {
		System.out.println(moveType + " " );
		Pattern p = new Pattern(); //random pattern
		count ++;
		long start = System.currentTimeMillis();
		for(int i = 0 ; i < 100000000; i++) {
			for(int j = 0; j < 100000000; j++) {
				int x = i*j;
			}
		}
		System.out.println(System.currentTimeMillis()-start);
		 return p.toString();
	}

}
