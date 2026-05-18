package gui;

import api.Database;
import api.Movie;
import api.Rating;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
/**
 * A class representing a dialog for adding, editing, or deleting movie information.
 */
public class MovieAddEditDelete extends JDialog {
    // Fields for storing movie information
    private String title;
    private String description;
    private String cast;
    private Integer year;
    private Boolean under18;
    private String genre;
    private Integer duration;
    private String relative;
    private HashMap<String, Rating> ratings;

    // Fields for GUI components
    private JTextField titleField;
    private JTextArea descriptionField;
    private JTextField yearField;
    private JCheckBox under18Field;
    private JTextField genreField;
    private JTextField durationField;
    private JTextField castField;

    private JTextArea relativeField;
    /**
     * Default constructor for creating a new MovieAddEditDelete dialog.
     */
    public MovieAddEditDelete() {
        super((JFrame)null, "MyTV Movie Add/Edit",  true);
        title="";
        description="";
        cast="";
        year=null;
        under18=false;
        genre="";
        duration=null;
        relative="";
        ratings=new HashMap<>();
        createForm();
    }
    /**
     * Constructor for creating a MovieAddEditDelete dialog with pre-filled movie information.
     * @param title The title of the movie.
     * @param description The description of the movie.
     * @param cast The cast of the movie.
     * @param year The release year of the movie.
     * @param under18 Indicates if the movie is suitable for audiences under 18.
     * @param genre The genre of the movie.
     * @param duration The duration of the movie in minutes.
     * @param relative The relative movies of the movie.
     * @param ratings The ratings of the movie.
     */
    public MovieAddEditDelete(String title, String description, String cast, Integer year, Boolean under18,
                              String genre, Integer duration, String relative,
                              HashMap<String,Rating> ratings){
        super((JFrame)null, "Add/Edit Movie",  true);
        this.title=title;
        this.description=description;
        this.cast = cast;
        this.year=year;
        this.under18=under18;
        this.genre=genre;
        this.duration=duration;
        this.relative=relative;
        this.ratings=ratings;
        createForm();
    }
    /**
     * Constructor for creating a MovieAddEditDelete dialog with information from an existing Movie object.
     * @param movie The Movie object containing the movie information.
     */
    public MovieAddEditDelete(Movie movie){
        super((JFrame)null, "Add/Edit Movie",  true);
        this.title=movie.getTitle();
        this.description=movie.getDescription();
        this.cast = movie.getCast();
        this.year=movie.getReleaseYear();
        this.under18=movie.getUnder18();
        this.genre=movie.getGenre();
        this.duration=movie.getDurationMinutes();
        this.relative=movie.getRelative();
        this.ratings=movie.getRatings();
        createForm();
    }
    /**
     * Creates the GUI form for the MovieAddEditDelete dialog.
     */
    public void createForm(){
        // Set dialog properties
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(null);


        // Labels and text fields for various season details
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(title);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextArea(description);
        JScrollPane descrScroll = new JScrollPane (descriptionField);

        JLabel castLabel = new JLabel("Cast:");
        castField = new JTextField(cast);

        JLabel under18Label = new JLabel("Under18:");
        under18Field = new JCheckBox("",under18);

        JLabel genreLabel = new JLabel("Genre:");
        genreField = new JTextField(genre);

        JLabel durationLabel = new JLabel("Duration (min):");
        if (duration==null)
            durationField = new JTextField();
        else
            durationField = new JTextField(duration.toString());

        JLabel yearLabel = new JLabel("Year:");
        if (year==null)
            yearField = new JTextField();
        else
            yearField = new JTextField(year.toString());

        JLabel relativeLabel = new JLabel("Relative Movies:");
        relativeField = new JTextArea();
        updateRelatives();

        JScrollPane relativeScroll = new JScrollPane (relativeField);
        JButton relButton = new JButton("+");
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        if (title.isEmpty()) deleteButton.setEnabled(false);

        // Set positions for labels, fields, and buttons
        int xlbl=80;
        int xtxt=200;

        titleLabel.setBounds(xlbl,20,60,20);
        titleField.setBounds(xtxt,20,350,20);
        if (!title.isEmpty()) {
            titleField.setEnabled(false);
        }
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
        relButton.setBounds(550,310,50,20);
        saveButton.setBounds(200,440,80,30);
        deleteButton.setBounds(467,440,80,30);

        //Set action listeners for the buttons of the form
        relButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RelativeAddEdit relObj = new RelativeAddEdit(titleField.getText().trim(),
                        relativeField.getText().trim(),"movie");
                relative = relObj.getRelative();

                updateRelatives();

            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title=titleField.getText().trim();
                if (title.isEmpty()) {
                       JOptionPane.showMessageDialog(null,
                            "Please provide at least the Movie title", "Error", JOptionPane.ERROR_MESSAGE);
                       return;
                }

                description=descriptionField.getText().trim();
                cast = castField.getText().trim();
                try {
                    // Attempt to parse the text as an integer
                    year=Integer.parseInt(yearField.getText().trim());
                } catch (NumberFormatException ex) {
                    // Handle the case where the input is not a valid integer
                    JOptionPane.showMessageDialog(null,
                            "Year should be an Integer value!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                under18=under18Field.isSelected();
                genre=genreField.getText().trim();
                try {
                    // Attempt to parse the text as an integer
                    duration=Integer.parseInt(durationField.getText().trim());
                } catch (NumberFormatException ex) {
                    // Handle the case where the input is not a valid integer
                    JOptionPane.showMessageDialog(null,
                            "Duration should be an Integer value!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

               Database.media.addMedia(new Movie(title, "movie", cast, under18,
                            genre, year, duration, description, relative, ratings));
               dispose();


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer =  JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the movie?",
                        "Attention",JOptionPane.YES_NO_OPTION);
                if (answer==0){
                    Database.media.deleteMedia(title);
                    System.out.println("Movie DELETED");
                    dispose();
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
        add(relButton);
        add(saveButton);
        add(deleteButton);


       // Set dialog properties
        setSize(700,550);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


}

    /**
     * Updates the relative movies field in the GUI.
     */
    private void updateRelatives(){
        relativeField.setText(relative.replace(",", "\n"));
        relativeField.setEnabled(false);
    }

    /**
     * The main method to demonstrate the MovieAddEditDelete class.
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        Database.media.loadFromFile();
        new MovieAddEditDelete();
    }

}
