package models;

import java.util.HashMap;
import java.util.Map;

public class ActorModel {

    private String name;
    private int occurence;
    public Map<Integer, MovieModel> movies;

    public String Name() {
        return name;
    }

    public ActorModel(String name, int occurence) {
        this.name=  name;
        this.occurence = occurence;
        this.movies = new HashMap<>();
    }
}
