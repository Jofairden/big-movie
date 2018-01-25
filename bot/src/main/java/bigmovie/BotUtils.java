package bigmovie;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class BotUtils {
	
	/**
	 * @author Daniel
	 * The following function sends a GET request
	 * to the given url, with optional parameters given (?q=a&b=c&c=d etc.)
	 * parameters will be set from the given Map, in the format: key=value
	 * The function assumes a JSON response, and return it as a JSONObject
	 */
	public static JSONObject httpGetJsonResponse(String reqUrl, Map<String,String> args) throws Exception {
		
		boolean anyArgs = !args.isEmpty();
		
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(reqUrl);
		
		// optional parameters
		if (anyArgs) {
			urlBuilder.append("?");
			
			// append every parameter
			args.forEach((key, value) -> {
				urlBuilder.append(String.format("%s=%s&", key, value));
			});
			
			urlBuilder.deleteCharAt(urlBuilder.toString().length() - 1); // delete trailing &
		}
		
		String url = urlBuilder.toString();
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return new JSONObject(response.toString());
	}
	
	/**
	 * @author Daniel
	 * @important remark: https://stackoverflow.com/questions/6164448/convert-url-to-normal-windows-filename-java
	 * Gets the resource's path as a Path
	 */
	public static Path getResourcePath(String resourceName) {
		try {
			return Paths.get(Objects.requireNonNull(BotUtils
					.class
					.getClassLoader()
					.getResource(resourceName))
					.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @author Daniel
	 * Gets the resource file contents as a string
	 */
	public static String getResourceAsString(String relativePath) throws IOException {
		return Resources.toString(Objects.requireNonNull(getResourcePath(relativePath)).toUri().toURL(), Charsets.UTF_8);
	}
	
	/**
	 * @author Jildert, Daniel
	 * Will attempt to perform an sql query
	 * on the database
	 * Arguments can be given as PrepArgs in an array
	 */
	public static String execSqlQuery(String queryFileName, PrepArg[] args) {
		
		String sql = null;
		StringBuilder result = new StringBuilder();
		boolean hasPrepArgs = args != null && args.length >= 1;
		
		// attempt to load the sql resource
		try {
			sql = getResourceAsString(String.format("sql/%s", queryFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// setup vars
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		BotConfig config = Bot.getConfig();
		
		// attempt to perform sql query
		try {
			connection = (Connection) DriverManager.getConnection(
					"jdbc:mysql://" + config.getDB_HOST() + ":" + config.getDB_PORT() + "/" + config.getDB_COL() + "?autoReconnect=true&useSSL=false",
					config.getDB_USR(), config.getDB_PW());
			// prepare statements to avoid sql injects
			statement = (PreparedStatement) connection.prepareStatement(sql);
			// has prep args
			if (hasPrepArgs) {
				int index = 1;
				for (PrepArg arg : args) {
					arg.apply(statement, index);
					index++;
				}
			}
			
			// execute
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				int i = resultSet.getMetaData().getColumnCount();
				for (int j = 1; j <= i; j++) {
					if (result.toString().isEmpty()) {
						result = new StringBuilder(resultSet.getString(j));
					} else {
						result.append(resultSet.getString(j)).append(" ");
					}
				}
				if (!result.toString().equals(""))
					result.append("\n");
			}
		} catch (SQLException ignored) {
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException ignored) {
			}
		}
		
		String queryResult = result.toString();
		Bot.lastSqlResult = queryResult;
		return queryResult;
	}
	
	/**
	 * @author Daniel
	 * Attempts to execute an Rscript
	 * using a ProcessBuilder
	 * all of its outputs will be logged
	 * using sout with this setup
	 */
	public static void execRSript(String pathToScript) {
		ProcessBuilder pb = new ProcessBuilder(
				Bot.getConfig().getRscript_PATH(),
				pathToScript)
				.inheritIO(); //java 7 or higher
		try {
			Process p = pb.start();
			p.waitFor();
			
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			if (!result.isEmpty())
				System.out.println(builder.toString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void embedErr(String msg) {
		if (msg.startsWith("embedErr:")) {
			String[] split = msg.split(":");
			String total = split[2];
			String sent = split[3];
			String rem = split[4];
			
			Bot.lastMessageReceivedEvent
					.getChannel()
					.sendMessage(
							String.format("I found %s movies, but I can only list %s, so %s remain"
									, total, sent, rem)
					).queue();
		}
	}
	
	public static void writeFile(String path, String content) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.println(content);
		writer.close();
	}
	
	public static String firstToUpper(String input) {
		return Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}
}


