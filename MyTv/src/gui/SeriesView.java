package gui;

import api.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Represents the dialog for viewing detailed information about a TV series in the MyTV application.
 */
public class SeriesView extends JDialog {

    // Fields to store information about the series
    private Series series;
    private String title;
    private String description;
    private String cast;
    private Boolean under18;
    private String genre;
    private String relative;
    private HashMap<String, Rating> ratings;
    private ArrayList<Season> seasons;
    private Double avgRating;

    // Fields for GUI components
    private JTextField titleField;
    private JTextArea descriptionField;
    private JCheckBox under18Field;
    private JTextField genreField;
    private JTextField castField;
    private JTextArea relativeField;
    private DefaultTableModel model;
    private JTable seasonsTable;
    private JTextField avgRatingField;

    /**
     * Constructs a new SeriesView dialog for the given series.
     *
     * @param series The series to be displayed.
     */
    public SeriesView(Series series){
        super((JFrame)null, "MyTV Series View",  true);
        this.series = series;
        this.title=series.getTitle();
        this.description=series.getDescription();
        this.cast = series.getCast();
        this.under18=series.getUnder18();
        this.genre=series.getGenre();
        this.relative=series.getRelative();
        this.ratings=series.getRatings();
        this.seasons=series.getSeasons();
        this.avgRating= series.averageRank();

        createForm();
    }

    /**
     * Creates and initializes the components of the SeriesView dialog.
     */
    public void createForm(){

        // Set up frame properties
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Labels and Text Fields for Series Information
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(title);
        titleField.setEditable(false);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextArea(description);
        JScrollPane descrScroll = new JScrollPane (descriptionField);
        descriptionField.setEditable(false);

        JLabel castLabel = new JLabel("Cast:");
        castField = new JTextField(cast);
        castField.setEditable(false);

        JLabel under18Label = new JLabel("Under18:");
        under18Field = new JCheckBox("",under18);
        under18Field.setEnabled(false);

        JLabel genreLabel = new JLabel("Genre:");
        genreField = new JTextField(genre);
        genreField.setEditable(false);

        JLabel relativeLabel = new JLabel("Relative Series:");
        relativeField = new JTextArea();
        updateRelatives();

         // Labels and Table for Seasons Information
        JScrollPane relativeScroll = new JScrollPane (relativeField);
        JLabel seasonsLabel = new JLabel("Seasons:");
        JLabel seasonsHelpLabel = new JLabel("<html>(Double click on a Season to view its Episodes)</html>");
        seasonsHelpLabel.setFont(new Font("Arial", Font.PLAIN,10));
        JButton saveButton = new JButton("Save");
        JButton editRatButton = new JButton("Edit/View Ratings");
        JButton editButton = new JButton("Edit Series");
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };


        //create table with data
        model.addColumn("Title");
        model.addColumn("Year");
        model.addColumn("# Episodes");
        seasonsTable = new JTable(model);
        seasonsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        seasonsTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
        seasonsTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
        seasonsTable.getColumnModel().getColumn(2).setCellRenderer(renderer);

        updateSeasonsTable();

        // Labels and Field for Average Rating
        JScrollPane seasonsScroll = new JScrollPane (seasonsTable);
        JLabel avgRatingLabel = new JLabel("Avg Rating:");
        if (avgRating>-1)
            avgRatingField = new JTextField(String.format("%.1f",avgRating));
        else
            avgRatingField = new JTextField("-----");
        avgRatingField.setEditable(false);

        // CheckBox for Adding to Favorites
        JCheckBox addToFavoritesCheckbox = new JCheckBox("Add to favorites");

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
        under18Label.setBounds(xlbl,190,60,20);
        under18Field.setBounds(xtxt,190,100,20);
        genreLabel.setBounds(xlbl,220,60,20);
        genreField.setBounds(xtxt,220,250,20);
        relativeLabel.setBounds(xlbl,250,150,20);
        relativeScroll.setBounds(xtxt,250,350,100);
        seasonsLabel.setBounds(xlbl,360,150,20);
        seasonsScroll.setBounds(xtxt,360,350,100);
        seasonsHelpLabel.setBounds(560,330,100,100);
        avgRatingLabel.setBounds(xlbl,470,150,20);
        avgRatingField.setBounds(xtxt,470,40,20);
        addToFavoritesCheckbox.setBounds(470,470,150,20);
        saveButton.setBounds(200,480,80,30);
        editRatButton.setBounds(290,520,150,20);
        editButton.setBounds(290,520,150,20);

        // Double-click event for Seasons Table
       seasonsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Check for double-click
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    Season season;
                    String SeasonNoStr = (String)target.getValueAt(row, 0);
                    Integer seasonNo = Integer.parseInt(SeasonNoStr.substring(7,SeasonNoStr.length()));
                    // Open the SeasonView dialog for the selected season
                    new SeasonView(titleField.getText().trim(),seasons,seasonNo);
                }
            }
        });

        // Action listener for Edit/View Ratings button
        editRatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the RatingsView dialog for the series
                new RatingsView(series);
                avgRating=series.averageRank();
                if (avgRating>-1)
                    avgRatingField.setText(String.format("%.1f",avgRating));
                else
                    avgRatingField.setText("-----");
            }
        });

        // Action listener for Edit Series button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the SeriesAddEditDelete dialog for editing the series
                new SeriesAddEditDelete(series);
                dispose();

            }
        });

       // Action listener for Add to Favorites checkbox
        addToFavoritesCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Subscriber sub = (Subscriber) Database.current_user;
                if (addToFavoritesCheckbox.isSelected()) {
                    sub.addFavorite(series);
                    Database.users.addUser(sub);
                }
                else {
                    sub.deleteFavorite(series);
                    Database.users.addUser(sub);
                }

            }
        });

        // Add components to the dialog
        add(titleLabel);
        add(titleField);
        add(descriptionLabel);
        add(descrScroll);
        add(castLabel);
        add(castField);
        add(under18Label);
        add(under18Field);
        add(genreLabel);
        add(genreField);
        add(relativeLabel);
        add(relativeScroll);
        add(seasonsLabel);
        add(seasonsScroll);
        add(seasonsHelpLabel);
        add(avgRatingLabel);
        add(avgRatingField);

       //Choose which buttons to display depending on user type (subscriber, administrator)
        if (Database.current_user instanceof Subscriber) {
            Subscriber sub = (Subscriber) Database.current_user;
            addToFavoritesCheckbox.setSelected(sub.getFavorites().containsKey(series.getTitle()));
            add(addToFavoritesCheckbox);
            add(editRatButton);
        }
        else {
            add(editButton);
        }

        // Set dialog properties
        setSize(700,630);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


}

    /**
     * Updates the display of related series in the dialog.
     */
    private void updateRelatives(){
        //if (!relative.isEmpty()) {
            relativeField.setText(relative.replace(",", "\n"));
        //}
        relativeField.setEnabled(false);
    }

    /**
     * Updates the display of seasons in the dialog.
     */
    private void updateSeasonsTable(){
        model.setRowCount(0);

        for (Season s: seasons){
            model.addRow(new Object[]{"Season "+s.getSeasonNumber(),s.getReleaseYear(),
                    s.getNumberofEpisodes()});
        }
        seasonsTable.repaint();
    }

    /**
     * The main method to launch the application and test the SeriesView class.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        Database.media.loadFromFile();
        Database.users.loadFromFile();
        Database.current_user = Database.users.getUser("john");

        new SeriesView((Series)Database.media.getMedialist().get("Squid game"));


    }

}
