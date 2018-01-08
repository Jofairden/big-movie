package parsers;

import main.Box;
import main.ImdbUtils;
import models.ActorModel;
import models.MovieModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static main.ImdbUtils.romanToDecimal;

/*
	Authors: Daniël, Jeroen
	@notes: can parse both actors and actressess
 */
// The actor parser needs specific models due to the high amount of inconsistencies in the data source
// Mainly because it appears the third-party's sort method is broken, as we found some actors display every other entry
// which requires us to keep track of at least 5 to 10 actors or so, and see if they were already found before.
// This makes the code quite a bit more inefficient and slower, unfortunately
public final class ActorParser extends Parser {
	
	// Keep track of actors in a hashmap
	private final Map<Integer,ActorModel> actors = new HashMap<>();
	// The last key for the hashmap is tracked
	private int lastKey;
	
	// If we can write (or rather can we parse)
	// Used to stop parsing at eof, where useless data lies
	private boolean canWrite;
	// int needs to be boxed to pass by reference to utils
	// another trick is to pass a one-sized array
	private Box<Integer> count;
	
	// Used to count past the header of the file
	private int header;
	
	public ActorParser(String inputFile) {
		super(inputFile);
		lastKey = 0;
		canWrite = true;
		count = new Box<>(0);
		header = 0;
	}
	
	// We can only parse after the header
	@Override
	public boolean canParse(String line) {
		return ++header > 239 && canWrite;
	}
	
	@Override
	public void parseLine(String line) {
		
		// Split input line on tabs, and we filter any empty splits
		List<String> split = Arrays.stream(line.split("\t")).filter(x -> x.length() > 0).collect(Collectors.toList());
		
		// With a size of 2, contains the actor first followed by a movie
		// Both the actor and movie are stripped of useless data, leaving only their name/title and year (if applicable)
		// The occurrence of the actor means how many nth actor it is with that name
		if (split.size() == 2) {
			++count.value;
			
			String actorName = split.get(0);
			String movieName = split.get(1);
			int occurrence = 0;
			String year = "0";
			
			if (actorName.contains("(")) {
				occurrence = romanToDecimal(new StringBuilder(actorName).reverse().toString().split(" ")[0].replaceAll("[)|(]", ""));
			}
			if (movieName.contains("(")) {
				year = movieName.split("\\(")[1].split("\\)")[0];
			}
			
			// temp store actor hash
			lastKey = actorName.hashCode();
			
			// Create actor, put the movie, put the actor
			ActorModel actor = new ActorModel(actorName.split("\\(")[0].replace("(", "").trim(), occurrence);
			actor.movies.putIfAbsent(movieName.hashCode(), new MovieModel(movieName.split("\\(")[0].replace("(", ""), year));
			actors.putIfAbsent(actorName.hashCode(), actor);
			// With a size of 1, we are on a line for a movie the previous actor played in
		} else if (split.size() == 1) {
			// When we hit a specific match of dashes, we reached eof after which we disallow further parsing
			if (line.equalsIgnoreCase("-----------------------------------------------------------------------------")) {
				canWrite = false;
				ImdbUtils.cleanActorsMap(actors, count, writer, 4);
			} else {
				// We can continue, clean up the movie like above and attach it to the actor
				String movieName = split.get(0);
				
				String year = "0";
				if (movieName.contains("(")) {
					year = movieName.split("\\(")[1].split("\\)")[0];
				}
				actors.get(lastKey).movies.putIfAbsent(movieName.hashCode(), new MovieModel(movieName.split("\\(")[0].replace("(", ""), year));
			}
		} else {
			// When we hit a blank line, we reset data needed, and flush the actors
			lastKey = 0;
			ImdbUtils.cleanActorsMap(actors, count, writer, 10);
		}
	}
}
