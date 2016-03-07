package codebreaker;

import java.util.ArrayList;

import engine.BotChat;
import engine.Engine;
import engine.Player;
import starter.SDBot;

public class CodeBreaker extends Game {
	
	public static final int COLS = 5;             // the number of marbles in a pattern 
	public static final int MAX_GAMES = 2;        // the maximum number of games that can be played before the engine exits 
	public static final int BEST_OF = 1;          // the number of games a player needs to win to win the entire match 
	public static final int TIME_PER_MOVE = 500;  // maximum number of milliseconds an AI  can take to make a move 
	public static final int MAX_TIMEBANK = 5000;  // maximum amount of milliseconds an AI can have in its time bank 
	public static final int MAX_OFFENCES = 2;     // maximum number of times an AI is allowed to not respond or go over its time limit
	
	private void setupGame(){
		
		BotLogic bot1 = new SDBot(); // change this to use players AI's or leave as Bot to have Random Opponent
		BotLogic bot2 = new SDBot(); // change this to use players AI's or leave as Bot to have Random Opponent
		
		super.bots =  new ArrayList<Player>();
		bots.add(new Player(new BotChat(bot1), "player_1"));
		bots.add(new Player(new BotChat(bot2), "player_2")); 
		setOpponents();
		
		for(Player p : bots) { 
			p.botIO.start();
			p.botIO.sendSetting("settings your_bot " + p.getName());
		}
		
		super.engine = new Engine(this.bots);
		engine.sendSetting("size " + COLS);
		engine.sendSetting("best_of " + BEST_OF);
		engine.sendSetting("time_per_move " + TIME_PER_MOVE);
		engine.sendSetting("max_timebank " + MAX_TIMEBANK);
	}
	
	
	public void setOpponents() { 
		Player bot1 = bots.get(0);
		Player bot2 = bots.get(1);
		bot1.setOpponent(bot2);
		bot2.setOpponent(bot1);
	}
	
	
	public static void main(String[] args) {
		CodeBreaker game = new CodeBreaker();
		game.setupGame();
		game.run();
		System.exit(0);
	}
	

}
