package ru.gamesphere;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.gamesphere.factories.FileBookFactory;
import ru.gamesphere.factories.LibraryFactory;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.params.provider.Arguments.*;

class LibraryTest extends AbstractTest {

    @Inject
    @NotNull
    private LibraryFactory libraryFactory;

    @Inject
    @NotNull
    private FileBookFactory fileBookFactory;

    @Inject
    @NotNull
    private Gson gson;


    @Test
    void createLibraryWrongCapacityTest() {
        assertThrows(IllegalArgumentException.class, () -> libraryFactory.library(0));
        assertThrows(IllegalArgumentException.class, () -> libraryFactory.library(1));
        assertThrows(IllegalArgumentException.class, () -> libraryFactory.library(99));
        assertThrows(IllegalArgumentException.class, () -> libraryFactory.library(-100));
    }

    @Test
    void createLibraryCorrectCapacityTest() {
        assertDoesNotThrow(() -> libraryFactory.library(100));
        assertDoesNotThrow(() -> libraryFactory.library(101));
        assertDoesNotThrow(() -> libraryFactory.library(10000));
    }

    @Test
    void correctBookOrderTest() {
        Library library = libraryFactory.library(110);
        ArrayList<Book> arrayList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            arrayList.add(library.getBook(i));
        }

        assertEquals(arrayList, fileBookFactory.books());
    }

    @ParameterizedTest
    @MethodSource("argsForBookTest")
    void getBookConsoleOutputTest(int index, int bookNumber, int authorNumber) {
        libraryFactory.library(100).getBook(index);
        String toAssert = String.format("Cell number %d %s",
                index, gson.toJson(new Book("Book " + bookNumber, new Author("Author" + authorNumber))));

        assertEquals(toAssert, outputStream.toString().trim());
    }

    static Stream<Arguments> argsForBookTest() {
        return Stream.of(
                arguments(0, 0, 0),
                arguments(1, 1, 0),
                arguments(2, 2, 0),
                arguments(4, 4, 0),
                arguments(5, 5, 0),
                arguments(6, 6, 0),
                arguments(7, 7, 0),
                arguments(8, 8, 0),
                arguments(9, 9, 0),
                arguments(10, 0, 1),
                arguments(11, 1, 1),
                arguments(12, 2, 1),
                arguments(13, 3, 1),
                arguments(14, 4, 1)
        );
    }

    @Test
    void getBookFromEmptyCellTest() {
        Library library = libraryFactory.library(100);
        assertThrows(IndexOutOfBoundsException.class, () -> library.getBook(100));
        assertThrows(IndexOutOfBoundsException.class, () -> library.getBook(999));
        assertThrows(IndexOutOfBoundsException.class, () -> library.getBook(-1));

        library.getBook(0);
        library.getBook(10);
        library.getBook(99);
        assertThrows(IllegalArgumentException.class, () -> library.getBook(0));
        assertThrows(IllegalArgumentException.class, () -> library.getBook(10));
        assertThrows(IllegalArgumentException.class, () -> library.getBook(99));
    }

    @Test
    void getBookFromNonEmptyCellTest() {
        assertDoesNotThrow(() -> libraryFactory.library(100).getBook(0));
        assertDoesNotThrow(() -> libraryFactory.library(100).getBook(50));
        assertDoesNotThrow(() -> libraryFactory.library(100).getBook(99));
    }

    @Test
    void addBookToFirstEmptyCellTest() {
        Library library = libraryFactory.library(101);
        Book emptyBook = new Book("", new Author(""));

        library.addBook(emptyBook);
        assertEquals(library.getBook(100), emptyBook);

        library.getBook(0);
        library.addBook(emptyBook);
        assertEquals(library.getBook(0), emptyBook);
    }

    @Test
    void addBookNoSpaceTest() {
        Library library = libraryFactory.library(100);
        assertThrows(IllegalStateException.class, () -> library.addBook(new Book("", new Author(""))));
    }

    @Test
    void printAlBooksTest() {
        libraryFactory.library(100).printAllBooks();
        assertEquals(gson.toJson(fileBookFactory.books()), outputStream.toString().trim());
    }
}