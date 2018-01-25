package bigmovie;

/**
 * @param <X>
 * @param <Y>
 * @author Daniel
 * Tuple import, need this. ;x
 * (really want c#8, anonymous tuples: (val1, val2) :(
 */
public class Tuple<X, Y> {
	
	public final X x;
	public final Y y;
	
	public Tuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}
}
