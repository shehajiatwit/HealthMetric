import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class HealthReportLauncher extends Application {
    private static boolean isJavaFXInitialized = false;
    private static String username;
    
    /**
     * Launches the HealthReport JavaFX application with the specified username.
     *
     * @param user The username to pass to the HealthReport application.
     */
    public static void launchHealthReport(String user) {
        username = user;

        if (!isJavaFXInitialized) {
            isJavaFXInitialized = true;
            // launches JavaFX without freezing the main application.
            new Thread(() -> Application.launch(HealthReportLauncher.class)).start();
        } else {
        	// Ensures that code runs safely on the JavaFX thread
        	Platform.runLater(() -> HealthReportLauncher.openHealthReport());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);  // Prevent JavaFX from shutting down
        openHealthReport();
    }

    public static void openHealthReport() {
        try {
            Stage stage = new Stage();
            // Create an instance of HealthReport and set the username
            HealthReport healthReport = new HealthReport();
            healthReport.setUsername(username);
            // Call the startReport method to initizalize the UI
            healthReport.startReport(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
