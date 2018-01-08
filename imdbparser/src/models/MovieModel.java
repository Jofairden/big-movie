package models;

/*
	Author: Daniël
 */
// A movie defines the information a movie contains
// The fields are marked final which allows them to become public, because they are immutable outside the constructor
public class MovieModel {
	
	public final String name;
	public final String year;
	
	public MovieModel(String name, String year) {
		this.name = name;
		this.year = year;
	}
}
