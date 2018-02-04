package friend.bot.telegram.api

class Response {
	private ArrayList<IAction> actions	=	new ArrayList<IAction>();
	
	public void add(IAction action) {
		actions.add(action);
	} 
	
	public ArrayList<IAction> get() {
		return actions;
	}
}
