package org.vm;

import java.util.Arrays;
import java.util.Objects;

public class Reader extends Person {
    private final long readerCard;
    private int[] booksTaken = new int[7];
    private String email;
    private ReaderStatus readerStatus;

    public Reader(String name, String surname, char sex, int age, String email) {
        super(name, surname, sex, age);
        long temporary = Counter.getANewReaderNumber();
        if (temporary == -1) throw new IllegalArgumentException("A reader card number was not generated.");
        this.readerCard = temporary;
        this.email = email;
        this.readerStatus = ReaderStatus.ACTIVE;
        System.out.printf("Your reader card number is: %d%n", temporary);
    }

    public Reader(String name, String surname, char sex, int age, long readerCard, int[] booksTaken, String email, ReaderStatus readerStatus) {
        super(name, surname, sex, age);
        this.readerCard = readerCard;
        this.booksTaken = booksTaken;
        this.email = email;
        this.readerStatus = readerStatus;
    }

    public long getReaderCard() {
        return readerCard;
    }

    public int[] getBooksTaken() {
        return booksTaken;
    }

    public void setBooksTaken(int catalogNumber) {
        boolean flag = false;
        for (int a = 0; a < booksTaken.length; a++) {
            if (booksTaken[a] == 0) {
                booksTaken[a] = catalogNumber;
                flag = true;
                break;
            }
        }
        if (flag) System.out.println("The book has been added.");
        else System.out.println("The book has not been added.");
    }

    public void removeBook(int catalogNumber) {
        boolean flag = false;
        for (int a = 0; a < booksTaken.length; a++) {
            if (booksTaken[a] == catalogNumber) {
                booksTaken[a] = 0;
                flag = true;
                break;
            }
        }
        if (flag) System.out.println("The book has been removed.");
        else System.out.println("The book has not been  removed.");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ReaderStatus getReaderStatus() {
        return readerStatus;
    }

    public void setReaderStatus(ReaderStatus readerStatus) {
        this.readerStatus = readerStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Reader reader = (Reader) obj;
        return readerCard == reader.readerCard;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), readerCard);
    }

    @Override
    public String toString() {
        return String.format("Reader card: %d%nReader: %s%nBook(-s): %s%nE-mail: %s%nReader status: %s",
                readerCard,
                super.toString(),
                Arrays.stream(booksTaken).filter(b -> b > 0).count() == 0 ?
                        "The reader didn't take any books." :
                        String.format("The reader has taken %d book(-s).", Arrays.stream(booksTaken).filter(b -> b > 0).count()),
                email,
                readerStatus.toString());
    }
}
