package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.PrepArg;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

public class MoviesInXCountriesSubroutine implements Subroutine {
	
	@Override
	public String call(RiveScript rs, String[] args) {
		
		try {
			try {
				int parsed = Integer.parseInt(args[1]);
				
				String result = BotUtils.execSqlQuery(args[0], new PrepArg[] {
						new PrepArg<>(parsed)
				});
				
				String msg = Bot.messageSubroutine.call(rs, String.format("context:embed fieldtitle:Movies %s", result).split(" "));
				
				BotUtils.embedErr(msg);
			} catch (NumberFormatException e) {
				Bot.lastMessageReceivedEvent
						.getChannel()
						.sendMessage("Please supply a number!")
						.queue();
			}
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		return "";
	}
}
