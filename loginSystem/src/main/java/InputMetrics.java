import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InputMetrics {
    private JFrame frame;
    private JButton addHeartRate, addBloodPressure, addGlucose,logout;
    private JTextField inputField;
    private JButton submitButton;
    private JLabel metricLabel, alertLabel;
    private final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private String currentMetric;
    private final String username;
    //reference for Welcome page to dispose in logout
    JFrame welcomeFrame;

    public InputMetrics(String username, JFrame welcomeFrame) {
        this.username = username;
        this.welcomeFrame = welcomeFrame;
        initializeFrame();
        createUIComponents();
        setupLayout();
        frame.setVisible(true);
    }

    public InputMetrics(String username){
        this.username = username;

    }

    private void initializeFrame() {
        frame = new JFrame("Input Metrics - " + username);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        addHeartRate = createStyledButton("Add Heart Rate");
        addBloodPressure = createStyledButton("Add Blood Pressure");
        addGlucose = createStyledButton("Add Glucose Level");
        logout = createStyledButton("Cancel");

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

        JLabel titleLabel = new JLabel("Select Metric Type", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 60, 60));
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addHeartRate);
        buttonPanel.add(addBloodPressure);
        buttonPanel.add(addGlucose);
        buttonPanel.add(logout);

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 5, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.add(metricLabel);
        inputPanel.add(inputField);
        inputPanel.add(submitButton);
        inputPanel.setVisible(false);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(inputPanel);
        southPanel.add(alertLabel);
        southPanel.setBackground(Color.WHITE);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        addHeartRate.addActionListener(e -> {
            currentMetric = "heartRate";
            showInputField("Heart Rate (bpm):");
        });
        addBloodPressure.addActionListener(e -> {
            currentMetric = "bloodPressure";
            showInputField("Blood Pressure (mmHg):");
        });
        addGlucose.addActionListener(e -> {
            currentMetric = "glucoseRate";
            showInputField("Glucose Level (mg/dL):");
        });
        logout.addActionListener(e -> handleLogout());

        submitButton.addActionListener(e -> handleSubmission());

        frame.add(mainPanel);
        frame.pack();
    }

    public void handleLogout() {
        frame.dispose();
    }
    private void showInputField(String metric) {
        metricLabel.setText(metric);
        //currentMetric = metric.split(" ")[0].toLowerCase() + "Rate";
        inputField.setVisible(true);
        submitButton.setVisible(true);
        ((JPanel) submitButton.getParent()).setVisible(true);
        inputField.requestFocus();
        alertLabel.setText("");
        frame.pack();
    }

    private void handleSubmission() {
        String inputValue = inputField.getText().trim();
        String alertMessage = validateInput(inputValue);

        if (alertMessage.startsWith("Invalid")) {
            alertLabel.setText("<html><font color='red'>Alert: " + alertMessage + "</font></html>");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = now.format(formatter);

        saveToFile(inputValue, formattedTime);
        showConfirmation(inputValue, formattedTime);
        resetInputFields();
    }

    private String validateInput(String inputValue) {
        try {
            switch (currentMetric) {
                case "heartRate":
                    int hr = Integer.parseInt(inputValue);
                    if (hr < 30 || hr > 250) return "Invalid heart rate value"; 
                    break;
                case "bloodPressure":
                    String[] parts = inputValue.split("/");
                    if (parts.length != 2) return "Invalid format. Use systolic/diastolic";
                    int systolic = Integer.parseInt(parts[0].trim());
                    int diastolic = Integer.parseInt(parts[1].trim());
                    if (systolic < 50 || diastolic < 30) return "Invalid. Impossibly low blood pressure";
                    if (systolic > 250 || diastolic > 150) return "Invalid. Dangerously high blood pressure";
                    break;
                case "glucoseRate":
                    int glucose = Integer.parseInt(inputValue);
                    if (glucose < 20 || glucose > 1000) return "Invalid. Extreme glucose level";
                    break;
            }
        } catch (NumberFormatException e) {
            return "Invalid numerical input";
        }
        return "";
    }

    private void saveToFile(String inputValue, String timestamp) {
        String filename = "health_data.csv";
        String sanitizedValue = inputValue.replace(",", ";");  // Prevent CSV injection

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(String.join(",",
                    username,
                    currentMetric,
                    sanitizedValue,
                    timestamp
            ));
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame,
                    "Error saving data: " + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private String formatAlertMessage(String alertMessage) {
        return "<font color='red'>Alert: " + alertMessage + "</font>";
    }

    private void showConfirmation(String inputValue, String timestamp) {
        String alertMessage = validateInput(inputValue);

        if (!alertMessage.isEmpty()) {
            String message = String.format("<html><div style='width: 250px; padding: 10px;'>"
                            + "<b>Data Recorded:</b><br>"
                            + "Metric: %s<br>"
                            + "Value: %s<br>"
                            + "Time: %s<br>"
                            + "%s</div></html>",
                    currentMetric, inputValue, timestamp, formatAlertMessage(alertMessage));
            JOptionPane.showMessageDialog(frame , message);
            return;
        }

        String message = String.format("<html><div style='width: 250px; padding: 10px;'>"
                        + "<b>Data Recorded:</b><br>"
                        + "Metric: %s<br>"
                        + "Value: %s<br>"
                        + "Time: %s</div></html>",
                currentMetric, inputValue, timestamp);

        JOptionPane.showMessageDialog(frame, message);
    }

    private void resetInputFields() {
        inputField.setText("");
        inputField.setVisible(false);
        submitButton.setVisible(false);
        ((JPanel) submitButton.getParent()).setVisible(false);
        alertLabel.setText("");
        frame.pack();
    }
}