package org.gameplanner.gui;

import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import org.gameplanner.Game;
import org.gameplanner.GameService;
import org.gameplanner.Genre;
import org.gameplanner.Status;

import java.time.Year;

public class EditGameForm extends GameForm {

  private final String id;

  public EditGameForm(GameService gameService, TableView<Game> gamesTable, Game selectedGame, String id) {
    super(gameService, gamesTable, selectedGame);
    this.id = id;
  }

  @Override
  void onSubmit(String title, Genre genre, String publisher, Year releaseYear, Status status) {
    getGameService().update(new Game(id, title, genre, publisher, releaseYear, status));
    getGamesTable().setItems(FXCollections.observableArrayList(getGameService().getAllGames()));
  }
}
