package parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Daniël Zondervan
 */
// Will parse to the following: title;year;occurrence
public final class MovieParser extends Parser {
	
	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	private final Pattern moviesPatternMovies = Pattern.compile("(.+?)(?=\\s\\().*(?=[\\d|?]{4})(.*)");
	
	private String lastKnownName;
	private int count;
	private int header;
	
	public MovieParser(String inputFile) {
		super(inputFile);
		lastKnownName = "";
		count = 0;
		header = 0;
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 15;
	}
	
	@Override
	public void parseLine(String line) {
		
		Matcher seriesMatcher = seriesPatternMovies.matcher(line);
		Matcher moviesMatcher = moviesPatternMovies.matcher(line);
		
		if (!seriesMatcher.matches() && moviesMatcher.matches()) {
			
			final String title = moviesMatcher.replaceAll("$1");
			final String year = moviesMatcher.replaceAll("$2");
			String movieYear = String.format("%s%s", title, year);
			
			if (movieYear.equalsIgnoreCase(lastKnownName)) {
				++count;
			} else {
				count = 0;
				lastKnownName = title;
			}
			
			super.writeLine = String.format("%s||%s||%s\n", title, year, count);
			lastKnownName = movieYear;
		}
	}
}
