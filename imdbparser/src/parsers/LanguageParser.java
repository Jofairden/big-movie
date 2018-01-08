package parsers;

import main.ImdbUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Authors: Fadi, Daniël
 */
// Will parse to the following: title;year;language;occurrence
public final class LanguageParser extends Parser {
	
	private final Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
	private final Pattern moviesPatternRunningTimes = Pattern.compile("(.*)\\((\\d{4}|\\?{1,})(\\/)?(\\w*)?(\\))(\\s*)(\\(V\\))?\\s*((\\{\\{.*\\}\\})|\\(TV\\))?\\s*(.*)");
	
	private int header;
	
	public LanguageParser(String inputFile) {
		super(inputFile);
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 14;
	}
	
	@Override
	public void parseLine(String line) {
		Matcher seriesMatcher = seriesPatternMovies.matcher(line);
		Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);
		
		if (!seriesMatcher.matches() && moviesMatcher.matches())
			super.writeLine =
					String.format("%s;%s;%s\n",
							moviesMatcher.replaceAll("$1").trim(),
							moviesMatcher.replaceAll("$2;$10"),
							ImdbUtils.romanToDecimal(moviesMatcher.replaceAll("$4")));
	}
}
