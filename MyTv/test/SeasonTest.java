import api.Season;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;


public class SeasonTest {

    private Season season;

    @Before
    public void setUp() {
        // Initialize a new Season object before each test
        season = new Season(1, 2022, "34,46,25");
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(Integer.valueOf(1), season.getSeasonNumber());
        assertEquals(Integer.valueOf(2022), season.getReleaseYear());
        assertEquals("34,46,25", season.getEpisodes());

        // Test setters
        season.setSeasonNumber(2);
        season.setReleaseYear(2023);
        season.setEpisodes("34,46,25");

        assertEquals(Integer.valueOf(2), season.getSeasonNumber());
        assertEquals(Integer.valueOf(2023), season.getReleaseYear());
        assertEquals("34,46,25", season.getEpisodes());
    }

    @Test
    public void testGetNumberofEpisodesWithNonEmptyEpisodes() {
        int numberOfEpisodes = season.getNumberofEpisodes();
        assertEquals(3, numberOfEpisodes);
    }

    @Test
    public void testGetNumberofEpisodesWithEmptyEpisodes() {
        Season emptySeason = new Season(1, 2022, "");
        int numberOfEpisodes = emptySeason.getNumberofEpisodes();
        assertEquals(0, numberOfEpisodes);
    }

    @Test
    public void testGetNumberofEpisodesWithSingleEpisode() {
        Season singleEpisodeSeason = new Season(1, 2022, "34,46,25");
        int numberOfEpisodes = singleEpisodeSeason.getNumberofEpisodes();
        assertEquals(3, numberOfEpisodes);
    }
}
