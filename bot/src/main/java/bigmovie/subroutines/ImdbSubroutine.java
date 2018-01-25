package bigmovie.subroutines;

import bigmovie.Bot;
import com.rivescript.macro.Subroutine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author Fadi (Daniel: cleanup)
 * @todo convert to httpGET subroutine
 */
public class ImdbSubroutine implements Subroutine {
	
	@Override
	public String call(com.rivescript.RiveScript rs, String[] args) {
		
		
		try {
			StringBuilder movieName = new StringBuilder();
			
			for (int i = 1; i < args.length; i++) {
				movieName.append(" ").append(args[i]);
			}
			// Haal de movie naam op en maak er 1 geheel van
			System.out.println(movieName);
			
			String imdbName = movieName.toString().replace(' ', '+');
			String webPage = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + imdbName;
			String year = args[0];
			//Convert de input naar een imdb link
			System.out.println(year);
			
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			//Maak de connectie naar de imdb link aan
			
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			//Zet de inhoud van de imdb link in een string
			
			int movieNotFound = result.indexOf("No results found for");
			if (movieNotFound != -1)         // 1ste controle, kijk of er resultaten zijn voor de search
				return "Movie not found";
			
			
			String search = "(" + year + ") </td>";
			int indexFound = result.indexOf(search);
			if (indexFound == -1)        // 2de controle, kijk of het jaar erbj staat
				return "Year/movie not found, did you mistype it?";
			
			int indexStart = indexFound - 1;
			int indexEnd = 0;
			int indexCounter = indexStart;
			String stringEnd = "\"";
			
			while (!result.substring(indexCounter, indexCounter + 1).equals(stringEnd)) {
				indexCounter -= 1;
			}
			indexEnd = indexCounter;
			// End van de imdb movie title-id
			while (!result.substring(indexCounter - 1, indexCounter).equals(stringEnd)) {
				indexCounter -= 1;
			}
			indexStart = indexCounter;
			// Start van de imdb movie title-id
			
			String normalMovieName = args[1];
			String movieNameUpper = normalMovieName.toUpperCase();
			if (result.charAt(indexEnd + 3) != normalMovieName.charAt(0) && result.charAt(indexEnd + 3) != movieNameUpper.charAt(0))      // Derde controle, Kijk of de letters overeen komen
				return "Movie not found, did you mistype it?";
			
			String webAdress = "http://www.imdb.com" + result.substring(indexStart, indexEnd);
			//Maak de link aan van de movie info op de imdb website
			System.out.println(movieName);
			System.out.println(webPage);
			System.out.println(indexStart);
			System.out.println(indexEnd);
			System.out.println(webAdress);
			
			return webAdress;
			// Geef het webadress door als een discord message
			
		} catch (IOException e) {
			Bot.logger.error(e.toString());
		}
		
		return "";
		
	}
}
