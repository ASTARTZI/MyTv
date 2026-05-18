package api;

import api.Series;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a season of a series with information such as season number, release year, and episodes.
 */
public class Season implements Serializable {
    private Integer seasonNumber;
    private Integer releaseYear;
    private String episodes;

    /**
     * Constructs a new Season object with the specified attributes.
     *
     * @param seasonNumber The number associated with the season.
     * @param releaseYear  The year when the season was released.
     * @param episodes     A string representation of episodes in the season.
     */

    public Season(Integer seasonNumber, Integer releaseYear,String episodes) {
        this.seasonNumber = seasonNumber;
        this.releaseYear = releaseYear;
        this.episodes = episodes;
    }

    /**
     * Retrieves the season number.
     *
     * @return The season number.
     */
    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    /**
     * Sets the season number.
     *
     * @param seasonNumber The new season number to set.
     */
    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    /**
     * Retrieves the release year of the season.
     *
     * @return The release year of the season.
     */
    public Integer getReleaseYear() {
        return releaseYear;
    }

    /**
     * Sets the release year of the season.
     *
     * @param releaseYear The new release year to set.
     */
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * Retrieves a string representation of episodes in the season.
     *
     * @return A string containing episode information.
     */
    public String getEpisodes() {
        return episodes;
    }

    /**
     * Sets the string representation of episodes in the season.
     *
     * @param episodes The new string containing episode information to set.
     */
    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    /**
     * Calculates and retrieves the number of episodes in the season.
     *
     * @return The number of episodes in the season.
     */
    public int getNumberofEpisodes(){
        if (episodes.isEmpty())
            return 0;
        else
            return episodes.length() - episodes.replace(",", "").length()+1;
    }
}


