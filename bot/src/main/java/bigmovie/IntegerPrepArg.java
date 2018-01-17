package bigmovie;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntegerPrepArg<Integer> extends PrepArg<java.lang.Integer> {

	IntegerPrepArg(java.lang.Integer prop) {
		super(prop);
	}

	@Override
	void applyLogic(PreparedStatement statement, int index) throws SQLException {
		statement.setInt(index, prop);
	}
}
