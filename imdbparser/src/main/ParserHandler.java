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
	
	private String filePath;
	
	public ParserHandler() {
		filePath = new File("").getAbsolutePath();
	}
	
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
		
		if (outputFile.exists() && !outputFile.isDirectory()) {
			System.out.println(String.format("Output for %s already exists, skipping parse.", parser.inputFile));
		} else {
			try {
				inputStream = new FileInputStream(parser.getInputPath());
				sc = new Scanner(inputStream, StandardCharsets.ISO_8859_1.name());
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(parser.getOutputPath()), StandardCharsets.UTF_8));
				
				
				while (sc.hasNextLine()) {
					
					String line = sc.nextLine();
					
					if (parser.canParse(line)) {
						parser.attachWriter(writer);
						parser.parseLine(line);
						
						if (parser.needsWrite())
							parser.write(writer);
						
						parser.clear();
					}
				}
				// note that Scanner suppresses exceptions
				if (sc.ioException() != null) {
					throw sc.ioException();
				}
			} finally {
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
		
		ZonedDateTime dt2 = ZonedDateTime.now();
		
		return Duration.between(dt, dt2).getSeconds();
	}
}
