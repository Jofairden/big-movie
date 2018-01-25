package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Daniel
 * Connects to imageflip meme api, to get 100 popular memes
 * Then on request, memes can be sent.
 */
public class MemeSubroutine implements Subroutine {
	
	// where do we get memes from?
	private static final String getAddr = "https://api.imgflip.com/get_memes";
	
	// cache urls to memes
	private static ArrayList<String> memes = new ArrayList<>();
	
	// cache needs update?
	public static Boolean needsUpdate = true;
	
	// random object
	private static final Random random = new Random();
	
	// get random meme
	private static String getRandMeme() {
		return memes.get(random.nextInt(memes.size()));
	}
	
	@Override
	public String call(RiveScript rs, String[] args) {
		
		try {
			// we dont know any memes, get the memes
			if (needsUpdate || memes.isEmpty()) {
				memes = new ArrayList<>(); // reset cache
				
				// perform http GET request, get some memes.
				JSONObject json = BotUtils.httpGetJsonResponse(getAddr, new HashMap<>());
				Boolean success = json.getBoolean("success");
				if (success) {
					JSONArray data = json.getJSONObject("data").getJSONArray("memes");
					
					for (int i = 0; i < data.length(); ++i) {
						memes.add(data.getJSONObject(i).getString("url"));
					}
				}
				
				needsUpdate = false;
			}
			
			// we have memes, send a meme
			if (!memes.isEmpty()) {
				String url = getRandMeme();
				
				// attempt sending as a file
				try {
					InputStream input = new URL(url).openStream();
					
					if (input != null) {
						Bot.lastMessageReceivedEvent
								.getChannel()
								.sendFile(input, "thememe")
								.queue();
						
						input.close();
					}
				} catch (Exception e) {
					// we got denied, just send the url as a link
					Bot.logger.error(e.getStackTrace());
					
					Bot.lastMessageReceivedEvent
							.getChannel()
							.sendMessage(url)
							.queue();
				}
				
			}
		} catch (Exception e) {
			// something else went wrong
			Bot.logger.error(e.getStackTrace());
		}
		return null;
	}
}
