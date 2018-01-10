package main;

import models.ActorModel;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public final class ImdbUtils {
	
	/*
		Will convert any given roman string to a decimal output
	 */
	public static int romanToDecimal(java.lang.String romanNumber) {
		int decimal = 0;
		int lastNumber = 0;
		String romanNumeral = romanNumber.toUpperCase();
        /* operation to be performed on upper cases even if user
           enters roman values in lower case chars */
		for (int x = romanNumeral.length() - 1; x >= 0; x--) {
			char convertToDecimal = romanNumeral.charAt(x);
			
			switch (convertToDecimal) {
				case 'M':
					decimal = processDecimal(1000, lastNumber, decimal);
					lastNumber = 1000;
					break;
				
				case 'D':
					decimal = processDecimal(500, lastNumber, decimal);
					lastNumber = 500;
					break;
				
				case 'C':
					decimal = processDecimal(100, lastNumber, decimal);
					lastNumber = 100;
					break;
				
				case 'L':
					decimal = processDecimal(50, lastNumber, decimal);
					lastNumber = 50;
					break;
				
				case 'X':
					decimal = processDecimal(10, lastNumber, decimal);
					lastNumber = 10;
					break;
				
				case 'V':
					decimal = processDecimal(5, lastNumber, decimal);
					lastNumber = 5;
					break;
				
				case 'I':
					decimal = processDecimal(1, lastNumber, decimal);
					lastNumber = 1;
					break;
			}
		}
		return decimal;
	}
	
	// If nested functions were a thing, this would be part of the above
	private static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
		if (lastNumber > decimal) {
			return lastDecimal - decimal;
		} else {
			return lastDecimal + decimal;
		}
	}
	
	
	/*
		Author: Jildert
	 */
	public static String getYear(String line) {
		int index = line.lastIndexOf('(');
		while (index != -1) {
			if (Character.isDigit(line.charAt(index + 1)) && line.length() > index + 5) {
				return line.substring(index + 1, index + 5);
			} else {
				if (index - 1 == -1) {
					index = -1;
					continue;
				}
				index = line.substring(0, index - 1).lastIndexOf('(');
			}
		}
		
		return "????";
	}
	
	/*
		Authors: Daniël, Jeroen
	 */
	// Will flush the actors hashmap, and write whichever were remaining
	public static void cleanActorsMap(Map<Integer,ActorModel> actors, Box<Integer> count, Writer writer, int number) {
		if (count.value >= number) {
			actors.forEach((x, y) -> {
				y.movies.forEach((a, b) -> {
					try {
						// write artist name, artist occurrence number, movie name, movie year
						writer.write(String.format("%s||%s||%s||%s\n", y.name, y.occurence, b.name, b.year));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			});
			
			actors.clear();
			count.value = 0;
		}
	}
}
