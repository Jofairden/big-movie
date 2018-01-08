package parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors:
	@todo: parsed year still needs separation between year and roman numerals, roman numeral = movie with the same name released in the same year
 */
public final class LanguageParser extends Parser {
	
	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	private final Pattern moviesPatternRunningTimes = Pattern.compile("(.*)\\((\\d{4}|\\?{4})(\\/\\w*)?\\)?(\\s\\(.{1,2}\\))?\\s*(.*)");
	
	private int header;
	
	public LanguageParser(String inputFile) {
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
		
		if (!seriesMatcher.matches() && moviesMatcher.matches())
			super.writeLine = moviesMatcher.replaceAll("$1;$2$3;$5 \n");
	}
}
