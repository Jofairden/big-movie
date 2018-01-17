package bigmovie.Subroutines;

import bigmovie.Bot;
import bigmovie.BotConfig;
import com.rivescript.macro.Subroutine;
import com.rivescript.util.StringUtils;
import java.io.IOException;
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
            s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream());
            return s.hasNext() ? s.next() : "";
        } catch (IOException ex) {
            Logger.getLogger(SystemSubroutine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
}