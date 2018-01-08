package models;

import java.util.HashMap;
import java.util.Map;

/*
	Author: Daniël
 */
// A movie defines the information an actor (or actress) contains
// The fields are marked final which allows them to become public, because they are immutable outside the constructor
public class ActorModel {
	
	public final String name;
	public final int occurence;
	public final Map<Integer,MovieModel> movies;
	
	public ActorModel(String name, int occurence) {
		this.name = name;
		this.occurence = occurence;
		this.movies = new HashMap<>();
	}
}
