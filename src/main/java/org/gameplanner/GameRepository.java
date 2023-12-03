package org.gameplanner;

import java.util.List;

public interface GameRepository {
    List<Game> getAllGames();

    List<Game> search(String title);

    void add(Game game);

    void update(Game game);

    boolean delete(String id);
}
