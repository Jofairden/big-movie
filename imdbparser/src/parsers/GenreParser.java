package parsers;

import main.ImdbUtils;

/*
	Authors: Jildert
	@todo: problems parsing
 */
public final class GenreParser extends Parser {
	
	private String lastKnownName;
	private int header;
	private int count;
	
	public GenreParser(String inputFile) {
		super(inputFile);
		lastKnownName = "";
		header = 0;
		count = 0;
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 385 && line.indexOf('(') != -1 || line.indexOf('\t') != -1;
	}
	
	@Override
	public void parseLine(String line) {
		String year = ImdbUtils.getYear(line);
		String movie = line.substring(0, line.lastIndexOf(ImdbUtils.getYear(line)) - 1);
		
		if (movie.equals(lastKnownName)) {
			++count;
		} else {
			count = 1;
		}
		
		String genre = line.substring(line.lastIndexOf('\t') + 1);
		
		super.writeLine = String.format("%s,%s,%s,%s\n", movie, genre, year, count);
		lastKnownName = movie;
	}
}
