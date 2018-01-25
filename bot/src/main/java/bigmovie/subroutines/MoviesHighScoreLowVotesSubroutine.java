package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

public class MoviesHighScoreLowVotesSubroutine implements Subroutine {
	@Override
	public String call(RiveScript rs, String[] args) {
		String result = BotUtils.execSqlQuery(args[0], null);
		// Voer de query uit
		String msg = Bot.messageSubroutine.call(rs, String.format("context:embed fieldtitle:Movies %s", result).split(" "));
		BotUtils.embedErr(msg);
		// Geef een discord message terug
		return "";
	}
}
