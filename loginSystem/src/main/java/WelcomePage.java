import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

public class WelcomePage {
    private JFrame frame = new JFrame();
    private JLabel welcomeLabel = new JLabel("Welcome");
    private JButton checkUserButton = new JButton("Check User Data");
    private JButton retrieveUserButton = new JButton("Retrieve User Data");

    public WelcomePage() {
        // Set up main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // padding for spacing between components

        // Set up welcome label
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Center the label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(welcomeLabel, gbc);

        // Set up buttons
        checkUserButton.setFont(new Font("Arial", Font.PLAIN, 16));
        retrieveUserButton.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add action listener to "Check User Data" button
        checkUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openChartWindow();
            }
        });

        // Set up layout for buttons: Centering them
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);  // Larger padding for the buttons
        frame.add(checkUserButton, gbc);

        gbc.gridx = 1;
        frame.add(retrieveUserButton, gbc);

        // Show frame
        frame.setVisible(true);
    }

    // Method to open the new window with the chart
    private void openChartWindow() {
        // Create a new JFrame for the chart window
        JFrame chartFrame = new JFrame("Random Data Chart");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(600, 400);

        // Generate some random data for the chart
        DefaultCategoryDataset dataset = createRandomDataset();

        // Create a chart using JFreeChart
        JFreeChart chart = ChartFactory.createBarChart(
                "Random Data", // chart title
                "Category", // x-axis label
                "Value", // y-axis label
                dataset, // dataset
                PlotOrientation.VERTICAL, // plot orientation
                true, // include legend
                true, // tooltips
                false // URLs
        );

        // Add chart to a panel and set it as the content of the new frame
        ChartPanel chartPanel = new ChartPanel(chart);
        chartFrame.add(chartPanel, BorderLayout.CENTER);

        // Display the chart window
        chartFrame.setVisible(true);
    }

    // Generate some random data for the chart
    private DefaultCategoryDataset createRandomDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 1; i <= 5; i++) {
            dataset.addValue(Math.random() * 100, "Series1", "Category " + i);
        }

        return dataset;
    }

    public static void main(String[] args) {
        // Run the WelcomePage UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WelcomePage();
            }
        });
    }
}
