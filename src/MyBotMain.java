//The bot connects to freenode.net and joins the channel called "dkChatBot".
//
//


public class MyBotMain {
	
	public static void main(String[] args) throws Exception {
		//start bot
		MyBot bot = new MyBot();
		
		//enable debugging
		bot.setVerbose(true);
		
		//connect to irc server
		bot.connect("irc.freenode.net");
		
		//join channel
		bot.joinChannel("#dkChatBot");
	}

}
