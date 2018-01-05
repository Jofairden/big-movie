import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
	

	static ImdbParser parser;
    static AtomicInteger header;

	public static void main(String[] args) {

	    parser = new ImdbParser();
        header = new AtomicInteger(1);

		try {

            //parseMovies();
            //parseRunningtimes();
            parseActors();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void parseMovies() throws IOException {

        header.set(1);

        Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
        Pattern moviesPatternMovies = Pattern.compile("(.+?)(?=\\s\\().*(?=[\\d|?]{4})(.*)");

        System.out.println(String.format("Parsed movies.list in %s seconds", parser.streamFile("movies.list", (line, writer) -> {
            if (header.get() > 15) {
                Matcher seriesMatcher = seriesPatternMovies.matcher(line);
                Matcher moviesMatcher = moviesPatternMovies.matcher(line);
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
    }

    private static void parseRunningtimes() throws IOException {
        header.set(1);

        Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
        Pattern moviesPatternRunningTimes = Pattern.compile("(.+?)(?=\\s\\(\\d{4,})(.+?)(?=\\t)(.+?)([0-9]+).*");

        System.out.println(String.format("Parsed running-times.list in %s seconds", parser.streamFile("running-times.list", (line, writer) -> {
            if (header.get() > 14) {
                Matcher seriesMatcher = seriesPatternMovies.matcher(line);
                Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);
                if (!seriesMatcher.matches() && moviesMatcher.matches()) {
                    try {
                        writer.write(moviesMatcher.replaceAll("$1 - $4 min.\n"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else
                header.incrementAndGet();
        })));
    }

    private static void parseActors() throws IOException {
        header.set(1);

        System.out.println(String.format("Parsed actors.list in %s seconds", parser.streamFile("actors.list", (line, writer) -> {
            if (header.get() > 239) {
                List<String> split = Arrays.stream(line.split("\t")).filter(x -> x.length() > 0).collect(Collectors.toList());
                    try {
                        if (split.size() == 2)
                            writer.write(String.format("%s - %s", split.get(0), split.get(1)));

                        else if(split.size() == 1)
                            writer.write(String.format(", %s", split.get(0)));
                        else if(split.size() == 0)
                            writer.write("\n\n");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else
                header.incrementAndGet();
        })));
    }

}
