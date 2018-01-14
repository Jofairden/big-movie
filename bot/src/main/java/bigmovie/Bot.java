package bigmovie;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public final class Bot extends ListenerAdapter {
	
	// Bot example © Groep 11
	// Replace token! - @todo: make configuration file handling
	// Build project: CTRL + F9
	// Grade JavaExec: CTRL + SHIFT + F10
	// OAuth2 URL Gen: https://discordapp.com/developers/tools/oauth2-url-generator/
	// Make discord app: https://discordapp.com/developers/applications/me
	// -> ensure to transform to user bot! -> grab token
	// RestAction docs: https://github.com/DV8FromTheWorld/JDA/wiki/7%29-Using-RestAction
	// Yui bot: https://github.com/DV8FromTheWorld/Yui/blob/master/src/main/java/net/dv8tion/discord/
	// (db / settings and more)
	
	private static JDA api;
	
	public JDA getAPI() {
		return api;
	}
	
	public static void main(String[] args) {
		//We construct a builder for a BOT account. If we wanted to use a CLIENT account
		// we would use AccountType.CLIENT
		try {
			api = new JDABuilder(AccountType.BOT)
					.setToken("token")           //The token of the account that is logging in.
					.addEventListener(new Bot())  //An instance of a class that will handle events.
					.buildBlocking();  //There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
			api.getPresence().setGame(Game.watching("videos"));
			System.out.println("Bot ready! API loaded!");
		} catch (LoginException | InterruptedException e) {
			//If anything goes wrong in terms of authentication, this is the exception that will represent it
			e.printStackTrace();
		}
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		// We don't want to respond to other bot accounts, including ourselves
		Message message = event.getMessage();
		String content = message.getContentRaw();
		// getContentRaw() is an atomic getter
		// getContent() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
		if (content.equals("!ping")) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
		}
	}
}
