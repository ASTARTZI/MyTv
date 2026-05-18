import api.Database;


/**
 * Το πρόγραμμά σας πρέπει να έχει μόνο μία main, η οποία πρέπει να είναι η παρακάτω.
 * <p>
 * <p>
 * ************* ΜΗ ΣΒΗΣΕΤΕ ΑΥΤΗ ΤΗΝ ΚΛΑΣΗ ************
 */


import gui.SignUpSignIn;

public class Mainapp {

    public static void main(String[] args) {

        Database.users.loadFromFile();
        Database.media.loadFromFile();
        new SignUpSignIn();
    }
}