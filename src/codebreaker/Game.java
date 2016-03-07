package codebreaker;

import java.util.ArrayList;

import engine.Engine;
import engine.Player;

public class Game  {
	
	protected ArrayList<Player> bots;
	protected Engine engine;
	

	public void run(){
		
		int gameNum = 0;
		while(!this.gameOver()) {
			engine.sendUpdate("game " + gameNum);
			for(Player p : bots) { 
				engine.sendUpdate(p.status());
			}
			engine.playGame();
			
			if( gameNum++  > CodeBreaker.MAX_GAMES) { break; }  
		}
		
		saveGame();
		
		engine.finish();
	}
	
	public void saveGame() {
		
		// write to file if you want to save 
		
		String gameDump = engine.getDump();
		
		System.out.println(gameDump);
		
	}
	
	public boolean gameOver() { 
		for(Player p : bots ) { 
			if(p.hasWon()) { 
				return true; 
			}
		}
		return false;
	}

}
