package org.gameplanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JDBCGameRepositoryTest {

    Connection connection;
    GameRepository gameRepository;
    Game game;
    Game secondGame;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        gameRepository = new JDBCGameRepository(connection);
        game = new Game("Grand Theft Auto V", Genre.ACTION, "Rockstar Games", Year.of(2013), Status.BACKLOG);
        secondGame = new Game("Grand Theft Auto IV", Genre.ACTION, "Rockstar Games", Year.of(2008), Status.COMPLETED);

        try {
            connection.createStatement().executeUpdate(
                "DROP TABLE IF EXISTS Game;" + "CREATE TABLE IF NOT EXISTS Game" +
                "(" +
                    "id varchar(36) PRIMARY KEY," +
                    "title varchar(256) NOT NULL," +
                    "genre enum('ACTION', 'ADVENTURE', 'FPS', 'RPG', 'STRATEGY', 'SANDBOX', 'SURVIVAL'," +
                    "'HORROR', 'PLATFORMER', 'PUZZLE', 'SIMULATION', 'SPORTS', 'VISUAL_NOVEL', 'UNKNOWN') NOT NULL," +
                    "publisher varchar(64) NOT NULL," +
                    "releaseYear int NOT NULL," +
                    "status enum('BACKLOG', 'IN_PROGRESS', 'ABANDONED', 'COMPLETED', 'COMPLETED_100') NOT NULL" +
                ");");

        } catch (SQLException throwables) {
            System.out.println("An error has occured!");
            throwables.printStackTrace();
        }
    }

    @Test
    void testSelectsHardcodedInteger() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT 5");
        rs.next();
        assertEquals(5, rs.getInt(1));
    }

    @Test
    void testDatabaseContainsNoGames() {
        assertTrue(gameRepository.getAllGames().isEmpty());
    }

    @Test
    void testInsertOneRow() {
        gameRepository.add(game);
        assertTrue(gameRepository.getAllGames().contains(game));
    }

    @Test
    void testSearchMatchesExactlyOneTitle() {
        gameRepository.add(game);
        gameRepository.add(secondGame);

        List<Game> actual = gameRepository.search("IV");
        List<Game> expected = new ArrayList<>();
        expected.add(secondGame);

        assertEquals(expected, actual);
    }

    @Test
    void testSearchMatchesMultipleTitles() {
        gameRepository.add(game);
        gameRepository.add(secondGame);

        List<Game> actual = gameRepository.search("V");
        List<Game> expected = new ArrayList<>();
        expected.add(game);
        expected.add(secondGame);

        assertEquals(expected, actual);
    }

    @Test
    void testSearchesMatchesNothing() {
        gameRepository.add(game);
        gameRepository.add(secondGame);

        List<Game> actual = gameRepository.search("Secret of Monkey Island");

        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    void testUpdate() {
        gameRepository.add(game);

        game.setStatus(Status.IN_PROGRESS);
        gameRepository.update(game);

        Game updatedGame = gameRepository.getAllGames().get(0);
        assertEquals(Status.IN_PROGRESS, updatedGame.getStatus());
    }

    @Test
    void testDelete() {
        gameRepository.add(game);

        boolean success = gameRepository.delete(game.getId());
        assertTrue(success);
        assertFalse(gameRepository.getAllGames().contains(game));

    }

    @Test
    void testDeleteFromEmptyRepository() {
        boolean success = gameRepository.delete(game.getId());

        assertFalse(success);
    }
}