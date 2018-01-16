package bigmovie;

import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;
import net.dv8tion.jda.core.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
	Author: Daniel
	The subroutine can queue a single message at a time
	It is possible to supply an embed with title and description
 */
public class MessageSubroutine implements Subroutine {
	
	@Override
	public String call(RiveScript rs, String[] args) {
		if (Bot.lastMessageReceivedEvent != null) {
			
			if (args[0].equals("embed")) {
				int skip = 1;
				EmbedBuilder eb = new EmbedBuilder();
				if (args[1].startsWith("title:")) {
					eb.setTitle(args[1].split(":")[1], null);
					++skip;
				}
				if (args[2].startsWith("info:")) {
					eb.setDescription(args[2].split(":")[1]);
					++skip;
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
				eb.addField("movies", String.join("\n", trueRemainder), true);
				
				// If we went beyond cap, tell them
				int rem = remainderSplit.size() - trueRemainder.size();
				if (rem > 0) {
					Bot.lastMessageReceivedEvent
							.getChannel()
							.sendMessage(String.format(
									"I found %s movies, but I can only list so many...",
									remainderSplit.size()
							))
							.queue();
				}
				
				// send embed
				Bot.lastMessageReceivedEvent
						.getChannel()
						.sendMessage(eb.build())
						.queue();
				
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
