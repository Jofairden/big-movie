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
        String path;
        if (args.length > 1){

            if (args[0].equals("context:genres") && args.length > 2){
                path = args[1];
                for (int i = 2; i < args.length; i++) {
                    sb.append(BotUtils.firstToUpper(args[i])).append("\n");
                }

            }
            else {

                //write temp country for R to read
                path = args[0];

                for (int i = 1; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
            }

            try {
                String data = sb.toString();
                data = data.trim();
                BotUtils.writeFile(path, data);
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
