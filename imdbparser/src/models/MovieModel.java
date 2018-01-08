package models;

public class MovieModel {

    private String name;
    private String year;

    public String Name() {
        return name;
    }

    public String Year() {
        return year;
    }

    public MovieModel(String name, String year) {
        this.name = name;
        this.year = year;
    }
}
