package parsers;

import main.ImdbUtils;

/*
	Authors: Jildert
	@todo: wrong parsing for name: sometimes skips first letter, titles end on " with whitespace, but don't start with "
 */
// Will parse to the following: movie;track;year;occurrence
public final class SoundtrackParser extends Parser {
	
	private String movie;
	private String year;
	private int count;
	private String lastKnownName;
	
	public SoundtrackParser(String inputFile) {
		super(inputFile);
		movie = "";
		year = "";
		count = 0;
		lastKnownName = "";
	}
	
	@Override
	public boolean canParse(String line) {
		return true;
	}
	
	@Override
	public void parseLine(String line) {
		if (line.length() > 2 && line.charAt(0) == '#' && line.indexOf('"') != -1) {
			movie = line.substring(3, line.lastIndexOf(ImdbUtils.getYear(line)) - 1);
			year = ImdbUtils.getYear(line);
			String movieYear = String.format("%s%s", movie, year);
			
			if (movieYear.equals(lastKnownName)) {
				++count;
			} else {
				count = 0;
			}
			lastKnownName = movieYear;
			
		} else if (!movie.isEmpty()) {
			if (line.length() > 0 && line.charAt(0) == '-') {
				String soundTrack = line.substring(2);
				super.writeLine = String.format("%s||%s||%s||%s\n", movie, soundTrack, year, count);
			}
		}
	}
}
