// author Fadi
package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.PrepArg;
import com.rivescript.macro.Subroutine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.stream.Collectors;

public class YoutubeSubroutine implements Subroutine {
	
	@Override
	public String call(com.rivescript.RiveScript rs, String[] args) {
		
		
		String sqlResult = BotUtils.execSqlQuery(args[0], new PrepArg[] {
				new PrepArg<>(String.join("%", Arrays.stream(args).skip(1).collect(Collectors.toList())) + "%")
		});
		//Voer de youtube query uit
		
		try {
			
			float movieBool = Float.valueOf(sqlResult);
			
			if (movieBool == 1.0) {
				// Controleer of de film in de database zit
				
				String movieName = "";
				
				for (int i = 1; i < args.length; i++) {
					movieName = movieName + " " + args[i];
				}
				//Bouw de volledige naam van de film
				
				String movieTrailer = movieName + " " + "trailer";
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
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				String result = sb.toString();
				//Zet de inhoud van de youtube link in een string
				
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
				
				String webAdres = "https://www.youtube.com/watch?v=" + result.substring(indexStart, indexEnd);
				System.out.println(movieName);
				System.out.println(webPage);
				System.out.println(indexStart);
				System.out.println(indexEnd);
				System.out.println(movieName);
				System.out.println("https://www.youtube.com/watch?v=" + result.substring(indexStart, indexEnd));
				//Maak de link aan van de meest relevante video
				
				Bot.randMessageSubroutine.call(rs, new String[] {
						"context:trailer"
				});
				return webAdres;
			} else {
				return "I couldn't find this movie in my database.";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ex) {
			System.err.println("ERR: Something went wrong (Trailer)");
		}
		return null;
	}
	
}
