package gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.Font.BOLD;
/**
 * A dialog for adding, editing, or deleting TV series seasons.
 */
public class SeasonAddEditDelete extends JDialog {
    // Fields to store information about the season and TV series
    private String seriesTitle;
    private ArrayList<Season> seasons;
    private Season season;
    private Integer seasonNo;
    boolean forAddition;

    // Fields for GUI components
    private JTextField titleField;
    private JTextField yearField;
    private JTextField episodesField;
    JButton deleteButton;


    /**
     * Constructor for SeasonAddEditDelete used for adding a new season.
     *
     * @param seriesTitle Title of the series.
     * @param seasons     List of seasons for the series.
     */
    public SeasonAddEditDelete(String seriesTitle,ArrayList<Season>seasons){  //For addition
        super((JFrame)null, "MyTV Season Add",  true);

        this.forAddition = true;

        if (seriesTitle.isEmpty())
            this.seriesTitle = "<Unnamed>";
        else
            this.seriesTitle = seriesTitle;
        this.seasons = seasons;
        this.seasonNo=0;
        this.season=new Season(seasons.size()+1,null,"");
        createForm();
    }

    /**
     * Constructor for SeasonAddEditDelete used for updating an existing season.
     *
     * @param seriesTitle Title of the TV series.
     * @param seasons     List of seasons for the TV series.
     * @param seasonNo    Number of the specific season to edit.
     */
    public SeasonAddEditDelete(String seriesTitle,ArrayList<Season>seasons,Integer seasonNo){ //For Update
        super((JFrame)null, "MyTV Season Edit",  true);
        forAddition=false;
        this.seasons =seasons;
        if (seriesTitle.isEmpty())
            this.seriesTitle = "<Unnamed>";
        else
            this.seriesTitle = seriesTitle;
        this.seasonNo=seasonNo;
        this.season=seasons.get(seasonNo-1);

        createForm();
    }

    /**
     * Creates the UI components for the SeasonAddEditDelete dialog.
     */
    public void createForm(){

        // Set dialog properties
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Labels and text fields for various season details
        JLabel seriesTitleLabel = new JLabel("Series: " + seriesTitle);
        seriesTitleLabel.setFont(new Font("Arial",BOLD,20));
        JLabel titleLabel = new JLabel("Season's Title:");
        titleField = new JTextField("Season "+season.getSeasonNumber().toString());
        titleField.setEditable(false);

        JLabel yearLabel = new JLabel("Season's Year:");
        if (season.getReleaseYear()==null)
            yearField = new JTextField();
        else
            yearField = new JTextField(season.getReleaseYear().toString());

        JLabel episodesLabel = new JLabel("Season's Episodes:");
        JLabel episodesHelpLabel = new JLabel("(Please provide a comma separated list of the durations of each Episode)");

        JButton saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");

        // Set positions for labels, fields, and buttons
        int xlbl=30;
        int xtxt=170;
        seriesTitleLabel.setBounds(xlbl,50,300,30);

        titleLabel.setBounds(xlbl,100,100,20);
        titleField.setBounds(xtxt,100,100,20);

        yearLabel.setBounds(xlbl,130,100,20);
        yearField.setBounds(xtxt,130,100,20);
        episodesLabel.setBounds(xlbl,160,300,20);
        episodesField = new JTextField(season.getEpisodes().toString());
        episodesField.setBounds(xtxt,160,300,20);
        episodesHelpLabel.setBounds(xlbl,190,500,20);

       if (forAddition) {
         saveButton.setBounds(210, 230, 100, 20);
       } else {
         saveButton.setBounds(150, 230, 100, 20);
       }
        deleteButton.setBounds(270,230,100,20);


        //Set action listeners for the buttons of the form
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //new MovieAddEditDelete(movie);
                Integer year;
                if (yearField.getText().trim().isEmpty() ||
                    episodesField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Please fill in all fields.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    // Attempt to parse the text as an integer
                    year = Integer.parseInt(yearField.getText().trim());
                } catch (NumberFormatException ex) {
                    // Handle the case where the input is not a valid integer
                    JOptionPane.showMessageDialog(null,
                            "Season's Year should be an Integer value!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //System.out.println("year"+year);
                String episodes = episodesField.getText().trim();
                season.setReleaseYear(year);
                season.setEpisodes(episodes);
                if (forAddition) {
                    seasons.add(season);


                }

                dispose();

            }
        });



        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (seasonNo==seasons.size()) {
                    int answer =  JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the Season?",
                            "Attention",JOptionPane.YES_NO_OPTION);
                    if (answer==0) {
                        seasons.remove(seasonNo - 1);
                        dispose();
                    }

                }
                else {
                    JOptionPane.showMessageDialog(null,"You can delete only the last Season.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    deleteButton.setEnabled(false);

                }
            }
        });

        // Adding components to the dialog
        add(seriesTitleLabel);
        add(titleLabel);
        add(titleField);
        add(yearLabel);
        add(yearField);
        add(episodesLabel);
        add(episodesHelpLabel);
        add(episodesField);
        add(saveButton);
        if (!forAddition) {
            add(deleteButton);
        }


        // Set dialog properties
        setSize(500,350);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * The main method for testing the SeasonAddEditDelete class.
     */
    public static void main(String[] args) {

    }

}


