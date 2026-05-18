package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RatingTest {

    private Rating rating;

    @Before
    public void setUp() {
        // Initialize a new Rating object before each test
        rating = new Rating("user1", "Great movie!", 4, "02/03/2021");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("user1", rating.getUsername());
        assertEquals("Great movie!", rating.getReview());
        assertEquals(4, rating.getRank());
        assertEquals("02/03/2021", rating.getDate());
    }

    @Test
    public void testSetters() {
        // Test setters
        rating.setUsername("user2");
        rating.setReview("Good movie");
        rating.setRank(3);
        rating.setDate("02/03/2021");

        assertEquals("user2", rating.getUsername());
        assertEquals("Good movie", rating.getReview());
        assertEquals(3, rating.getRank());
        assertEquals("02/03/2021", rating.getDate());
    }
}
