package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Represents the main sign-up and sign-in graphical user interface for the MyTV application.
 * Allows users to navigate between sign-up and sign-in frames.
 */
public class SignUpSignIn extends JFrame {

    /**
     * Constructs a new SignUpSignIn frame with the main title, layout, panels, buttons, and developer information.
     * Initializes the sign-up and sign-in buttons with corresponding action listeners.
     */
    public SignUpSignIn() {

        // Set up the main frame
        setTitle("MyTV");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create panels for different sections
        JPanel paneltop = new JPanel();
        paneltop.setBounds(0,0,400,310);

        JPanel panelbuttons = new JPanel();
        panelbuttons.setBounds(0,310,400,40);

        JPanel paneldevelopers = new JPanel();
        paneldevelopers.setBounds(0,350,400,30);

        // Set up application logo
        Icon logo = new ImageIcon("logo.jpg");

        // Create and configure components
        JLabel lbltitle = new JLabel("");
        lbltitle.setIcon(logo);
        lbltitle.setFont(new Font("Arial", Font.BOLD,40));
        lbltitle.setHorizontalAlignment(JLabel.CENTER);

        JLabel lblwelcome = new JLabel("Welcome!");
        lblwelcome.setHorizontalAlignment(JLabel.CENTER);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFocusPainted(false);
        JButton signInButton = new JButton("Sign In");
        signInButton.setFocusPainted(false);

        JLabel developersLabel = new JLabel("Developed by: Elena Lychnaropoulou, Martha Astartzi");

        // Add components to panels
        paneltop.add(lbltitle);
        paneltop.add(lblwelcome);
        panelbuttons.add(signUpButton);
        panelbuttons.add(signInButton);
        paneldevelopers.add(developersLabel);


        // Action listener for Sign Up button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the sign-up frame
                SignUp signUpFrame = new SignUp();
                dispose(); // Close the current frame
            }
        });

        // Action listener for Sign In button
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the sign-in frame
                SignIn signInFrame = new SignIn();
                dispose(); // Close the current frame
            }
        });

        // Add panels to the main frame
        add(paneltop);
        add(panelbuttons);
        add(paneldevelopers);

        // Configure main frame properties
        setSize(410,450);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * The main method to launch the application and test the SignUpSignIn class.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {

        // Create an instance of SignUpSignIn and display the GUI
        SignUpSignIn frame = new SignUpSignIn();
    }
}

