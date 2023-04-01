package org.vm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private static final File file = new File("src/main/java/org/vm/data/LibraryUnits.csv");

    public static Map<Integer, LibraryUnit> uploadCatalog() {
        Map<Integer, LibraryUnit> catalogue = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String data;

            while ((data = br.readLine()) != null) {
                String[] array = data.split(",\\s");
                Book book = new Book(array[1], (array[2].equals("N/A") ? new String[0] : array[2].split(":")), array[3], array[4], Integer.parseInt(array[5]));
                BookStatus status = BookStatus.UNKNOWN;

                switch (array[6]) {
                    case "AVAILABLE" -> status = BookStatus.AVAILABLE;
                    case "TAKEN" -> status = BookStatus.TAKEN;
                    case "REPAIR" -> status = BookStatus.REPAIR;
                }

                LibraryUnit unit = new LibraryUnit(Integer.parseInt(array[0]), book, status, array[7].equals("N/A") ? null : LocalDate.parse(array[7]));
                catalogue.put(unit.getCatalogNumber(), unit);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return catalogue;
    }

    public static void writeCatalogue(Map<Integer, LibraryUnit> catalogue) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            for (int key : catalogue.keySet()) {
                LibraryUnit lw = catalogue.get(key);
                Book bk = catalogue.get(key).getBook();
                String aut = Arrays.equals(bk.getAuthors(), new String[0]) ? "N/A" : String.join(":", bk.getAuthors());
                bw.write(Integer.toString(lw.getCatalogNumber()));
                bw.write(", ");
                bw.write(bk.getTitle());
                bw.write(", ");
                bw.write(aut);
                bw.write(", ");
                bw.write(bk.getPublisher());
                bw.write(", ");
                bw.write(bk.getIsbn());
                bw.write(", ");
                bw.write(Integer.toString(bk.getYear()));
                bw.write(", ");
                bw.write(lw.getStatus().toString());
                bw.write(", ");
                bw.write(lw.getTakenOnDate() == null ? "N/A" : lw.getTakenOnDate().toString());
                bw.newLine();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
