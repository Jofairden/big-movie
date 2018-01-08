package parsers;

/*
	Authors:
	@todo: wrong parsing for name, example: "#1 Single" (2006,USA,
 */
public final class LocationParser extends Parser {
	
	private int header;
	private String lastKnownName;
	private int count;
	
	public LocationParser(String inputFile) {
		super(inputFile);
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 14 && line.lastIndexOf(')') != -1;
	}
	
	@Override
	public void parseLine(String line) {
		String movie = line.substring(0, line.lastIndexOf(')'));
		if (movie.equals(lastKnownName)) {
			++count;
		} else {
			count = 1;
		}
		
		String locations = line.substring(line.lastIndexOf('\t') + 1);
		String[] locationArray = locations.split(",");
		
		for (String aLocationArray : locationArray) {
			lastKnownName = movie;
			super.writeLine = String.format("%s,%s,\n", movie, aLocationArray.replaceAll("\\s+", ""));
			
		}
	}
}
