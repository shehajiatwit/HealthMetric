// Kevin Eloi <eloik@wit.edu>


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class User {
    private boolean loggedIn;
    public Data data;
    public static final String[] StringKeys = new String[]{"systolicbp", "dystolicbp", "heartrate", "glucose"};
    private HealthMetric[] healthMetric;
    private String[] login = {null, null};

    public User(String username, String password) {
        healthMetric = new HealthMetric[] {
                new HealthMetric(105.0f, StringKeys[0]), //Systolic
                new HealthMetric(70.0f, StringKeys[1]), //Dystolic
                new HealthMetric(80.0f, StringKeys[2]), //Heart rate
                new HealthMetric(85.0f, StringKeys[3]) //Glucose
        };

        data = new Data("Data/");
        setLogin(username, password);
    }

    public void setLogin(String username, String password) {
        login[0] = username; login[1] = password;
        loggedIn = data.validateCredentials(username, password);
    }

    public void createLogin(String username, String password) throws IOException {
        String status = data.createData(username, password);
        System.out.println("Create Login Status: " + status);

        // Check if files exist
        File userFolder = new File("Data/" + login[0]);
        File usernameFile = new File(userFolder, "login_username.txt");
        File passwordFile = new File(userFolder, "login_password.txt");

        if (usernameFile.exists() && passwordFile.exists()) {
            System.out.println("Login files created successfully!");
        } else {
            System.out.println("ERROR: Login files were NOT created.");
        }

        loggedIn = status.equals("Data folder successfully created!") || status.equals("Data folder already exists.");
    }

    public void createLoginX() throws IOException {
        data.createData(login[0], login[1]);
        loggedIn = true;
    }

    public void setData() throws IOException {
        if (loggedIn) {
            data.saveData(getUsername(),
                    new String[]{
                            Float.toString(healthMetric[0].getSystolic()),
                            Float.toString(healthMetric[1].getDystolic())},
                    Float.toString(healthMetric[2].getHeartRate()),
                    Float.toString(healthMetric[3].getGlucose()));
        }
    }

    public Long[] getActivityLog() {
        Superloop:
        if (loggedIn) {
            try {
                ArrayList<String[]> loadedData = data.loadData(getUsername());
                Long[] unix = new Long[loadedData.size()];

                for (int i = 0; i < unix.length; i++) {
                    unix[i] = Long.parseLong(loadedData.get(i)[0]); //Unix timestamp
                }

                return unix;
            } catch (IOException e) {break Superloop;}
        }

        return null;
    }

    public float[][] getBloodPressure() {
        Superloop:
        if (loggedIn) {
            try {
                ArrayList<String[]> loadedData = data.loadData(getUsername());
                float[][] d = new float[loadedData.size()][2];

                for (int i = 0; i < d.length; i++) {
                    d[i][0] = Float.parseFloat(loadedData.get(i)[1]); //Systolic
                    d[i][0] = Float.parseFloat(loadedData.get(i)[2]); //Dystolic
                }

                return d;
            } catch (IOException e) {break Superloop;}
        }

        return null;
    }

    public float[] getHeartRate() {
        Superloop:
        if (loggedIn) {
            try {
                ArrayList<String[]> loadedData = data.loadData(getUsername());
                float[] d = new float[loadedData.size()];

                for (int i = 0; i < d.length; i++) {
                    d[i] = Float.parseFloat(loadedData.get(i)[3]); //Heartrate
                }

                return d;
            } catch (IOException e) {break Superloop;}
        }

        return null;
    }

    public float[] getGlucose() {
        Superloop:
        if (loggedIn) {
            try {
                ArrayList<String[]> loadedData = data.loadData(getUsername());
                float[] d = new float[loadedData.size()];

                for (int i = 0; i < d.length; i++) {
                    d[i] = Float.parseFloat(loadedData.get(i)[4]); //Glucose
                }

                return d;
            } catch (IOException e) {break Superloop;}
        }

        return null;
    }

    public String[] getLogin() {
        return login;
    }

    public String getUsername() {
        return getLogin()[0];
    }

    public String getPassword() {
        return getLogin()[1];
    }

    public boolean getLoggedInStatus() {
        return loggedIn;
    }
}