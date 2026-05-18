package api;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a subscriber with user information and a list of favorite media content.
 */
public class Subscriber extends Userinfo implements Serializable {

    private HashMap<String, MediaContent> favorites;

    /**
     * Constructs a new Subscriber object with the specified user information.
     *
     * @param name     The first name of the subscriber.
     * @param lastName The last name of the subscriber.
     * @param username The username of the subscriber.
     * @param password The password of the subscriber.
     */
    public Subscriber(String name, String lastName, String username, String password) {
        super(name, lastName, username, password);
        this.favorites = new HashMap<>();

    }

    /**
     * Gets the list of favorite media content associated with the subscriber.
     *
     * @return The list of favorite media content.
     */
    public HashMap<String, MediaContent> getFavorites() {
        return favorites;
    }

    /**
     * Sets the list of favorite media content associated with the subscriber.
     *
     * @param favorites The new list of favorite media content to set.
     */
    public void setFavorites(HashMap<String, MediaContent> favorites) {
        this.favorites = favorites;
    }

    /**
     * Adds the specified media content to the list of favorites.
     *
     * @param media The media content to add to favorites.
     */
    public void addFavorite(MediaContent media) {
        favorites.put(media.getTitle(), media);
    }

    /**
     * Deletes the specified media content from the list of favorites.
     *
     * @param media The media content to delete from favorites.
     */
    public void deleteFavorite(MediaContent media) {

        favorites.remove(media.getTitle());
    }

    /**
     * Prints information about the subscriber, including name, username, and password.
     */
    public void print(){
        System.out.println("sub:"+getName()+" "+getLastName()+"/"+getUsername()+" "+getPassword());
    }

}


