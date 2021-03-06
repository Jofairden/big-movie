package parsers;


import main.ImdbUtils;

/*
	Authors: Jildert
 */
// Will parse to the following: title;country;year;occurrence
public final class CountryParser extends Parser {
	
	private String lastKnownName;
	private int count;
	
	public CountryParser(String inputFile) {
		super(inputFile);
		lastKnownName = "";
		count = 0;
	}
	
	@Override
	public boolean canParse(String line) {
		return !line.startsWith("\"") && (line.indexOf('(') != -1 || line.indexOf('\t') != -1);
	}
	
	@Override
	public void parseLine(String line) {
		String movie = line.substring(0, line.lastIndexOf(ImdbUtils.getYear(line)) - 1);
		String country = line.substring(line.lastIndexOf('\t') + 1);
		String year = ImdbUtils.getYear(line);
		String movieYear = String.format("%s%s", movie, year);
		
		if (movieYear.equals(lastKnownName)) {
			++count;
		} else {
			count = 0;
		}
		
		super.writeLine = String.format("%s||%s||%s||%s\n", movie.trim(), country, year, count);
		lastKnownName = movieYear;
	}
}
