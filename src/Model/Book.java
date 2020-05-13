package Model;

public class Book {
    public String ISBN;
    public String title;
    public String authors;
    public String genre;
    public float price;
    public String publishingHouse;
    public int publishingYear;
    public float discount;
    public String description;
    public int availableCopies;

    public Book (String ISBN, String title, String authors, String genre, float price,
                 String description, String publishingHouse, int publishingYear,
                 float discount, int availableCopies){
        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.genre = genre;
        this.price = price;
        this.publishingHouse = publishingHouse;
        this.publishingYear = publishingYear;
        this.discount = discount;
        this.description = description;
        this.availableCopies = availableCopies;
    }

    public String getISBN(){
        return ISBN;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthors(){
        return authors;
    }

    public String getGenre(){
        return genre;
    }

    public float getPrice(){
        return price;
    }

    public String getPublishingHouse(){
        return publishingHouse;
    }

    public int getPublishingYear(){
        return publishingYear;
    }

    public float getDiscount(){
        return discount;
    }

    public String getDescription(){
        return description;
    }

    public int getAvailableCopies(){
        return availableCopies;
    }
}
