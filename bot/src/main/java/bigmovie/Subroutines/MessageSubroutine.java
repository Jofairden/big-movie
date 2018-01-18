package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;
import net.dv8tion.jda.core.EmbedBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
	@author Daniel
	The subroutine can queue a single message at a time
	It is possible to supply an embed with title and description
 */
public class MessageSubroutine implements Subroutine {
	
	@Override
	public String call(RiveScript rs, String[] args) {
		if (Bot.lastMessageReceivedEvent != null) {
			
			// assume embed
			// which will put remaining args in a single field
			if (args[0].equalsIgnoreCase("context:embed")) {
				
				// we will skip these many for the fields
				int skip = 1;
				EmbedBuilder eb = new EmbedBuilder();
				String fieldTitle = "";
				
				// optional infos
				final int MAX_OPTIONS = 3;
				for (int i = 1; i < (args.length < MAX_OPTIONS ? args.length : MAX_OPTIONS); ++i) {
					String current = args[i];
					if (current.startsWith("title:")) {
						eb.setTitle(current.split(":")[1], null);
						++skip;
					}
					if (current.startsWith("info:")) {
						eb.setDescription(current.split(":")[1]);
						++skip;
					}
					if (current.startsWith("fieldtitle:")) {
						fieldTitle = current.split(":")[1];
						++skip;
					}
				}
				
				// first we skip the extra args like title and descriptions
				List<String> remainder = Arrays.stream(args).skip(skip).collect(Collectors.toList());
				// next, join together on spaces and split on newlines
				List<String> remainderSplit = Arrays.stream(String.join(" ", remainder).split("\n")).collect(Collectors.toList());
				
				// embeds have a max of 25 fields, so cap on that amount
				List<String> trueRemainder = new ArrayList<>();
				for (int i = 0; i < 25; ++i) {
					if (i >= remainderSplit.size())
						break;
					trueRemainder.add(remainderSplit.get(i));
				}
				
				// add every field
				eb.addField(fieldTitle, String.join("\n", trueRemainder), true);
				
				// send embed
				Bot.lastMessageReceivedEvent
						.getChannel()
						.sendMessage(eb.build())
						.queue();
				
				// If we went beyond cap, tell them
				int rem = remainderSplit.size() - trueRemainder.size();
				if (rem > 0) {
					return String.format("embedErr:tooManyFields:%s:%s:%s", remainderSplit.size(), trueRemainder.size(), rem);
				}
				
			} else if (args[0].equalsIgnoreCase("context:file")) {
				
				String filePath = null;
				String caption = null;
				// assume file
				if (args[1].startsWith("resource:")) {
					// assume resource
					filePath = Objects.requireNonNull(BotUtils
							.getResourcePath(args[1].split(":")[1]))
							.toString();
				} else {
					filePath = args[1];
				}
				
				if (args.length > 2) {
					// assume rest was a caption
					caption = String.join(
							" ",
							Arrays
									.stream(args)
									.skip(2)
									.collect(Collectors.toList()))
							.trim();
				}
				
				if (filePath != null) {
					// assume file can be sent
					File file = new File(filePath);
					
					// attempt send
					Bot.lastMessageReceivedEvent
							.getChannel()
							.sendFile(file)
							.queue();
					
					if (caption != null)
						return caption;
				}
			} else {
				Bot.lastMessageReceivedEvent
						.getChannel()
						.sendMessage(String.join(" ", args))
						.queue();
			}
		}
		
		return "";
	}
}
