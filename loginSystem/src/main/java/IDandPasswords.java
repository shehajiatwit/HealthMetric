import java.util.HashMap;

public class IDandPasswords {

    HashMap<String , String> loginInfo = new HashMap<>();

    IDandPasswords() {

        loginInfo.put("ilgert", "123");
        

    }

    protected HashMap getLoginInfo(){
        return loginInfo;
    }

}
