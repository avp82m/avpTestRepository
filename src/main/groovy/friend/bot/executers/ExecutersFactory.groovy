package friend.bot.executers;

import java.util.HashMap;

import friend.bot.executers.command.AboutMeExecuter
import friend.bot.executers.command.DefaultExecuter
import friend.bot.executers.command.HelpExecuter
import friend.bot.executers.command.LoginExecuter
import friend.bot.executers.command.LogoutExecuter
import friend.bot.executers.command.StartExecuter

public class ExecutersFactory {
	private HashMap<String,IExecuters>botCommands = new HashMap<String,IExecuters>();
	
	ExecutersFactory() {
		botCommands.put("/help", new HelpExecuter());
		botCommands.put("/start", new StartExecuter());
		botCommands.put("/login", new LoginExecuter());
		botCommands.put("/logout", new LogoutExecuter()); 
		botCommands.put("/aboutme", new AboutMeExecuter());
	}
	
	public HashMap<String,IExecuters> getRegisterExecuters(){
		return botCommands;
	}
	
	public IExecuters getExecuterForCommand(String command) {
		if(botCommands.containsKey(command)) {
			return botCommands.get(command);
		}
		return new DefaultExecuter();
	}
}