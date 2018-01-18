package bigmovie;


import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Daniel
 * Defines a PrepArg (prepared argument)
 * Basically is a Box<T>, which applies itself on a PreparedStatement
 * automagically
 */
public final class PrepArg<T> {
	
	T prop;
	
	public PrepArg(T prop) {
		this.prop = prop;
	}
	
	// apply boxed property to statement
	public void apply(PreparedStatement statement, int index) throws SQLException {
		// @todo when needed, add more checks here
		if (prop instanceof String) {
			statement.setString(index, (String) prop);
		} else if (prop instanceof Integer) {
			statement.setInt(index, (Integer) prop);
		}
	}
}
