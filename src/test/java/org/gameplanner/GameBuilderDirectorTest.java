package org.gameplanner;

import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class GameBuilderDirectorTest {

    @Test
    void buildGameWorksCorrectly() {
        GameBuilderDirector director = new GameBuilderDirector();
        Game actualGame = director.buildGame("Grand Theft Auto V", Genre.ACTION, "Rockstar Games", Year.of(2013), Status.COMPLETED_100);
        Game expected = new Game("Grand Theft Auto V", Genre.ACTION, "Rockstar Games", Year.of(2013), Status.COMPLETED_100);

        // Not testing the ID/GUID because it is (theoretically) unique for each Game object.
        assertEquals(expected.getTitle(), actualGame.getTitle());
        assertEquals(expected.getGenre(), actualGame.getGenre());
        assertEquals(expected.getPublisher(), actualGame.getPublisher());
        assertEquals(expected.getReleaseYear(), actualGame.getReleaseYear());
        assertEquals(expected.getStatus(), actualGame.getStatus());
    }

}