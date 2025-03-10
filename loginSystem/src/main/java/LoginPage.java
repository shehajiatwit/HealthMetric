import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * Author: Ilgert Shehaj
 * LoginPage, where user inputs for username and password are checked in the hashmap
 * A method chaining is used for the implementation for simplicity
 * BorderFactory && GridBagConstrains is used for a dynamic layout
 *
 */

public class LoginPage implements ActionListener {
    private JFrame frame;
    private JButton loginButton, resetButton;
    private JTextField userIDField;
    private JPasswordField userPasswordField;
    private JLabel userIDLabel, userPasswordLabel, messageLabel;
    private HashMap<String, String> loginInfo;

    public LoginPage(HashMap<String, String> loginInfoOriginal) {
        loginInfo = loginInfoOriginal;
        initializeFrame();
        createUIComponents();
        setupLayout();
        frame.setVisible(true);
    }

    private void initializeFrame() {
        frame = new JFrame("Login System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // Labels
        userIDLabel = new JLabel("Username:");
        userPasswordLabel = new JLabel("Password:");

        // Text Fields
        userIDField = new JTextField(15);
        userPasswordField = new JPasswordField(15);

        // Buttons
        loginButton = createStyledButton("Login", new Color(76, 175, 80));
        resetButton = createStyledButton("Reset", new Color(33, 150, 243));

        // Message Label
        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.setOpaque(true);  // Crucial for custom backgrounds
        button.setBorderPainted(false); // Remove default border
        button.addActionListener(this);

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add components to form panel
        addFormComponent(formPanel, gbc, userIDLabel, userIDField, 0);
        addFormComponent(formPanel, gbc, userPasswordLabel, userPasswordField, 1);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(loginButton);
        buttonPanel.add(resetButton);

        // Assemble main panel
        mainPanel.add(messageLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
    }

    private void addFormComponent(JPanel panel, GridBagConstraints gbc,
                                  JLabel label, JComponent field, int yPos) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            userIDField.setText("");
            userPasswordField.setText("");
            messageLabel.setText("");
        } else if (e.getSource() == loginButton) {
            performLogin();
        }
    }
//if user is found or not
    private void performLogin() {
        String userID = userIDField.getText();
        String password = new String(userPasswordField.getPassword());

        if (loginInfo.containsKey(userID)) {
            if (loginInfo.get(userID).equals(password)) {
                showMessage("Login Successful", Color.GREEN.darker());
                frame.dispose();
                new WelcomePage(userID);
            } else {
                showMessage("Invalid Password", Color.RED);
            }
        } else {
            showMessage("User Not Found", Color.RED);
        }
    }

    private void showMessage(String text, Color color) {
        messageLabel.setForeground(color);
        messageLabel.setText(text);
    }

    public static void main(String[] args) {
       //not necessary
        SwingUtilities.invokeLater(() -> {
            // Remove the system look and feel line
            IDandPasswords idAndPasswords = new IDandPasswords();
            new LoginPage(idAndPasswords.getLoginInfo());
        });
    }
}