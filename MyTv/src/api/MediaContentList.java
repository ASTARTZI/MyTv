package api;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;
/**
 * Represents a collection of media content, including movies and TV series.
 * Provides methods for managing media content such as addition, deletion, and searching.
 */
public class MediaContentList implements Serializable {
    private HashMap<String,MediaContent> medialist;
    private String filename;

    /**
     * Constructs a new MediaContentList and specifies the associated filename to store and load the data.
     *
     * @param filename The filename associated with the media content list.
     */
    public MediaContentList(String filename) {
        this.filename = filename;
        medialist = new HashMap<>();
    }

    /**
     * Adds media content to the list.
     *
     * @param media The media content to add.
     */
    public void addMedia(MediaContent media){
        medialist.put(media.getTitle(), media);
        saveToFile();
    }
    /**
     * Retrieves media content by title.
     *
     * @param title The title of the media content to retrieve.
     * @return The media content corresponding to the given title.
     */
    public MediaContent getMedia(String title){

        return medialist.get(title);
    }

    /**
     * Deletes media content by title.
     *
     * @param title The title of the media content to delete.
     */
    public void deleteMedia(String title){

        for (Subscriber sub : Database.users.getSubscribers().values()){
            HashMap<String,MediaContent> favorites = sub.getFavorites();
            if (favorites.containsKey(title)){
                sub.deleteFavorite(Database.media.getMedia(title));
                System.out.println("Favorite deleted from user"+sub.getUsername());
            }
            Database.users.addUser(sub);
        }
        medialist.remove(title);
        saveToFile();
    }
    /**
     * Saves the Serialized media content list to the associated file of the object.
     */
    public void saveToFile(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(medialist);
            System.out.println("Medialist saved");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Loads the Serialized media content list from the associated file of the object.
     */
    public void loadFromFile(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            medialist=(HashMap<String,MediaContent>)ois.readObject();
            System.out.println("Medialist loaded");
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    /**
     * Retrieves all unique genres present in the media content list.
     *
     * @return A HashSet containing all unique genres.
     */
    public HashSet<String>getAllgenre(){
        HashSet<String> g = new HashSet<>();
        for(MediaContent x: medialist.values() ){
            g.add(x.getGenre());
        }
        return g;

    }

    /**
     * Retrieves the entire media content list.
     *
     * @return A HashMap containing all media content.
     */
    public HashMap<String,MediaContent> getMedialist(){

        return medialist;
    }

    /**
     * Searches for media content based on specified criteria.
     *
     * @param title   The title to search for.
     * @param type    The type to filter by.
     * @param genre   The genre to filter by.
     * @param actor   The actor to search for.
     * @param under18 The suitability for audiences under 18.
     * @param minrank The minimum ranking for filtering.
     * @return A HashMap containing search results based on the given criteria.
     */
    public HashMap<String,MediaContent> search(String title,String type,String genre,String actor,
                                               String under18,String minrank)
    {
        HashMap<String,MediaContent>searchResults=new HashMap<>();

        for (MediaContent media : medialist.values() ){
            Boolean cond1 = title.isEmpty() || media.getTitle().toLowerCase().contains(title.toLowerCase());
            Boolean cond2 = type.isEmpty()||media.getType().toLowerCase().equals(type.toLowerCase());
            Boolean cond3 = actor.isEmpty() || media.getCast().toLowerCase().contains(actor.toLowerCase());
            Boolean cond4 = genre.isEmpty() || media.getGenre().toLowerCase().contains(genre.toLowerCase());
            Boolean cond5 = under18.equals("All") ||
                    media.getUnder18() && under18.equals("Yes") ||
                    !media.getUnder18() && under18.equals("No");
            Boolean cond6;
            if (minrank.equals("All")) {
                cond6 = true;
            }
            else {
                cond6 = media.averageRank() >= Integer.parseInt(minrank);
            }
            if (cond1 && cond2 && cond3 && cond4 && cond5 && cond6){
                searchResults.put(media.getTitle(),media);
            }

        }
        return searchResults;

    }

    /**
     * Filters the media content list based on the specified type (movie or series).
     *
     * @param type The type to filter by.
     * @return A HashMap containing media content filtered by type.
     */
    public HashMap<String,MediaContent> filterHashMap(String type){
        HashMap<String,MediaContent> newMap = new HashMap<>();
        for (MediaContent m : medialist.values()){
            if (m.getType().equals(type)) {
                newMap.put(m.getTitle(),m);
            }
        }
        return newMap;
    }

    /**
     * Retrieves a sorted list of keys based on the specified type (movie or series).
     *
     * @param type The type to filter by.
     * @return An ArrayList containing sorted keys.
     */
    public ArrayList<String> getSortedKeys(String type){
        HashMap<String,MediaContent> mlist = filterHashMap(type);
        ArrayList<String> sortedKeys = new ArrayList<>(mlist.keySet());
        Collections.sort(sortedKeys);
        return sortedKeys;
    }

    /**
     * Placeholder method for printing information about the media content.
     * Actual implementation can be added as needed.
     */
    public void print(){
        for(MediaContent x: medialist.values() ){
            x.print();

        }
    }

    /**
     * The main method for testing the MediaContentList class.
     *
     * @param args Command line arguments (not used in this context).
     */
    public static void main(String[] args) {
        MediaContentList list = new MediaContentList("media.dat");

        list.loadFromFile();
        list.print();

    }


}
