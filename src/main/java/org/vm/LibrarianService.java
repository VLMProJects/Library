package org.vm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

public class LibrarianService {
    private static final File file = new File("src/main/java/org/vm/data/Librarians.csv");

    protected static void createNewID() {
        String[] temporary = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            temporary = br.lines().toArray(String[]::new);
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            if (Objects.nonNull(temporary)) {
                int current = Integer.parseInt(temporary[0]) + 1;

                bw.write(Integer.toString(current));
                bw.newLine();

                for (int a = 1; a < temporary.length; a++) {
                    bw.write(temporary[a]);
                    bw.newLine();
                }

                String newID = String.format("LIB%d-%06d", current, new Random().nextInt(1000000));

                bw.write(newID);
                bw.newLine();

                System.out.printf("The new librarian ID is: %s%n", newID);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    protected static void deleteID(String id) {
        String[] temporary = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            temporary = br.lines().toArray(String[]::new);
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            if (Objects.nonNull(temporary)) {
                for (int a = 1; a < temporary.length; a++) {
                    if (temporary[a].equals(id)) {
                        temporary[a] = null;
                        System.out.println("The librarian id was successfully deleted.");
                        break;
                    }
                }

                for (int b = 0; b < temporary.length; b++) {
                    bw.write(temporary[b]);
                    bw.newLine();
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    protected static boolean checkID(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String[] temporary = br.lines().toArray(String[]::new);

            for (int a = 1; a < temporary.length; a++) {
                if (temporary[a].equals(id)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
}
