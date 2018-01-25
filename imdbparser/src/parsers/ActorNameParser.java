package parsers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static main.ImdbUtils.romanToDecimal;

/**
 * @author Daniel & Jeroen
 * Will parse actor and actresses names, their gender and occurrence.
 */
public class ActorNameParser extends Parser {
	
	private final Set<Integer> actorNames = new HashSet<>();
	
	// If we can write (or rather can we parse)
	// Used to stop parsing at eof, where useless data lies
	private boolean canWrite;
	
	// Used to count past the header of the file
	private int header;
	
	public ActorNameParser(String inputFile) {
		super(inputFile);
		canWrite = true;
		header = 0;
	}
	
	@Override
	public boolean canParse(String line) {
		return ++header > 239 && canWrite;
	}
	
	@Override
	public void parseLine(String line) {
		// Split input line on tabs, and we filter any empty splits
		List<String> split = Arrays.stream(line.split("\t")).filter(x -> x.length() > 0).collect(Collectors.toList());
		
		if (split.size() == 2) {
			String actorName = split.get(0);
			int occurrence = 0;
			
			if (actorName.contains("(")) {
				occurrence = romanToDecimal(new StringBuilder(actorName).reverse().toString().split(" ")[0].replaceAll("[)|(]", ""));
			}
			
			String combActorName = String.format("%s%s", actorName, occurrence);
			
			if (!actorNames.contains(combActorName.hashCode())) {
				actorNames.add(combActorName.hashCode());
				super.writeLine = String.format("%s||%s||%s\n", actorName.split("\\(")[0].replace("(", "").trim(), super.overrideInput.equalsIgnoreCase("actors") ? "m" : "f", occurrence);
			}
		} else if (split.size() == 1) {
			if (line.equalsIgnoreCase("-----------------------------------------------------------------------------")) {
				canWrite = false;
				actorNames.clear();
			}
		}
	}
}
