package main;

/**
 * @author Daniel
 * Box T value, making things mutable and so forth
 */
public final class Box<T> {
	
	public T value;
	
	public Box(T value) {
		this.value = value;
	}
}
