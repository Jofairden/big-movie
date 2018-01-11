package parsers;

import main.ImdbUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Fadi (1e ver), Daniel (2e ver)

	// -> votes, score, title, year, occurrence
 */
public final class RatingParser extends Parser {
	
	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	private final Pattern moviesPatternRunningTimes = Pattern.compile("\\s*(.{10})\\s*([0-9]*)\\s*([0-9].[0-9])\\s*(.*)(\\()(\\d{4}|\\?{1,})(\\/?\\w*|)\\)");
	
	private int header;
	
	public RatingParser(String inputFile) {
		super(inputFile);
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 28;
	}
	
	@Override
	public void parseLine(String line) {
		Matcher seriesMatcher = seriesPatternMovies.matcher(line);
		Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);
		
		if (!seriesMatcher.matches() && moviesMatcher.matches()) {
			super.writeLine =
					String.format("%s||%s||%s||%s\n",
							moviesMatcher.replaceAll("$2||$3"),
							moviesMatcher.replaceAll("$4").trim(),
							moviesMatcher.replaceAll("$6"),
							ImdbUtils.romanToDecimal(moviesMatcher.replaceAll("$7")));
		}
	}
}
