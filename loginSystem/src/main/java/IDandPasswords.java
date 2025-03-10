import java.util.HashMap;

/**
 * Author: Ilgert Shehaj
 * Very simple hard coded usernames and passwords
 * There is no security or authentication for simplicity
 */
public class IDandPasswords {

    HashMap<String , String> loginInfo = new HashMap<>();

    IDandPasswords() {

        loginInfo.put("ilgert", "123");
        loginInfo.put("abia", "123");
        loginInfo.put("kevin", "123");
        

    }

    protected HashMap getLoginInfo(){
        return loginInfo;
    }

}
