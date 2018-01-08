package parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Jildert
	@todo: problems parsing
 */
public final class GenreParser extends Parser {

	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	private final Pattern moviesPatternGenre = Pattern.compile("(.*)(\\()(\\d{4}|\\?{4})()(\\/?\\w*|)(\\))(.*)([A-Z][a-z]*)");

	private int header;

	public GenreParser(String inputFile) {
		super(inputFile);
	}

	@Override
	public boolean canParse(String line) {
		return ++header > 28;
	}

	@Override
	public void parseLine(String line) {
		Matcher seriesMatcher = seriesPatternMovies.matcher(line);
		Matcher moviesMatcher = moviesPatternGenre.matcher(line);

		if (!seriesMatcher.matches() && moviesMatcher.matches()) {
			super.writeLine = moviesMatcher.replaceAll("$1;$3$5;$8 \n");
		}
	}
}
