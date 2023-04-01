package org.vm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReaderService {
    private static final File file = new File("src/main/java/org/vm/data/Readers.csv");
    private final Map<Long, Reader> readers = new HashMap<>();

    public ReaderService() {
        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String data;
            while ((data = br.readLine()) != null) {
                String[] array = data.split(",\\s");
                int[] books = new int[7];
                if (!array[5].equals("N/A")) {
                    int[] current = Arrays.stream(array[5].split(":")).mapToInt(Integer::parseInt).toArray();
                    System.arraycopy(current, 0, books, 0, current.length);
                }
                ReaderStatus status = ReaderStatus.UNKNOWN;

                switch (array[7]) {
                    case "ACTIVE" -> status = ReaderStatus.ACTIVE;
                    case "NON_RENEW" -> status = ReaderStatus.NON_RENEW;
                    case "BANNED" -> status = ReaderStatus.BANNED;
                }

                Reader reader = new Reader(array[0], array[1], array[2].charAt(0), Integer.parseInt(array[3]), Long.parseLong(array[4]), books, array[6], status);
                readers.put(reader.getReaderCard(), reader);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("There is not such file!");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void getReaderInfo(long readerCard) {
        System.out.println(readers.get(readerCard));
    }

    public void getReaderInfo(String name) {
        String[] data = name.split("\\s");
        readers.entrySet()
                .stream()
                .filter(r -> r.getValue().getName().equals(data[0]) && r.getValue().getSurname().equals(data[1]))
                .map(Map.Entry::getKey)
                .forEach(this::getReaderInfo);
    }

    public void search() {
        System.out.println("Enter a reader card or a reader name:");
        String data = Main.readLine();

        if (data.matches("\\d+")) {
            long number = Long.parseLong(data);

            if (searchForReader(Long.parseLong(data)).isPresent()) {
                getReaderInfo(number);
            } else {
                System.out.println("There is not such reader.");
            }
        } else if (data.matches("^[A-Z][a-z]{1,25}\\s[A-Z][a-z]{1,25}$") || data.matches("^[А-Я][а-я]{1,25}\\s[А-Я][а-я]{1,25}$")) {
            if (searchForReader(data).isPresent()) {
                getReaderInfo(data);
            } else {
                System.out.println("There is not such reader.");
            }
        } else {
            System.out.println("Wrong data!");
        }
    }

    public Optional<Reader> searchForReader(long readerCard) {
        return readers.get(readerCard) == null ? Optional.empty() : Optional.of(readers.get(readerCard));
    }

    public Optional<Reader> searchForReader(String names) {
        String[] data = names.split("\\s");
        return readers.values()
                .stream()
                .filter(reader -> reader.getName().equals(data[0]) && reader.getSurname().equals(data[1]))
                .findFirst();
    }

    public void createANewReader() {
        System.out.println("Please, enter a name:");
        String name = Main.readLine();
        System.out.println("Please, enter a surname:");
        String surname = Main.readLine();
        System.out.println("Please, enter a sex [m/f]:");
        char sex = Main.readLine().toUpperCase().charAt(0);
        System.out.println("Please, enter an age:");
        int age = Integer.parseInt(Main.readLine());
        System.out.println("Please, enter an email:");
        String email = Main.readLine();
        Reader reader = new Reader(name, surname, sex, age, email);
        readers.put(reader.getReaderCard(), reader);
    }

    public void changeStatus() {
        Optional<Reader> reader = Optional.empty();

        System.out.println("Enter name or reader card:");
        String data = Main.readLine();

        if (data.matches("\\d+")) {
            reader = searchForReader(Long.parseLong(data));
        } else if(data.matches("^[A-Z][a-z]{1,25}\\s[A-Z][a-z]{1,25}$") || data.matches("^[А-Я][а-я]{1,25}\\s[А-Я][а-я]{1,25}$")) {
            reader = searchForReader(data);
        } else {
            System.out.println("Wrong data!");
        }

        if (reader.isPresent()) {
            System.out.println("Enter \"ACTIVE\", \"NON_RENEW\" or \"BANNED\":");
            data = Main.readLine();
            switch (data.toUpperCase()) {
                case "ACTIVE" -> readers.get(reader.get().getReaderCard()).setReaderStatus(ReaderStatus.ACTIVE);
                case "NON_RENEW" -> readers.get(reader.get().getReaderCard()).setReaderStatus(ReaderStatus.NON_RENEW);
                case "BANNED" -> readers.get(reader.get().getReaderCard()).setReaderStatus(ReaderStatus.BANNED);
                default -> System.out.println("Wrong status!");
            }
        } else {
            System.out.println("There is not such reader!");
        }
    }

    public boolean takeABook(long readerCard, int catalogNumber) {
        if (readers.get(readerCard).getReaderStatus() != ReaderStatus.ACTIVE) {
            System.out.println("The reader cannot take books!");
            return false;
        } else {
            if (Arrays.stream(readers.get(readerCard).getBooksTaken()).filter(b -> b > 0).count() == 7) {
                System.out.println("The reader has taken the maximum number of books");
                return false;
            } else {
                readers.get(readerCard).setBooksTaken(catalogNumber);
                return true;
            }
        }
    }

    public void returnTheBook(long readerCard, int catalogNumber) {
        readers.get(readerCard).removeBook(catalogNumber);
    }

    public void close() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            for (Map.Entry<Long, Reader> entry : readers.entrySet()) {
                Reader rd = entry.getValue();
                bw.write(rd.getName());
                bw.write(", ");
                bw.write(rd.getSurname());
                bw.write(", ");
                bw.write(rd.getSex());
                bw.write(", ");
                bw.write(Integer.toString(rd.getAge()));
                bw.write(", ");
                bw.write(Long.toString(rd.getReaderCard()));
                bw.write(", ");
                int [] current = rd.getBooksTaken();
                if (Arrays.stream(current).filter(n -> n > 0).count() == 0) {
                    bw.write("N/A");
                    bw.write(", ");
                } else {
                    String temporary = Arrays.stream(current).filter(b -> b > 0)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining(":"));
                    bw.write(temporary);
                    bw.write(", ");
                }
                bw.write(rd.getEmail());
                bw.write(", ");
                bw.write(rd.getReaderStatus().toString());
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
