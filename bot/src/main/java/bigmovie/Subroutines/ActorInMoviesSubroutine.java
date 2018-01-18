package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.PrepArg;
import com.rivescript.macro.Subroutine;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Daniel
 */
public class ActorInMoviesSubroutine implements Subroutine {
	
	@Override
	public String call(com.rivescript.RiveScript rs, String[] args) {
		
		String result = BotUtils.execSqlQuery(args[0], new PrepArg[] {
				new PrepArg<>(String.join("%", Arrays.stream(args).skip(1).collect(Collectors.toList())) + "%")
		});
		
		String msg = Bot.messageSubroutine.call(rs, String.format("context:embed fieldtitle:Movies %s", result).split(" "));
		BotUtils.embedErr(msg);
		return "";
	}
	
}
