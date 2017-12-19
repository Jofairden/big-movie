import java.io.*;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	private static String filePath = new File("").getAbsolutePath();
	
	public static void main(String[] args) {
		
		ZonedDateTime dt = ZonedDateTime.now();
		
		try {
			streamFile(filePath + "\\data\\movies.list");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ZonedDateTime dt2 = ZonedDateTime.now();
		
		System.out.println(String.format("Parsed movies.list in %s seconds", Duration.between(dt, dt2).getSeconds()));
	}
	
	private static void streamFile(String path) throws IOException {
		FileInputStream inputStream = null;
		Scanner sc = null;
		int header = 1;
		
		Writer writer = null;
		
		try {
			inputStream = new FileInputStream(path);
			sc = new Scanner(inputStream, "UTF-8");
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filePath + "\\data\\moviesParsed.txt"), "ASCII"));
			
			Pattern seriesPattern = Pattern.compile("(^\".+)");
			Pattern moviesPattern = Pattern.compile("(.*)(?=\\s\\().*(?=[\\d|?]{4})(.*)");
			
			while (sc.hasNextLine()) {
				if (header > 15) {
					String line = sc.nextLine();
					Matcher seriesMatcher = seriesPattern.matcher(line);
					Matcher moviesMatcher = moviesPattern.matcher(line);
					if (!seriesMatcher.matches() && moviesMatcher.matches()) {
						//System.out.println(moviesMatcher.replaceAll("$1 - $2"));
						writer.write(moviesMatcher.replaceAll("$1 - $2\n"));
					}
				} else
					++header;
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
}
