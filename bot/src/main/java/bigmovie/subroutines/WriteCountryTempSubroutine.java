package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.PrepArg;
import com.rivescript.macro.Subroutine;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class WriteCountryTempSubroutine implements Subroutine{
    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {
        StringBuilder sb = new StringBuilder();
        if (args.length > 1){
            //write temp country for R to read
            String path = args[0];

            for (int i = 1 ; i < args.length; i ++){
                sb.append(args[i]).append(" ");
            }
            try {
                String country = sb.toString();
                country = country.trim();
                BotUtils.writeFile(args[0], country);
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
