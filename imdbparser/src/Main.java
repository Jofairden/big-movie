import com.sun.org.apache.xpath.internal.operations.Bool;
import models.ActorModel;
import models.MovieModel;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
            parseRunningtimes();
            //parseActors();
            //parseSoundTracksParser();
            //parseCountriesParser();
           // parseGenresParser();
            //parseLocationsParser();
            parseRatings();
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


    private static void parseActors() throws IOException {
        header.set(1);

       // Map<Integer, ActorModel> actorBackup = new HashMap<>();
        Map<Integer, ActorModel> actors = new HashMap<>();
        AtomicReference<Integer> lastKey = new AtomicReference<>(0);
        AtomicReference<Boolean> canWrite = new AtomicReference<>(true);
        AtomicInteger count = new AtomicInteger(0);
        //AtomicInteger count2 = new AtomicInteger(0);

        System.out.println(String.format("Parsed actors.list in %s seconds", parser.streamFile("actors.list", (line, writer) -> {
            if (header.get() > 239 && canWrite.get()) {
                List<String> split = Arrays.stream(line.split("\t")).filter(x -> x.length() > 0).collect(Collectors.toList());

                try {

                        if (split.size() == 2) {
                            count.incrementAndGet();

                            String actorName = split.get(0);
                            String movieName = split.get(1);
                            int occurrence = 1;
                            String year = "0";

                            if (actorName.contains("(")){
                                occurrence = romanToDecimal(new StringBuilder(actorName).reverse().toString().split(" ")[0].replaceAll("[)|(]", ""));
                            }
                            if (movieName.contains("(")){
                                year = movieName.split("\\(")[1].split("\\)")[0];
                            }

                            lastKey.set(actorName.hashCode());

                            ActorModel actor = new ActorModel(actorName.split("\\(")[0].replace("(","").trim(), occurrence);
                            actor.movies.putIfAbsent(movieName.hashCode(), new MovieModel(movieName.split("\\(")[0].replace("(",""), year));
                            //actorBackup.putIfAbsent(actorName.hashCode(), actor);
                            actors.putIfAbsent(actorName.hashCode(), actor);
                        }
                        else if(split.size() == 1)
                        {
                            if (line.equalsIgnoreCase("-----------------------------------------------------------------------------")){
                                canWrite.set(false);
                                cleanActorsMap(actors, count, writer, 4);
                            }
                            else {
                                String movieName = split.get(0);

                                String year = "0";
                                if (movieName.contains("(")){
                                    year = movieName.split("\\(")[1].split("\\)")[0];
                                }
                                Integer key = lastKey.get();
                                actors.get(key).movies.putIfAbsent(movieName.hashCode(), new MovieModel(movieName.split("\\(")[0].replace("(",""), year));
                                //actorBackup.get(key).movies.putIfAbsent(movieName.hashCode(), new MovieModel(movieName, 0000));
                            }
                        }
                        else {
                            lastKey.set(0);
                            cleanActorsMap(actors, count, writer,10);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            } else
                header.incrementAndGet();
        })));

//
//        Writer writer = new BufferedWriter(new OutputStreamWriter(
//                new FileOutputStream(new File("").getAbsolutePath() + "\\data\\parsed\\actors.csv"), StandardCharsets.UTF_8));


    }

    private static void cleanActorsMap(Map<Integer,ActorModel> actors, AtomicInteger count, Writer writer, int number) {
        if (count.get() >= number)
        {
            actors.forEach((x,y) -> {
                y.movies.forEach((a, b) -> {
                    try {
                        //System.out.println(String.format("%s,%s\n", y.Name(), b.Name()));
                        writer.write(String.format("%s,%s\n", y.Name(), b.Name()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                try {
                    writer.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            actors.clear();
            count.set(0);
//            count2.incrementAndGet();
//
//            if (count2.get() > 1) {
//                actorBackup.clear();
//                count2.set(0);
//            }
        }
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
                if (!line.startsWith("\"") && line.indexOf('(') != -1 || line.indexOf('\t') != -1) {


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

    public static int romanToDecimal(java.lang.String romanNumber) {
        int decimal = 0;
        int lastNumber = 0;
        String romanNumeral = romanNumber.toUpperCase();
        /* operation to be performed on upper cases even if user
           enters roman values in lower case chars */
        for (int x = romanNumeral.length() - 1; x >= 0 ; x--) {
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

    public static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
        if (lastNumber > decimal) {
            return lastDecimal - decimal;
        } else {
            return lastDecimal + decimal;
        }
    }

    private static void parseRatings() throws IOException {
        header.set(1);

        Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
        Pattern moviesPatternRunningTimes = Pattern.compile("\\s*(.{10})\\s*([0-9]*)\\s*([0-9].[0-9])\\s*(.*)(\\()(\\d{4}\\/?(I+|V|))\\)");

        System.out.println(String.format("Parsed Movie Ratings", parser.streamFile("ratings.list", (line, writer) -> {
            if (header.get() > 28) {
                Matcher seriesMatcher = seriesPatternMovies.matcher(line);
                Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);
                if (!seriesMatcher.matches() && moviesMatcher.matches()) {
                    try {
                        writer.write(moviesMatcher.replaceAll("$2;$3;$4;$6$7 \n"));
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
        Pattern moviesPatternRunningTimes = Pattern.compile("(.*)\\(((\\d{4}\\/?(I+|V|))\\))(.*?)(\\t)(\\w+)");
        System.out.println(String.format("Parsed Movie Language", parser.streamFile("language.list", (line, writer) -> {
            if (header.get() > 14) {
                Matcher seriesMatcher = seriesPatternMovies.matcher(line);
                Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);
                if (!seriesMatcher.matches() && moviesMatcher.matches()) {
                    try {
                        writer.write(moviesMatcher.replaceAll("$1;$3$4;$7 \n"));
                    } catch (IOException var7) {
                        var7.printStackTrace();
                    }
                }
            } else {
                header.incrementAndGet();
            }

        })));
    }

    private static void parseRunningtimes() throws IOException {
        header.set(1);
        Pattern seriesPatternMovies = Pattern.compile("(^\".+)");
        Pattern moviesPatternRunningTimes = Pattern.compile("(.+) \\((\\d{4}\\/?(I+|))(\\))(.+?)([0-9]+)");
        System.out.println(String.format("Parsed Movie running-times", parser.streamFile("running-times.list", (line, writer) -> {
            if (header.get() > 17) {
                Matcher seriesMatcher = seriesPatternMovies.matcher(line);
                Matcher moviesMatcher = moviesPatternRunningTimes.matcher(line);
                if (!seriesMatcher.matches() && moviesMatcher.matches()) {
                    try {
                        writer.write(moviesMatcher.replaceAll("$1;$2$3;$6 minuten \n"));
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
