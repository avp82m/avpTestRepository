package com.telegram.bot.executers;

import java.util.HashMap;
import com.telegram.bot.executers.command.AboutMeExecuter
import com.telegram.bot.executers.command.DefaultExecuter
import com.telegram.bot.executers.command.HelpExecuter
import com.telegram.bot.executers.command.LoginExecuter
import com.telegram.bot.executers.command.LogoutExecuter
import com.telegram.bot.executers.command.StartExecuter

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