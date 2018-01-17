package bigmovie;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class PrepArg<T> {
	
	T prop;
	
	PrepArg(T prop) {
		this.prop = prop;
	}
	
	abstract void applyLogic(PreparedStatement statement, int index) throws SQLException;
}
