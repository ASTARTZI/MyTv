import api.Userinfo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserinfoTest {

    private Userinfo userinfo;

    @Before
    public void setUp() {
        // Initialize a new Userinfo object before each test
        userinfo = new Userinfo("John", "Doe", "johndoe", "password");
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals("John", userinfo.getName());
        assertEquals("Doe", userinfo.getLastName());
        assertEquals("johndoe", userinfo.getUsername());
        assertEquals("password", userinfo.getPassword());

        // Test setters
        userinfo.setName("Jane");
        userinfo.setLastName("Smith");
        userinfo.setUsername("janesmith");
        userinfo.setPassword("newpassword");

        assertEquals("Jane", userinfo.getName());
        assertEquals("Smith", userinfo.getLastName());
        assertEquals("janesmith", userinfo.getUsername());
        assertEquals("newpassword", userinfo.getPassword());
    }


}
