package parsers;

import main.ImdbUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Jildert, Fadi
 */
// Will parse to the following: movie;year;location;occurrence
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
			super.writeLine = String.format("%s||%s||%s\n",
					moviesMatcher.replaceAll("$1").trim(),
					moviesMatcher.replaceAll("$2||$5"),
					ImdbUtils.romanToDecimal(moviesMatcher.replaceAll("$3")));
		}
	}
}