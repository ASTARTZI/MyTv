package api;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class AdminTest {

    private Admin admin;

    @Before
    public void setUp() {
        // Initialize a new Admin object before each test
        admin = new Admin("John", "Doe", "johndoe", "password");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("John", admin.getName());
        assertEquals("Doe", admin.getLastName());
        assertEquals("johndoe", admin.getUsername());
        assertEquals("password", admin.getPassword());
    }

    @Test
    public void testPrint() {
        // Redirect standard output to capture printed text
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the overridden print method
        admin.print();

        // Restore standard output
        System.setOut(System.out);

        // Verify the printed output
        String expectedOutput = "adm:John Doe/johndoe password\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
