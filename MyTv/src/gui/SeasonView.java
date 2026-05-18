package gui;

import api.Season;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.Font.BOLD;

/**
 * A dialog for viewing details of a TV series season.
 */
public class SeasonView extends JDialog {

    // Fields to store information about the season and TV series
    private String seriesTitle;
    private ArrayList<Season> seasons;
    private Season season;
    private Integer seasonNo;

    // Fields for GUI components
    private JTextField titleField;
    private JTextField yearField;
    private JTextField episodesField;
    boolean forAddition;
    JButton deleteButton;

    /**
     * Constructor for SeasonView used for updating season details.
     *
     * @param seriesTitle Title of the TV series.
     * @param seasons     List of seasons for the TV series.
     * @param seasonNo    Number of the specific season to view.
     */
    public SeasonView(String seriesTitle, ArrayList<Season>seasons, Integer seasonNo){ //For Update
        super((JFrame)null, "MyTV Season View",  true);
        forAddition=false;
        this.seasons =seasons;
        this.seriesTitle = seriesTitle;
        this.seasonNo=seasonNo;
        this.season=seasons.get(seasonNo-1);

        createForm();
    }

    /**
     * Creates the GUI components for the SeasonView dialog.
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
        yearField.setEditable(false);

        JLabel episodesLabel = new JLabel("Season's Episodes:");

        DefaultTableModel model = new DefaultTableModel();

        JTable episodesTable;
        //create table with data
        model.addColumn("No");
        model.addColumn("Duration (min)");
        episodesTable = new JTable(model);

        episodesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane episodesScroll = new JScrollPane(episodesTable);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        episodesTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
        episodesTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
        Integer N=0;
        for (String dur: season.getEpisodes().split(",")){
            N++;
            model.addRow(new Object[]{"Episode "+ N.toString(),dur});
        }
        JButton closeButton = new JButton("Close");

        // Set positions for labels, fields, and buttons
        int xlbl=30;
        int xtxt=170;
        seriesTitleLabel.setBounds(xlbl,50,300,30);
        titleLabel.setBounds(xlbl,100,100,20);
        titleField.setBounds(xtxt,100,100,20);
        yearLabel.setBounds(xlbl,130,100,20);
        yearField.setBounds(xtxt,130,100,20);
        episodesLabel.setBounds(xlbl,160,300,20);
        episodesScroll.setBounds(xtxt,160,300,200);
        closeButton.setBounds(220,380,80,20);

        //Set action listeners for the buttons of the form
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

        // Adding components to the dialog
        add(seriesTitleLabel);
        add(titleLabel);
        add(titleField);
        add(yearLabel);
        add(yearField);
        add(episodesLabel);
        add(episodesScroll);
        add(closeButton);

        // Set dialog properties
        setSize(500,460);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }


    /**
     * The main method for testing the SeasonView class.
     */

    public static void main(String[] args) {

    }

}


