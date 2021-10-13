import java.sql.SQLOutput;

public class Book {
    private String author;
    private String title;
    private double price;

    Book(String title, String author, double price) {
        this.author = author;
        this.title = title;
        this.price = price;
    }

    Book() {
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public void getInfo() {
        System.out.println("Book");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Price: " + price);
        System.out.println("=================");
    }


}
