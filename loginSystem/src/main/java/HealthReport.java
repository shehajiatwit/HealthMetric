import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.chart.CategoryAxis;
import java.io.*;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import javafx.stage.FileChooser;

/*
 * Author: Abia Hasan
 * This program generates a Health Report containing three linear graphs showcasing all of user's recorded heart beat, glucose levels, blood pressure over time.
 * There is a additional section that gives a score and a recommendation to the user based on their health metric trends. 
 */

public class HealthReport {

    private static String username;

    /*
     * Sets the username to the argument
     */
    public static void setUsername(String user) {
        username = user;
    }

    /*
     * Returns username
     */
    public String userName() {
        return username;
    }

    /*
     * Returns an ArrayList of Strings of the user's current and past health metrics.
     */
    public ArrayList<String[]> getUserData() throws FileNotFoundException {
        return readCSVData();
    }

    /*
     * Returns an ArrayList of Strings of the all the times the user's health metrics recorded from oldest to most recent.
     */
    public ArrayList<String> getTimes() throws FileNotFoundException {
        ArrayList<String> userTimes = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();

        for (String[] metric : allMetrics) {
            userTimes.add(metric[3]); // Add timestamp
        }

        return userTimes;
    }

    /*
     * Returns an ArrayList of floats of the user's measures of their systolic bp values from oldest to most recent.
     */
    public ArrayList<Float> getSystolic() throws FileNotFoundException {
        ArrayList<Float> userSystolic = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();

        for (String[] metric : allMetrics) {
            if (metric[1].equals("bloodPressure")) {
                String[] bpParts = metric[2].split("/"); // Split systolic/diastolic
                userSystolic.add(Float.parseFloat(bpParts[0])); // Add systolic value
            }
        }

        return userSystolic;
    }

    /*
     * Returns an ArrayList of floats of the user's measures of their dystolic bp values from oldest to most recent.
     */
    public ArrayList<Float> getDystolic() throws FileNotFoundException{
        ArrayList<Float> userDystolic = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();

        for (String[] metric : allMetrics) {
            if (metric[1].equals("bloodPressure")) {
                String[] bpParts = metric[2].split("/"); // Split systolic/diastolic
                userDystolic.add(Float.parseFloat(bpParts[1])); // Add diastolic value
            }
        }

        return userDystolic;
    }
    /*
     * Returns an ArrayList of floats of the user's measures of their heart beat from oldest to most recent.
     */
    public ArrayList<Float> getHeartRates() throws FileNotFoundException {
        ArrayList<Float> userHeartRates = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();

        for (String[] metric : allMetrics) {
            if (metric[1].equals("heartRate")) {
                userHeartRates.add(Float.parseFloat(metric[2])); // Add heart rate value
            }
        }

        return userHeartRates;
    }

    /*
     * Returns an ArrayList of floats of the user's measures of their glucose levels from oldest to most recent.
     */
    public ArrayList<Float> getGlucoseLevel() throws FileNotFoundException {
        ArrayList<Float> userGlucoseLevels = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();

        for (String[] metric : allMetrics) {
            if (metric[1].equals("glucoseRate")) {
                userGlucoseLevels.add(Float.parseFloat(metric[2])); // Add glucose value
            }
        }

        return userGlucoseLevels;
    }

    /*
     * Returns the average ratings of each of the user's health metrics.
     */
    public float[] getAverageRatings() throws FileNotFoundException {
        float[] ratings = new float[3];

        // Computes average ratings of heart rate.
        HealthRecommendation hr = new HealthRecommendation();
        float sumHR = 0;
        float countHR = 0;
        for (int i = 0; i < getHeartRates().size(); i++) {
            if (getHeartRates().get(i) != 0) {
                sumHR += hr.compareHeartRate(getHeartRates().get(i));
                countHR++;
            }
        }
        ratings[0] = (sumHR / countHR);

        // Computes average rating of glucose levels.
        HealthRecommendation gl = new HealthRecommendation();
        float sumGL = 0;
        float countGL = 0;
        for (int i = 0; i < getGlucoseLevel().size(); i++) {
            if (getGlucoseLevel().get(i) != 0) {
                sumGL += gl.compareGlucose(getGlucoseLevel().get(i));
                countGL++;
            }
        }
        ratings[1] = (sumGL / countGL);

        // Computes average rating of blood pressure.
        HealthRecommendation bp = new HealthRecommendation();
        float sumBP = 0;
        float countBP = 0;
        int size = getSystolic().size(); // It's ensured that dystolic and systolic are the same size since it's required for user to input systolic and dystolic
        for (int i = 0; i < size; i++) {
            if (getSystolic().get(i) != 0 && getDystolic().get(i) != 0) {
                sumBP += bp.compareBloodPressure(getSystolic().get(i), getDystolic().get(i));
                countBP++;
            }
        }
        ratings[2] = (sumBP / countBP);

        return ratings;
    }

    public ArrayList<String[]> readCSVData(){
        ArrayList<String[]> userData = new ArrayList<>();
        String filename = "health_data.csv";

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length == 4 && parts[0].equals(username)){
                    userData.add(parts);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return userData;
    }

    /*
     * Initializes and displays the health report UI.
     */
    public void startReport(Stage stage) {
        try {
            // Read data from the CSV file
            ArrayList<String[]> userData = getUserData();

            // Extract timestamps, heart rates, glucose levels, and blood pressure values
            ArrayList<String> times = new ArrayList<>();
            ArrayList<Float> heartRates = new ArrayList<>();
            ArrayList<Float> glucoseLevels = new ArrayList<>();
            ArrayList<Float> systolicPressures = new ArrayList<>();
            ArrayList<Float> diastolicPressures = new ArrayList<>();

            for (String[] metric : userData) {
                times.add(metric[3]); // Full timestamp
                switch (metric[1]) {
                    case "heartRate":
                        heartRates.add(Float.parseFloat(metric[2])); // Add heart rate value
                        break;
                    case "glucoseRate":
                        glucoseLevels.add(Float.parseFloat(metric[2])); // Add glucose value
                        break;
                    case "bloodPressure":
                        String[] bpParts = metric[2].split("/"); // Split systolic/diastolic
                        systolicPressures.add(Float.parseFloat(bpParts[0])); // Add systolic value
                        diastolicPressures.add(Float.parseFloat(bpParts[1])); // Add diastolic value
                        break;
                }
            }

            // Create data series for heart rate chart
            CategoryAxis heartRateXAxis = new CategoryAxis();
            NumberAxis heartRateYAxis = new NumberAxis();
            heartRateXAxis.setLabel("Timestamps");
            heartRateYAxis.setLabel("Heart Rate (bpm)");
            heartRateXAxis.setCategories(FXCollections.observableArrayList(times));

            // Create line chart for heart rate
            LineChart<String, Number> heartRateChart = new LineChart<>(heartRateXAxis, heartRateYAxis);
            heartRateChart.setTitle("Heart Rate vs Time");
            XYChart.Series<String, Number> heartRateSeries = new XYChart.Series<>();
            heartRateSeries.setName("Heart Rate");

            // Add heart rate data points to the chart
            for (int i = 0; i < heartRates.size(); i++) {
                heartRateSeries.getData().add(new XYChart.Data<>(times.get(i), heartRates.get(i)));
            }
            heartRateChart.getData().add(heartRateSeries);
            
            
            // Create data series for glucose levels chart
            CategoryAxis glucoseXAxis = new CategoryAxis();
            NumberAxis glucoseYAxis = new NumberAxis();
            glucoseXAxis.setLabel("Timestamps");
            glucoseYAxis.setLabel("Glucose Level (mg/dL)");
            glucoseXAxis.setCategories(FXCollections.observableArrayList(times));

            // Create line chart for glucose
            LineChart<String, Number> glucoseChart = new LineChart<>(glucoseXAxis, glucoseYAxis);
            glucoseChart.setTitle("Fasting Blood Glucose Levels vs Time");
            XYChart.Series<String, Number> glucoseSeries = new XYChart.Series<>();
            glucoseSeries.setName("Glucose Levels");

            // Add glucose data points to the chart
            for (int i = 0; i < glucoseLevels.size(); i++) {
                glucoseSeries.getData().add(new XYChart.Data<>(times.get(i), glucoseLevels.get(i)));
            }
            glucoseChart.getData().add(glucoseSeries);
            

            // Create data series for blood pressure levels chart
            CategoryAxis bpXAxis = new CategoryAxis();
            NumberAxis bpYAxis = new NumberAxis();
            bpXAxis.setLabel("Timestamps");
            bpYAxis.setLabel("Blood Pressure (mmHg)");
            bpXAxis.setCategories(FXCollections.observableArrayList(times));

            // Create line chart for blood pressure
            LineChart<String, Number> bpChart = new LineChart<>(bpXAxis, bpYAxis);
            bpChart.setTitle("Blood Pressure vs Time");
            XYChart.Series<String, Number> systolicSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> diastolicSeries = new XYChart.Series<>();
            systolicSeries.setName("Systolic Pressure");
            diastolicSeries.setName("Diastolic Pressure");

            // Add blood pressure data points to the chart
            for (int i = 0; i < systolicPressures.size(); i++) {
                systolicSeries.getData().add(new XYChart.Data<>(times.get(i), systolicPressures.get(i)));
                diastolicSeries.getData().add(new XYChart.Data<>(times.get(i), diastolicPressures.get(i)));
            }
            bpChart.getData().addAll(systolicSeries, diastolicSeries);
            

            // Compute average ratings
            float[] averageRatings = getAverageRatings();
            String recommendationText = generateRecommendation(averageRatings);

            // Set the size of all charts
            heartRateChart.setPrefSize(800, 400);
            glucoseChart.setPrefSize(800,400);
            bpChart.setPrefSize(800,400);

            // Create a Text node to display the title
            Text title = new Text(userName() + "'s Health Report");
            title.setFill(Color.BLACK);
            title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
            VBox tbox = new VBox(title);
            tbox.setAlignment(Pos.CENTER); // Center the text

            // Create a Text node to display recommendations
            Text recommendationLabel = new Text(recommendationText);
            recommendationLabel.setFill(Color.BLACK);
            recommendationLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            // Create a full report containing title, charts, and recommendation
            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(tbox, heartRateChart, glucoseChart, bpChart, recommendationLabel);

            // Create a "Download Report" button
            Button downloadButton = new Button("Download Report");
            downloadButton.setOnAction(e -> downloadReport(vbox)); // Calls the download method
            //create a cancel button to go back to welcome page
            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction(e -> {
                stage.close();
                new WelcomePage(userName());
                    });
            // Add the button to the VBox
            HBox buttonBox = new HBox(10, downloadButton, cancelButton);
            buttonBox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(buttonBox);
            
            // Add a scroll bar
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vbox);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(850);

            Scene scene = new Scene(scrollPane,1000,850);
            stage.setScene(scene);
            stage.setTitle(userName() + "'s Health Metric Report");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error generating health report: " + e.getMessage());
        }
    }

    private String generateRecommendation(float[] ratings) throws FileNotFoundException {
        StringBuilder recommendation = new StringBuilder("Health Recommendations:\n");
        
        String hrRecommendation = String.format("%.1f", ratings[0]);
        String glucoseRecommendation = String.format("%.1f", ratings[1]);
        String bpRecommendation = String.format("%.1f", ratings[2]);


        // Heart Rate Recommendation
        if (getHeartRates().size() == 0) {
        	recommendation.append("You do not have enough data to generate a recommendation for heart rates.\n");
        } else if (ratings[0] < 2) {
            recommendation.append("Your average heart rate score is " + hrRecommendation + "/5 - Your average heart rate is abnormal. Consult a doctor.\n");
        } else if (ratings[0] > 4) {
            recommendation.append("Your average heart rate score is " + hrRecommendation + "/5 - Your average heart rate is within a healthy range.\n");
        } else {
            recommendation.append("Your average heart rate score is " + hrRecommendation + "/5 - Your average heart rate is moderate. Ensure regular check-ups to avoid a severe progression.\n");
        }

        // Glucose Level Recommendation
        if (getGlucoseLevel().size() == 0) {
        	recommendation.append("You do not have enough data to generate a recommendation for glucose levels.\n");
        } else if (ratings[1] < 2) {
            recommendation.append("Your average glucose level score is " + glucoseRecommendation + "/5 - Your average glucose levels are abnormal. Consult a doctor. Maintain a balanced diet.\n");
        } else if (ratings[1] > 4) {
            recommendation.append("Your average glucose level score is " + glucoseRecommendation + "/5 - Your average glucose levels are within a healthy range.\n");
        } else {
            recommendation.append("Your average glucose level score is " + glucoseRecommendation + "/5 - Your glucose levels are moderate. Ensure regular check-ups to avoid a severe progression.\n");
        }

        // Blood Pressure Recommendation
        if (getSystolic().size() == 0 || getDystolic().size() == 0) {
        	recommendation.append("You do not have enough data to generate a recommendation for blood pressure.\n");
        } else if (ratings[2] < 2) {
            recommendation.append("Your average blood pressure score is " + bpRecommendation + "/5 - Your average blood pressure is abnormal. Consult a doctor. Stay hydrated and maintain proper nutrition.\n");
        } else if (ratings[2] > 4) {
            recommendation.append("Your average blood pressure score is " + bpRecommendation + "/5 - Your average blood pressure is within a healthy range.\n");
        } else {
            recommendation.append("Your average blood pressure score is " + bpRecommendation + "/5 - Your average blood pressure is moderate. Ensure regular check-ups to avoid a severe progression. \n");
        }

        return recommendation.toString();
    }

    // Method that allows the user to download the report.
    public static void downloadReport(VBox vbox) {
        WritableImage image = new WritableImage((int) vbox.getWidth(), (int) vbox.getHeight());
        vbox.snapshot(null, image);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                // Write the image to a file
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                System.out.println("Report saved as " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}