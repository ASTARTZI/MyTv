package gui;

import api.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
/**
 * Represents the main graphical user interface for both subscribers and administrators.
 * Displays user information, media content lists, and provides various actions and filters.
 */
public class MainSubAdm extends JFrame {
    private HashMap<String,MediaContent> mediamaplist;
    private String usertype;
   private DefaultTableModel model;
   private JTable table;

    /**
     * Constructs a new MainSubAdm frame, initializing user type and creating the main form.
     */
    public MainSubAdm(){

        this.mediamaplist = Database.media.getMedialist();
        if (Database.current_user instanceof Admin) {
            usertype = "adm";
        }
        else {
            usertype = "sub";
        }
        createForm();
    }

    /**
     * Creates the main graphical user interface form, including user information, media content lists,
     * toolbars, and buttons for various actions.
     */
    public void createForm(){
        // Set dialog properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);


        // Labels and text fields for various season details
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(null);

        userInfoPanel.setBackground(Color.lightGray);
        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(null);
        JPanel addBarPanel = new JPanel();
        addBarPanel.setLayout(null);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(null);
        JLabel userNameLabel = new JLabel();
        userNameLabel.setFont(new Font("Arial", Font.BOLD,20));

        JButton logoutButton = new JButton("LogOut");


        userInfoPanel.add(userNameLabel);
        userInfoPanel.add(logoutButton);
        model = new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        //create table with data
        model.addColumn("Title");
        model.addColumn("Type");
        model.addColumn("Genre");
        model.addColumn("Cast");
        table = new JTable(model);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setPreferredWidth(20);
        table.getColumnModel().getColumn(3).setPreferredWidth(220);

        update_table_contents();

        JScrollPane tableScroll = new JScrollPane (table);

        tablePanel.add(tableScroll);

        JLabel tabletitle = new JLabel("Media List");
        tabletitle.setFont(new Font("Italic", Font.BOLD,16));
        JLabel displayLabel = new JLabel("Currently Displaying: All Media");
        displayLabel.setFont(new Font("Italic", Font.BOLD,12));
        JLabel title = new JLabel("Filters");
        title.setFont(new Font("Italic", Font.BOLD,16));
        JButton allButton = new JButton("All media");
        JButton moviesButton = new JButton("Movies");
        JButton seriesButton = new JButton("Series");
        JButton favoritesButton = new JButton("Favorites");
        JButton searchButton = new JButton("Search...");
        JButton addmovieButton = new JButton("Add Movie");
        JButton addseriesButton = new JButton("Add Series");

        // Set positions for labels, fields, and buttons
        userInfoPanel.setBounds(10,10,770,40);
        toolBarPanel.setBounds(10,90,150,300);
        addBarPanel.setBounds(10,400,150,100);
        userNameLabel.setBounds(3,5,300,30);
        logoutButton.setBounds(680,10,80,20);
        tableScroll.setBounds(10,40,600,400);
        tablePanel.setBounds(170,50,700,440);
        tabletitle.setBounds(10,0,100,40);
        displayLabel.setBounds(120,1,300,40);
        title.setBounds(50,0,100,40);
        allButton.setBounds(10,50,130,40);
        moviesButton.setBounds(10,100,130,40);
        seriesButton.setBounds(10,150,130,40);
        favoritesButton.setBounds(10,200,130,40);
        searchButton.setBounds(10,250,130,40);
        addmovieButton.setBounds(10,0,130,40);
        addseriesButton.setBounds(10,50,130,40);


        //Set action listeners for the buttons of the form
        logoutButton.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       dispose();
                     new SignUpSignIn();
                 }
              });

        moviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password

                mediamaplist = Database.media.filterHashMap("movie");
                update_table_contents();
                displayLabel.setText("Currently Displaying: Only Movies");
            }
        });


        seriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password

                mediamaplist = Database.media.filterHashMap("series");
                update_table_contents();
                displayLabel.setText("Currently Displaying: Only Series");
            }
        });
        allButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password

                mediamaplist = Database.media.getMedialist();
                update_table_contents();
                displayLabel.setText("Currently Displaying: All Media");
            }
        });

        favoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password

                mediamaplist = ((Subscriber) Database.current_user).getFavorites();
                update_table_contents();
                displayLabel.setText("Currently Displaying: Favorites");
            }
        });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password
                Search searchObj = new Search();
                mediamaplist = searchObj.getSearchResults();
                update_table_contents();
                displayLabel.setText("Currently Displaying: Search Results");
            }
        });

        addmovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password
               new MovieAddEditDelete();
                mediamaplist = Database.media.filterHashMap("movie");
                update_table_contents();
                displayLabel.setText("Currently Displaying: Only Movies");
            }
        });

        addseriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password
                new SeriesAddEditDelete();
                mediamaplist = Database.media.filterHashMap("series");
                update_table_contents();
                displayLabel.setText("Currently Displaying: Only Series");

            }
        });



        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Check for double-click
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    // Now 'row' contains the index of the selected row
                    String media_title = (String) target.getValueAt(row, 0);
                    //System.out.println(media_title);
                    String selected_type = (String) target.getValueAt(row, 1);
                    if (selected_type.equals("movie")) {
                        new MovieView((Movie) mediamaplist.get(media_title));
                        mediamaplist = Database.media.filterHashMap("movie");
                        update_table_contents();
                        displayLabel.setText("Currently Displaying: Only Movies");
                    }
                    else {
                        new SeriesView((Series) mediamaplist.get(media_title));
                        mediamaplist = Database.media.filterHashMap("series");
                        update_table_contents();
                        displayLabel.setText("Currently Displaying: Only Series");
                    }
                }
            }
        });

        Border border = new LineBorder(Color.LIGHT_GRAY, 2);


        tablePanel.add(tabletitle);
        tablePanel.add(displayLabel);
        toolBarPanel.add(title);
        toolBarPanel.setBorder(border);
        toolBarPanel.add(allButton);
        toolBarPanel.add(moviesButton);
        toolBarPanel.add(seriesButton);
        toolBarPanel.add(searchButton);

        // Adding components to the dialog
        addBarPanel.add(addmovieButton);
        addBarPanel.add(addseriesButton);

        if (usertype.equals("sub")){
            setTitle("MyTV Main Subscriber's Form");
            userNameLabel.setText("User: " + Database.current_user.getName()+" "+
                    Database.current_user.getLastName());
            toolBarPanel.add(favoritesButton);
        }
        else {
            setTitle("MyTV Main Administrator's Form");
            searchButton.setBounds(10,200,130,40);
            userNameLabel.setText("Administrator");
            add(addBarPanel);

        }

        add(userInfoPanel);
        add(toolBarPanel);
        add(tablePanel);


        // Set dialog properties
        setSize(820,550);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


    }
    /**
     * Updates the contents of the media content table based on the current media map list.
     */
public void update_table_contents(){
        model.setRowCount(0);
        for (MediaContent m: mediamaplist.values()){
            model.addRow(new Object[]{m.getTitle(),m.getType(),m.getGenre(), m.getCast()});
        }
        table.repaint();
}
    /**
     * The main method to launch the application and test the MainSubAdm class.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        Database.media.loadFromFile();
        Database.users.loadFromFile();

        Database.current_user = Database.users.getUser("admin1");

        new MainSubAdm();
    }

}
