package parsers;

import main.ImdbUtils;
import sun.invoke.empty.Empty;

/*
	Authors: Jildert
 */
// Will parse to the following: movie;track;year;occurrence
public final class SoundtrackParser extends Parser {

	private String movie;
	private String year;
	private int count;
	private String lastKnownName;
	private boolean written = false;

	public SoundtrackParser(String inputFile) {
		super(inputFile);
		movie = "";
		year = "";
	}

	@Override
	public boolean canParse(String line) {
		return true;
	}

	@Override
	public void parseLine(String line) {
		if (written){
			super.writeLine = String.format("||%s \n", line.trim());
			written = false;
		}
		else if (line.startsWith("#")) {

			if (line.length() > 2)
			{
				movie = line.substring(2, line.lastIndexOf(ImdbUtils.getYear(line)) - 1);
				year = ImdbUtils.getYear(line);
			}
			else {
				movie = "#";
				year = "????";
			}

			count = 0;
			//String movieYear = String.format("%s%s", movie, year);

			int indexRom = line.lastIndexOf("/I");
			if (indexRom == -1) indexRom = line.lastIndexOf("/V");
			if (indexRom == -1) indexRom = line.lastIndexOf("/X");
			int lastIndexRom = indexRom;

			if(indexRom != -1){
				for (int i = lastIndexRom + 1; i < line.length() ; i ++){
					if (line.charAt(i) == ')'){
						lastIndexRom = i ;
						break;
					}
				}
				if (lastIndexRom > indexRom +1 )count = ImdbUtils.romanToDecimal(line.substring(indexRom + 1, lastIndexRom));
			}

		} else if (!movie.isEmpty()) {
			if (line.length() > 0 && line.charAt(0) == '-') {
				String soundTrack = line.substring(2);
				if(!soundTrack.contains("--------------------------------------------------")) {
					super.writeLine = String.format("%s||%s||%s||%s", movie.trim(), soundTrack, year, count);
					written = true;
				}
			}
		}
	}
}
