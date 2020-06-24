package Controller;

public class BookInstanceController {
    private static String currentBookISBN = null;

    public static void setCurrentBookISBN(String ISBN){
        currentBookISBN = ISBN;
    }

    public static String getCurrentBookISBN(){
        return currentBookISBN;
    }

    public static void exitView(){
        currentBookISBN = null;
    }
}
