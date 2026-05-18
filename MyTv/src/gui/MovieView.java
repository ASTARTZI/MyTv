package gui;

import api.Database;
import api.Movie;
import api.Subscriber;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Represents a dialog for viewing details of a movie, including its information,
 * related movies, average rating, and options for user interaction.
 */
public class MovieView extends JDialog {
    // Fields to store information about the movie
    private Movie movie;
    private String title;
    private String description;
    private String cast;
    private Integer year;
    private Boolean under18;
    private String genre;
    private Integer duration;
    private String relative;
    private Double avgRating;
    private String current_user;

    // Fields for GUI components
    private JTextField titleField;
    private JTextArea descriptionField;
    private JTextField yearField;
    private JCheckBox under18Field;
    private JTextField genreField;
    private JTextField durationField;
    private JTextField castField;

    private JTextArea relativeField;

    private JTextField avgRatingField;

    /**
     * Constructs a MovieView dialog for the given movie.
     *
     * @param movie The movie to display details for.
     */

    public MovieView(Movie movie){
        super((JFrame)null, "MyTV Movie View",  true);
        this.movie=movie;
        this.title=movie.getTitle();
        this.description=movie.getDescription();
        this.cast = movie.getCast();
        this.year=movie.getReleaseYear();
        this.under18=movie.getUnder18();
        this.genre=movie.getGenre();
        this.duration=movie.getDurationMinutes();
        this.relative=movie.getRelative();
        this.avgRating= movie.averageRank();
       // System.out.println(avgRating);
        createForm();
    }
    /**
     * Creates the GUI components for the MovieView dialog.
     */
    public void createForm(){
        // Set dialog properties
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(null);



        // Labels and text fields for various season details
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(title);
        titleField.setEditable(false);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextArea(description);
        descriptionField.setEditable(false);
        JScrollPane descrScroll = new JScrollPane (descriptionField);
        descrScroll.setEnabled(false);

        JLabel castLabel = new JLabel("Cast:");
        castField = new JTextField(cast);
        castField.setEditable(false);

        JLabel under18Label = new JLabel("Under18:");
        under18Field = new JCheckBox("",under18);
        under18Field.setEnabled(false);

        JLabel genreLabel = new JLabel("Genre:");
        genreField = new JTextField(genre);
        genreField.setEditable(false);

        JLabel durationLabel = new JLabel("Duration (min):");
        if (duration==null)
            durationField = new JTextField();
        else
            durationField = new JTextField(duration.toString());
        durationField.setEditable(false);

        JLabel yearLabel = new JLabel("Year:");
        if (year==null)
            yearField = new JTextField();
        else
            yearField = new JTextField(year.toString());
        yearField.setEditable(false);

        JLabel relativeLabel = new JLabel("Relative Movies:");
        relativeField = new JTextArea();
        updateRelatives();
        relativeField.setEditable(false);
        JScrollPane relativeScroll = new JScrollPane (relativeField);
        relativeScroll.setEnabled(false);

        JLabel avgRatingLabel = new JLabel("Avg Rating:");
        if (avgRating>-1)
            avgRatingField = new JTextField(String.format("%.1f",avgRating));
        else
            avgRatingField = new JTextField("-----");
        avgRatingField.setEditable(false);

        JCheckBox addToFavoritesCheckbox = new JCheckBox("Add to favorites");
        JButton editRatButton = new JButton("Edit/View Ratings");
        JButton editButton = new JButton("Edit Movie");


        // Set positions for labels, fields, and buttons
        int xlbl=80;
        int xtxt=200;
        titleLabel.setBounds(xlbl,20,60,20);
        titleField.setBounds(xtxt,20,350,20);
        descriptionLabel.setBounds(xlbl,50,80,20);
        descrScroll.setBounds(xtxt,50,350,100);
        castLabel.setBounds(xlbl,160,60,20);
        castField.setBounds(xtxt,160,350,20);
        yearLabel.setBounds(xlbl,190,60,20);
        yearField.setBounds(xtxt,190,100,20);
        under18Label.setBounds(xlbl,220,60,20);
        under18Field.setBounds(xtxt,220,100,20);
        genreLabel.setBounds(xlbl,250,60,20);
        genreField.setBounds(xtxt,250,250,20);
        durationLabel.setBounds(xlbl,280,150,20);
        durationField.setBounds(xtxt,280,40,20);
        relativeLabel.setBounds(xlbl,310,150,20);
        relativeScroll.setBounds(xtxt,310,350,100);
        avgRatingLabel.setBounds(xlbl,420,150,20);
        avgRatingField.setBounds(xtxt,420,40,20);
        addToFavoritesCheckbox.setBounds(440,470,150,20);
        editRatButton.setBounds(xtxt,470,150,20);
        editButton.setBounds(290,470,150,20);



        //Set action listeners for the buttons of the form
        editRatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password
                new RatingsView(movie);
                avgRating=movie.averageRank();
                if (avgRating>-1)
                    avgRatingField.setText(String.format("%.1f",avgRating));
                else
                    avgRatingField.setText("-----");
             //   System.out.println(movie.averageRank());
              //  System.out.println("OK");
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MovieAddEditDelete(movie);
                dispose();

            }
        });


    addToFavoritesCheckbox.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Subscriber sub = (Subscriber) Database.current_user;
            if (addToFavoritesCheckbox.isSelected()) {
                sub.addFavorite(movie);
                Database.users.addUser(sub);
            }
            else {
               sub.deleteFavorite(movie);
               Database.users.addUser(sub);
            }

        }
    });

        // Adding components to the dialog
        add(titleLabel);
        add(titleField);
        add(descriptionLabel);
        add(descrScroll);
        add(castLabel);
        add(castField);
        add(yearLabel);
        add(yearField);
        add(under18Label);
        add(under18Field);
        add(genreLabel);
        add(genreField);
        add(durationLabel);
        add(durationField);
        add(relativeLabel);
        add(relativeScroll);
        add(avgRatingLabel);
        add(avgRatingField);


        if (Database.current_user instanceof Subscriber) {
            Subscriber sub = (Subscriber) Database.current_user;
            addToFavoritesCheckbox.setSelected(sub.getFavorites().containsKey(movie.getTitle()));
            add(addToFavoritesCheckbox);
            add(editRatButton);

        }
        else {
            add(editButton);
        }

        // Set dialog properties
        setSize(700,550);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);




    }
    /**
     * Updates the displayed list of relative movies.
     */
    private void updateRelatives(){
        relativeField.setText(relative.replace(",", "\n"));

    }

    /**
     * The main method for testing the MovieView class.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Database.media.loadFromFile();
        Database.users.loadFromFile();

        Database.current_user = Database.users.getUser("john");

    }
}


