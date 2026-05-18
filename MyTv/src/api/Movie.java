package api;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;
/**
 * Represents a movie as a specific type of media content.
 * Extends the MediaContent class and includes additional properties specific to movies.
 */
public class Movie extends MediaContent implements Serializable {

    private int releaseYear;
    private int durationMinutes;
    /**
     * Constructs a new Movie object with the specified attributes.
     *
     * @param title          The title of the movie.
     * @param type           The type of media content (e.g., movie, series).
     * @param cast           The cast or actors involved in the movie.
     * @param under18        Indicates if the movie is suitable for audiences under 18.
     * @param genre          The genre of the movie.
     * @param releaseYear    The release year of the movie.
     * @param durationMinutes The duration of the movie in minutes.
     * @param description    A brief description of the movie.
     * @param relative       A relative reference or identifier for the movie.
     * @param ratings        A HashMap containing user ratings for the movie.
     */
    public Movie(String title, String type, String cast, boolean under18, String genre,
                 int releaseYear, int durationMinutes, String description,String relative,
                 HashMap<String,Rating> ratings) {
        super(title, type, cast, under18, genre, description,relative,ratings);
        this.releaseYear = releaseYear;
        this.durationMinutes = durationMinutes;
    }

    /**
     * Retrieves the release year of the movie.
     *
     * @return The release year of the movie.
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Sets the release year of the movie.
     *
     * @param releaseYear The new release year to set.
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * Retrieves the duration of the movie in minutes.
     *
     * @return The duration of the movie in minutes.
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Sets the duration of the movie in minutes.
     *
     * @param durationMinutes The new duration to set.
     */
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * Prints information about the movie.
     * This method overrides the print method in the parent class.
     */
    public void print(){
        System.out.println("movie:"+getTitle()+" "+getGenre()+" "+getCast());
    }
}

