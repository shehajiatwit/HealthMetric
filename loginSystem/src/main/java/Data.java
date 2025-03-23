
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

// Kevin Eloi <eloik@wit.edu>

/* Data Class File Organization
 * 1. There is one main "Data" folder for all users
 * 2. Each user has a folder inside "Data" marked with their usernames. It holds their login info and health data
 * 3. In the user's folder will be subfolders with txt files for all individual health metrics. This subfolder will be named by the time the data was saved.
 */
public class Data {
    private String mainDirect;

    public Data (String directory) {
        mainDirect = directory;
    }

    public ArrayList<String[]> loadData(String username) throws FileNotFoundException {
        ArrayList<String> directories = arrangeData(username); //Data file directories in order of time
        ArrayList<String[]> dataset = new ArrayList<>();

        for (int i = 0; i < directories.size(); i++) {
            String directory = directories.get(i);
            File[] healthData = new File[] {
                    new File(directory + "/timestamp.txt"),
                    new File(directory + "/1_systolicBP.txt"),
                    new File(directory + "/2_dystolicBP.txt"),
                    new File(directory + "/3_heartrate.txt"),
                    new File(directory + "/4_glucose.txt")
            };

            //Creates scanner to read the data files
            Scanner[] readData = new Scanner[] {
                    new Scanner(healthData[0]),
                    new Scanner(healthData[1]),
                    new Scanner(healthData[2]),
                    new Scanner(healthData[3]),
                    new Scanner(healthData[4])
            };

            //Loads data into a string
            String[] obtainedData = new String[] {
                    readData[0].next(), //Timestamp
                    readData[1].next(), //Systolic BP
                    readData[2].next(), //Dystolic BP
                    readData[3].next(), //Heart rate
                    readData[4].next() //Glucose
            };

            readData[0].close(); readData[1].close(); readData[2].close();
            readData[3].close(); readData[4].close();

            dataset.add(obtainedData);
        }

        return dataset;
    }

    public void saveData(String username, String[] bp, String hr, String glucose) throws IOException {
        File folder = new File(getDirectory(username)[2]);

        if (folder.exists() && folder.isDirectory()) {
            String directory = getDirectory(username)[2] + "/" + clock();
            File subfolder = new File(directory);

            if (subfolder.mkdir()) { //Creates new user data folder for time snapshot
                //Creates data file
                File[] healthData = new File[] {
                        new File(directory + "/timestamp.txt"),
                        new File(directory + "/1_systolicBP.txt"),
                        new File(directory + "/2_dystolicBP.txt"),
                        new File(directory + "/3_heartrate.txt"),
                        new File(directory + "/4_glucose.txt")
                };

                //Creates new files for data
                for (int i = 0; i < healthData.length; i++) {
                    healthData[i].createNewFile();
                }

                //Makes printwriter for all data files
                PrintWriter[] writeOutput = new PrintWriter[]{
                        new PrintWriter(healthData[0]),
                        new PrintWriter(healthData[1]),
                        new PrintWriter(healthData[2]),
                        new PrintWriter(healthData[3]),
                        new PrintWriter(healthData[4])
                };

                //Saves time to a file
                writeOutput[0].print(clock());

                //Saves health data to file
                writeOutput[1].print(bp[0]); //Systolic
                writeOutput[2].print(bp[1]); //Dystolic
                writeOutput[3].print(hr); //Heart rate
                writeOutput[4].print(glucose); //Glucose


                writeOutput[0].close(); writeOutput[1].close(); writeOutput[2].close();
                writeOutput[3].close(); writeOutput[4].close();
            }
        } else {

        }
    }

    public String createData(String username, String password) throws IOException {
        final String[] status = new String[] {
                "Data folder successfully created!", //0
                "Failed to create data folder.", //1
                "Data folder already exists." //2
        };

        final String[] directory = getDirectory(username);

        final File folder = new File(directory[0] + directory[1]);

        System.out.println("Attempting to create folder at: " + folder.getAbsolutePath());
        if (!folder.exists()) {
            if (folder.mkdirs()) { //Creates a folder
                File[] loginFiles = new File[]{
                        new File(directory[2] + "/login_username.txt"),
                        new File(directory[2] + "/login_password.txt")
                };
                loginFiles[0].createNewFile(); loginFiles[1].createNewFile(); //Creates new files

                PrintWriter[] loginOutput = new PrintWriter[]{
                        new PrintWriter(loginFiles[0]),
                        new PrintWriter(loginFiles[1])
                };

                loginOutput[0].print(username); //Prints the new username to a file
                loginOutput[1].print(password); //Prints the new password to a file

                loginOutput[0].close(); loginOutput[1].close();

                return status[0];
            }
            return status[1]; //No need to create a folder that already exists!
        } else {
            return status[2];
        }
    }

    //Arranges user data based on time
    public ArrayList<String> arrangeData(String username) {
        String mainDir = getDirectory(username)[2];
        File directory = new File(mainDir);
        ArrayList<String> dataPaths = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] subDirs = directory.listFiles(File::isDirectory); //Excludes paths that are not directories

            if (subDirs != null) {
                // Sort subdirectories based on long value
                for (int i = 0; i < subDirs.length - 1; i++) {
                    for (int j = i + 1; j < subDirs.length; j++) {
                        try {
                            long num1 = Long.parseLong(subDirs[i].getName());
                            long num2 = Long.parseLong(subDirs[j].getName());

                            if (num1 < num2) {
                                File temp = subDirs[i];
                                subDirs[i] = subDirs[j];
                                subDirs[j] = temp;
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                }

                // Store the sorted paths
                for (File subDir : subDirs) {
                    try {
                        Long.parseLong(subDir.getName()); // Ensure it's a number
                        dataPaths.add(subDir.getPath());
                    } catch (NumberFormatException e) {
                        // Skip non-numeric folders
                    }
                }
            }
        }

        return dataPaths;
    }

    public String[] getDirectory(String username) {
        String[] directory = new String[] { //Creates a data directory for the specifc user
                mainDirect, //0, main folder for all users
                username, //1, sub folder for specific user
                mainDirect + username //2, full path
        };

        return directory;
    }

    public boolean validateCredentials(String inputUsername, String inputPassword) {
        ArrayList<String[]> credentials = getAllUserCredentials(); //Gets the database of all login info

        for (int i = 0; i < credentials.size(); i++) {
            //Checks if login parameters entered are in the database
            if ((inputUsername.equals(credentials.get(i)[0])) && (inputPassword.equals(credentials.get(i)[1]))) {
                return true;
            }
        }

        return false;
    }

    private ArrayList<String[]> getAllUserCredentials() {
        File mainDir = new File(mainDirect);
        ArrayList<String[]> credentials = new ArrayList<>(); //Makes an ArrayList of all login info

        if (mainDir.exists() && mainDir.isDirectory()) {
            File[] userFolders = mainDir.listFiles(File::isDirectory); //Files that are directories

            if (userFolders != null) {
                for (File userFolder : userFolders) {
                    String directory = userFolder.getPath();
                    File[] loginFiles = new File[]{ //Login info of specific user
                            new File(directory + "/login_username.txt"),
                            new File(directory + "/login_password.txt")
                    };

                    if (loginFiles[0].exists() && loginFiles[1].exists()) {
                        Scanner[] readLogin = new Scanner[2];

                        try {
                            readLogin[0] = new Scanner(loginFiles[0]);
                            readLogin[1] = new Scanner(loginFiles[1]);

                            if (readLogin[0].hasNext() && readLogin[1].hasNext()) {
                                String[] userPass = new String[]{
                                        readLogin[0].next().trim(), // Username
                                        readLogin[1].next().trim()  // Password
                                };
                                credentials.add(userPass);
                            }
                        } catch (FileNotFoundException e) {
                        } finally {
                            readLogin[0].close();
                            readLogin[1].close();
                        }
                    }
                }
            }
        }
        return credentials;
    }

    private String clock() {
        long currentSeconds = System.currentTimeMillis() / 1000; //Number of seconds since 1970
        String currentTime = Long.toString(currentSeconds);

        return currentTime; //Returns time as a string
    }
}