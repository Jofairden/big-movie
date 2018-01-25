package bigmovie.gethandlers;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.subroutines.HttpGETSubroutine;
import org.json.JSONObject;

/**
 * @author Daniel
 * Will answer yes or no, with a gif response
 * @TODO: their API is broken or something, do not use.
 */
public class YesOrNoHandler extends GetHandler {
	
	@Override
	public boolean handleRequest(HttpGETSubroutine.GetAddr getAddr, String[] args) {
		try {
			JSONObject json = HttpGETSubroutine.GetAddr.asJSONObject(getAddr.get(null));
			
			String answer = json.getString("answer");
			String image = json.getString("image");
			
			BotUtils.trySendFileFromStream(image, answer);
			return true;
			
		} catch (Exception e) {
			// something else went wrong
			Bot.logger.error(e.toString());
		}
		
		return false;
	}
}
