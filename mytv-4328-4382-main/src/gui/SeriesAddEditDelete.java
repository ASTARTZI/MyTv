package gui;

import api.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Represents a dialog for adding, editing, or deleting TV series in the MyTV application.
 */
public class SeriesAddEditDelete extends JDialog {

    // Fields to store information about the  series
    private Series series;
    private String title;
    private String description;
    private String cast;
    private Boolean under18;
    private String genre;
    private String relative;
    private HashMap<String, Rating> ratings;
    private ArrayList<Season> seasons;


    // Fields for GUI components
    private JTextField titleField;
    private JTextArea descriptionField;
    private JCheckBox under18Field;
    private JTextField genreField;
    private JTextField castField;
    private JTextArea relativeField;
    private DefaultTableModel model;
    private JTable seasonsTable;

    /**
     * Constructs a new SeriesAddEditDelete dialog for creating a new TV series.
     */
    public SeriesAddEditDelete() {
        super((JFrame)null, "MyTV Series Add/Edit",  true);
        title="";
        description="";
        cast="";
        under18=false;
        genre="";
        relative="";
        ratings=new HashMap<>();
        seasons=new ArrayList<>();
        createForm();
    }


    /**
     * Constructs a new SeriesAddEditDelete dialog for editing an existing TV series.
     *
     * @param series The TV series to be edited.
     */
    public SeriesAddEditDelete(Series series){
        super((JFrame)null, "Add/Edit Series",  true);
        this.series = series;
        // Initialize fields with existing series data
        this.title=series.getTitle();
        this.description=series.getDescription();
        this.cast = series.getCast();
        this.under18=series.getUnder18();
        this.genre=series.getGenre();
        this.relative=series.getRelative();
        this.ratings=series.getRatings();
        this.seasons=series.getSeasons();


        createForm();
    }

    /**
     * Creates and initializes the components of the SeriesAddEditDelete dialog.
     */
    public void createForm(){

         // Set up frame properties
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Labels and text fields for various properties of the TV series
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

        JLabel relativeLabel = new JLabel("Relative Series:");
        relativeField = new JTextArea();
        updateRelatives();

        JScrollPane relativeScroll = new JScrollPane (relativeField);

        JLabel seasonsLabel = new JLabel("Seasons:");

        JButton seasonsaddButton = new JButton("+");

        JButton relButton = new JButton("+");

        JButton saveButton = new JButton("Save");

        JButton deleteButton = new JButton("Delete");
        if (title.isEmpty()) deleteButton.setEnabled(false);

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
        // table.setEnabled(false);
        seasonsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        seasonsTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
        seasonsTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
        seasonsTable.getColumnModel().getColumn(2).setCellRenderer(renderer);

        updateSeasonsTable();

        JScrollPane seasonsScroll = new JScrollPane (seasonsTable);

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
        relButton.setBounds(550,250,50,20);
        seasonsLabel.setBounds(xlbl,360,150,20);
        seasonsScroll.setBounds(xtxt,360,350,100);
        seasonsaddButton.setBounds(550,360,50,20);
        saveButton.setBounds(200,480,80,30);
        deleteButton.setBounds(467,480,80,30);

       //Set action listeners for the buttons of the form
        relButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password

                RelativeAddEdit relObj = new RelativeAddEdit(titleField.getText().trim(),
                        relativeField.getText().trim(),"series");
                relative = relObj.getRelative();
                //System.out.println(relative);
                updateRelatives();
                //System.out.println("OK");
            }
        });


        seasonsaddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password
                new SeasonAddEditDelete(titleField.getText().trim(),seasons);
                updateSeasonsTable();
               // System.out.println("OK");
            }
        });

        seasonsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Check for double-click
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();

                    Season season;

                    String SeasonNoStr = (String)target.getValueAt(row, 0);
                    Integer seasonNo = Integer.parseInt(SeasonNoStr.substring(7,SeasonNoStr.length()));
                    new SeasonAddEditDelete(titleField.getText().trim(),seasons,seasonNo);
                    updateSeasonsTable();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title=titleField.getText().trim();
                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please provide at least the Series title", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                description=descriptionField.getText().trim();
                cast = castField.getText().trim();
                under18=under18Field.isSelected();
                genre=genreField.getText().trim();

                Database.media.addMedia(new Series(title, "series", cast, under18,
                            genre, description, relative, ratings, seasons));
                dispose();


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer =  JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the series?",
                        "Attention",JOptionPane.YES_NO_OPTION);
                if (answer==0){
                    Database.media.deleteMedia(title);
                    System.out.println("Series DELETED");
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
        add(under18Label);
        add(under18Field);
        add(genreLabel);
        add(genreField);
        add(relativeLabel);
        add(relativeScroll);
        add(relButton);
        add(seasonsLabel);
        add(seasonsScroll);
        add(seasonsaddButton);
        add(saveButton);
        add(deleteButton);


        // Set dialog properties
        setSize(700,600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);




}
    /**
     * Updates the UI component displaying relative series.
     * If the relative field is not empty, it replaces commas with line breaks for better readability.
     * Finally, it disables the relativeField.
     */
    private void updateRelatives(){
        relativeField.setText(relative.replace(",", "\n"));
        relativeField.setEnabled(false);
    }

    /**
     * Updates the table displaying TV series seasons.
     * Clears the existing rows and populates the table with information from the 'seasons' list.
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
     * The main method for testing the SeriesAddEditDelete class.
     * Loads media data from a file, creates an instance of SeriesAddEditDelete, and displays the dialog.
     */
    public static void main(String[] args) {
        Database.media.loadFromFile();

        new SeriesAddEditDelete();
    }







}
