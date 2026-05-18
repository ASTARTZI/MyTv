package api;

import java.io.Serializable;
import java.lang.management.OperatingSystemMXBean;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Represents media content, including movies and TV series, with details such as title, type, cast, and ratings.
 * This class provides methods for managing media content information.
 */
public class MediaContent implements Serializable {
    private String title;
    private String type;
    private String cast;
    private boolean Under18;
    private String description;
    private String genre;
    private String relative;
    private HashMap<String,Rating> ratings;

    /**
     * Constructs a new MediaContent object with the specified parameters.
     *
     * @param title       The title of the media content.
     * @param type        The type of the media content (e.g., movie, series).
     * @param cast        The cast or actors in the media content.
     * @param under18     A boolean indicating whether the content is suitable for audiences under 18.
     * @param genre       The genre of the media content.
     * @param description A brief description of the media content.
     * @param relative    A relative reference associated with the media content.
     * @param ratings     A HashMap containing user ratings for the media content.
     */
    public MediaContent(String title, String type, String cast, boolean under18, String genre, String description,
                        String relative,HashMap<String,Rating> ratings) {
        this.title = title;
        this.type = type;
        this.cast = cast;
        Under18 = under18;
        this.genre = genre;
        this.description=description;
        this.relative=relative;
        this.ratings=ratings;
    }

    /**
     * Retrieves the relative reference associated with the media content.
     *
     * @return The relative reference.
     */
    public String getRelative() {
        return relative;
    }

    /**
     * Sets the relative reference associated with the media content.
     *
     * @param relative The relative reference to set.
     */
    public void setRelative(String relative) {
        this.relative = relative;
    }

    /**
     * Retrieves the ratings for the media content.
     *
     * @return A HashMap containing user ratings.
     */
    public HashMap<String, Rating> getRatings() {
        return ratings;
    }

    /**
     * Sets the ratings for the media content.
     *
     * @param ratings A HashMap containing user ratings to set.
     */
    public void setRatings(HashMap<String, Rating> ratings) {
        this.ratings = ratings;
    }

    /**
     * Retrieves the description of the media content.
     *
     * @return The description of the media content.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the media content.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the title of the media content.
     *
     * @return The title of the media content.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the type of the media content.
     *
     * @return The type of the media content.
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieves the cast or actors in the media content.
     *
     * @return The cast or actors in the media content.
     */
    public String getCast() {
        return cast;
    }

    /**
     * Checks if the media content is suitable for audiences under 18.
     *
     * @return true if the content is suitable for under 18, false otherwise.
     */
    public boolean getUnder18() {
        return Under18;
    }

    /**
     * Retrieves the genre of the media content.
     *
     * @return The genre of the media content.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the title of the media content.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the type of the media content.
     *
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the cast or actors in the media content.
     *
     * @param cast The cast or actors to set.
     */
    public void setCast(String cast) {
        this.cast = cast;
    }

    /**
     * Sets whether the media content is suitable for audiences under 18.
     *
     * @param under18 true if the content is suitable for under 18, false otherwise.
     */
    public void setUnder18(boolean under18) {
        Under18 = under18;
    }

    /**
     * Sets the genre of the media content.
     *
     * @param genre The genre to set.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Adds a user rating to the media content.
     *
     * @param r The Rating object representing the user rating to add.
     */
    public void addRating(Rating r){
        ratings.put(r.getUsername(),r);
    }

    /**
     * Calculates the average rank based on user ratings.
     *
     * @return The average rank as a double value.
     */
    public double averageRank(){
        double s;
        s=0;
        for(Rating r: ratings.values()){
            s= s +r.getRank();
        }
        if (ratings.size()>0)
            return s / ratings.size();
        else
            return -1;   // Indicates No Ratings Available

    }

    /**
     * Converts the average rank to a formatted string.
     *
     * @return The average rank as a formatted string.
     */
    public String averageRankAsString(){
        Double avg= averageRank();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(avg);
    }

    /**
     * Placeholder method for printing information about the media content.
     * This method is overridden in subclasses.
     */
    public void print(){

    }

}
