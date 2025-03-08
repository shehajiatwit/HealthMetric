package SensitiveLoginandData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.chart.CategoryAxis;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class HealthReport extends Application {

    /*
     * Returns a user's username.
     */
    public String userName() {
        IDandPasswords user = new IDandPasswords();
        HashMap<String, String> loginInfo = user.getLoginInfo();
        String username = null;
        for (String key : loginInfo.keySet()) {
            username = key;
            break;
        }
        return username;
    }

    /*
     * Returns an ArrayList of Strings of the user's current and past health metrics.
     */
    public ArrayList<String[]> getUserData() throws FileNotFoundException {
        Data userData = new Data("File Directory");
        ArrayList<String[]> userMetrics = userData.loadData(userName());
        return userMetrics;
    }


    /*
     * Returns an ArrayList of Strings of the all the times the user's health metrics recorded
     */
    public ArrayList<String> getTimes() throws FileNotFoundException {
        ArrayList<String> userTimes = new ArrayList<>();
        ArrayList<Long> userTimesL = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();
        for (int i = 0; i < allMetrics.size(); i++) {
            userTimesL.add(Long.parseLong(allMetrics.get(i)[0]));
        }
        for (int i = 0; i < userTimesL.size(); i++) {
            ZonedDateTime dateTime = (Instant.ofEpochSecond(userTimesL.get(i)).atZone(ZoneId.of("America/New_York")));
            userTimes.add(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return userTimes;
    }

    /*
     * Returns an ArrayList of floats of the user's current and past measures of their systolic bp values.
     */
    public ArrayList<Float> getSystolic() throws FileNotFoundException {
        ArrayList<Float> userSystolic = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();
        for (int i = 0; i < allMetrics.size(); i++) {
            userSystolic.add(Float.parseFloat(allMetrics.get(i)[1]));
        }

        return userSystolic;
    }


    /*
     * Returns an ArrayList of floats of the user's current and past measures of their dystolic bp values.
     */
    public ArrayList<Float> getDystolic() throws FileNotFoundException{
        ArrayList<Float> userDystolic = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();
        for (int i = 0; i < allMetrics.size(); i++) {
            userDystolic.add(Float.parseFloat(allMetrics.get(i)[2]));
        }

        return userDystolic;
    }

    /*
     * Returns an ArrayList of floats of the user's current and past measures of their heart beat.
     */
    public ArrayList<Float> getHeartRates() throws FileNotFoundException {
        ArrayList<Float> userHeartRates = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();
        for (int i = 0; i < allMetrics.size(); i++) {
            userHeartRates.add(Float.parseFloat(allMetrics.get(i)[3]));
        }
        return userHeartRates;
    }

    /*
     * Returns an ArrayList of floats of the user's current and past measures of their glucose levels.
     */
    public ArrayList<Float> getGlucoseLevel() throws FileNotFoundException {
        ArrayList<Float> userGlucoseLevels = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();
        for (int i = 0; i < allMetrics.size(); i++) {
            userGlucoseLevels.add(Float.parseFloat(allMetrics.get(i)[4]));
        }

        return userGlucoseLevels;
    }

    /*
     * TO BE IMPLEMENTED IN INCREMENT 2
     * This method will return the average ratings of each of the user's health metrics.
     * int[0] = average rating of user's heart beat
     * int[1] = average rating of user's glucose levels
     * int[2] = average rating of user's blood pressure
     */
    public int[] getAverageRatings() {
        return new int[0];
    }

    /*
     * This creates all three linear graphs of a user's heart rate, glucose, and blood pressure over time.
     */
    @Override
    public void start(Stage stage) throws FileNotFoundException {

        // Creates an ArrayList of time, which will later be used as the x-axis for all three graphs
        ArrayList<String> times = getTimes();

        // Creates data series for heart rate chart
        CategoryAxis heartRateXAxis = new CategoryAxis();
        NumberAxis heartRateYAxis = new NumberAxis();
        heartRateXAxis.setLabel("Dates");
        heartRateYAxis.setLabel("Heart Rate (bpm)");

        // Creates line chart for heart rate
        LineChart<String, Number> heartRateChart = new LineChart<>(heartRateXAxis, heartRateYAxis);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Heart Rate");

        // Adds each data point (the user's heart rates) onto the graph to create a trendline
        ArrayList<Float> heartRates = getHeartRates();
        for (int i = 0; i < heartRates.size(); i++) {
            series1.getData().add(new XYChart.Data<>(times.get(i),heartRates.get(i)));
        }
        heartRateChart.getData().add(series1);


        // Creates data series for glucose levels chart
        CategoryAxis glucoseXAxis = new CategoryAxis();
        NumberAxis glucoseYAxis = new NumberAxis();
        glucoseXAxis.setLabel("Dates");
        glucoseYAxis.setLabel("Glucose Level (mg/dL)");

        // Creates line chart for glucose
        LineChart<String, Number> glucoseChart = new LineChart<>(glucoseXAxis, glucoseYAxis);
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Fasting Blood Glucose Levels");

        // Adds each data point (the user's glucose levels) onto the graph to create a trendline
        ArrayList<Float> glucose = getGlucoseLevel();
        for (int i = 0; i < glucose.size(); i++) {
            series2.getData().add(new XYChart.Data<>(times.get(i),glucose.get(i)));
        }
        glucoseChart.getData().add(series2);

        // Create data series for blood pressure levels chart
        CategoryAxis bpXAxis = new CategoryAxis();
        NumberAxis bpYAxis = new NumberAxis();
        bpXAxis.setLabel("Dates");
        bpYAxis.setLabel("Blood Pressure (mmHg)");

        // Creates line chart for blood pressure
        LineChart<String, Number> bpChart = new LineChart<>(bpXAxis, bpYAxis);
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        XYChart.Series<String, Number> series4 = new XYChart.Series<>();
        // There are two series in this graph. One representing the systolic values and the other dystolic.
        series3.setName("Systolic Pressure");
        series4.setName("Dystolic Pressure");

        // Creates the systolic trendline
        ArrayList<Float> systolic = getSystolic();
        for (int i = 0; i < systolic.size(); i++) {
            series3.getData().add(new XYChart.Data<>(times.get(i),systolic.get(i)));
        }
        // Creates the dystolic trendline
        ArrayList<Float> dystolic = getDystolic();
        for (int i = 0; i < dystolic.size(); i++) {
            series4.getData().add(new XYChart.Data<>(times.get(i),dystolic.get(i)));
        }
        bpChart.getData().addAll(series3,series4);

        // Will add a additional section (in increment 2) to the application that will show the average ratings of a user's health metrics

        // Sets the size of all charts
        heartRateChart.setPrefSize(800, 300);
        glucoseChart.setPrefSize(800, 300);
        bpChart.setPrefSize(800, 300);

        // Create a VBox layout to hold the charts
        VBox vbox = new VBox(10); // 10px spacing between charts
        vbox.getChildren().addAll(heartRateChart, glucoseChart, bpChart);

        // Create a scene and add it to the stage
        Scene scene = new Scene(vbox, 800, 900); // Set the size of the scene
        stage.setScene(scene);
        stage.setTitle(userName() + "'s Health Metric Report");
        stage.show();
    }

    // Method to start JavaFX application. So, if calling from a different class, call HealthReport.startApp();

    public static void startApp() {
        Thread thread = new Thread(() -> Application.launch(HealthReport.class));
        thread.setDaemon(false);
        thread.start();
    }

    public static void main(String[] args) {
        startApp();
    }
}