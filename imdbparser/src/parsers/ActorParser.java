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
	@todo: implement actressses, check last condition
 */
public final class ActorParser extends Parser {
	
	private final Map<Integer,ActorModel> actors = new HashMap<>();
	private int lastKey;
	private boolean canWrite;
	// int needs to be boxed to pass by refence to utils
	private Box<Integer> count;
	private int header;
	
	public ActorParser(String inputFile) {
		super(inputFile);
		lastKey = 0;
		canWrite = true;
		count = new Box<>(0);
		header = 0;
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 239 && canWrite;
	}
	
	@Override
	public void parseLine(String line) {
		
		List<String> split = Arrays.stream(line.split("\t")).filter(x -> x.length() > 0).collect(Collectors.toList());
		
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
			
			lastKey = actorName.hashCode();
			
			ActorModel actor = new ActorModel(actorName.split("\\(")[0].replace("(", "").trim(), occurrence);
			actor.movies.putIfAbsent(movieName.hashCode(), new MovieModel(movieName.split("\\(")[0].replace("(", ""), year));
			actors.putIfAbsent(actorName.hashCode(), actor);
		} else if (split.size() == 1) {
			if (line.equalsIgnoreCase("-----------------------------------------------------------------------------")) {
				canWrite = false;
				ImdbUtils.cleanActorsMap(actors, count, writer, 4);
			} else {
				String movieName = split.get(0);
				
				String year = "0";
				if (movieName.contains("(")) {
					year = movieName.split("\\(")[1].split("\\)")[0];
				}
				actors.get(lastKey).movies.putIfAbsent(movieName.hashCode(), new MovieModel(movieName.split("\\(")[0].replace("(", ""), year));
			}
		} else {
			lastKey = 0;
			ImdbUtils.cleanActorsMap(actors, count, writer, 10);
		}
	}
}
