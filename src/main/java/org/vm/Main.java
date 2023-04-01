package org.vm;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * @author Vladimir Metodiev
 */

public class Main {
    private static final Scanner SC = new Scanner(System.in, StandardCharsets.UTF_8);
    private static final Supplier<String> ENTER_ID = () -> "Enter your librarian ID or type \"END\":";
    private static final StringBuilder SB = new StringBuilder()
            .append("| CHOOSE THE COMMAND NUMBER                                 |").append(System.lineSeparator())
            .append("| READER                       | BOOK                       |").append(System.lineSeparator())
            .append("| Is the reader available? [1] | Is the book available? [6] |").append(System.lineSeparator())
            .append("| Add a reader             [2] | Add a book             [7] |").append(System.lineSeparator())
            .append("| Change the reader status [3] | Change the book status [8] |").append(System.lineSeparator())
            .append("| Take a book              [4] |                            |").append(System.lineSeparator())
            .append("| Return the book          [5] | END                        |");

    public static void main(String[] args) {
        LibraryService libraryService;
        String command;

        System.out.println(ENTER_ID.get());
        // LIB2-523792
        while (!(command = SC.nextLine()).equals("END")) {
            if (!LibrarianService.checkID(command)) {
                System.out.println(ENTER_ID.get());
                continue;
            }

            libraryService = new LibraryService();
            System.out.println(SB);

            while (!(command = SC.nextLine()).equals("END")) {
                try {
                    if (!command.matches("^\\d$")) {
                        throw new IllegalArgumentException("This is not a command number!");
                    }

                    if (Integer.parseInt(command) == 0) LibrarianService.createNewID();
                    else libraryService.execute(command);
                } catch (IllegalArgumentException ex) {
                    System.err.println(ex.getMessage());
                }

                System.out.println(SB);
            }

            libraryService.close();
            SC.close();
            System.exit(0);
        }
    }

    public static String readLine() {
        return SC.nextLine();
    }
}
