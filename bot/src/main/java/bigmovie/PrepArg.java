package bigmovie;

public abstract class PrepArg<T> {
	
	T prop;
	
	PrepArg(T prop) {
		this.prop = prop;
	}
	
	abstract void applyLogic();
}
