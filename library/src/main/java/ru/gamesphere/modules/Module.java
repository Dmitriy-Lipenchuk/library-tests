package ru.gamesphere.modules;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import org.jetbrains.annotations.NotNull;
import ru.gamesphere.annotations.FileName;
import ru.gamesphere.factories.BooksFactory;
import ru.gamesphere.factories.FileBookFactory;

public class Module extends AbstractModule {
    @NotNull
    private final String path;

    public Module(@NotNull String path) {
        this.path = path;
    }

    @Override
    protected void configure() {
        bind(BooksFactory.class).to(FileBookFactory.class);
        bind(String.class).annotatedWith(FileName.class).toInstance(path);
        bind(Gson.class).toInstance(new Gson());
    }
}
