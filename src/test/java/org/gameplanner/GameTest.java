package org.gameplanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game("Grand Theft Auto V", Genre.ACTION, "Rockstar Games", Year.of(2013), Status.COMPLETED_100);
    }

    @Test
    void testNewGameIsGivenUniqueId() {
        Game anotherGame = new Game("Grand Theft Auto V", Genre.ACTION, "Rockstar Games", Year.of(2013), Status.COMPLETED_100);
        assertNotEquals(game.getId(), anotherGame.getId());
    }
}