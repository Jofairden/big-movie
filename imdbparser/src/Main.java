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
            parseCountriesParser();
            //parseCountries();
            //parseGenres();

            //parseLocations();

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
        System.out.println(String.format("Parsed soundtracks.list in %s seconds", parser.streamFile("soundtracks.list", (line, writer) -> {
            StringBuilder sb = new StringBuilder();

                try {
                    if (line.length() > 2 && line.charAt(0) == '#' && line.indexOf('"') != -1) {

                        movie.set(line.substring(3, line.lastIndexOf('"')));
                        //year = thisLine.substring(thisLine.indexOf('(' + 1 ), 4);
                    } else if (movie.get() != "") {
                        if (line.length() > 0 && line.charAt(0) == '-') {
                            String soundTrack = line.substring(2);
                            sb.append(movie.get());
                            sb.append(';');
                            sb.append(soundTrack);
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

                    String movie = line.substring(0,line.lastIndexOf(')') -1);
                    String country = line.substring(line.lastIndexOf('\t')+1);
                    sb.append(movie);
                    sb.append(';');
                    sb.append(country);
                    sb.append('\n');
                    writer.write(sb.toString());
                    sb = new StringBuilder();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }

        })));
    }
    
    private static  void parseGenres() throws java.io.IOException{
        PrintWriter pw = new PrintWriter(new File("genres.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("movie");
        sb.append(';');
        sb.append("genre");
        sb.append('\n');
        pw.write(sb.toString());
        sb = new StringBuilder();


        String thisLine = null;

        try {

            // open input stream test.txt for reading purpose.
            BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\Jildert\\temporaryaccess\\genres.list")));

            for (int i = 1 ; i < 385; i ++){
                br.readLine();
            }

            while ((thisLine = br.readLine()) != null) {
                String movie = thisLine.substring(0,thisLine.lastIndexOf(')'));
                String genre = thisLine.substring(thisLine.lastIndexOf('\t') + 1);
                sb.append(movie);
                sb.append(';');
                sb.append(genre);
                sb.append('\n');
                pw.write(sb.toString());
                sb = new StringBuilder();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }


    private static  void parseLocations() throws java.io.IOException{
        PrintWriter pw = new PrintWriter(new File("locations.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("movie");
        sb.append(';');
        sb.append("location");
        sb.append('\n');
        pw.write(sb.toString());
        sb = new StringBuilder();


        String thisLine = null;
        String movie = null;
        String locations = null;


        try {

            // open input stream test.txt for reading purpose.
            BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\Jildert\\temporaryaccess\\locations.list")));

            for (int i = 1 ; i < 14; i ++){
                br.readLine();
            }

            while ((thisLine = br.readLine()) != null) {

                if (thisLine.lastIndexOf(')') == -1) continue;

                movie = thisLine.substring(0, thisLine.lastIndexOf(')'));
                locations = thisLine.substring(thisLine.lastIndexOf('\t') + 1);
                String[] locationArray = locations.split(",");
                for(int i = 0 ; i < locationArray.length ; i++){
                    sb.append(movie);
                    sb.append(';');
                    sb.append(locationArray[i].replaceAll("\\s+",""));
                    sb.append('\n');
                    pw.write(sb.toString());
                    sb = new StringBuilder();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }
}
