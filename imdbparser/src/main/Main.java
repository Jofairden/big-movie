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
			put("countryNames", CountryNamesParser.class);
			put("soundtrackNames", SoundTrackNamesParser.class);
			put("soundtracks", SoundtrackParser.class);
			put("genres", GenreParser.class);
			put("genreNames", GenreNamesParser.class);
			put("ratings", RatingParser.class);
			put("locations", LocationParser.class);
			put("locationNames", LocationNamesParser.class);
			put("running-times", RunningTimeParser.class);
			put("language", LanguageParser.class);
			put("languageNames", LanguageNamesParser.class);
			// note: the ActorParser can be used for both actors and actresses
			put("actors", ActorParser.class);
			put("actresses", ActorParser.class);
			put("actorNames", ActorNameParser.class);
			put("actressesNames", ActorNameParser.class);
		}};
		
		// loop every parser map
		// for each combination, create a new instance and arise the constructor
		// then the parser is handled by the global parser handler
		parsers.forEach((f, p) -> {
			try {
				// these overrides are required becuase the parser
				// will attempt to parse a file with the given name
				// but for names parsers these files do not exist,
				// and instead the regular file needs to be parsed
				// this solution is a bit ugly,  but was the easiest
				// to implement at the time without overhauling
				// project structure.
				// countryNames, locationNames and languageNames
				// were introduced in late changes
				Constructor c = p.getConstructor(String.class);
				Parser parser = (Parser) c.newInstance(f);
				if (f.equalsIgnoreCase("actorNames"))
					parser.setOverrideInput("actors");
				else if (f.equalsIgnoreCase("actressesNames"))
					parser.setOverrideInput("actresses");
				else if (f.equalsIgnoreCase("soundtrackNames"))
					parser.setOverrideInput("soundtracks");
				else if (f.equalsIgnoreCase("countryNames"))
					parser.setOverrideInput("countries");
				else if (f.equalsIgnoreCase("genreNames"))
					parser.setOverrideInput("genres");
				else if (f.equalsIgnoreCase("locationNames"))
					parser.setOverrideInput("locations");
				else if (f.equalsIgnoreCase("languageNames"))
					parser.setOverrideInput("language");
				
				long takenTime = parserHandler.handleParse(parser);
				if (takenTime > 0)
					System.out.println(String.format("Parsed %s in %s seconds", parser.inputFile, takenTime));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}
}
