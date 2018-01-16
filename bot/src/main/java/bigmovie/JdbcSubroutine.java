package bigmovie;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.rivescript.macro.Subroutine;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author anoniem
 */
public class JdbcSubroutine implements Subroutine {
	
	@Override
	public String call(com.rivescript.RiveScript rs, String[] args) {
		String sql = null;
		StringBuilder result = new StringBuilder();
		
		// if 1 arg passed, attempt to load sql file
		// else, interpret as sql query
		if (args.length == 1) {
			try {
				sql = Resources.toString(JdbcSubroutine.class.getResource(String.format("/sql/%s", args[0])), Charsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			for (String arg : args) {
				result.append(arg).append(" ");
			}
			sql = result.toString();
		}
		
		// nothing found, return
		if (sql == null || sql.isEmpty())
			return "";
		result = new StringBuilder();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		BotConfig config = Bot.getConfig();
		
		// @todo: convert to prepared statement to avoid sql injections
		try {
			connection = (Connection) DriverManager.getConnection(
					"jdbc:mysql://" + config.getDB_HOST() + ":" + config.getDB_PORT() + "/" + config.getDB_COL() + "?autoReconnect=true&useSSL=false",
					config.getDB_USR(), config.getDB_PW());
			statement = (Statement) connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int i = resultSet.getMetaData().getColumnCount();
				for (int j = 1; j <= i; j++) {
					if (result.toString().equals("")) {
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
		
		return result.toString();
	}
	
}
