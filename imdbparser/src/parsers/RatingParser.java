package parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors:
	@todo: gaat fout op 9, Il buono, also needs roman numerals separated from year
 */
public final class RatingParser extends Parser {
	
	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	private final Pattern moviesPatternRunningTimes = Pattern.compile("\\s*(.{10})\\s*([0-9]*)\\s*([0-9].[0-9])\\s*(.*)(\\()(\\d{4}\\/?(I+|V|))\\)");
	
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
			super.writeLine = moviesMatcher.replaceAll("$2,$3,$4,$6$7 \n");
		}
	}
}
