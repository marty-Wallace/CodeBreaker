package codebreaker;

import field.Pattern;


/**
 * A Random AI that simply returns a random pattern every time a move is requested  
 * 
 * @author Martin Wallace
 * <p> Martin.V.Wallace@ieee.org 
 *
 */
public class RandomBot implements BotLogic{
	
	private int count = 0; 

	@Override
	public void updateSettings(String setting) {
		
	}

	@Override
	public void updateGame(String update) {
		
	}

	@Override
	public String requestMove(String moveType, int timeLimit) {
		Pattern p = new Pattern(); //random pattern
		 return p.toString();
	}

}
