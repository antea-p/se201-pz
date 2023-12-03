package org.gameplanner;

import java.util.ArrayList;
import java.util.List;

public interface GameService {
    List<Game> getAllGames();

    List<Game> search(String title);

    void add(Game game);

    void update(Game game);

    boolean delete(Game game);
}
