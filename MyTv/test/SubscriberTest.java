import api.MediaContent;
import api.Movie;
import api.Series;
import api.Subscriber;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SubscriberTest {

    private Subscriber subscriber;

    @Before
    public void setUp() {
        // Initialize a new Subscriber object before each test
        subscriber = new Subscriber("John", "Doe", "johndoe", "password");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("John", subscriber.getName());
        assertEquals("Doe", subscriber.getLastName());
        assertEquals("johndoe", subscriber.getUsername());
        assertEquals("password", subscriber.getPassword());
        assertNotNull(subscriber.getFavorites());
        assertTrue(subscriber.getFavorites().isEmpty());
    }

    @Test
    public void testSetFavorites() {
        HashMap<String, MediaContent> newFavorites = new HashMap<>();
        newFavorites.put("Movie1", new Movie("Movie1", "movie", "Cast1", false, "Action",
                2022, 120, "Description1", "Relative1", new HashMap<>()));
        newFavorites.put("Series1", new Series("Series1", "series", "Cast2", false, "Drama",
                "Description2", "Relative2", new HashMap<>(), new ArrayList<>()));
        subscriber.setFavorites(newFavorites);

        assertEquals(newFavorites, subscriber.getFavorites());
    }

    @Test
    public void testAddFavorite() {
        Movie movie = new Movie("Movie1", "movie", "Cast1", false, "Action",
                2022, 120, "Description1", "Relative1", new HashMap<>());
        subscriber.addFavorite(movie);

        assertTrue(subscriber.getFavorites().containsKey("Movie1"));
        assertEquals(movie, subscriber.getFavorites().get("Movie1"));
    }

    @Test
    public void testDeleteFavorite() {
        Movie movie = new Movie("Movie1", "movie", "Cast1", false, "Action",
                2022, 120, "Description1", "Relative1", new HashMap<>());
        subscriber.addFavorite(movie);

        subscriber.deleteFavorite(movie);

        assertFalse(subscriber.getFavorites().containsKey("Movie1"));
    }

    @Test
    public void testPrint() {
        // Redirect standard output to capture printed text
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the overridden print method
        subscriber.print();

        // Restore standard output
        System.setOut(System.out);

        // Verify the printed output
        String expectedOutput = "sub:John Doe/johndoe password\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
