package bigmovie;

import bigmovie.subroutines.*;
import com.rivescript.Config;
import com.rivescript.RiveScript;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
	
	// Define a static logger variable so that it references the
	// Logger instance named "Bot".
	private static final Logger logger = LogManager.getLogger(Bot.class);
	
	private static JDA api;
	private static BotConfig config;
	
	public static JDA getAPI() {
		return api;
	}
	
	public static BotConfig getConfig() {
		return config;
	}
	
	public static RiveScript bot = new RiveScript(Config.utf8());
	
	// subroutines
	public final static ActorInMoviesSubroutine actorInMoviesSubroutine = new ActorInMoviesSubroutine();
	public final static SystemSubroutine systemSubroutine = new SystemSubroutine();
	public final static RscriptSubroutine rscriptSubroutine = new RscriptSubroutine();
	public final static MessageSubroutine messageSubroutine = new MessageSubroutine();
	public final static MoviesInXCountriesSubroutine moviesInXCountriesSubroutine = new MoviesInXCountriesSubroutine();
	public final static SoundtrackSubroutine soundtrackSubroutine = new SoundtrackSubroutine();
	public final static MoviesHighScoreLowVotesSubroutine moviesHighScoreLowVotesSubroutine = new MoviesHighScoreLowVotesSubroutine();
	public final static MovieXScoreSubroutine moviesXScoreSubroutine = new MovieXScoreSubroutine();
	public final static PopularLanguageSubroutine popularLanguageSubroutine = new PopularLanguageSubroutine();
	public final static MovieLocationSubroutine movieLocationSubroutine = new MovieLocationSubroutine();
	public final static WriteExecuteSendR writeExecuteSendR = new WriteExecuteSendR();
	public final static YoutubeSubroutine youtubeSubroutine = new YoutubeSubroutine();
	public final static RandMessageSubroutine randMessageSubroutine = new RandMessageSubroutine();
	
	public static void main(String[] args) {
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("java logging level is DEBUG Enabled");
		}
		
		try {
			logger.info("Loading BotConfig...");
			config = BotConfig.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("Setting up RiveScript subroutines...");
		// Load subroutines
		bot.loadDirectory("src/main/resources/rivescript");
		bot.sortReplies();
		bot.setSubroutine("actorinmovies", actorInMoviesSubroutine);
		bot.setSubroutine("system", systemSubroutine);
		bot.setSubroutine("rscript", rscriptSubroutine);
		bot.setSubroutine("buildmessage", messageSubroutine);
		bot.setSubroutine("moviesinxcountries", moviesInXCountriesSubroutine);
		bot.setSubroutine("soundtracks", soundtrackSubroutine);
		bot.setSubroutine("movieshighscorelowvotes", moviesHighScoreLowVotesSubroutine);
		bot.setSubroutine("moviesxscore", moviesXScoreSubroutine);
		bot.setSubroutine("language", popularLanguageSubroutine);
		bot.setSubroutine("movielocation", movieLocationSubroutine);
		bot.setSubroutine("write", writeExecuteSendR);
		bot.setSubroutine("youtube", youtubeSubroutine);
		bot.setSubroutine("randmessage", randMessageSubroutine);
		
		logger.info("Initiating JDA API and logging in...");
		//We construct a builder for a BOT account. If we wanted to use a CLIENT account
		// we would use AccountType.CLIENT
		try {
			api = new JDABuilder(AccountType.BOT)
					.setToken(config.getTOKEN()) //The token of the account that is logging in
					.addEventListener(new Bot())  //An instance of a class that will handle events.
					.buildBlocking();  //There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
			api.getPresence().setGame(Game.watching("movies"));
			logger.info("Bot ready! API loaded!");
		} catch (LoginException | InterruptedException e) {
			//If anything goes wrong in terms of authentication, this is the exception that will represent it
			e.printStackTrace();
		}
		
		logger.info("Initializing pacman...");
		// init stuff
		BotUtils.execRSript(Objects.requireNonNull(BotUtils
				.getResourcePath("rscript/initPacman.R"))
				.toString());
	}
	
	// stores the last received event
	// used to access last channel etc.
	public static MessageReceivedEvent lastMessageReceivedEvent;
	
	// stores last query result
	public static String lastSqlResult;
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		lastMessageReceivedEvent = event;
		
		// We don't want to respond to other bot accounts, including ourselves
		if (event.getAuthor().isBot())
			return;
		
		Message message = event.getMessage();
		String content = message.getContentRaw();
		long chat_id = event.getMessageIdLong();
		
		// getContentRaw() is an atomic getter
		// getContent() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
		
		String reply;
		MessageChannel channel = event.getChannel();
		
		if (message.isMentioned(api.getSelfUser())) {
			reply = bot.reply(String.valueOf(chat_id), content.toLowerCase().replace(api.getSelfUser().getAsMention(), ""));
			if (!reply.isEmpty()) {
				if (reply.startsWith("There it is!")) {
					// @todo ??
				}
			}
			
			// Substring if too long
			if (reply.length() > 2000)
				reply = reply.substring(0, 2000);
			
			if (reply.startsWith("There it is!RGenreFile:")) {
				String path = reply.substring(reply.indexOf('&') + 1);
				channel.sendFile(new File(path)).queue();
			}
			
			// If not empty, attempt send.
			if (!reply.isEmpty())
				channel.sendMessage(reply).queue();
		}
	}
}
