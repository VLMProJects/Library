package org.vm;

import java.util.Optional;

public class LibraryService {
    private final Library library = new Library();
    private final ReaderService readerService = new ReaderService();

    public void execute(String command) {
        switch (command) {
            case "1" -> {
                readerService.search();
            }
            case "2" -> {
                readerService.createANewReader();
            }
            case "3" -> {
                readerService.changeStatus();
            }
            case "4" -> {
                takeBooks();
            }
            case "5" -> {
                returnBooks();
            }
            case "6" -> {
                library.searchFor();
            }
            case "7" -> {
                library.addABook();
            }
            case "8" -> {
                library.changeTheBookStatus();
            }
            default -> System.out.println("Wrong command!");
        }
    }

    public void takeBooks() {
        String data;
        Optional<Reader> reader;

        while (true) {
            System.out.println("Enter a reader card number or \"END\"");
            data = Main.readLine();

            if (data.equals("END")) {
                return;
            }

            if (data.matches("\\d+")) {
                reader = readerService.searchForReader(Long.parseLong(data));
                if (reader.isPresent()) {
                    break;
                }
            }
        }

        while (true) {
            System.out.println("Enter a catalog number or \"END\"");
            data = Main.readLine();

            if (data.equals("END")) {
                return;
            }

            if (data.matches("\\d+")) {
                int catalogNumber = Integer.parseInt(data);
                boolean status = library.checkIsTheBookAvailable(catalogNumber);

                if (status) {
                    boolean result = readerService.takeABook(reader.get().getReaderCard(), catalogNumber);
                    if (result) library.takeLibraryUnit(catalogNumber);
                }
            }
        }
    }

    public void returnBooks() {
        String data;
        Optional<Reader> reader;

        while (true) {
            System.out.println("Enter a reader card number or \"END\"");
            data = Main.readLine();

            if (data.equals("END")) {
                return;
            }

            if (data.matches("\\d+")) {
                reader = readerService.searchForReader(Long.parseLong(data));
                if (reader.isPresent()) {
                    break;
                }
            }
        }

        while (true) {
            System.out.println("Enter a catalog number or \"END\"");
            data = Main.readLine();

            if (data.equals("END")) {
                return;
            }

            int catalogNumber = library.checkACatalogNumber(Integer.parseInt(data));

            if (catalogNumber > -1) {
                readerService.returnTheBook(reader.get().getReaderCard(), catalogNumber);
                library.returnLibraryUnit(catalogNumber);
            }
        }
    }

    public void close() {
        library.close();
        readerService.close();
    }
}
