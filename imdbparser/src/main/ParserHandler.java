package main;

import parsers.Parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Scanner;

/*
	Authors: Daniël Zondervan
 */
public class ParserHandler {
	
	// Handles a parser
	// Will read whatever attached file to the parser, and attempts running it through that parser
	// If an output is found, parsing is skipped
	public long handleParse(Parser parser) throws IOException {
		ZonedDateTime dt = ZonedDateTime.now();
		
		FileInputStream inputStream = null;
		Scanner sc = null;
		
		Writer writer = null;
		
		File inputFile = new File(parser.getInputPath());
		//noinspection ResultOfMethodCallIgnored
		//inputFile.getParentFile().mkdirs();
		File outputFile = new File(parser.getOutputPath());
		//noinspection ResultOfMethodCallIgnored
		//outputFile.getParentFile().mkdirs();
		
		// If the output is found, we can skip the entire parsing process
		if (outputFile.exists() && !outputFile.isDirectory()) {
			System.out.println(String.format("Output for %s already exists, skipping parse.", parser.inputFile));
		} else {
			try {
				// setup input and outputs
				inputStream = new FileInputStream(parser.getInputPath());
				sc = new Scanner(inputStream, StandardCharsets.ISO_8859_1.name());
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(parser.getOutputPath()), StandardCharsets.UTF_8));
				
				// continue reading lines through the input stream
				while (sc.hasNextLine()) {
					
					String line = sc.nextLine();
					
					// if we can parse, attach a writer
					// following parsing the line,
					// if it needs a write, invoke the parser write function,
					// after which the parser is cleared, and the cycle continues
					if (parser.canParse(line)) {
						parser.attachWriter(writer);
						parser.parseLine(line);
						
						if (parser.needsWrite())
							parser.write();
						
						parser.clear();
					}
				}
				// note that Scanner suppresses exceptions
				if (sc.ioException() != null) {
					throw sc.ioException();
				}
			} finally {
				// finally, dispose/close everything where possible
				if (inputStream != null) {
					inputStream.close();
				}
				if (sc != null) {
					sc.close();
				}
				if (writer != null) {
					writer.close();
				}
			}
		}
		
		// Calculate and return the used time for parsing
		ZonedDateTime dt2 = ZonedDateTime.now();
		return Duration.between(dt, dt2).getSeconds();
	}
}
