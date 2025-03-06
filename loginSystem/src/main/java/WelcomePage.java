import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage {
    JFrame frame = new JFrame();
    JLabel welcomeLabel = new JLabel("Welcome ");
    JButton checkUserButton = new JButton("Check User Data");
    JButton retrieveUserButton = new JButton("Retrieve User Data");

    WelcomePage(){
        // Set up main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420 , 420);
        frame.setLayout(null);

        // Set up welcome label
        welcomeLabel.setBounds(0, 0, 200, 35);
        welcomeLabel.setFont(new Font(null , Font.PLAIN, 25));

        // Set button bounds
        checkUserButton.setBounds(125, 200, 180, 25);
        retrieveUserButton.setBounds(225 , 200 , 180 , 25);

        // Add action listener to button
        checkUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openChartWindow();
            }
        });

        // Add components to frame
        frame.add(welcomeLabel);
        frame.add(checkUserButton);
        frame.add(retrieveUserButton);

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
