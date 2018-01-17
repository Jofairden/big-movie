package bigmovie;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BotUtils {

	public static String readFile(String path, Charset encoding)
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static URL getResourcePath(String resourceName) {
		return BotUtils.class.getResource(resourceName);
	}

	public static String getResourceAsString(String relativePath) throws IOException {
			return Resources.toString(getResourcePath(relativePath), Charsets.UTF_8);
	}

	public static String execSqlQuery(String queryFileName, PrepArg[] args) {

		String sql = null;
		StringBuilder result = new StringBuilder();
		boolean hasPrepArgs = args != null && args.length >= 1;

		// attempt to load the sql resource
		try {
			sql = getResourceAsString(String.format("/sql/%s", queryFileName));
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
					arg.applyLogic(statement, index);
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
		//Bot.messageSubroutine.call(rs, String.format("embed title:test info:test %s", result.toString()).split(" "));
		return queryResult;
	}

	public static void execRSript(String pathToScript)
	{
		ProcessBuilder pb = new ProcessBuilder(Bot.getConfig().getRscript_PATH(), pathToScript);
		try {
			Process p = pb.start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static <T> Spliterator<T> takeWhile(
			Spliterator<T> splitr, Predicate<? super T> predicate) {
		return new Spliterators.AbstractSpliterator<T>(splitr.estimateSize(), 0) {
			boolean stillGoing = true;
			
			@Override
			public boolean tryAdvance(Consumer<? super T> consumer) {
				if (stillGoing) {
					boolean hadNext = splitr.tryAdvance(elem -> {
						if (predicate.test(elem)) {
							consumer.accept(elem);
						} else {
							stillGoing = false;
						}
					});
					return hadNext && stillGoing;
				}
				return false;
			}
		};
	}

	public static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> predicate) {
		return StreamSupport.stream(takeWhile(stream.spliterator(), predicate), false);
	}
}


