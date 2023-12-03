package org.gameplanner.gui;

import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import org.gameplanner.*;

import java.time.Year;

public class AddGameForm extends GameForm {

  public AddGameForm(GameService gameService, TableView<Game> gamesTable) {
    super(gameService, gamesTable, null);
  }

  @Override
  void onSubmit(String title, Genre genre, String publisher, Year releaseYear, Status status) {
    getGameService().add(new GameBuilderDirector().buildGame(title, genre, publisher, releaseYear, status));
    getGamesTable().setItems(FXCollections.observableArrayList(getGameService().getAllGames()));
  }
}
