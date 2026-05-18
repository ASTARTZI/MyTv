import api.Rating;
import api.Season;
import api.Series;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SeriesTest {

    private Series series;

    @Before
    public void setUp() {
        // Initialize a new Series object before each test
        series = new Series("Sample Series", "series", "Sample Cast", false, "Drama",
                "Sample Description", "Sample Relative", new HashMap<>(), new ArrayList<>());
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals("Sample Series", series.getTitle());
        assertEquals("series", series.getType());
        assertEquals("Sample Cast", series.getCast());
        assertFalse(series.getUnder18());
        assertEquals("Drama", series.getGenre());
        assertEquals("Sample Description", series.getDescription());
        assertEquals("Sample Relative", series.getRelative());
        assertNotNull(series.getRatings());
        assertTrue(series.getRatings().isEmpty());
        assertNotNull(series.getSeasons());
        assertTrue(series.getSeasons().isEmpty());

        // Test setters
        series.setTitle("New Series");
        series.setType("series");
        series.setCast("New Cast");
        series.setUnder18(true);
        series.setGenre("Comedy");
        series.setDescription("New Description");
        series.setRelative("New Relative");
        HashMap<String, Rating> newRatings = new HashMap<>();
        newRatings.put("user1", new Rating("user1", "Good", 4, "02/12/2022"));
        series.setRatings(newRatings);
        ArrayList<Season> newSeasons = new ArrayList<>();
        newSeasons.add(new Season(1, 2022, "34,25,54"));
        series.setSeasons(newSeasons);

        assertEquals("New Series", series.getTitle());
        assertEquals("series", series.getType());
        assertEquals("New Cast", series.getCast());
        assertTrue(series.getUnder18());
        assertEquals("Comedy", series.getGenre());
        assertEquals("New Description", series.getDescription());
        assertEquals("New Relative", series.getRelative());
        assertEquals(newRatings, series.getRatings());
        assertEquals(newSeasons, series.getSeasons());
    }

    @Test
    public void testPrint() {
        // Redirect standard output to capture printed text
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the overridden print method
        series.print();

        // Restore standard output
        System.setOut(System.out);

        // Verify the printed output
        String expectedOutput = "series:Sample Series Drama\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
