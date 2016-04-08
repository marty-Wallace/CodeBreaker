package engine;

import codebreaker.BotLogic;

public class BotChat extends Thread {

	private BotLogic bot;
	public String botSays = null;
	private boolean requestMove;
	private boolean running;
	private String moveType;
	private int timeLimit;
	
	public BotChat(BotLogic bot){
		this.bot = bot;
		this.running = true;
		this.requestMove = false;
	}

	public void sendUpdate(String update){
		bot.updateGame(update);
	}

	public void sendSetting(String setting){
		bot.updateSettings(setting);
	}

	public void requestMove(String moveType, int timeLimit) {
		this.moveType = moveType;
		this.timeLimit = timeLimit;
		this.requestMove = true;
	}

	@Override
	public void run() {

		while(this.running){ // keep BotChat running 
			
			if(requestMove){
				botSays = bot.requestMove(moveType, timeLimit);
				requestMove  = false;
			}

			synchronized(this){
				try {
					while(!requestMove){
						this.wait(); // wait for Engine to wake up bot 
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	public String getReply() {
		return botSays;
	}

	public void finish(){
		this.running = false;
	}


}
