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
        String host = args[0];
        String port = args[1];
        String db = args[2];
        String username = args[3];
        String password = args[4];
        StringBuilder sql = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for (int i=5; i<args.length; i++) 
            sql.append(" ").append(args[i]);
        sql = new StringBuilder(sql.toString().trim());

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        //pak DB password uit DbPassword.txt file
        try {
            password = BotUtils.Readfile("src/main/java/bigmovie/DbPassword.txt", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            connection=(Connection) DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + db + "?autoReconnect=true&useSSL=false",
                    username, password);
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
