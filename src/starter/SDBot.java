package starter;


/**
 * Starter bot for the Okanagan College Student Branch IEEE AI Competition. 
 * All bots must implement the BotLogic interface to be able to run on the Engine. 
 * Please name this class with your first and last initials followed by "Bot" so we can keep track of the bots. 
 * So for example Snoop Dog's bot would be named "SDBot.java" 
 * <p> 
 * You may choose to use all or none of this code for your BOT so long as you implement 
 * the BotLogic interface in your __Bot.java class. 
 * 
 * @author Martin Wallace 
 * <p> Martin.V.Wallace@ieee.org
 *
 */
public class SDBot implements codebreaker.BotLogic {

	GameState state; 
	
	public SDBot() { 
		this.state = new GameState(); 
	}
	
	@Override
	public void updateSettings(String setting) {
		state.updateSettings(setting);
	}

	@Override
	public void updateGame(String update) {
		state.updateGame(update);
	}

	@Override
	public String requestMove(String moveType, int timeLimit) {
		
		/// This is where the magic happens dawg. Good luck! // 
		
		
		System.out.println(moveType + " " + state.my_bot);
		// the following is a valid strategy albeit not a good one 
		if(moveType.equals("secret_pattern") && state.my_bot.equals("player_1")){
			return "RED,RED,BLUE,RED,RED";
		}else if(moveType.equals("secret_pattern") && state.my_bot.equals("player_2")){
			return "PINK,BROWN,ORANGE,BLACK,WHITE";
		}
		
		if(state.round_number == 9){
			return "RED,RED,BLUE,RED,RED";
		}
		
		return new Pattern(state.getSize()).toString(); // returns a random pattern for now 
		
		
		
	}

	
}
