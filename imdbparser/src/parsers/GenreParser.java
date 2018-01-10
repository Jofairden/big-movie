package parsers;

import main.ImdbUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Jildert (1e ver), Fadi (2e ver), Daniel (3e ver)
 */
// Will parse to the following: title;year;genre;occurrence
public final class GenreParser extends Parser {
	
	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	// old by fadi: (.*)(\()(\d{4}|\?+)()(\/?\w*|)(\))(.*)([A-Z][a-z]*)
	// new by daniel: (.*)\((\d{4}|\?+)(\/(.+?))?\).+([A-Z][a-z]*)
	// -> group 1 = title, 2 = year, 5 = genre, 4 = occurrence
	private final Pattern moviesPatternGenre = Pattern.compile("(.*)\\((\\d{4}|\\?+)(\\/(.+?))?\\).+([A-Z][a-z]*)");
	
	private int header;
	
	public GenreParser(String inputFile) {
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
			super.writeLine = String.format("%s||%s||%s\n",
					moviesMatcher.replaceAll("$1").trim(),
					moviesMatcher.replaceAll("$2||$5"),
					ImdbUtils.romanToDecimal(moviesMatcher.replaceAll("$4")));
		}
	}
}
