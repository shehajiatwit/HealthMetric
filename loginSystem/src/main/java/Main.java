public class Main {
    /**
     * Author: Ilgert Shehaj
     * @param args
     * Main program where the whole code flow starts
     * Login -> Welcome page -> Display Options
     */
    public static void main(String[] args) {
        IDandPasswords idandPasswords = new IDandPasswords();
        LoginPage loginPage = new LoginPage(idandPasswords.getLoginInfo());

    }
}
