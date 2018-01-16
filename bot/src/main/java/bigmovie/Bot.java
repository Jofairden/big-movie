package bigmovie;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import com.rivescript.RiveScript;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

public final class Bot extends ListenerAdapter {
	
	// Bot example © Groep 11
	// Build project: CTRL + F9
	// Grade JavaExec: CTRL + SHIFT + F10
	// OAuth2 URL Gen: https://discordapp.com/developers/tools/oauth2-url-generator/
	// Make discord app: https://discordapp.com/developers/applications/me
	// -> ensure to transform to user bot! -> grab token
	// RestAction docs: https://github.com/DV8FromTheWorld/JDA/wiki/7%29-Using-RestAction
	// Yui bot: https://github.com/DV8FromTheWorld/Yui/blob/master/src/main/java/net/dv8tion/discord/
	// (db / settings and more)
	
	private static JDA api;
	private static BotConfig config;

	public static JDA getAPI() {
		return api;
	}
	public static BotConfig getConfig() { return config; }
	public static RiveScript bot = new RiveScript();
	
	public static void main(String[] args){
		try {
			config = BotConfig.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		bot.loadDirectory("src/main/java/bigmovie/RiveScript");
		bot.sortReplies();
		bot.setSubroutine("jdbc", new JdbcSubroutine());
		bot.setSubroutine("send", new SendSubroutine());
		bot.setSubroutine("system", new SystemSubroutine());

		//We construct a builder for a BOT account. If we wanted to use a CLIENT account
		// we would use AccountType.CLIENT
		try {
			api = new JDABuilder(AccountType.BOT)
					.setToken(config.getTOKEN()) //The token of the account that is logging in
					.addEventListener(new Bot())  //An instance of a class that will handle events.
					.buildBlocking();  //There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
			api.getPresence().setGame(Game.streaming("porn", "pornhub.com"));
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
		long chat_id = event.getMessageIdLong();

		// getContentRaw() is an atomic getter
		// getContent() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
		if (content.equals("!ping")) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
		}
		else {
			try {
				if (content.toLowerCase().startsWith("<@" + BotUtils.Readfile("src/main/java/bigmovie/BotID.txt", StandardCharsets.UTF_8) + ">")) {
                    String reply = bot.reply(String.valueOf(chat_id), content.toLowerCase().replaceFirst("bot", "").trim());
                    MessageChannel channel = event.getChannel();
				if (!reply.isEmpty()) {
					if (reply.startsWith("There it is!")) {
						String path = reply.substring(reply.indexOf('&') + 1);
						channel.sendFile(new File(path)).queue();
						reply = "There it is! The graph produced by R";
					}
				}
				/*reply = reply.replace("\n", ", ").substring(0, 2000);
				int last = reply.lastIndexOf(",");
				reply = reply.substring(0, last);*/
				if (reply.length() > 2000)
					reply = reply.substring(0, 2000);

				channel.sendMessage(reply).queue();

					if (reply.startsWith("There it is!RGenreFile:")) {

						String path = reply.substring(reply.indexOf('&') + 1);
						channel.sendFile(new File(path)).queue();
					}
					/*reply = reply.replace("\n", ", ").substring(0, 2000);
					int last = reply.lastIndexOf(",");
					reply = reply.substring(0, last);*/
					channel.sendMessage(reply).queue();
        	//				}
                    }
                }
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
