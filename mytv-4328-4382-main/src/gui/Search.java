package gui;

import api.Database;
import api.MediaContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * A dialog for searching media content in the MyTV application.
 */
public class Search extends JDialog {
    private HashMap<String,MediaContent> search_results;
    private JTextField titleField;
    private JComboBox typeFieldcmb;
    private JTextField actorField;
    private JComboBox under18Field;
    private JComboBox genreFieldcmb;
    private JComboBox ratingFieldcmb;



    /**
     * Constructor for the Search dialog.
     */
    public Search() {
        super((JFrame)null, "MyTV Search Form",  true);

        search_results= new HashMap<>();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        genreFieldcmb = new JComboBox();
        Database.media.loadFromFile();

        genreFieldcmb.addItem("All");

        for(String x: Database.media.getAllgenre() ){
            genreFieldcmb.addItem(x);

        }


        // Labels and text fields for various season details
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField();
        JLabel typeLabel = new JLabel("Type:");
        typeFieldcmb = new JComboBox(new String[] {"All","Movie","Series"});
        JLabel actorLabel = new JLabel("Actor:");
        actorField = new JTextField("",20);
        JLabel under18Label = new JLabel("Under18:");
        under18Field = new JComboBox(new String[] {"All","Yes","No"});
        JLabel genreLabel = new JLabel("Genre:");
        JLabel ratingLabel = new JLabel("Min Avg Rating:");
        ratingFieldcmb = new JComboBox(new String[] {"All","1","2","3","4","5"});
        JLabel lblprompt = new JLabel("Search");
        lblprompt.setFont(new Font("Arial", Font.BOLD,20));

        // Set positions for labels, fields, and buttons
        int xlbl=80;
        int xtxt=200;
        lblprompt.setBounds(80,10,100,20);
        titleLabel.setBounds(xlbl,50,60,20);
        titleField.setBounds(xtxt,50,150,20);
        typeLabel.setBounds(xlbl,80,60,20);
        typeFieldcmb.setBounds(xtxt,80,100,20);
        actorLabel.setBounds(xlbl,110,60,20);
        actorField.setBounds(xtxt,110,150,20);
        under18Label.setBounds(xlbl,140,60,20);
        under18Field.setBounds(xtxt,140,100,20);
        genreLabel.setBounds(xlbl,170,60,20);
        genreFieldcmb.setBounds(xtxt,170,100,20);
        ratingLabel.setBounds(xlbl,200,100,20);
        ratingFieldcmb.setBounds(xtxt,200,40,20);
        JButton goButton = new JButton("GO");
        goButton.setBounds(200,250,70,20);




        //Set action listeners for the buttons of the form
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password
                go_search();

            }
        });

        // Adding components to the dialog
        add(lblprompt);
        add(titleLabel);
        add(titleField);
        add(typeLabel);
        add(typeFieldcmb);
        add(actorLabel);
        add(actorField);
        add(under18Label);
        add(under18Field);
        add(genreLabel);
        add(genreFieldcmb);
        add(ratingLabel);
        add(ratingFieldcmb);
        add(goButton);


        // Set dialog properties
        setSize(450,350);
        setLocationRelativeTo(null);
        setVisible(true);

    }


    /**
     * Gets the search results.
     *
     * @return A HashMap containing the search results.
     */
    public HashMap<String, MediaContent> getSearchResults() {
        return search_results;
    }

    /**
     * Performs the search operation based on user input and closes the search dialog.
     */
    public void go_search(){
        String title = titleField.getText().trim();
        String actor = actorField.getText().trim();

        String type = (String) typeFieldcmb.getSelectedItem();
        if (type=="All") type="";

        String under18 = (String) under18Field.getSelectedItem();

        String genre = (String) genreFieldcmb.getSelectedItem();
        if (genre=="All") genre="";

        String rating = (String) ratingFieldcmb.getSelectedItem();

        search_results = Database.media.search(title,type,genre,actor,under18,rating);
        dispose();

    }
    /**
     * The main method for testing the Search class.
     *
     * @param args Command-line arguments.
     */
     public static void main(String[] args) {
        new Search();
    }

}
