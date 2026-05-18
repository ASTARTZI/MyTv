package api;

import java.io.Serializable;

/**
 * Represents an administrator user with details such as name, last name, username, and password.
 * This class extends the Userinfo class and provides specific functionality for administrator users.
 */
public class Admin extends Userinfo implements Serializable {

    /**
     * Constructs a new Admin object with the specified parameters.
     *
     * @param name     The name of the administrator.
     * @param lastName The last name of the administrator.
     * @param username The username of the administrator.
     * @param password The password of the administrator.
     */
    public Admin(String name, String lastName, String username, String password) {
        super(name, lastName, username, password);
    }



    /**
     * Prints information about the administrator user.
     */
    public void print(){
        System.out.println("adm:"+getName()+" "+getLastName()+"/"+getUsername()+" "+getPassword());
    }

}




