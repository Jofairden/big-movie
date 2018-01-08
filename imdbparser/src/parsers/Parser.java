package parsers;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/*
	Authors: Daniël Zondervan
 */
public abstract class Parser {
	
	protected Writer writer;
	
	public void attachWriter(Writer writer) {
		this.writer = writer;
	}
	
	// The input file name
	public final String inputFile;
	
	// The next line to be written, empty or null if not writing
	protected String writeLine;
	
	// Returns the path to the input file
	public String getInputPath() {
		return String.format("%s\\data\\raw\\%s.list", new File("").getAbsolutePath(), inputFile);
	}
	
	// Returns the path to the output file
	public String getOutputPath() {
		return String.format("%s\\data\\parsed\\%s.csv", new File("").getAbsolutePath(), inputFile);
	}
	
	// ctor
	public Parser(String inputFile) {
		this.inputFile = inputFile;
		this.writeLine = "";
	}
	
	// Can we parse/handle the line?
	public abstract boolean canParse(String line);
	
	// Parse the line
	public abstract void parseLine(String line);
	
	// We need a write if the line to be written is not null or empty
	public boolean needsWrite() {
		return writeLine != null && !writeLine.isEmpty();
	}
	
	// Clear the parser, in this case simply reset the line to be written
	public void clear() {
		writeLine = "";
		writer = null;
	}
	
	// Write the line
	public void write(Writer writer) {
		try {
			writer.write(writeLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
