package gui;

import api.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A modal dialog for adding or editing relative movies or series.
 */
public class RelativeAddEdit extends JDialog {

    // Fields to store information about the season and series
    String relative;
    String media;
    String type;

    // Fields for GUI components
    private JTextField titleField;
    private JTextArea descriptionField;
    private JTextField yearField;
    private JCheckBox under18Field;
    private JTextField genreField;
    private JTextField durationField;
    private JTextField cast;
    private JList relativeField;

    /**
     * Constructs a RelativeAddEdit dialog for adding or editing relatives.
     *
     * @param media The media title.
     * @param type  The type of media (movie or series).
     */
    public RelativeAddEdit(String media, String type) {
        super((JFrame)null, "Modal Dialog",  true);
        this.media = media;
        this.type = type;
        relative="";
        createForm();
    }

    /**
     * Constructs a RelativeAddEdit dialog for editing relatives.
     *
     * @param media     The media title.
     * @param relative  The current relatives of the media.
     * @param type      The type of media (movie or series).
     */
    public RelativeAddEdit(String media, String relative, String type){
        super((JFrame)null, "Modal Dialog",  true);
        this.media = media;
        this.type = type;
        this.relative=relative;
        createForm();
    }

    /**
     * Creates the GUI components for the RelativeAddEdit dialog.
     */
    public void createForm(){

        // Set dialog properties
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Labels and text fields for various season details
        JLabel titleLabel = new JLabel("Choose Relative Movies-Series (Use Ctrl for multi-selection):");
        JButton okButton = new JButton("OK");
        DefaultListModel model = new DefaultListModel();
        relativeField = new JList(model);
        relativeField.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane relativeScroll = new JScrollPane (relativeField);
        // Initialize the list with items
        int i=0;
        ArrayList<String> keys = Database.media.getSortedKeys(type);
        ArrayList<Integer> selectedIndices = new ArrayList<>();

        for (String key : keys){
            if (key.equals(media)) continue;
            if (!relative.isEmpty() && relative.contains(key)) selectedIndices.add(i);

            model.add(i,key  );
            i++;

        }
        relativeField.setSelectedIndices(selectedIndices.stream()
                .mapToInt(Integer::intValue)
                .toArray());


        // Set positions for labels, fields, and buttons
        titleLabel.setBounds(50,10,450,20);
        relativeScroll.setBounds(50,40,550,400);
        okButton.setBounds(300,460,70,20);

        //Set action listeners for the buttons of the form
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<String> selectedItems = relativeField.getSelectedValuesList();
                relative = String.join(",", selectedItems);
              //  System.out.println("relative"+relative);
                dispose();
            }

        });

        // Adding components to the dialog
        add(titleLabel);
        add(relativeScroll);
        add(okButton);


        // Set dialog properties
        setSize(700,550);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


}

    /**
     * Gets the selected relatives.
     *
     * @return A comma-separated string of selected relatives.
     */
    public String getRelative() {
        return relative;
    }

    /**
     * The main method for testing the RelativeAddEdit class.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Database.media.loadFromFile();
        new RelativeAddEdit("Barbie","movie");
    }

}
