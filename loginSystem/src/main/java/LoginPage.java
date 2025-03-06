import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginPage implements ActionListener {
    // frame and UI components
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("User ID:");
    JLabel userPasswordLabel = new JLabel("Password:");
    JLabel messageLabel = new JLabel();

    public static HashMap<String, String> loginInfo = new HashMap<String, String>();

    LoginPage(HashMap<String, String> loginInfoOriginal) {
        loginInfo = loginInfoOriginal;

        // Set layout to GridBagLayout for responsiveness
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // space between components

        // User ID Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(userIDLabel, gbc);

        // User ID Field
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(userIDField, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(userPasswordLabel, gbc);

        // Password Field
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(userPasswordField, gbc);

        // Message Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        frame.add(messageLabel, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        frame.add(loginButton, gbc);

        // Reset Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(resetButton, gbc);

        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        // Frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setVisible(true);
    }

    public static String returnUserID() {
        return loginInfo.keySet().toArray()[0].toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            userIDField.setText("");
            userPasswordField.setText("");
        }

        if (e.getSource() == loginButton) {
            String userID = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            if (loginInfo.containsKey(userID)) {
                if (loginInfo.get(userID).equals(password)) {
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setText("Login Successful");
                    frame.dispose();
                    WelcomePage welcomePage = new WelcomePage();
                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Wrong password");
                }
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("User not found");
            }
        }
    }

    public static void main(String[] args) {
        // Sample data for loginInfo (this could be loaded from a file or database)
        HashMap<String, String> loginData = new HashMap<>();
        loginData.put("user1", "password1");
        loginData.put("user2", "password2");

        // Start the LoginPage
        new LoginPage(loginData);
    }
}
