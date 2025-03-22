package SensitiveLoginandData;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
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
        Data userData = new Data("File Directory");
        ArrayList<String[]> userMetrics = userData.loadData(userName());
        return userMetrics;
    }


    /*
     * Returns an ArrayList of Strings of the all the times the user's health metrics recorded from oldest to most recent. 
     */
    public ArrayList<String> getTimes() throws FileNotFoundException {
        ArrayList<String> userTimes = new ArrayList<>();
        ArrayList<Long> userTimesL = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();
        for (int i = allMetrics.size() - 1; i >= 0;  i--) {
            userTimesL.add(Long.parseLong(allMetrics.get(i)[0]));
        }
        for (int i = 0; i < userTimesL.size(); i++) {
            ZonedDateTime dateTime = (Instant.ofEpochSecond(userTimesL.get(i)).atZone(ZoneId.of("America/New_York")));
            userTimes.add(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        return userTimes;
    }

    /*
     * Returns an ArrayList of floats of the user's measures of their systolic bp values from oldest to most recent. 
     */
    public ArrayList<Float> getSystolic() throws FileNotFoundException {
        ArrayList<Float> userSystolic = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();
        for (int i = allMetrics.size() - 1; i >= 0; i--) {
            userSystolic.add(Float.parseFloat(allMetrics.get(i)[1]));
        }

        return userSystolic;
    }


    /*
     * Returns an ArrayList of floats of the user's measures of their dystolic bp values from oldest to most recent. 
     */
    public ArrayList<Float> getDystolic() throws FileNotFoundException{
        ArrayList<Float> userDystolic = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();
        for (int i = allMetrics.size() - 1; i >= 0; i--) {
            userDystolic.add(Float.parseFloat(allMetrics.get(i)[2]));
        }

        return userDystolic;
    }

    /*
     * Returns an ArrayList of floats of the user's measures of their heart beat from oldest to most recent. 
     */
    public ArrayList<Float> getHeartRates() throws FileNotFoundException {
        ArrayList<Float> userHeartRates = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();
        for (int i = allMetrics.size() - 1; i >= 0; i--) {
            userHeartRates.add(Float.parseFloat(allMetrics.get(i)[3]));
        }
        return userHeartRates;
    }

    /*
     * Returns an ArrayList of floats of the user's measures of their glucose levels from oldest to most recent. 
     */
    public ArrayList<Float> getGlucoseLevel() throws FileNotFoundException {
        ArrayList<Float> userGlucoseLevels = new ArrayList<>();
        ArrayList<String[]> allMetrics = getUserData();
        for (int i = allMetrics.size() - 1; i >= 0; i--) {
            userGlucoseLevels.add(Float.parseFloat(allMetrics.get(i)[4])); 
        }

        return userGlucoseLevels;
    }

    /*
     * TO BE IMPLEMENTED IN INCREMENT 2
     * This method will return the average ratings of each of the user's health metrics.
     * [0] = average rating of user's heart beat
     * [1] = average rating of user's glucose levels
     * [2] = average rating of user's blood pressure
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
    	ratings[1] = (sumGL/countGL);
    	
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
    	ratings[2] = (sumBP/countBP);
    	
    	return ratings;		
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
        heartRateXAxis.setCategories(FXCollections.observableArrayList(times));


        // Creates line chart for heart rate
        LineChart<String, Number> heartRateChart = new LineChart<>(heartRateXAxis, heartRateYAxis);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Heart Rate");

        // Adds each data point (the user's heart rates) onto the graph to create a trendline
        ArrayList<Float> heartRates = getHeartRates();
        for (int i = 0; i < heartRates.size(); i++) {
        	if (heartRates.get(i) != 0) {
        		series1.getData().add(new XYChart.Data<>(times.get(i),heartRates.get(i)));
        	}
        }
        heartRateChart.getData().add(series1);


        // Creates data series for glucose levels chart
        CategoryAxis glucoseXAxis = new CategoryAxis();
        NumberAxis glucoseYAxis = new NumberAxis();
        glucoseXAxis.setLabel("Dates");
        glucoseYAxis.setLabel("Glucose Level (mg/dL)");
        glucoseXAxis.setCategories(FXCollections.observableArrayList(times));


        // Creates line chart for glucose
        LineChart<String, Number> glucoseChart = new LineChart<>(glucoseXAxis, glucoseYAxis);
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Fasting Blood Glucose Levels");

        // Adds each data point (the user's glucose levels) onto the graph to create a trendline
        ArrayList<Float> glucose = getGlucoseLevel();
        for (int i = 0; i < glucose.size(); i++) {
        	if (glucose.get(i) != 0) {
        		series2.getData().add(new XYChart.Data<>(times.get(i),glucose.get(i)));
        	}
        }
        glucoseChart.getData().add(series2);

        // Create data series for blood pressure levels chart
        CategoryAxis bpXAxis = new CategoryAxis();
        NumberAxis bpYAxis = new NumberAxis();
        bpXAxis.setLabel("Dates");
        bpYAxis.setLabel("Blood Pressure (mmHg)");
        bpXAxis.setCategories(FXCollections.observableArrayList(times));


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
        	if (systolic.get(i) != 0) {
        		series3.getData().add(new XYChart.Data<>(times.get(i),systolic.get(i)));
        	}
        }
        // Creates the dystolic trendline
        ArrayList<Float> dystolic = getDystolic();
        for (int i = 0; i < dystolic.size(); i++) {
        	if (dystolic.get(i) != 0) {
        		series4.getData().add(new XYChart.Data<>(times.get(i),dystolic.get(i)));
        	}
        }
        bpChart.getData().addAll(series3,series4);

        // Will add a additional section (in increment 2) to the application that will show the average ratings of a user's health metrics
        
        // Compute average ratings
        float[] averageRatings = getAverageRatings();
        String recommendationText = generateRecommendation(averageRatings);
        
        // Sets the size of all charts
        heartRateChart.setPrefSize(800, 300);
        glucoseChart.setPrefSize(800, 300);
        bpChart.setPrefSize(800, 300);

        // Create a Text node to display recommendations
        Text recommendationLabel = new Text(recommendationText);
        recommendationLabel.setFill(Color.DARKBLUE);
        recommendationLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Add recommendations to the VBox layout
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(heartRateChart, glucoseChart, bpChart, recommendationLabel);

        // Set the scene
        Scene scene = new Scene(vbox, 800, 950);
        stage.setScene(scene);
        stage.setTitle(userName() + "'s Health Metric Report");
        stage.show();
    }
    
    private String generateRecommendation(float[] ratings) {
        StringBuilder recommendation = new StringBuilder("Health Recommendations:\n");

        // Heart Rate Recommendation
        if (ratings[0] < 2) {
            recommendation.append("- Your heart rate is low. Consider exercising or consulting a doctor.\n");
        } else if (ratings[0] > 4) {
            recommendation.append("- Your heart rate is above average. Ensure regular check-ups.\n");
        } else {
            recommendation.append("- Your heart rate is within a healthy range.\n");
        }

        // Glucose Level Recommendation
        if (ratings[1] < 2) {
            recommendation.append("- Your glucose levels are low. Maintain a balanced diet.\n");
        } else if (ratings[1] > 4) {
            recommendation.append("- Your glucose levels are high. Consider reducing sugar intake.\n");
        } else {
            recommendation.append("- Your glucose levels are normal.\n");
        }

        // Blood Pressure Recommendation
        if (ratings[2] < 2) {
            recommendation.append("- Your blood pressure is low. Stay hydrated and maintain proper nutrition.\n");
        } else if (ratings[2] > 4) {
            recommendation.append("- Your blood pressure is high. Reduce sodium intake and consult a physician.\n");
        } else {
            recommendation.append("- Your blood pressure is in a good range.\n");
        }

        return recommendation.toString();
    }

    
    // Method that allows the user to download the report. 
    public static void downloadReport() {
    	
    }

    // Method to start JavaFX application. So, if calling from a different class, call HealthReport.startApp(String username);
    public static void startApp(String user) {
    	setUsername(user);
        Thread thread = new Thread(() -> Application.launch(HealthReport.class));
        thread.setDaemon(false);
        thread.start();
    }
}