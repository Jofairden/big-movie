package bigmovie;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.rivescript.macro.Subroutine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author anoniem
 */
public class JdbcSubroutine implements Subroutine {

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {
        StringBuilder sql = new StringBuilder();
        StringBuilder result = new StringBuilder();
	    for (String arg : args) sql.append(" ").append(arg);
        sql = new StringBuilder(sql.toString().trim());

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        BotConfig config = Bot.getConfig();

        try {
            connection=(Connection) DriverManager.getConnection(
                    "jdbc:mysql://" + config.getDB_HOST() + ":" + config.getDB_PORT() + "/" + config.getDB_COL() + "?autoReconnect=true&useSSL=false",
                    config.getDB_USR(), config.getDB_PW());
            statement=(Statement) connection.createStatement();
            resultSet=statement.executeQuery(sql.toString());
            while(resultSet.next()) {
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
        } finally{
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
