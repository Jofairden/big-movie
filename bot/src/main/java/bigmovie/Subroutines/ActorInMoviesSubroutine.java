package bigmovie.Subroutines;

import bigmovie.*;
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
 * @author Daniel
 */
public class ActorInMoviesSubroutine implements Subroutine {
	
	@Override
	public String call(com.rivescript.RiveScript rs, String[] args) {

		String result = BotUtils.execSqlQuery(args[0], new PrepArg[] {
			new StringPrepArg<String>(String.join("%", Arrays.stream(args).skip(1).collect(Collectors.toList())) + "%")
		});

		Bot.messageSubroutine.call(rs, String.format("embed title:test info:test %s", result).split(" "));
		return "";
	}
	
}
