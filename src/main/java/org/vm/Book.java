package org.vm;

import java.util.Arrays;

public class Book {
    private String title;
    private String[] authors;
    private String publisher;
    private String isbn;
    private int year;

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, String[] authors) {
        this(title);
        this.authors = authors;
    }

    public Book(String title, String[] authors, String publisher, int year) {
        this(title, authors);
        this.publisher = publisher;
        this.year = year;
    }

    public Book(String title, String[] authors, String publisher, String isbn, int year) {
        this(title, authors);
        this.publisher = publisher;
        this.isbn = isbn;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format("%s%n%s%n%s%n%s%n%d",
                title,
                authors.length == 0 ? "Unknown" : authors.length == 1 ? authors[0] : Arrays.toString(authors),
                publisher,
                isbn,
                year);
    }
}
