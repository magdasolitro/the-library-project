package Model;

public class Ranking {

    private String book;
    private int soldCopies;
    private int weeksInPosition;

    public Ranking(String book){
        this.book = book;
        this.soldCopies = 0;
    }

    public Ranking(String book, int soldCopies){
        this.book = book;
        this.soldCopies = soldCopies;
    }


    public String getBook(){
        return book;
    }

    public int getSoldCopies(){
        return soldCopies;
    }
}
