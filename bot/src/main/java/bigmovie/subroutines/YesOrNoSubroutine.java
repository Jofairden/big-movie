package bigmovie.subroutines;

import bigmovie.BotUtils;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;
import org.json.JSONObject;

/**
 * @author Daniel
 * Will answer yes or no, with a gif response
 * @TODO: their API is broken or something, do not use.
 */
public class YesOrNoSubroutine implements Subroutine {
	
	private static final String getAddr = "https://yesno.wtf/api";
	
	@Override
	public String call(RiveScript rs, String[] args) {
		
		try {
			JSONObject json = BotUtils.httpGetJsonResponse(getAddr, null);
			
			String answer = json.getString("answer");
			String image = json.getString("image");
			
			BotUtils.trySendFileFromStream(image, answer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
