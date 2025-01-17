package Model;

import Model.Exceptions.IllegalValueException;
import Model.Exceptions.InvalidStringException;
import Model.Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Pattern;

// possibili eccezioni: InvalidISBNException, NullStringException

public class Book implements Comparable<Book>{
    private final String ISBN;
    private final String title;
    private final String authors;
    private String genre;
    private BigDecimal price;
    private final String publishingHouse;
    private final int publishingYear;
    private BigDecimal discount;
    private String description;
    private int availableCopies;
    private int libroCardPoints;

    // to be used when adding a new book
    public Book (String ISBN, String title, String authors, String genre, BigDecimal price,
                 String description, String publishingHouse, int publishingYear,
                 BigDecimal discount, int availableCopies, int libroCardPoints) throws
            InvalidStringException, IllegalValueException {

        if(!Pattern.matches("[0-9]{3}-[0-9]{2}-[0-9]{5}-[0-9]{2}-[0-9]", ISBN)){
            throw new InvalidStringException();
        }

        if(!genre.equals(GenresEnum.AUTOBIOGRAPHY.toString())
                && !genre.equals(GenresEnum.CRIME_FICTION.toString())
                && !genre.equals(GenresEnum.FANTASY.toString())
                && !genre.equals(GenresEnum.HISTORY.toString())
                && !genre.equals(GenresEnum.NARRATIVE.toString())
                && !genre.equals(GenresEnum.PHILOSOPHY_OF_SCIENCE.toString())
                && !genre.equals(GenresEnum.POLITICS.toString())
                && !genre.equals((GenresEnum.SCIENCE_FICTION.toString()))){
            throw new InvalidStringException();
        }

        if(title.length() == 0 || authors.length() == 0 || genre.toString().length() == 0
            || description.length() == 0 || publishingHouse.length() == 0){
            throw new InvalidStringException();
        }

        if(price.compareTo(new BigDecimal(0)) <= 0 || publishingYear < 1000 ||
            discount.compareTo(new BigDecimal(0)) < 0 || availableCopies < 0 ||
            libroCardPoints < 0){
            throw new IllegalValueException();
        }

        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.genre = genre.toString();
        this.price = price;
        this.publishingHouse = publishingHouse;
        this.publishingYear = publishingYear;
        this.discount = discount;
        this.description = description;
        this.availableCopies = availableCopies;
        this.libroCardPoints = libroCardPoints;
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

    public BigDecimal getPrice(){
        return price;
    }

    public String getPublishingHouse(){
        return publishingHouse;
    }

    public int getPublishingYear(){
        return publishingYear;
    }

    public BigDecimal getDiscount(){
        return discount;
    }

    public String getDescription(){
        return description;
    }

    public int getAvailableCopies(){
        return availableCopies;
    }

    public int getLibroCardPoints(){ return libroCardPoints; }

    // ritorna il numero di copie di questo libro presenti nel carrello di un
    // determinato utente
    public int getQuantity(String email) throws SQLException {
        String sql = "SELECT quantity FROM cart WHERE user = ? AND book = ?";

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        connection.pstmt = connection.conn.prepareStatement(sql);
        connection.pstmt.setString(1, email);
        connection.pstmt.setString(2, this.ISBN);

        connection.rs = connection.pstmt.executeQuery();

        return connection.rs.getInt("quantity");
    }


    @Override
    public int compareTo(Book book) {
        return this.getTitle().compareTo(book.getTitle());
    }
}
