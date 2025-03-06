import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage {
    private JFrame frame = new JFrame();
    private JLabel welcomeLabel;

    // Buttons for user options
    private JButton addMetricsButton = new JButton("Add Metrics");
    private JButton displayMetricsButton = new JButton("Display Metrics");
    private JButton displayHealthReportButton = new JButton("Display Health Report");
    private JButton exitButton = new JButton("Exit");

    // Constructor to accept the username and display it
    public WelcomePage(String username) {
        // Set up main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // padding for spacing between components

        // Set up welcome label with the username
        welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Center the label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(welcomeLabel, gbc);

        // Set up the buttons with larger font for easy reading
        addMetricsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        displayMetricsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        displayHealthReportButton.setFont(new Font("Arial", Font.PLAIN, 16));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add action listeners to the buttons
        addMetricsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Add Metrics functionality is yet to be implemented.");
            }
        });

        displayMetricsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Display Metrics functionality is yet to be implemented.");
            }
        });

        displayHealthReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Display Health Report functionality is yet to be implemented.");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application when "Exit" button is pressed
                System.exit(0);
            }
        });

        // Layout for buttons: Center them
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);  // Larger padding for buttons
        frame.add(addMetricsButton, gbc);

        gbc.gridx = 1;
        frame.add(displayMetricsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(displayHealthReportButton, gbc);

        gbc.gridx = 1;
        frame.add(exitButton, gbc);

        // Show frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Example to show WelcomePage with a sample username
        new WelcomePage("ilgert");  // Change "ilgert" to whatever username you want to test
    }
}
