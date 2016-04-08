package codebreaker;

import field.Pattern;


/**
 * A Random AI for testing that simply returns a random pattern every time a move is requested  
 * 
 * @author Martin Wallace
 * <p> Martin.V.Wallace@ieee.org 
 *
 */
public class RNBot implements BotLogic{
	
	@Override
	public void updateSettings(String setting) {
		
	}

	@Override
	public void updateGame(String update) {
		
	}

	@Override
	public String requestMove(String moveType, int timeLimit) {
		 return new Pattern().toString(); //random pattern
	}

}
