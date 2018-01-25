package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.gethandlers.*;
import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @author Daniel
 * The subroutine handles GET requests generically
 * For new requests, append to GetAddr
 * and then create a new handler in gethandlers
 */
public class HttpGETSubroutine implements Subroutine {
	
	/**
	 * @author Daniel
	 * A GetAddr is an address we can use to perform GET requests on
	 * This makes use of the rest-assured dependency
	 * Why do we painfully have get and asX methods here?
	 * Because a request does not always have to involve JSON
	 * In our current state maybe in does, but in the future
	 * a new request may not.
	 */
	public enum GetAddr {
		MEMES("https://api.imgflip.com/get_memes", new MemeHandler()),
		YESORNO("https://yesno.wtf/api", new YesOrNoHandler()),
		IMDB("http://www.imdb.com/find", new ImdbHandler()),
		YOUTUBE("https://www.youtube.com/results", new YoutubeHandler());
		
		public final String url;
		private final GetHandler handler;
		
		GetAddr(String url, GetHandler handler) {
			this.url = url;
			this.handler = handler;
		}
		
		// Send the handle to the handler
		public boolean handle(String[] args) {
			return handler.handleRequest(this, args);
		}
		
		// Perform the get, with given args (optional)
		public Response get(Map<String,String> args) {
			return given()
					.relaxedHTTPSValidation()
					.params(args != null ? args : new HashMap<String,String>() {
					}) // optional parameters
					.when()
					.get(url);
		}
		
		// Verify the response as JSON, and extract the response
		public static Response asJson(Response response) {
			return response
					.then()
					.assertThat()
					// check that the content type return from the API is JSON
					.contentType(ContentType.JSON)
					.extract()
					.response();
		}
		
		// Return the response as a JSONObject
		public static JSONObject asJSONObject(Response response) {
			return new JSONObject(response.print());
		}
		
		// Return the response as a JSONArray
		public static JSONArray asJSONArray(Response response) {
			return new JSONArray(response.print());
		}
		
		public static Response asHTML(Response response) {
			return response
					.then()
					.assertThat()
					.contentType(ContentType.HTML)
					.extract()
					.response();
		}
	}
	
	@Override
	public String call(RiveScript rs, String[] args) {
		
		// do we have args?
		if (args.length > 0) {
			// do we have a context?
			if (args[0].startsWith("context:")) {
				
				String context = args[0].split(":")[1];
				
				//@todo: if this grows big, turn into a dataset
				if (context.equalsIgnoreCase("meme")) {
					boolean result = GetAddr.MEMES.handle(null);
				} else if (context.equalsIgnoreCase("yesorno")) {
					boolean result = GetAddr.YESORNO.handle(null);
				} else if (context.equalsIgnoreCase("imdb")) {
					boolean result = GetAddr.IMDB.handle(Arrays.stream(args).skip(1).toArray(String[]::new));
				} else if (context.equalsIgnoreCase("youtube")) {
					boolean result = GetAddr.YOUTUBE.handle(Arrays.stream(args).skip(1).toArray(String[]::new));
				}
			} else {
				Bot.logger.error("HttpGET requested, but no context given");
			}
		} else {
			Bot.logger.error("HttpGET requested, but no args given");
		}
		
		return null;
	}
}
