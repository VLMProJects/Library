package org.vm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class Counter {
    private static final File file = new File("src/main/java/org/vm/data/Counter.csv");

    public static int getANewCatalogNumber() {
        long[] temporary = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            temporary = Arrays.stream(br.readLine().split(", ")).mapToLong(Long::parseLong).toArray();
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            if (Objects.nonNull(temporary)) {
                bw.write(Long.toString(temporary[0] + 1L));
                bw.write(", ");
                bw.write(Long.toString(temporary[1]));
            }
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return temporary == null ? -1 : (int)temporary[0];
    }

    public static long getANewReaderNumber() {
        long[] temporary = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            temporary = Arrays.stream(br.readLine().split(", ")).mapToLong(Long::parseLong).toArray();
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            if (Objects.nonNull(temporary)) {
                bw.write(Long.toString(temporary[0]));
                bw.write(", ");
                bw.write(Long.toString(temporary[1] + 1L));
            }
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return temporary == null ? -1 : temporary[1];
    }
}
