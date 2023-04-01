package org.vm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Library {
    private final Catalogue catalogue = new Catalogue();

    public void searchFor() {
        System.out.println("Choose: \"By Title\" or \"By Author\":");
        String condition = Main.readLine();

        switch (condition.toLowerCase()) {
            case "by title" -> {
                System.out.println("Enter a title:");
                String title = Main.readLine();

                int[] numbers = catalogue.searchByExactTitle(title);

                if (numbers.length == 0) {
                    numbers = catalogue.searchForTitle(title);
                }

                if (numbers.length > 0) {
                    Arrays.stream(numbers).forEach(catalogue::printTheBook);
                } else {
                    System.out.println("There is not such title!");
                }
            }
            case "by author" -> {
                System.out.println("Enter an author:");
                String authorNames = Main.readLine();

                int[] numbers = catalogue.searchByExactName(authorNames);

                if (numbers.length == 0) {
                    numbers = catalogue.searchForName(authorNames);
                }

                if (numbers.length > 0) {
                    Arrays.stream(numbers).forEach(catalogue::printTheBook);
                } else {
                    System.out.println("There is not such title!");
                }
            }
            default -> System.out.println("Wrong condition!");
        }
    }

    public void addABook() {
        System.out.println("Enter a title:");
        String title = Main.readLine();

        List<String> current = new ArrayList<>();
        while (true) {
            System.out.println("Enter an author or \"END\":");
            String data = Main.readLine();
            if (data.equals("END")) {
                break;
            }
            current.add(data);
        }

        String[] authors = current.toArray(String[]::new);

        System.out.println("Enter a publisher:");
        String publisher = Main.readLine();

        System.out.println("Enter ISBN:");
        String isbn = Main.readLine();

        System.out.println("Enter a year:");
        String data = Main.readLine();
        int year = -1;
        if (data.matches("\\d{4}")) {
            year = Integer.parseInt(data);
        }

        System.out.println("If you want to add this book type Y, otherwise N:");
        if (Main.readLine().equals("N")) {
            return;
        }

        Book book = new Book(title, authors, publisher, isbn, year);
        LibraryUnit libraryUnit = new LibraryUnit(book);
        catalogue.put(libraryUnit);
    }

    public void changeTheBookStatus() {
        System.out.println("Enter a catalog number:");
        String number = Main.readLine();

        if (number.matches("\\d+") && catalogue.getCatalogue().containsKey(Integer.parseInt(number))) {
            int num = Integer.parseInt(number);
            System.out.printf("The current status of the book with catalog number %d is: %s%n", num, catalogue.getCatalogue().get(num).getStatus().toString());

            System.out.println("Choose: \"AVAILABLE\", \"TAKEN\", \"REPAIR\" or \"UNKNOWN\":");
            String status = Main.readLine().toUpperCase();

            switch (status) {
                case "AVAILABLE" -> change(num, BookStatus.AVAILABLE);
                case "TAKEN" -> change(num, BookStatus.TAKEN);
                case "REPAIR" -> change(num, BookStatus.REPAIR);
                case "UNKNOWN" -> change(num, BookStatus.UNKNOWN);
                default -> System.out.println("Wrong status!");
            }
        } else {
            System.out.println("There is not such catalog number.");
        }
    }

    private void change(int number, BookStatus status) {
        catalogue.getCatalogue().get(number).setStatus(status);
    }

    public int checkACatalogNumber(int number) {
        return catalogue.getCatalogue().containsKey(number) ? number : -1;
    }

    public void returnLibraryUnit(int number) {
        change(number, BookStatus.AVAILABLE);
    }

    public boolean checkIsTheBookAvailable(int number) {
        return catalogue.getCatalogue().containsKey(number) && catalogue.getCatalogue().get(number).getStatus() == BookStatus.AVAILABLE;
    }

    public void takeLibraryUnit(int number) {
        // За да спестя въвеждането на информация, реших да поставям ДАТАТА по този начин.
        catalogue.getCatalogue().get(number).setTakenOnDate(LocalDate.now());
        change(number, BookStatus.TAKEN);
    }

    public void close() {
        catalogue.finish();
    }
}
