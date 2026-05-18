package api;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MediaContentListTest {

    private MediaContentList mediaContentList;

    @Before
    public void setUp() {
        // Initialize a new MediaContentList object before each test
        mediaContentList = new MediaContentList("testMedia.dat");
    }

    @Test
    public void testAddMediaAndGetMedia() {
        Movie movie = new Movie("Test Movie", "movie", "Test Actor", false, "Test Genre", 2022,
                120, "Test Description", "Test Relative", new HashMap<>());

        mediaContentList.addMedia(movie);

        assertEquals(movie, mediaContentList.getMedia("Test Movie"));
    }

    @Test
    public void testDeleteMedia() {
        Movie movie = new Movie("Test Movie", "movie", "Test Actor", false, "Test Genre", 2022,
                120, "Test Description", "Test Relative", new HashMap<>());

        mediaContentList.addMedia(movie);
        mediaContentList.deleteMedia("Test Movie");

        assertNull(mediaContentList.getMedia("Test Movie"));
    }

    @Test
    public void testSaveAndLoadFromFile() {

        Movie movie = new Movie("Test Movie", "movie", "Test Actor", false, "Test Genre", 2022,
                120, "Test Description", "Test Relative", new HashMap<>());
        Series series = new Series("Sample Series", "series", "Sample Cast", false, "Drama",
                "Sample Description", "Sample Relative", new HashMap<>(), new ArrayList<>());


        mediaContentList.addMedia(movie);
        mediaContentList.addMedia(series);
        mediaContentList.saveToFile();
        mediaContentList.loadFromFile();



        assertEquals(movie.getTitle(), mediaContentList.getMedia("Test Movie").getTitle());
        assertEquals(movie.getType(), mediaContentList.getMedia("Test Movie").getType());
        assertEquals(movie.getCast(), mediaContentList.getMedia("Test Movie").getCast());

        assertEquals(series.getTitle(), mediaContentList.getMedia("Sample Series").getTitle());
        assertEquals(series.getType(), mediaContentList.getMedia("Sample Series").getType());
        assertEquals(series.getCast(), mediaContentList.getMedia("Sample Series").getCast());

        // Clean up the created test file
        File testFile = new File("testMedia.dat");
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testGetAllGenre() {
        Movie movie1 = new Movie("Movie1", "movie", "Actor1", false, "Genre1", 2022,
                120, "Description1", "Relative1", new HashMap<>());
        Movie movie2 = new Movie("Movie2", "movie", "Actor2", false, "Genre2", 2022,
                120, "Description2", "Relative2", new HashMap<>());

        mediaContentList.addMedia(movie1);
        mediaContentList.addMedia(movie2);

        ArrayList<String> allGenres = new ArrayList<>(mediaContentList.getAllgenre());

        assertTrue(allGenres.contains("Genre1"));
        assertTrue(allGenres.contains("Genre2"));
    }

    @Test
    public void testSearch() {
        Movie movie1 = new Movie("Test Movie1", "movie", "Test Actor1", false, "Test Genre1", 2022,
                120, "Test Description1", "Test Relative1", new HashMap<>());
        Movie movie2 = new Movie("Test Movie2", "movie", "Test Actor2", false, "Test Genre2", 2022,
                120, "Test Description2", "Test Relative2", new HashMap<>());

        mediaContentList.addMedia(movie1);
        mediaContentList.addMedia(movie2);

        HashMap<String, MediaContent> searchResults = mediaContentList.search("Test Movie", "movie",
                "Test Genre", "Test Actor", "All", "All");

        assertTrue(searchResults.containsKey("Test Movie1"));
        assertTrue(searchResults.containsKey("Test Movie2"));
    }

    @Test
    public void testFilterHashMap() {
        Movie movie1 = new Movie("Movie1", "movie", "Actor1", false, "Type1", 2022,
                120, "Description1", "Relative1", new HashMap<>());
        Movie movie2 = new Movie("Movie2", "movie", "Actor2", false, "Type2", 2022,
                120, "Description2", "Relative2", new HashMap<>());
        Series series = new Series("Series1", "series", "Sample Cast", false, "Drama",
                "Sample Description", "Sample Relative", new HashMap<>(), new ArrayList<>());

        mediaContentList.addMedia(movie1);
        mediaContentList.addMedia(movie2);
        mediaContentList.addMedia(series);

        HashMap<String, MediaContent> filteredMap = mediaContentList.filterHashMap("movie");

        assertTrue(filteredMap.containsKey("Movie1"));
        assertTrue(filteredMap.containsKey("Movie2"));
        assertFalse(filteredMap.containsKey("Series1"));
    }

    @Test
    public void testGetSortedKeys() {
        Movie movie2 = new Movie("Movie2", "movie", "Actor2", false, "Type2", 2022,
                120, "Description2", "Relative2", new HashMap<>());
        Movie movie1 = new Movie("Movie1", "movie", "Actor1", false, "Type1", 2022,
                120, "Description1", "Relative1", new HashMap<>());

        mediaContentList.addMedia(movie2);
        mediaContentList.addMedia(movie1);

        ArrayList<String> sortedKeys = mediaContentList.getSortedKeys("movie");

        assertEquals("Movie1", sortedKeys.get(0));
        assertEquals("Movie2", sortedKeys.get(1));
    }

    @Test
        public void testPrint() {
            // Redirect standard output to capture printed text
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            Movie movie1 = new Movie("Movie1", "movie", "Actor1", false, "Drama", 2022,
                    120, "Description1", "Relative1", new HashMap<>());
            Movie movie2 = new Movie("Movie2", "movie", "Actor2", false, "Action", 2022,
                    120, "Description2", "Relative2", new HashMap<>());

            mediaContentList.addMedia(movie1);
            mediaContentList.addMedia(movie2);

            mediaContentList.print();

            // Restore standard output
            System.setOut(System.out);

            // Verify the printed output
            String expectedOutput = "Medialist saved\r\nMedialist saved\r\nmovie:Movie1 Drama Actor1\r\nmovie:Movie2 Action Actor2\r\n";
            assertEquals(expectedOutput, outContent.toString());
        }
    }