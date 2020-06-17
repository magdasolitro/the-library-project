package Controller;

public class GeneralLoginController {

    private static String loginInstance = null;


    public static void setLoginInstance(String email){ loginInstance = email; }

    public static String getLoginInstance(){
        return loginInstance;
    }

    public static boolean isLogged() { return loginInstance != null; }

    public static void logout(){
        loginInstance = null;
    }


}
