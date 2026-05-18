package api;
/**
 * Represents the databases used by the application, containing user and media information
 * together with the current user info.
 * This class provides static instances for user lists, media content lists, and the current user.
 */
public class Database {
    /**
     * Static instance of the UserList containing user information.
     */
    static public UserList users = new UserList("users.dat");

    /**
     * Static instance of the MediaContentList containing media content information.
     */
    static public MediaContentList media = new MediaContentList("media.dat");

    /**
     * Static instance representing the current user logged into the system.
     */
    static public Userinfo current_user = null;
}
