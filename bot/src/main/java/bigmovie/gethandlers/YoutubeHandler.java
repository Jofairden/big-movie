package bigmovie.gethandlers;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.PrepArg;
import bigmovie.subroutines.HttpGETSubroutine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.stream.Collectors;

public class YoutubeHandler extends GetHandler {
	
	@Override
	public boolean handleRequest(HttpGETSubroutine.GetAddr getAddr, String[] args) {
		String sqlResult = BotUtils.execSqlQuery(args[0], new PrepArg[] {
				new PrepArg<>(String.join("%", Arrays.stream(args).skip(1).collect(Collectors.toList())) + "%")
		});
		
		//Voer de youtube query uit
		try {
			
			float movieBool = Float.valueOf(sqlResult);
			
			if (movieBool != 1.0) {
				Bot.messageSubroutine.call(null, new String[] {
						"I couldn't find this movie in my database."
				});
				return false;
			}
			
			// Controleer of de film in de database zit : 1ste controle
			StringBuilder movieName = new StringBuilder();
			
			for (int i = 1; i < args.length; i++) {
				movieName.append(" ").append(args[i]);
			}
			//Bouw de volledige naam van de film
			
			if (movieName.charAt(0) == ' ') {
				movieName = new StringBuilder(movieName.substring(1));
			}
			//Haal de eerste spatie weg
			
			System.out.println(movieName);
			
			String movieTrailer = movieName + " " + "movie trailer";
			String youtubeName = movieTrailer.replace(' ', '+');
			String webPage = "https://www.youtube.com/results?sp=EgIQAQ%253D%253D&search_query=" + youtubeName;
			//Convert de input naar een youtube link
			
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			//Maak de connectie naar de youtube link aan
			
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuilder sb = new StringBuilder();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			//Zet de inhoud van de youtube link in een string
			
			if (!result.contains(movieName.toString())) // Zoek naar de movieName op youtube : 2de controle
			{
				Bot.messageSubroutine.call(null, new String[] {
						"I couldn't find this movie on youtube."
				});
				return false;
			}
			
			if (!result.contains("Trailer") && !result.contains("trailer")) // Zoek of het filmpje ook echt een trailer is : 3rde controle
			{
				Bot.messageSubroutine.call(null, new String[] {
						"I couldnt find the trailer for this movie"
				});
				return false;
			}
			
			String search = "<a href=\"/watch?v=";
			int indexFound = result.indexOf(search);
			int indexStart = indexFound + search.length();
			int indexEnd = 0;
			int indexCounter = indexStart;
			String stringEnd = "\"";
			
			while (!result.substring(indexCounter, indexCounter + 1).equals(stringEnd)) {
				indexCounter += 1;
				
				if (result.substring(indexCounter, indexCounter + 1).equals(stringEnd)) {
					indexEnd = indexCounter;
				}
			}
			//Zoek waar de substring in de string met de website inhoud zit en sla de begin en eid positie ervan op
			
			String webAddress = "https://www.youtube.com/watch?v=" + result.substring(indexStart, indexEnd);
			//Maak de link aan van de meest relevante video
			
			Bot.messageSubroutine.call(null, new String[] {
					webAddress
			});
			
			Bot.randMessageSubroutine.call(null, new String[] {
					"context:trailer"
			});
			
			return true;
			
		} catch (IOException | NumberFormatException e) {
			Bot.logger.error(e.toString());
		}
		
		return false;
	}
}
