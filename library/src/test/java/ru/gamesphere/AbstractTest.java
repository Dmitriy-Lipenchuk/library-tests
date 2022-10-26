package ru.gamesphere;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.gamesphere.modules.Module;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

abstract class AbstractTest {
    @NotNull
    protected final Injector injector = Guice.createInjector(new Module(System.getProperty("user.dir") + "/src/test/resources/books.txt"));

    @NotNull
    private final PrintStream standardOut = System.out;

    @NotNull
    protected final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setup() {
        injector.injectMembers(this);
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
