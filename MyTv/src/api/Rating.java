package api;

import java.io.Serializable;
/**
 * Represents a user rating for media content.
 * This class stores information such as the username, review, rank, and date.
 */
public class Rating implements Serializable {
    private String username;
    private String review;
    private int rank;
    private String date;

    /**
     * Constructs a new Rating object with the specified attributes.
     *
     * @param username The username of the user providing the rating.
     * @param review   The written review or comments by the user.
     * @param rank     The numerical rank or rating given by the user.
     * @param date     The date when the rating was submitted.
     */
    public Rating(String username, String review, int rank, String date) {
        this.username=username;
        this.review = review;
        this.rank = rank;
        this.date = date;
    }

    /**
     * Retrieves the username associated with the rating.
     *
     * @return The username of the user who provided the rating.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username associated with the rating.
     *
     * @param username The new username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the written review or comments associated with the rating.
     *
     * @return The review provided by the user.
     */
    public String getReview() {
        return review;
    }

    /**
     * Sets the written review or comments associated with the rating.
     *
     * @param review The new review to set.
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Retrieves the numerical rank or rating given by the user.
     *
     * @return The rank or rating provided by the user.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Sets the numerical rank or rating given by the user.
     *
     * @param rank The new rank to set.
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Retrieves the date when the rating was submitted.
     *
     * @return The date of the rating submission.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date when the rating was submitted.
     *
     * @param date The new date to set.
     */
    public void setDate(String date) {
        this.date = date;
    }
}
