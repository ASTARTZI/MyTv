package gui;

import api.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the sign-in frame for the MyTV application.
 * Allows users to sign in by providing their username and password.
 */
public class SignIn extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Constructs a new SignIn frame with the title, layout, labels, text fields, and an OK button for user input.
     * Initializes the action listener for the OK button to handle the sign-in process.
     */
    public SignIn() {
        // Set up frame properties
        setTitle("MyTV Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create and configure components
        JLabel lblprompt = new JLabel("SingIn");
        lblprompt.setFont(new Font("Arial",Font.BOLD,20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton okButton = new JButton("OK");


        // Set positions for labels, text fields, and button
        int xlbl=40;
        int xtxt=110;

        lblprompt.setBounds(10,5,200,40);
        usernameLabel.setBounds(xlbl,60,100,20);
        usernameField.setBounds(xtxt,60,150,20);
        passwordLabel.setBounds(xlbl,90,100,20);
        passwordField.setBounds(xtxt,90,150,20);
        okButton.setBounds(100,150,100,20);



        /**
         * Handles the sign-in process when the user clicks the OK button.
         * Validates user input, checks the username and password against the database, and navigates to the main form upon successful sign-in.
         */
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check username and password
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                Database.users.loadFromFile();
                // Replace the condition with your authentication logic
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignIn.this,
                            "Please fill in all the fields.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else
                if (Database.users.getPassword(username).equals(password)) {
                    // Successful login
                    Database.current_user=Database.users.getUser(username);
                    new MainSubAdm();
                    dispose();// Close the current frame
                } else {
                    // Wrong username or password, show an error message
                    JOptionPane.showMessageDialog(SignIn.this,
                            "Wrong username or password",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Add components to the frame
        add(lblprompt);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(okButton);


        // Set frame properties
        setSize(300,250);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }


    /**
     * The main method to launch the application and test the SignIn class.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        // Create an instance of the SignIn class
       new SignIn();

    }
}
