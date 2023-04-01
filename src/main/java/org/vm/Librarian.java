package org.vm;

import java.util.Objects;

public class Librarian extends Person {
    private final String librarianCode;

    public Librarian(String name, String surname, char sex, int age, String librarianCode) {
        super(name, surname, sex, age);
        this.librarianCode = librarianCode;
    }

    public String getLibrarianCode() {
        return librarianCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Librarian librarian = (Librarian) obj;
        return Objects.equals(librarianCode, librarian.librarianCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), librarianCode);
    }
}
