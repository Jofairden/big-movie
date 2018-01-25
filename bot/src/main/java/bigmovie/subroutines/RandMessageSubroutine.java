package bigmovie.subroutines;

import bigmovie.Bot;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

import java.util.Random;

/**
 * @author Daniel
 * The subroutine will send out a random message, taken randomly from array data
 * This subroutine should usually be called after a user asked a question and got an answer
 */
public class RandMessageSubroutine implements Subroutine {
	
	// A set of random react messages
	private static final String[] reactMessages = {
			"BOI, what the freck do you need this data for?",
			"So much data... You're a freak",
			"Are you sure you can manage this data? Do you know da wae?",
			"Spying on those movies are ye? Creep-o.",
	};
	
	// A set of random messages after posting a trailer
	private static final String[] trailerMessages = {
			"I hope I didn't post some kind of porn right now...",
			"Well, enjoy the trailer. I hope it's not horror.. please... I'm scared..",
			"Did you search horror shit? Fuck.. please... hold me... please",
			"This better be good, damn it",
			"Fookin trailers, they sometimes have higher budget than the movie. Damn movie makers"
	};
	
	// Gets a next random message from the given array
	private static String[] randNextMsg(String[] arr) {
		return new String[] {
				arr[random.nextInt(arr.length)]
		};
	}
	
	// The random obj
	private static final Random random = new Random();
	
	@Override
	public String call(RiveScript rs, String[] args) {
		
		if (args.length > 0) {
			String arg = args[0];
			
			// call: context:
			if (arg.startsWith("context:")) {
				String context = arg.split(":")[1];
				
				// call: context:react
				if (context.equalsIgnoreCase("react")) {
					Bot.messageSubroutine.call(rs, randNextMsg(reactMessages));
				} else if (context.equalsIgnoreCase("trailer")) {
					Bot.messageSubroutine.call(rs, randNextMsg(trailerMessages));
				}
			}
		}
		
		return null;
	}
}
