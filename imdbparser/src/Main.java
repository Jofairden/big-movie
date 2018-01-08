import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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
            //parseActors();
            //parseSoundTracksParser();
            //parseCountriesParser();
            //parseGenresParser();
           // parseRatings();
           // parseLocationsParser();
            LanguageParser();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void parseMovies() throws IOException {

        header.set(1);

        Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
        Pattern moviesPatternMovies = Pattern.compile("(.+?)(?=\\s\\().*(?=[\\d|?]{4})(.*)");

        AtomicReference<String> lastKnownName = new AtomicReference<>("");
        AtomicInteger count = new AtomicInteger(1);

        System.out.println(String.format("Parsed movies.list in %s seconds", parser.streamFile("movies.list", (line, writer) -> {
            if (header.get() > 15) {
                Matcher seriesMatcher = seriesPatternMovies.matcher(line);
                Matcher moviesMatcher = moviesPatternMovies.matcher(line);
                if (!seriesMatcher.matches() && moviesMatcher.matches()) {
                    try {
                        String title = moviesMatcher.replaceAll("$1");
                        String year = moviesMatcher.replaceAll("$2");

                        if (title.equalsIgnoreCase(lastKnownName.get()))
                        {
                            count.incrementAndGet();
                        }
                        else {
                            count.set(1);
                            lastKnownName.set(title);
                        }

                        writer.write(String.format("%s,%s,%s\n",title,year,count));
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

        AtomicReference<String> lastKnownName = new AtomicReference<>("");
        AtomicInteger count = new AtomicInteger(1);

        System.out.println(String.format("Parsed running-times.list in %s seconds", parser.streamFile("running-times.list", (line, writer) -> {
            if (header.get() > 14) {
                Matcher seriesMatcher = seriesPatternMovies.matcher(line);
                Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);

                if (!seriesMatcher.matches() && moviesMatcher.matches()) {
                    try {
                        String title = moviesMatcher.replaceAll("$1");
                        String year = moviesMatcher.replaceAll("$2");

                        if (title.equalsIgnoreCase(lastKnownName.get()))
                        {
                            count.incrementAndGet();
                        }
                        else {
                            count.set(1);
                            lastKnownName.set(title);
                        }

                        writer.write(String.format("%s,%s,%s\n",title,year,count));
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

    private static void parseSoundTracksParser() throws java.io.IOException{
        header.set(1);
        AtomicReference<String> lastKnownName = new AtomicReference<>("");
        AtomicInteger count = new AtomicInteger(1);
        AtomicReference<String> movie  = new AtomicReference<>("");
        AtomicReference<String> year  = new AtomicReference<>("");
        System.out.println(String.format("Parsed soundtracks.list in %s seconds", parser.streamFile("soundtracks.list", (line, writer) -> {
            StringBuilder sb = new StringBuilder();

                try {
                    if (line.length() > 2 && line.charAt(0) == '#' && line.indexOf('"') != -1) {

                        movie.set(line.substring(3, line.lastIndexOf(getYear(line)) -1 ));
                        if (movie.get().equals(lastKnownName.get())){
                            count.incrementAndGet();
                        }
                        else{
                            count.set(1);
                        }
                        lastKnownName.set(movie.get());
                        year.set(getYear(line));
                    } else if (movie.get() != "") {
                        if (line.length() > 0 && line.charAt(0) == '-') {
                            String soundTrack = line.substring(2);
                            sb.append(movie.get());
                            sb.append(';');
                            sb.append(soundTrack);
                            sb.append(';');
                            sb.append(year);
                            sb.append(';');
                            sb.append(count.get());
                            sb.append('\n');
                            writer.write((sb.toString()));
                        }
                    }
                }
                    catch(IOException e){
                        e.printStackTrace();
                    }

        })));
    }

    private static void parseCountriesParser() throws java.io.IOException{
        header.set(1);
        AtomicReference<String> lastKnownName = new AtomicReference<>("");
        AtomicInteger count = new AtomicInteger(1);
        System.out.println(String.format("Parsed countries.list in %s seconds", parser.streamFile("countries.list", (line, writer) -> {
            StringBuilder sb = new StringBuilder();

            try {
                if ((line.indexOf('(') != -1 || line.indexOf('\t') != -1)) {


                    String movie = line.substring(0, line.lastIndexOf(getYear(line)) -1 );

                    if (movie.equals(lastKnownName.get())){
                        count.incrementAndGet();
                    }
                    else{
                        count.set(1);
                    }

                    String country = line.substring(line.lastIndexOf('\t')+1);
                    String year = getYear(line);
                    sb.append(movie);
                    sb.append(';');
                    sb.append(country);
                    sb.append(';');
                    sb.append(year);
                    sb.append(';');
                    sb.append(count.get());
                    sb.append('\n');
                    writer.write(sb.toString());
                    sb = new StringBuilder();
                    lastKnownName.set(movie);
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }

        })));
    }

    private static void parseGenresParser() throws java.io.IOException {
        header.set(1);
        AtomicReference<String> lastKnownName = new AtomicReference<>("");
        AtomicInteger count = new AtomicInteger(1);
        System.out.println(String.format("Parsed genres.list in %s seconds", parser.streamFile("genres.list", (line, writer) -> {
            StringBuilder sb = new StringBuilder();
            if (header.get() > 385) {
                try {
                    if ((line.indexOf('(') != -1 || line.indexOf('\t') != -1)) {

                        String year = getYear(line);
                        String movie = line.substring(0, line.lastIndexOf(getYear(line)) -1 );
                        if (movie.equals(lastKnownName.get())){
                            count.incrementAndGet();
                        }
                        else{
                            count.set(1);
                        }

                        String genre = line.substring(line.lastIndexOf('\t') + 1);
                        sb.append(movie);
                        sb.append(';');
                        sb.append(genre);
                        sb.append(';');
                        sb.append(year);
                        sb.append(';');
                        sb.append(count.get());
                        sb.append('\n');
                        writer.write(sb.toString());
                        sb = new StringBuilder();
                        lastKnownName.set(movie);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                header.incrementAndGet();
            }
        })));
    }

    private static void parseLocationsParser() throws java.io.IOException{
        header.set(1);
        AtomicReference<String> lastKnownName = new AtomicReference<>("");
        AtomicInteger count = new AtomicInteger(1);
        System.out.println(String.format("Parsed locations.list in %s seconds", parser.streamFile("locations.list", (line, writer) -> {
            StringBuilder sb = new StringBuilder();
            if (header.get() > 14) {
                try {
                    if (line.lastIndexOf(')') != -1) {


                        String movie = line.substring(0, line.lastIndexOf(')'));
                        if (movie.equals(lastKnownName.get())){
                            count.incrementAndGet();
                        }
                        else{
                            count.set(1);
                        }

                        String locations = line.substring(line.lastIndexOf('\t') + 1);
                        String[] locationArray = locations.split(",");

                        for(int i = 0 ; i < locationArray.length ; i++){
                            sb.append(movie);
                            sb.append(';');
                            sb.append(locationArray[i].replaceAll("\\s+",""));
                            sb.append('\n');
                            writer.write(sb.toString());
                            sb = new StringBuilder();
                            lastKnownName.set(movie);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                header.incrementAndGet();

            }})));
    }

    private static void parseRatings() throws IOException {
        header.set(1);

        Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
        Pattern moviesPatternRunningTimes = Pattern.compile("\\s*(.{10})\\s*([0-9]*)\\s*([0-9].[0-9])\\s*(.*)(\\(.+?\\))");

        System.out.println(String.format("Parsed Movie Ratings", parser.streamFile("ratings.list", (line, writer) -> {
            if (header.get() > 28) {
                Matcher seriesMatcher = seriesPatternMovies.matcher(line);
                Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);
                if (!seriesMatcher.matches() && moviesMatcher.matches()) {
                    try {
                        writer.write(moviesMatcher.replaceAll("$2 - $3 - $4 \n"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else
                header.incrementAndGet();
        })));
    }
    private static String getYear(String line){
	    int index = line.lastIndexOf('(');
	    while (index != -1){
	        if (Character.isDigit(line.charAt(index + 1)) && line.length() > index + 5){
	            return line.substring(index + 1, index + 5);
            }
            else{
	            if (index -1 == -1){
	                index = -1;
	                continue;
                }
	            index = line.substring(0,index -1).lastIndexOf('(');
            }
        }

        return "????";
    }

    private static void LanguageParser() throws IOException {
        header.set(1);
        Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
        Pattern moviesPatternRunningTimes = Pattern.compile("(.*)(\\([0-9]{4})(.*)(\\t)([A-Z].+)");
        System.out.println(String.format("Parsed Movie Language", parser.streamFile("language.list", (line, writer) -> {
            if (header.get() > 14) {
                Matcher seriesMatcher = seriesPatternMovies.matcher(line);
                Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);
                if (!seriesMatcher.matches() && moviesMatcher.matches()) {
                    try {
                        writer.write(moviesMatcher.replaceAll("$1 ; $5 \n"));
                    } catch (IOException var7) {
                        var7.printStackTrace();
                    }
                }
            } else {
                header.incrementAndGet();
            }

        })));
    }
}
