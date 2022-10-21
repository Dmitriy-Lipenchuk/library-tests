package ru.gamesphere.factories;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import ru.gamesphere.Library;

import javax.inject.Inject;

public class LibraryFactory {
    @NotNull
    private final BooksFactory booksFactory;

    @NotNull
    private final Gson gson;

    @Inject
    public LibraryFactory(@NotNull BooksFactory booksFactory, @NotNull Gson gson) {
        this.booksFactory = booksFactory;
        this.gson = gson;
    }

    public Library library(int capacity) {
        return new Library(capacity, booksFactory, gson);
    }
}
