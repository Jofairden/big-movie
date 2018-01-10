package main;

import parsers.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/*
	Authors: Daniël, Fadi, Jildert, Jeroen
	
	Movies/Series pattern base by Daniël
	
	© 2018
	
	Individual parsers are located in the parser package
	Base abstract class is the Parser class
	Individual models are located in the model package
	
	Main ties everything together, but with smart code remains small
	The data source names are 'connected' to their respective parser,
	after which they can be handled through the global parser handler.
	
	The handler will make use of input sources found in data\raw
	and will write any output to data\parsed
 */
public class Main {
	
	private static ParserHandler parserHandler;
	
	public static void main(String[] args) {
		
		parserHandler = new ParserHandler();
		
		// note that entries have to be manually added
		// java 9 adds Map.ofEntries to make things easier
		// we apply a small hack to do it in java 8
		// this trick is called double brace initialization
		Map<String,Class<? extends Parser>> parsers = new HashMap<String,Class<? extends Parser>>() {{
			put("movies", MovieParser.class);
			put("countries", CountryParser.class);
			put("SoundTrackPrimary", SoundTrackPrimaryParser.class);
			put("soundtracks", SoundtrackParser.class);
			put("genres", GenreParser.class);
			put("ratings", RatingParser.class);
			put("locations", LocationParser.class);
			put("running-times", RunningTimeParser.class);
			put("language", LanguageParser.class);
			put("actors", ActorParser.class);
			put("actresses", ActorParser.class);
		}};
		
		// loop every parser map
		// for each combination, create a new instance and arise the constructor
		// then the parser is handled by the global parser handler
		parsers.forEach((f, p) -> {
			try {
				Constructor c = p.getConstructor(String.class);
				Parser parser = (Parser) c.newInstance(f);
				long takenTime = parserHandler.handleParse(parser);
				if (takenTime > 0)
					System.out.println(String.format("Parsed %s in %s seconds", parser.inputFile, takenTime));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}
}
