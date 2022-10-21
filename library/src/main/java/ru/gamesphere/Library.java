package ru.gamesphere;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import ru.gamesphere.factories.BooksFactory;

import java.util.List;
import java.util.PriorityQueue;

public class Library {
    private final int capacity;

    @NotNull
    private final PriorityQueue<Integer> freeIndexes = new PriorityQueue<>();

    @NotNull
    private final List<Book> books;

    @NotNull
    private final Gson gson;

    public Library(int capacity, @NotNull BooksFactory booksFactory, @NotNull Gson gson) {
        List<Book> books = booksFactory.books();

        if (books.size() > capacity) {
            throw new IllegalArgumentException("Number of cell's must be equal or bigger than number of books");
        }

        this.capacity = capacity;
        this.books = books;
        this.gson = gson;
    }

    public Book getBook(int index) {
        if (index >= this.capacity || index < 0) {
            throw new IndexOutOfBoundsException("Cell number " + index + " out of bounds for length " + books.size());
        }

        if (index >= books.size() || books.get(index) == null) {
            throw new IllegalArgumentException("Cell is empty");
        }

        System.out.println("Cell number " + index + " " + gson.toJson(books.get(index)));

        freeIndexes.add(index);

        return books.set(index, null);
    }

    public void addBook(@NotNull Book book) {
        if (books.size() >= capacity && freeIndexes.isEmpty()) {
            throw new IllegalStateException("All cell's are used");
        }

        if (freeIndexes.isEmpty()) {
            books.add(book);
        } else {
            books.add(freeIndexes.poll(), book);
        }
    }

    public void printAllBooks() {
        System.out.println(gson.toJson(books));
    }
}
