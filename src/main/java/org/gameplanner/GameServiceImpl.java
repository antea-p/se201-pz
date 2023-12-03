package org.gameplanner;

import java.util.ArrayList;
import java.util.List;

public class GameServiceImpl implements GameService {

    GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    @Override
    public List<Game> search(String title) {
        return gameRepository.search(title);
    }

    @Override
    public void add(Game game) {
        gameRepository.add(game);
    }

    @Override
    public void update(Game game) {
        gameRepository.update(game);
    }

    @Override
    public boolean delete(Game game) {
        return gameRepository.delete(game.getId());
    }
}
