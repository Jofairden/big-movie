package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.PrepArg;
import com.rivescript.macro.Subroutine;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PopularLanguageSubroutine implements Subroutine {
    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {

        String result = BotUtils.execSqlQuery(args[0], null);

        String msg = Bot.messageSubroutine.call(rs, String.format("context:embed fieldtitle:Language %s", result).split(" "));
        BotUtils.embedErr(msg);
        return "";
    }
}
