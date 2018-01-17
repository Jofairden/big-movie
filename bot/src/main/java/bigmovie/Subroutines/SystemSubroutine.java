package bigmovie.Subroutines;

import bigmovie.Bot;
import bigmovie.BotConfig;
import com.rivescript.macro.Subroutine;
import com.rivescript.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anoniem
 */
public class SystemSubroutine implements Subroutine {

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {
        BotConfig config = Bot.getConfig();
        args[0] = config.getRscript_PATH();
        String cmd = StringUtils.join(args, " ");

        java.util.Scanner s;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            s = new java.util.Scanner(p.getInputStream());
            p.waitFor();


            rs.getSubstitution("send photo genre-fr-usa.jpg The graph produced by R");
            String argString ="photo genre-fr-usa.jpg The graph produced by R";
            String[] argsArray = argString.split(" ");
            Bot.sendSubroutine.call(rs, argsArray);

            return s.hasNext() ? s.next() : "";
        } catch (Exception e) {
            Logger.getLogger(SystemSubroutine.class.getName()).log(Level.SEVERE, null, e);
        }



        return "";
    }
    
}
