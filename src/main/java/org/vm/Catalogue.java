package org.vm;

import java.util.Arrays;
import java.util.Map;

public class Catalogue {
    private final Map<Integer, LibraryUnit> catalogue;

    public Catalogue() {
        catalogue = InventoryService.uploadCatalog();
    }

    public Map<Integer, LibraryUnit> getCatalogue() {
        return catalogue;
    }

    public void put(LibraryUnit unit) {
        if (catalogue.containsKey(unit.getCatalogNumber())) {
            throw new IllegalArgumentException("The book can not be added.");
        } else {
            catalogue.put(unit.getCatalogNumber(), unit);
        }
    }

    public int[] searchByExactTitle(String title) {
        return catalogue.entrySet()
                .stream()
                .filter(b -> b.getValue().getBook().getTitle().equals(title))
                .mapToInt(Map.Entry::getKey)
                .toArray();
    }

    public int[] searchForTitle(String title) {
        return catalogue.entrySet()
                .stream()
                .filter(b -> b.getValue().getBook().getTitle().contains(title))
                .mapToInt(Map.Entry::getKey)
                .toArray();
    }

    public int[] searchByExactName(String names) {
        return catalogue.entrySet()
                .stream()
                .filter(b -> Arrays.stream(b.getValue().getBook().getAuthors())
                        .filter(n -> n.equals(names))
                        .anyMatch(obj -> true))
                .mapToInt(Map.Entry::getKey)
                .toArray();
    }

    public int[] searchForName(String names) {
        return catalogue.entrySet()
                .stream()
                .filter(b -> Arrays.stream(b.getValue().getBook().getAuthors())
                        .filter(n -> n.contains(names))
                        .anyMatch(obj -> true))
                .mapToInt(Map.Entry::getKey)
                .toArray();
    }

    public void printTheBook(int catalogNumber) {
        catalogue.entrySet()
                .stream()
                .filter(c -> c.getKey() == catalogNumber)
                .forEach(b -> System.out.println(b.getValue()));
    }

    public void finish() {
        InventoryService.writeCatalogue(catalogue);
    }
}
