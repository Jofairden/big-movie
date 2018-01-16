package parsers;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Daniel
 */
// Will parse to the following: language
// introduced in late changes
public class LanguageNamesParser extends Parser {
	
	private Set<Integer> knownValues = new HashSet<>();
	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	private final Pattern moviesPatternRunningTimes = Pattern.compile("(.*)\\((\\d{4}|\\?{1,})(\\/)?(\\w*)?(\\))(\\s*)(\\(V\\))?\\s*((\\{\\{.*\\}\\})|\\(TV\\))?\\s*(.*)");
	
	private int header;
	
	public LanguageNamesParser(String inputFile) {
		super(inputFile);
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 14;
	}
	
	@Override
	public void parseLine(String line) {
		Matcher seriesMatcher = seriesPatternMovies.matcher(line);
		Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);
		
		if (!seriesMatcher.matches() && moviesMatcher.matches()) {
			String value = moviesMatcher.replaceAll("$10");
			if (!knownValues.contains(value.hashCode())) {
				knownValues.add(value.hashCode());
				super.writeLine = String.format("%s\n", value);
			}
		}
		
	}
}
