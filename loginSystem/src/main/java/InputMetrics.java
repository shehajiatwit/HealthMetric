import javax.swing.*;
import java.awt.*;

public class InputMetrics {
    JFrame frame;
    JButton addHeartRate;
    JButton addBloodPressure;
    JButton addGlucose;
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel blue
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 16);

    public InputMetrics() {
        initializeFrame();
        createUIComponents();
        setupLayout();
        frame.setVisible(true);
    }

    private void initializeFrame() {
        frame = new JFrame("Input Metrics Dashboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to dispose instead of exit
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        addHeartRate = createStyledButton("Add Heart Rate");
        addBloodPressure = createStyledButton("Add Blood Pressure");
        addGlucose = createStyledButton("Add Glucose Level");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);

        // Hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(Color.WHITE);

        // Title Panel
        JLabel titleLabel = new JLabel("Select Metric Type", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 60, 60));
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addHeartRate);
        buttonPanel.add(addBloodPressure);
        buttonPanel.add(addGlucose);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InputMetrics());
    }
}