package org.gameplanner;

import java.time.Year;

public class GameBuilderDirector {

    public Game buildGame(String title, Genre genre, String publisher, Year releaseYear, Status status) {
        return new ConcreteGameBuilder(title, genre, publisher, releaseYear, status).buildGame();
    }
}
