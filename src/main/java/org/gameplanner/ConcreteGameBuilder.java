package org.gameplanner;

import java.time.Year;

public class ConcreteGameBuilder implements GameBuilder {
    private final String title;
    private final Genre genre;
    private final String publisher;
    private final Year releaseYear;
    private final Status status;

    public ConcreteGameBuilder(String title, Genre genre, String publisher, Year releaseYear, Status status) {
        this.title = title;
        this.genre = genre;
        this.publisher = publisher;
        this.releaseYear = releaseYear;
        this.status = status;
    }

    @Override
    public Game buildGame() {
        return new Game(title, genre, publisher, releaseYear, status);
    }

}
