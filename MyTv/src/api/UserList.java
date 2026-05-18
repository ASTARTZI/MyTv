package api;

import java.io.Serializable;
import java.util.HashMap;
import java.io.*;
/**
 * Represents a list of users with associated functionalities such as addition, retrieval, and persistence.
 */
public class UserList implements Serializable {
    private HashMap<String,Userinfo> users;
    private String filename;

    /**
     * Constructs a new UserList object with the specified filename.
     *
     * @param filename The name of the file to save and load user information.
     */
    public UserList(String filename) {
        this.filename = filename;
        users = new HashMap<>();
    }

    /**
     * Adds a user to the user list and saves the updated list to the file.
     *
     * @param user The user to be added.
     */
    public void addUser(Userinfo user) {
        users.put(user.getUsername(),user);
        saveToFile();
    }

    /**
     * Retrieves user information based on the provided username.
     *
     * @param username The username of the user to retrieve.
     * @return The Userinfo object associated with the given username.
     */
    public Userinfo getUser(String username) {
        return users.get(username);
    }

    /**
     * Retrieves the password associated with the provided username.
     *
     * @param username The username for which to retrieve the password.
     * @return The password associated with the given username or an empty string if the username is not found.
     */
    public String getPassword(String username) {
        Userinfo user=users.get(username);
        if(user == null){
            return "";
        }
        else {
            return user.getPassword();}
    }

    /**
     * Retrieves the entire user list.
     *
     * @return The HashMap containing all users.
     */
    public HashMap<String,Userinfo> getUserlist(){
        return users;
    }

    /**
     * Retrieves a HashMap containing only the subscribers from the user list.
     *
     * @return The HashMap containing only Subscriber objects.
     */
    public HashMap<String,Subscriber> getSubscribers(){
        HashMap<String,Subscriber> newMap = new HashMap<>();
        for (Userinfo u : users.values()){
            if (u instanceof Subscriber ) {
                newMap.put(u.getUsername(),(Subscriber) u);
            }
        }
        return newMap;
    }

    /**
     * Prints information about all users in the user list.
     */
    public void print(){
        for(Userinfo x: users.values() ){
            x.print();
        }
    }

    /**
     * Saves the user list to the specified file.
     */
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(users);
            System.out.println("Userlist saved");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the user list from the specified file.
     */
    public void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            users = (HashMap<String,Userinfo>) ois.readObject();
            System.out.println("Userlist loaded");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method for testing the functionality of the UserList class.
     * Adding of Administrator Accounts
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        UserList list = new UserList("Users.dat");
        Admin a1= new Admin("Elena","Lychnaropoulou","admin1","password1");
        Admin a2= new Admin("Martha","Astartzi","admin2","password2");

        list.addUser(a1);
        list.addUser(a2);
        list.loadFromFile();
        list.print();

    }

}
