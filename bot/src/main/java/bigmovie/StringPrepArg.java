package bigmovie;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringPrepArg<String> extends PrepArg<java.lang.String> {

	public StringPrepArg(java.lang.String prop) {
		super(prop);
	}

	@Override
	void applyLogic(PreparedStatement statement, int index) throws SQLException {
		statement.setString(index, prop);
	}

}
