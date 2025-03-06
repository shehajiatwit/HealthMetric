import javax.swing.*;
import java.awt.*;

public class UserInfo {
    JFrame frame;
    //fix in further development
    JLabel userLabel = new JLabel("Helo (username)");

    public UserInfo(){
        frame = new JFrame();
        frame.setLayout(new FlowLayout());

        userLabel.setFont(new Font(null, Font.BOLD, 25));
        frame.add(userLabel);

        frame.setVisible(true);
        frame.setSize(500,500);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new UserInfo();
    }
}
