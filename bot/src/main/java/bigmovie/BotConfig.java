package bigmovie;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
	Author: Daniel Zondervan
	Contains bot metadata and can read the json to fill the config object
	Using the GSON lib
 */
public class BotConfig {

	private String DB_HOST;
	private String DB_COL;
	private String DB_USR;
	private String DB_PW;
	private String DB_PORT;
	private String TOKEN;

	public String getDB_HOST() {
		return DB_HOST;
	}

	public String getDB_USR() {
		return DB_USR;
	}

	public String getDB_PW() {
		return DB_PW;
	}

	public String getDB_PORT() {
		return DB_PORT;
	}

	public String getTOKEN() {
		return TOKEN;
	}

	public String getDB_COL() {
		return DB_COL;
	}

	public static BotConfig read() throws IOException {
		try(Reader reader = new InputStreamReader(BotConfig.class.getResourceAsStream("/BotConfig.json"), "UTF-8")){
			Gson gson = new GsonBuilder().create();
			return gson.fromJson(reader, BotConfig.class);
		}
	}
}
