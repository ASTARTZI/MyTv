package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Represents a series with information such as title, type, cast, under-18 status, genre, description, relative,
 * ratings, and a list of seasons.
 */
public class Series extends MediaContent implements Serializable {
    private ArrayList<Season>seasons;
    /**
     * Constructs a new Series object with the specified attributes.
     *
     * @param title       The title of the series.
     * @param type        The type of the series (e.g., "series").
     * @param cast        The cast members of the series.
     * @param under18     A boolean indicating if the series is suitable for audiences under 18.
     * @param genre       The genre of the series.
     * @param description A description of the series.
     * @param relative    A relative description.
     * @param ratings     A map of ratings associated with the series.
     * @param seasons     The list of seasons associated with the series.
     */
    public Series(String title, String type, String cast, boolean under18, String genre,
                  String description, String relative,
                  HashMap<String,Rating> ratings, ArrayList<Season> seasons) {
        super(title, type, cast, under18, genre, description,relative,ratings);
        this.seasons = seasons;
    }

    /**
     * Retrieves the list of seasons associated with the series.
     *
     * @return The list of seasons.
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * Sets the list of seasons associated with the series.
     *
     * @param seasons The new list of seasons to set.
     */
    public void setSeasons(ArrayList<Season> seasons) {

        this.seasons = seasons;

    }

    /**
     * Prints information about the series, including title and genre.
     */
    public void print(){
        System.out.println("series:"+getTitle()+" "+getGenre());
    }


}


