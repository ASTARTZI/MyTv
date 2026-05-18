package gui;

import api.Database;
import javax.swing.*;
import api.Subscriber;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Represents the sign-up frame for the MyTV application.
 * Allows Subscribers to sign up by providing their name, surname, username, and password.
 * Not used for adding Administrators
 */
public class SignUp extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField surnameField;


    /**
     * Constructs a new SignUp frame with the title, layout, labels, text fields, and an OK button for user input.
     * Initializes the action listener for the OK button to handle the sign-up process.
     */
    public SignUp(){

        // Set up frame properties
        setTitle("MyTV SignUp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create and configure components
        JLabel lblprompt = new JLabel("SingUp");
        lblprompt.setFont(new Font("Arial",Font.BOLD,20));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel surnameLabel = new JLabel("Surname:");
        surnameField = new JTextField();

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton okButton = new JButton("OK");


        // Set positions for labels, text fields, and button
        int xlbl=40;
        int xtxt=110;

        lblprompt.setBounds(10,5,200,40);
        nameLabel.setBounds(xlbl,60,100,20);
        nameField.setBounds(xtxt,60,150,20);
        surnameLabel.setBounds(xlbl,90,100,20);
        surnameField.setBounds(xtxt,90,150,20);
        usernameLabel.setBounds(xlbl,120,100,20);
        usernameField.setBounds(xtxt,120,150,20);
        passwordLabel.setBounds(xlbl,150,100,20);
        passwordField.setBounds(xtxt,150,150,20);
        okButton.setBounds(120,200,100,20);


        /**
         * Handles the sign-up process when the user clicks the OK button.
         * Validates user input, creates a new subscriber, and navigates to the main subscriber's form upon successful sign-up.
         */
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user input
                String username = usernameField.getText();
                String name = nameField.getText();
                String surname = surnameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                // Validate input fields
                if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUp.this,
                            "Please fill in all the fields.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else
                if (Database.users.getUser(username)==null) {
                    // Create a new subscriber and add it to the database
                    Subscriber s = new Subscriber(name,surname,username,password);
                    Database.users.addUser(s);

                    // Successful sign-up
                    JOptionPane.showMessageDialog(SignUp.this,
                            "Successful sign up!",
                            "",
                            JOptionPane.INFORMATION_MESSAGE);
                    Database.current_user=Database.users.getUser(username);
                    new MainSubAdm();
                    dispose();// Close the current frame
                } else {
                    // Wrong username or password, show an error message
                    JOptionPane.showMessageDialog(SignUp.this,
                            "The username already exits.Try something else",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add components to the frame
        add(lblprompt);
        add(nameLabel);
        add(nameField);
        add(usernameLabel);
        add(usernameField);
        add(surnameLabel);
        add(surnameField);
        add(passwordLabel);
        add(passwordField);
        add(okButton);


        // Configure frame properties
        setSize(340,300);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

    }
    /**
     * The main method to launch the application and test the SignUp class.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        // Create an instance of SignUp and display the GUI
        new SignUp();
    }
}
