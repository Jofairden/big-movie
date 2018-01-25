package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.Tuple;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * @author Daniel
 * Connects to imageflip meme api, to get 100 popular memes
 * Then on request, memes can be sent.
 */
public class MemeSubroutine implements Subroutine {
	
	// where do we get memes from?
	private static final String getAddr = "https://api.imgflip.com/get_memes";
	
	// cache urls to memes
	private static Map<String,String> memes = new HashMap<>();
	
	// cache needs update?
	public static Boolean needsUpdate = true;
	
	// random object
	private static final Random random = new Random();
	
	// get random meme
	private static Tuple<String,String> getRandMeme() {
		Random random = new Random();
		List<String> keys = new ArrayList<>(memes.keySet());
		String randomKey = keys.get(random.nextInt(keys.size()));
		return new Tuple<>(randomKey, memes.get(randomKey));
	}
	
	@Override
	public String call(RiveScript rs, String[] args) {
		
		try {
			// we dont know any memes, get the memes
			if (needsUpdate || memes.isEmpty()) {
				memes.clear(); // reset cache
				
				// perform http GET request, get some memes.
				JSONObject json = BotUtils.httpGetJsonResponse(getAddr, null);
				Boolean success = json.getBoolean("success");
				if (success) {
					
					// get meme data
					JSONArray data = json.getJSONObject("data").getJSONArray("memes");
					
					// loop memes, put in map
					for (int i = 0; i < data.length(); ++i) {
						JSONObject obj = data.getJSONObject(i);
						memes.putIfAbsent(obj.getString("name"), obj.getString("url"));
					}
				}
				
				needsUpdate = false;
			}
			
			// we have memes, send a meme
			if (!memes.isEmpty()) {
				Tuple<String,String> url = getRandMeme();
				BotUtils.trySendFileFromStream(url.y, url.x);
			}
		} catch (Exception e) {
			// something else went wrong
			Bot.logger.error(e.toString());
		}
		return null;
	}
}
