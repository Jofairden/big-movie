package parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Fadi
	@todo: parsed year still needs separation between year and roman numerals, roman numeral = movie with the same name released in the same year
 */
public final class RunningTimeParser extends Parser {
	
	private int header;
	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	private final Pattern moviesPatternRunningTimes = Pattern.compile("(.+)\\((\\d{4}|\\?{1,})(\\/?\\w*|)(\\))(.+?)([0-9]+)");
	
	public RunningTimeParser(String inputFile) {
		super(inputFile);
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 17;
	}
	
	@Override
	public void parseLine(String line) {
		
		Matcher seriesMatcher = seriesPatternMovies.matcher(line);
		Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);
		if (!seriesMatcher.matches() && moviesMatcher.matches()) {
			super.writeLine = moviesMatcher.replaceAll("$1||$2$3||$6 \n");
		}
	}
}
