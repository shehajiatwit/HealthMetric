package SensitiveLoginandData;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class HealthReportLauncher extends Application {
    private static String username;

    /**
     * Launches the HealthReport JavaFX application with the specified username.
     *
     * @param user The username to pass to the HealthReport application.
     */
    public static void launchHealthReport(String user) {
        username = user;
        launch();
    }

    @Override
    public void start(Stage stage) {
        // Create an instance of HealthReport and set the username
        HealthReport healthReport = new HealthReport();
        healthReport.setUsername(username);

        try {
            // Call the startReport method to initialize the UI
            healthReport.startReport(stage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}