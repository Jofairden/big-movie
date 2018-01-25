package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.PrepArg;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;

public class MovieXScoreSubroutine implements Subroutine {
	@Override
	public String call(RiveScript rs, String[] args) {
		try {
			try {
				double parsed = Double.parseDouble(args[1]);

				if (parsed > 10 && parsed < 100)
					parsed /= 10;

				if (parsed < 0 || parsed > 10)
					throw new Exception("Double out of range!");

				String result = BotUtils.execSqlQuery(args[0], new PrepArg[] {
						new PrepArg<Double>(parsed)
						// Voer de query uit
				});

				String msg = Bot.messageSubroutine.call(rs, String.format("context:embed fieldtitle:Movies %s", result).split(" "));
				// Geef een discord message terug
				BotUtils.embedErr(msg);
			} catch (Exception e) {
				Bot.lastMessageReceivedEvent
						.getChannel()
						.sendMessage("Please supply a number between 0 and 10!" + e.toString())
						.queue();
				// Controleer of er een gelig getal is meegegeven
			}


		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return "";
	}
}
