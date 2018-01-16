package parsers;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Daniel
 */
// Will parse to the following: location
// introduced in late changes
public class LocationNamesParser extends Parser {
	
	private Set<Integer> knownValues = new HashSet<>();
	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	private final Pattern moviesPatternLocation = Pattern.compile("(.*)\\((\\d{4}|\\?{1,})(\\/\\w*)?\\)?(\\s\\(.{1,2}\\))?\\s*(.*)");
	
	private int header;
	
	public LocationNamesParser(String inputFile) {
		super(inputFile);
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 17;
	}
	
	@Override
	public void parseLine(String line) {
		Matcher seriesMatcher = seriesPatternMovies.matcher(line);
		Matcher moviesMatcher = moviesPatternLocation.matcher(line);
		
		if (!seriesMatcher.matches() && moviesMatcher.matches()) {
			String value = moviesMatcher.replaceAll("$5");
			if (!knownValues.contains(value.hashCode())) {
				knownValues.add(value.hashCode());
				super.writeLine = String.format("%s\n", value);
			}
		}
	}
	
}
