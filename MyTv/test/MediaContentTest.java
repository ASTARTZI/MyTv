package api;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class MediaContentTest {

    private MediaContent mediaContent;

    @Before
    public void setUp() {
        // Initialize a new MediaContent object before each test
        mediaContent = new MediaContent("Sample Title", "Movie", "Sample Cast", false, "Action",
                "Sample Description", "Sample Relative", new HashMap<>());
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals("Sample Title", mediaContent.getTitle());
        assertEquals("Movie", mediaContent.getType());
        assertEquals("Sample Cast", mediaContent.getCast());
        assertFalse(mediaContent.getUnder18());
        assertEquals("Action", mediaContent.getGenre());
        assertEquals("Sample Description", mediaContent.getDescription());
        assertEquals("Sample Relative", mediaContent.getRelative());

        // Test setters
        mediaContent.setTitle("New Title");
        mediaContent.setType("TV Show");
        mediaContent.setCast("New Cast");
        mediaContent.setUnder18(true);
        mediaContent.setGenre("Comedy");
        mediaContent.setDescription("New Description");
        mediaContent.setRelative("New Relative");

        assertEquals("New Title", mediaContent.getTitle());
        assertEquals("TV Show", mediaContent.getType());
        assertEquals("New Cast", mediaContent.getCast());
        assertTrue(mediaContent.getUnder18());
        assertEquals("Comedy", mediaContent.getGenre());
        assertEquals("New Description", mediaContent.getDescription());
        assertEquals("New Relative", mediaContent.getRelative());
    }

    @Test
    public void testAddRating() {
        Rating rating = new Rating("user1", "review", 4, "12/03/2023");
        mediaContent.addRating(rating);

        assertTrue(mediaContent.getRatings().containsKey("user1"));
        assertEquals(rating, mediaContent.getRatings().get("user1"));
    }

    @Test
    public void testAverageRankWithNoRatings() {
        assertEquals(-1, mediaContent.averageRank(), 0.01);
    }

    @Test
    public void testAverageRankWithRatings() {
        Rating rating1 = new Rating("user1", "review1", 3, "13/07/2023");
        Rating rating2 = new Rating("user2", "review2", 4, "16/02/2023");
        mediaContent.addRating(rating1);
        mediaContent.addRating(rating2);

        double expectedAverage = (4.0 + 3.0) / 2;
        assertEquals(expectedAverage, mediaContent.averageRank(), 0.01);
    }

    @Test
    public void testAverageRankAsString() {
        Rating rating1 = new Rating("user1", "review1", 5, "12/03/2023");
        Rating rating2 = new Rating("user2", "review2", 4, "12/03/2022");
        mediaContent.addRating(rating1);
        mediaContent.addRating(rating2);

        String expectedAverageString = "4,5";
        assertEquals(expectedAverageString, mediaContent.averageRankAsString());
    }

}