package gui;

import api.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
/**
 * A dialog for viewing, adding, and editing ratings and reviews for a media content.
 */
public class RatingsView extends JDialog {
    private MediaContent media;
    private String title;
    private Integer myrating;
    private String myreview;
    private String current_user;
    private HashMap<String, Rating> ratings;
    // private JTable ratingsTable;

   private DefaultTableModel model;
   JTable table;

    private JComboBox myratingField;
    private JTextArea myreviewField;
    /**
     * Constructs a RatingsView dialog for the given media content.
     *
     * @param media The media content to display ratings and reviews for.
     */
    public RatingsView(MediaContent media){
        super((JFrame)null, "MyTV Ratings-Reviews",  true);
        this.media = media;
        this.ratings = media.getRatings();
        this.current_user = Database.current_user.getUsername();
       // System.out.println(current_user);
        createForm();
    }

    /**
     * Creates the GUI components for the RatingsView dialog.
     */
    public void createForm(){

       // Set dialog properties
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(null);


        // Labels and text fields for various season details
        JLabel titleLabel = new JLabel("Title: " + media.getTitle());

        Rating r;

        model = new DefaultTableModel();
        //create table with data
        model.addColumn("User");
        model.addColumn("Date");
        model.addColumn("Rating");
        model.addColumn("Review");
        table = new JTable(model);


        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setVerticalAlignment(JLabel.TOP);
        renderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(renderer);
        table.getColumnModel().getColumn(1).setCellRenderer(renderer);
        table.getColumnModel().getColumn(2).setCellRenderer(renderer);
        table.getColumnModel().getColumn(3).setCellRenderer(renderer);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setPreferredWidth(20);
        table.getColumnModel().getColumn(3).setPreferredWidth(220);


        update_table_contents();

        JScrollPane ratingsScroll = new JScrollPane (table);
        ratingsScroll.setBorder(null);
        titleLabel.setFont(new Font("Arial", Font.BOLD,20));

        JButton closeButton = new JButton("Close");


        JPanel myratingPanel = new JPanel();
        myratingPanel.setLayout(null);

        myratingPanel.setBackground(Color.lightGray);

        JButton saveButton = new JButton("Save");

        JButton deleteButton = new JButton("Delete");


        JLabel myratingLabel = new JLabel("My Rating:");
        myratingField = new JComboBox(new String[] {"","1","2","3","4","5"});
        if (myrating!=null) {
           myratingField.setSelectedItem(myrating.toString());}

        JLabel myreviewLabel = new JLabel("My Review:");
        myreviewField = new JTextArea(myreview);
        JScrollPane myreviewScroll = new JScrollPane (myreviewField);

        // Set positions for labels, fields, and buttons
        titleLabel.setBounds(80,20,300,20);
        ratingsScroll.setBounds(80,50,500,380);
        closeButton.setBounds(300,450,70,20);
        myratingPanel.setBounds(610,50,300,250);
        saveButton.setBounds(80,210,70,20);
        deleteButton.setBounds(180,210,70,20);
        myratingLabel.setBounds(10,10,100,20);
        myratingField.setBounds(80,10,40,20);
        myreviewLabel.setBounds(10,40,100,20);
        myreviewField.setBounds(80,40,200,150);
        myreviewScroll.setBounds(80,40,200,150);


        myratingPanel.add(myratingLabel);
        myratingPanel.add(myratingField);
        myratingPanel.add(myreviewLabel);
        myratingPanel.add(myreviewScroll);
        myratingPanel.add(saveButton);
        myratingPanel.add(deleteButton);



        //Set action listeners for the buttons of the form
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myreviewField.getText().trim().isEmpty() ||
                   myratingField.getSelectedIndex()==0) {
                    JOptionPane.showMessageDialog(null,"Please fill in both fields.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);  // null = parent component
                    return;
                }

                String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                myreview = myreviewField.getText().trim();
                myrating = myratingField.getSelectedIndex();

                Rating newrat = new Rating(current_user,myreview,myrating,date);
                ratings.put(current_user,newrat);
                media.setRatings(ratings);
                Database.media.addMedia(media);
                update_table_contents();


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myreviewField.setText("");
                myratingField.setSelectedItem("");
                ratings.remove(current_user);
                media.setRatings(ratings);
                Database.media.addMedia(media);
                update_table_contents();


            }
        });

        // Adding components to the dialog
        add(titleLabel);
        add(ratingsScroll);
        add(closeButton);
        add(myratingPanel);

        // Set dialog properties
        setSize(1000,550);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    /**
     * Updates the contents of the ratings table.
     */
public void update_table_contents(){
        model.setRowCount(0);
        int row=0;
        for (Rating rat:ratings.values()){

            String review=rat.getReview();


            int lines = review.length() / 40 +1;
            review =  "<html>"+review.replace("\n"," ")+"</html>";
            model.addRow(new Object[]{rat.getUsername(),rat.getDate(),rat.getRank(),review});
            table.setRowHeight(row, 24*(int)lines);
            if (current_user.equals(rat.getUsername())) {
                myrating=rat.getRank();
                myreview=rat.getReview();
            }
            row++;

        }
        table.repaint();
}

    /**
     * The main method for testing the RatingsView class.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Database.media.loadFromFile();
        Database.users.loadFromFile();

        Database.current_user = Database.users.getUser("john");


    }


}
