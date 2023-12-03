package org.gameplanner;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DummyGameService implements GameService {

    List<Game> games;

    @Override
    public List<Game> getAllGames() {
        games = new ArrayList<>();
        games.add(new Game("Game 1", Genre.RPG, "Sony", Year.of(2012), Status.COMPLETED));
        games.add(new Game("Game 2", Genre.ACTION, "Activision Blizzard", Year.of(2017), Status.COMPLETED_100));
        games.add(new Game("Game 3", Genre.ADVENTURE, "Nintendo", Year.of(1995), Status.IN_PROGRESS));
        games.add(new Game("Game 4", Genre.SURVIVAL, "Konami", Year.of(2019), Status.ABANDONED));
        games.add(new Game("Game 5", Genre.ADVENTURE, "Ubisoft", Year.of(2015), Status.BACKLOG));
        return games;
    }

    @Override
    public List<Game> search(String title) {
        return games.stream().filter(e -> e.getTitle().equals(title)).collect(Collectors.toList());
    }

    @Override
    public void add(Game game) {
        System.out.println("Added: " + game.toString());
    }

    @Override
    public void update(Game game) {
        System.out.println("Updated: " + game.toString());
    }

    @Override
    public boolean delete(Game game) {
        System.out.println("Deleting: " + game.toString());
        return false;
    }
}
