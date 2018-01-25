package bigmovie.gethandlers;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.Tuple;
import bigmovie.subroutines.HttpGETSubroutine;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * @author Daniel
 * Connects to imageflip meme api, to get 100 popular memes
 * Then on request, memes can be sent.
 */
public final class MemeHandler extends GetHandler {
	
	// cache urls to memes
	private static final Map<String,String> memes = new HashMap<>();
	// cache needs update?
	public static Boolean memesNeedUpdate = true;
	
	// get random meme
	private static Tuple<String,String> getRandMeme() {
		Random random = new Random();
		List<String> keys = new ArrayList<>(memes.keySet());
		String randomKey = keys.get(random.nextInt(keys.size()));
		return new Tuple<>(randomKey, memes.get(randomKey));
	}
	
	@Override
	public boolean handleRequest(HttpGETSubroutine.GetAddr getAddr) {
		try {
			// we dont know any memes, get the memes
			
			if (memesNeedUpdate || memes.isEmpty()) {
				memes.clear(); // reset cache
				
				// perform http GET request, get some memes.
				JSONObject json = HttpGETSubroutine.GetAddr.asJSONObject(getAddr.get(null));
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
				memesNeedUpdate = false;
			}
			
			// we have memes, send a meme
			if (!memes.isEmpty()) {
				Tuple<String,String> meme = getRandMeme();
				BotUtils.trySendFileFromStream(meme.y, meme.x);
				return true;
			}
		} catch (Exception e) {
			// something else went wrong
			Bot.logger.error(e.toString());
		}
		return false;
	}
}
