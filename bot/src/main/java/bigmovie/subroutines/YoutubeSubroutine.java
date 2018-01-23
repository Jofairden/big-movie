package bigmovie.subroutines;

import com.rivescript.macro.Subroutine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class YoutubeSubroutine implements Subroutine {

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {


        try {


            String movieName = args[0];
            String movieTrailer = movieName + "trailer";
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


            while (!result.substring(indexCounter,indexCounter+1).equals(stringEnd))
            {
                indexCounter += 1;

                if(result.substring(indexCounter,indexCounter+1).equals(stringEnd))
                {
                    indexEnd = indexCounter;
                }
            }
            //Zoek waar de substring in de string met de website inhoud zit en sla de begin en eid positie ervan op


            System.out.println(movieName);
            System.out.println(webPage);
            System.out.println(indexStart);
            System.out.println(indexEnd);
            System.out.println("https://www.youtube.com/watch?v=" + result.substring(indexStart,indexEnd));
            //Maak de link aan van de meest relevante video

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
