package models;

import java.util.HashMap;
import java.util.Map;

public class ActorModel {
	
	public final String name;
	public final int occurence;
	public Map<Integer,MovieModel> movies;
	
	public ActorModel(String name, int occurence) {
		this.name = name;
		this.occurence = occurence;
		this.movies = new HashMap<>();
	}
}
