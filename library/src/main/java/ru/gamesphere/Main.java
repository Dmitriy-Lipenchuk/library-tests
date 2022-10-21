package ru.gamesphere;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.gamesphere.factories.LibraryFactory;
import ru.gamesphere.modules.Module;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Number of args must be 2");
        }

        Injector injector = Guice.createInjector(new Module(args[0]));
        Library library = injector.getInstance(LibraryFactory.class).library(Integer.parseInt(args[1]));
        library.printAllBooks();
    }
}
