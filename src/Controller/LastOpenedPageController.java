package Controller;

public class LastOpenedPageController {
    private static String lastOpenedPage = null;

    public static void setLastOpenedPage(String path) { lastOpenedPage = path; }

    public static String getLastOpenedPage(){ return lastOpenedPage; }
}
