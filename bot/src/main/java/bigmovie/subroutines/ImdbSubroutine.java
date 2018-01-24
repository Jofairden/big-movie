package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.PrepArg;
import com.rivescript.macro.Subroutine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.stream.Collectors;


public class ImdbSubroutine implements Subroutine {

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {


        try {
            String movieName = "";

            for (int i = 1; i < args.length; i++) {
                movieName = movieName + " " + args[i];
            }
            // Haal de movie naam op en maak er 1 geheel van

            String imdbName = movieName.replace(' ', '+');
            String webPage = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + imdbName;
            String year = args[0];
            //Convert de input naar een imdb link

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
            if(movieNotFound == -1)         // 1ste controle, kijk of er resultaten zijn voor de search
            {

                String search = "(" + year + ") </td>";
                int indexFound = result.indexOf(search);
                if(indexFound != -1)        // 2de controle, kijk of het jaar erbj staat
                {
                    int indexStart = indexFound -1;
                    int indexEnd = 0;
                    int indexCounter = indexStart;
                    String stringEnd = "\"";


                    while (!result.substring(indexCounter,indexCounter+1).equals(stringEnd))
                    {
                        indexCounter -= 1;
                    }
                    indexEnd = indexCounter;
                    // End van de imdb movie title-id
                    while (!result.substring(indexCounter-1,indexCounter).equals(stringEnd))
                    {
                        indexCounter -= 1;
                    }
                    indexStart = indexCounter;
                    // Start van de imdb movie title-id

                    String normalMovieName = args[1];
                    String movieNameUpper = normalMovieName.toUpperCase();
                    if(result.charAt(indexEnd +3) == normalMovieName.charAt(0) || result.charAt(indexEnd +3) == movieNameUpper.charAt(0))      // Derde controle, Kijk of de letters overeen komen
                    {
                        String webAdress = "http://www.imdb.com" + result.substring(indexStart,indexEnd);
                        //Maak de link aan van de movie info op de imdb website
                        System.out.println(movieName);
                        System.out.println(webPage);
                        System.out.println(indexStart);
                        System.out.println(indexEnd);
                        System.out.println(webAdress);

                        return webAdress;
                        // Geef het webadress door als een discord message
                    }
                    else
                    {
                        return ("Movie not found, did you mistype it?");
                    }
                }
                else
                {
                    return ("Year not found");
                }

            }
            else
            {
                return ("Movie not found");
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";

    }
}
