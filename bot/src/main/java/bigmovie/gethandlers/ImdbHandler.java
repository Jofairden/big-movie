package bigmovie.gethandlers;

import bigmovie.Bot;
import bigmovie.subroutines.HttpGETSubroutine;

import java.util.HashMap;

/**
 * @author Fadi
 * Can search for the requested movie on imdb, and serve the link
 */
public class ImdbHandler extends GetHandler {
	
	@Override
	public boolean handleRequest(HttpGETSubroutine.GetAddr getAddr, String[] args) {
		
		StringBuilder movieName = new StringBuilder();
		
		for (int i = 1; i < args.length; i++) {
			movieName.append(" ").append(args[i]);
		}
		
		String imdbName = movieName.toString().replace(' ', '+');
		String year = args[0];
		
		// perform get
		String result = HttpGETSubroutine.GetAddr.asHTML(getAddr.get(new HashMap<String,String>() {
			{
				put("ref_", "nv_sr_fn");
				put("q", imdbName);
			}
		})).getBody().asString();
		
		//Zet de inhoud van de imdb link in een string
		
		int movieNotFound = result.indexOf("No results found for");
		if (movieNotFound != -1)         // 1ste controle, kijk of er resultaten zijn voor de search
		{
			Bot.messageSubroutine.call(null, new String[] {
					"Movie not found"
			});
			return false;
		}
		
		String search = "(" + year + ") </td>";
		int indexFound = result.indexOf(search);
		if (indexFound == -1)        // 2de controle, kijk of het jaar erbj staat
		{
			Bot.messageSubroutine.call(null, new String[] {
					"Year/movie not found, did you mistype it?"
			});
			return false;
		}
		
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
		{
			Bot.messageSubroutine.call(null, new String[] {
					"Movie not found, did you mistype it?"
			});
			return false;
		}
		
		String webAdress = "http://www.imdb.com" + result.substring(indexStart, indexEnd);
		//Maak de link aan van de movie info op de imdb website
		
		
		Bot.messageSubroutine.call(null, new String[] {
				"I found it! Link:", webAdress
		});
		// Geef het webadress door als een discord message
		
		return false;
	}
}
