package parsers;

import java.util.HashSet;
import java.util.Set;

/*
	Authors: Daniel
 */
// Will parse to the following: country
// introduced in late changes
public class CountryNamesParser extends Parser {
	
	private Set<Integer> knownValues = new HashSet<>();
	
	public CountryNamesParser(String inputFile) {
		super(inputFile);
	}
	
	@Override
	public boolean canParse(String line) {
		return !line.startsWith("\"") && (line.indexOf('(') != -1 || line.indexOf('\t') != -1);
	}
	
	@Override
	public void parseLine(String line) {
		String value = line.substring(line.lastIndexOf('\t') + 1);
		if (!knownValues.contains(value.hashCode())) {
			knownValues.add(value.hashCode());
			super.writeLine = String.format("%s\n", value);
		}
	}
}
