import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	

	
	public static void main(String[] args) {

		ImdbParser parser = new ImdbParser();
		
		try {

            Pattern seriesPattern = Pattern.compile("(^\".+)");
            Pattern moviesPattern = Pattern.compile("(.+?)(?=\\s\\().*(?=[\\d|?]{4})(.*)");

            AtomicInteger header = new AtomicInteger(1);

			System.out.println(String.format("Parsed movies.list in %s seconds", parser.streamFile("movies.list", (line, writer) -> {
                if (header.get() > 15) {
                    Matcher seriesMatcher = seriesPattern.matcher(line);
                    Matcher moviesMatcher = moviesPattern.matcher(line);
                    if (!seriesMatcher.matches() && moviesMatcher.matches()) {
                        try {
                            writer.write(moviesMatcher.replaceAll("$1 - $2\n"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else
                    header.incrementAndGet();
			})));
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		

	}
	

}
