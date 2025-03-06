import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage {
    private JFrame frame;
    private JLabel welcomeLabel;
    private JPanel mainPanel, buttonPanel;
    private JButton addMetricsButton, displayMetricsButton, displayHealthReportButton, exitButton;

    private final Color BG_COLOR = new Color(255, 255, 255);
    private final Color PRIMARY_COLOR = new Color(70, 130, 180);  // Steel blue
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 16);

    public WelcomePage(String username) {
        initializeFrame();
        createComponents(username);
        setupLayout();
        setupActions();
        frame.setVisible(true);
    }

    private void initializeFrame() {
        frame = new JFrame("Health Metrics Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setLocationRelativeTo(null);
    }

    private void createComponents(String username) {
        welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(60, 60, 60));

        // Create all buttons with the same color scheme
        addMetricsButton = createStyledButton("Add Metrics");
        displayMetricsButton = createStyledButton("Display Metrics");
        displayHealthReportButton = createStyledButton("Generate Report");
        exitButton = createStyledButton("Exit System");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);

        // Add subtle hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void setupLayout() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(BG_COLOR);

        JPanel headerPanel = new JPanel();
        headerPanel.add(welcomeLabel);
        headerPanel.setBackground(BG_COLOR);

        buttonPanel = new JPanel(new GridLayout(4, 1, 10, 15)); // Vertical stack
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.add(addMetricsButton);
        buttonPanel.add(displayMetricsButton);
        buttonPanel.add(displayHealthReportButton);
        buttonPanel.add(exitButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.pack();
    }

    private void setupActions() {

        addMetricsButton.addActionListener(e ->
                new InputMetrics());

        displayMetricsButton.addActionListener(e ->
                showFeatureMessage("Display Metrics", "View historical health data"));

        displayHealthReportButton.addActionListener(e ->
                showFeatureMessage("Health Report", "Generate comprehensive health analysis"));

        exitButton.addActionListener(e -> confirmExit());
    }

    private void showFeatureMessage(String title, String message) {
        JOptionPane.showMessageDialog(frame,
                "<html><div style='width: 250px; padding: 10px; text-align: center;'>" + message + "</div></html>",
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void confirmExit() {
        int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to exit the application?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage("ilgert"));
    }
}