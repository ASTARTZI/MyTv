package api;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MovieTest {

    private Movie movie;

    @Before
    public void setUp() {
        // Initialize a new Movie object before each test
        movie = new Movie("Sample Movie", "Movie", "Sample Cast", false, "Action",
                2022, 120, "Sample Description", "Sample Relative", new HashMap<>());
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals("Sample Movie", movie.getTitle());
        assertEquals("Movie", movie.getType());
        assertEquals("Sample Cast", movie.getCast());
        assertFalse(movie.getUnder18());
        assertEquals("Action", movie.getGenre());
        assertEquals(2022, movie.getReleaseYear());
        assertEquals(120, movie.getDurationMinutes());
        assertEquals("Sample Description", movie.getDescription());
        assertEquals("Sample Relative", movie.getRelative());

        // Test setters
        movie.setTitle("New Movie");
        movie.setType("Film");
        movie.setCast("New Cast");
        movie.setUnder18(true);
        movie.setGenre("Adventure");
        movie.setReleaseYear(2023);
        movie.setDurationMinutes(150);
        movie.setDescription("New Description");
        movie.setRelative("New Relative");

        assertEquals("New Movie", movie.getTitle());
        assertEquals("Film", movie.getType());
        assertEquals("New Cast", movie.getCast());
        assertTrue(movie.getUnder18());
        assertEquals("Adventure", movie.getGenre());
        assertEquals(2023, movie.getReleaseYear());
        assertEquals(150, movie.getDurationMinutes());
        assertEquals("New Description", movie.getDescription());
        assertEquals("New Relative", movie.getRelative());
    }

    @Test
    public void testAddRating() {
        Rating rating = new Rating("user1", "Great movie!", 4, "2022-01-01");
        movie.addRating(rating);

        assertTrue(movie.getRatings().containsKey("user1"));
        assertEquals(rating, movie.getRatings().get("user1"));
    }

    @Test
    public void testAverageRankWithNoRatings() {
        assertEquals(-1, movie.averageRank(), 0.01);
    }

    @Test
    public void testAverageRankWithRatings() {
        Rating rating1 = new Rating("user1", "Excellent!", 4, "2022-01-01");
        Rating rating2 = new Rating("user2", "Good movie", 3, "2022-01-02");
        movie.addRating(rating1);
        movie.addRating(rating2);

        double expectedAverage = (4.0 + 3.0) / 2.0;
        assertEquals(expectedAverage, movie.averageRank(), 0.01);
    }

    @Test
    public void testAverageRankAsString() {
        Rating rating1 = new Rating("user1", "Excellent!", 5, "2022-01-01");
        Rating rating2 = new Rating("user2", "Good movie", 4, "2022-01-02");
        movie.addRating(rating1);
        movie.addRating(rating2);

        String expectedAverageString = "4,5";
        assertEquals(expectedAverageString, movie.averageRankAsString());
    }

    @Test
    public void testPrint() {
        // Redirect standard output to capture printed text
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the overridden print method
        movie.print();

        // Restore standard output
        System.setOut(System.out);

        // Verify the printed output
        String expectedOutput = "movie:Sample Movie Action Sample Cast\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}



