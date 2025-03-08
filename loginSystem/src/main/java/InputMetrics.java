import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InputMetrics {
    JFrame frame;
    JButton addHeartRate, addBloodPressure, addGlucose;
    JTextField inputField;
    JButton submitButton;
    JLabel metricLabel;
    private final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 16);

    // Alert components
    JLabel alertLabel;
    private String currentMetric;

    public InputMetrics() {
        initializeFrame();
        createUIComponents();
        setupLayout();
        frame.setVisible(true);
    }

    private void initializeFrame() {
        frame = new JFrame("Input Metrics Dashboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        addHeartRate = createStyledButton("Add Heart Rate");
        addBloodPressure = createStyledButton("Add Blood Pressure");
        addGlucose = createStyledButton("Add Glucose Level");

        metricLabel = new JLabel("", SwingConstants.CENTER);
        metricLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        metricLabel.setForeground(new Color(60, 60, 60));

        inputField = new JTextField();
        inputField.setFont(BUTTON_FONT);
        inputField.setVisible(false);

        submitButton = createStyledButton("Submit");
        submitButton.setVisible(false);

        alertLabel = new JLabel("", SwingConstants.CENTER);
        alertLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        alertLabel.setForeground(Color.RED);
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

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.darker());
            }

            public void mouseExited(MouseEvent evt) {
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

        // Metric Selection Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addHeartRate);
        buttonPanel.add(addBloodPressure);
        buttonPanel.add(addGlucose);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 5, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.add(metricLabel);
        inputPanel.add(inputField);
        inputPanel.add(submitButton);
        inputPanel.setVisible(false);

        // South Panel for input and alerts
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(inputPanel);
        southPanel.add(alertLabel);
        southPanel.setBackground(Color.WHITE);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Add action listeners
        addHeartRate.addActionListener(e -> showInputField("Heart Rate (bpm):"));
        addBloodPressure.addActionListener(e -> showInputField("Blood Pressure (mmHg):"));
        addGlucose.addActionListener(e -> showInputField("Glucose Level (mg/dL):"));

        submitButton.addActionListener(e -> {
            String inputValue = inputField.getText().trim();
            String alertMessage = validateInput(currentMetric, inputValue);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = now.format(formatter);
            String message = String.format("<html>Submitted at %s:<br>Value: %s</html>", formattedTime, inputValue);

            JOptionPane.showMessageDialog(frame, message);

            if (!alertMessage.isEmpty()) {
                alertLabel.setText("<html><font color='red'>Alert: " + alertMessage + "</font></html>");
            } else {
                alertLabel.setText("");
            }

            inputPanel.setVisible(false);
            inputField.setText("");
        });

        frame.add(mainPanel);
        frame.pack();
    }

    private void showInputField(String metric) {
        metricLabel.setText(metric);
        if (metric.startsWith("Heart Rate")) {
            currentMetric = "heartRate";
        } else if (metric.startsWith("Blood Pressure")) {
            currentMetric = "bloodPressure";
        } else if (metric.startsWith("Glucose")) {
            currentMetric = "glucose";
        }
        inputField.setVisible(true);
        submitButton.setVisible(true);
        ((JPanel) submitButton.getParent()).setVisible(true);
        inputField.requestFocus();
        alertLabel.setText("");
        frame.pack();
    }

    private String validateInput(String metricType, String inputValue) {
        try {
            switch (metricType) {
                case "heartRate":
                    int hr = Integer.parseInt(inputValue);
                    if (hr < 60) return "Heart rate is too low. Seek medical attention.";
                    if (hr > 100) return "Heart rate is too high. Seek medical attention.";
                    break;
                case "bloodPressure":
                    String[] parts = inputValue.split("/");
                    if (parts.length != 2) return "Invalid format. Use systolic/diastolic (e.g., 120/80)";
                    int systolic = Integer.parseInt(parts[0].trim());
                    int diastolic = Integer.parseInt(parts[1].trim());
                    if (systolic >= 130 || diastolic >= 80) return "High blood pressure. Seek medical attention.";
                    if (systolic < 90 || diastolic < 60) return "Low blood pressure. Seek medical attention.";
                    break;
                case "glucose":
                    int glucose = Integer.parseInt(inputValue);
                    if (glucose < 70) return "Glucose level is too low. Seek medical attention.";
                    if (glucose > 140) return "Glucose level is too high. Seek medical attention.";
                    break;
            }
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter a valid number.";
        }
        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InputMetrics());
    }
}