package parsers;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Daniel
 */
// Will parse to the following: genre
public class GenreNamesParser extends Parser {
	
	private Set<Integer> knownValues = new HashSet<>();
	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	// -> group 1 = title, 2 = year, 5 = genre, 4 = occurrence
	private final Pattern moviesPatternGenre = Pattern.compile("(.*)\\((\\d{4}|\\?+)(\\/(.+?))?\\).+([A-Z][a-z]*)");
	
	private int header;
	
	public GenreNamesParser(String inputFile) {
		super(inputFile);
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 455;
	}
	
	@Override
	public void parseLine(String line) {
		Matcher seriesMatcher = seriesPatternMovies.matcher(line);
		Matcher moviesMatcher = moviesPatternGenre.matcher(line);
		
		if (!seriesMatcher.matches() && moviesMatcher.matches()) {
			String value = moviesMatcher.replaceAll("$5");
			if (!knownValues.contains(value.hashCode())) {
				knownValues.add(value.hashCode());
				super.writeLine = String.format("%s\n", value);
			}
		}
	}
}
