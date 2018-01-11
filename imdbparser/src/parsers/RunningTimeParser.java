package parsers;

import main.ImdbUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Fadi (1e ver), Daniel (2e ver)
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
			super.writeLine =
					String.format("%s||%s||%s||%s\n",
							moviesMatcher.replaceAll("$1").trim(),
							moviesMatcher.replaceAll("$2"),
							moviesMatcher.replaceAll("$6"),
							ImdbUtils.romanToDecimal(moviesMatcher.replaceAll("$3")));
		}
	}
}
