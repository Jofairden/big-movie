package bigmovie;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.rivescript.macro.Subroutine;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author anoniem
 */
public class JdbcSubroutine implements Subroutine {
	
	// @note : make sure to ESCAPE '!' on query
	private String prepLIKEQuery(String q) {
		return q
				.replace("!", "!!")
				.replace("%", "!%")
				.replace("_", "!_")
				.replace("[", "![");
	}
	
	@Override
	public String call(com.rivescript.RiveScript rs, String[] args) {
		String sql = null;
		StringBuilder result = new StringBuilder();
		boolean hasPrepArgs = args.length > 1;
		
		try {
			sql = Resources.toString(JdbcSubroutine.class.getResource(String.format("/sql/%s", args[0])), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// nothing found, return
//		if (sql == null || sql.isEmpty())
//			return "";
		
		// args can be passed as json
//		Map<String,String> prepArgs = null;
//
//		if (hasPrepArgs) {
//			String args2 = String.join("",
//					Arrays.stream(args)
//							.skip(1)
//							.collect(Collectors.toList()))
//					.replaceAll("!!", "\"");
//			Type type = new TypeToken<Map<String,String>>() {
//			}.getType();
//			prepArgs = new Gson().fromJson(args2, type);
//		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		BotConfig config = Bot.getConfig();
		
		// @todo: convert to prepared statement to avoid sql injections (done?)
		// @todo: per sql handler?
		try {
			connection = (Connection) DriverManager.getConnection(
					"jdbc:mysql://" + config.getDB_HOST() + ":" + config.getDB_PORT() + "/" + config.getDB_COL() + "?autoReconnect=true&useSSL=false",
					config.getDB_USR(), config.getDB_PW());
			// prepare statements to avoid sql injects
			statement = (PreparedStatement) connection.prepareStatement(sql);
			// has prep args
			if (hasPrepArgs) {
				// @todo: create PrepArg object to handle this better
				statement.setString(1, String.join("%", Arrays.stream(args).skip(1).collect(Collectors.toList())) + "%");
				// for now, is always a string, needs PrepArg object
			}
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
		
		Bot.lastSqlResult = result.toString();
		Bot.messageSubroutine.call(rs, String.format("embed title:test info:test %s", result.toString()).split(" "));
		return "";
	}
	
}
