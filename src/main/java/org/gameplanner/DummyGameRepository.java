package org.gameplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DummyGameRepository implements GameRepository {

    private List<Game> games = new ArrayList<>();

    @Override
    public List<Game> getAllGames() {
        System.out.println("List:" + games);
        return games;
    }

    @Override
    public List<Game> search(String title) {
        return games.stream().filter(e -> e.getTitle().equals(title)).collect(Collectors.toList());
    }

    @Override
    public void add(Game game) {
        System.out.println("Adding: " + game);
        games.add(game);
    }

    @Override
    public void update(Game game) {
        System.out.println("Old list: " + games);
        System.out.println("Updating game info with: " + game);
        int firstMatch = IntStream.range(0, games.size())
                .filter(i -> games.get(i).getId().equals(game.getId()))
                .findFirst().orElseThrow(() -> new IllegalStateException("Invalid game ID!"));
        games.remove(firstMatch);
        games.add(firstMatch,game);
    }

    @Override
    public boolean delete(String id) {
        System.out.println("Deleting game with ID: " + id);
        return games.removeIf(game -> game.getId().equals(id));
    }
}
