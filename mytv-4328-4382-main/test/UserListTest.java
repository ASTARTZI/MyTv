import api.Admin;
import api.Subscriber;
import api.UserList;
import api.Userinfo;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class UserListTest {

    private UserList userList;

    @Before
    public void setUp() {
        // Initialize a new UserList object before each test
        userList = new UserList("testUsers.dat");
    }



    @Test
    public void testGetPassword() {
        Subscriber subscriber = new Subscriber("Test", "User", "testuser", "testpassword");

        userList.addUser(subscriber);

        assertEquals("testpassword", userList.getPassword("testuser"));
        assertEquals("", userList.getPassword("nonexistentuser"));
    }

    @Test
    public void testGetUserlist() {
        Subscriber subscriber1 = new Subscriber("User1", "Last1", "user1", "password1");
        Subscriber subscriber2 = new Subscriber("User2", "Last2", "user2", "password2");

        userList.addUser(subscriber1);
        userList.addUser(subscriber2);

        HashMap<String, Userinfo> userlist = userList.getUserlist();

        assertTrue(userlist.containsKey("user1"));
        assertTrue(userlist.containsKey("user2"));
    }

    @Test
    public void testGetSubscribers() {
        Subscriber subscriber1 = new Subscriber("User1", "Last1", "user1", "password1");
        Subscriber subscriber2 = new Subscriber("User2", "Last2", "user2", "password2");
        Admin admin = new Admin("Admin", "Last", "admin", "adminpassword");

        userList.addUser(subscriber1);
        userList.addUser(subscriber2);
        userList.addUser(admin);

        HashMap<String, Subscriber> subscribers = userList.getSubscribers();

        assertTrue(subscribers.containsKey("user1"));
        assertTrue(subscribers.containsKey("user2"));
        assertFalse(subscribers.containsKey("admin"));
    }

    @Test
    public void testPrint() {
        // Redirect standard output to capture printed text
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Subscriber subscriber1 = new Subscriber("User1", "Last1", "user1", "password1");
        Subscriber subscriber2 = new Subscriber("User2", "Last2", "user2", "password2");
        Admin admin = new Admin("Admin", "Last", "admin", "adminpassword");

        userList.addUser(subscriber1);
        userList.addUser(subscriber2);
        userList.addUser(admin);

        userList.print();

        // Restore standard output
        System.setOut(System.out);

        // Verify the printed output
        String expectedOutput = "Userlist saved\r\nUserlist saved\r\nUserlist "+
                "saved\r\nsub:User1 Last1/user1 password1\r\n"+
                "sub:User2 Last2/user2 password2\r\nadm:Admin Last/admin adminpassword\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }



    @Test
    public void testSaveLoadFromFile() {

        Subscriber subscriber = new Subscriber("Test", "User", "testuser", "testpassword");

        userList.addUser(subscriber);
        userList.saveToFile();
        userList.loadFromFile();

        assertEquals(subscriber.getUsername(), userList.getUserlist().get("testuser").getUsername());



        // Clean up the created test file
        File testFile = new File("testUsers.dat");
        if (testFile.exists()) {
            testFile.delete();
        }

    }
}
