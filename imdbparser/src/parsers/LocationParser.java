package parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Jildert, Fadi
	@todo: wrong parsing for name, example: "#1 Single" (2006,USA,
 */
public final class LocationParser extends Parser {

	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	private final Pattern moviesPatternLocation = Pattern.compile("(.*)\\((\\d{4}|\\?{1,})(\\/\\w*)?\\)?(\\s\\(.{1,2}\\))?\\s*(.*)");

	private int header;

	public LocationParser(String inputFile) {
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
			super.writeLine = moviesMatcher.replaceAll("$1;$2$3;$5 \n");
		}
	}
}