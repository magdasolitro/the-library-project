package Model;

public class Rankings {

    private String book;
    private int soldCopies;
    private int currentPosition;
    private int weeksInPosition;

    public Rankings(String book){
        this.book = book;
        this.soldCopies = 0;
        this.currentPosition = 0;
        this.weeksInPosition = 0;
    }

    public Rankings(String book, int soldCopies, int currentPosition, int weeksInPosition){
        this.book = book;
        this.soldCopies = soldCopies;
        this.currentPosition = currentPosition;
        this.weeksInPosition = weeksInPosition;
    }


    public String getBook(){
        return book;
    }

    public int getSoldCopies(){
        return soldCopies;
    }

    public int getCurrentPosition(){ return currentPosition; }

    public int getWeeksInPosition(){ return weeksInPosition; }
}
