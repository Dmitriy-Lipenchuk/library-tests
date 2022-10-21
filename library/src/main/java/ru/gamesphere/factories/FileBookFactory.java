package ru.gamesphere.factories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import ru.gamesphere.Book;
import ru.gamesphere.annotations.FileName;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class FileBookFactory implements BooksFactory {
    @NotNull
    private static final Type listBooksType = new TypeToken<ArrayList<Book>>() {
    }.getType();

    @NotNull
    private final String fileName;

    @Inject
    public FileBookFactory(@NotNull @FileName String fileName) {
        this.fileName = fileName;
    }

    @NotNull
    @Override
    public List<Book> books() {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), listBooksType);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
