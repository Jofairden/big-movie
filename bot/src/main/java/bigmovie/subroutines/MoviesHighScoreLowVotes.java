package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

public class MoviesHighScoreLowVotes implements Subroutine {
	@Override
	public String call(RiveScript rs, String[] args) {
		String result = BotUtils.execSqlQuery(args[0], null);
		String msg = Bot.messageSubroutine.call(rs, String.format("context:embed fieldtitle:Movies %s", result).split(" "));
		BotUtils.embedErr(msg);
		return "";
	}
}
