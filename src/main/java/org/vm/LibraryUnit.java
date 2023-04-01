package org.vm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LibraryUnit {
    private final int catalogNumber;
    private Book book;
    private BookStatus status;
    private LocalDate takenOnDate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LibraryUnit(Book book) {
        int temporary = Counter.getANewCatalogNumber();
        if (temporary < 1) throw new IllegalArgumentException("A catalog number was not generated.");
        this.catalogNumber = temporary;
        this.book = book;
        this.status = BookStatus.AVAILABLE;
    }

    public LibraryUnit(int catalogNumber, Book book, BookStatus status, LocalDate takenOnDate) {
        this.catalogNumber = catalogNumber;
        this.book = book;
        this.status = status;
        this.takenOnDate = takenOnDate;
    }

    public int getCatalogNumber() {
        return catalogNumber;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        if (status == BookStatus.AVAILABLE) takenOnDate = null;
        this.status = status;
    }

    public LocalDate getTakenOnDate() {
        return takenOnDate;
    }

    public void setTakenOnDate(LocalDate takenOnDate) {
        if (status != BookStatus.AVAILABLE) throw new IllegalArgumentException("The book has not been taken.");
        this.takenOnDate = takenOnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryUnit that = (LibraryUnit) o;
        return catalogNumber == that.catalogNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalogNumber);
    }

    @Override
    public String toString() {
        return String.format("%d%n%s%n%s%n%s%n", catalogNumber, book, status.toString(), Objects.isNull(takenOnDate) ? "N/A" : formatter.format(takenOnDate));
    }
}
