package org.gameplanner.gui;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.gameplanner.*;

import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


abstract public class GameForm extends Application {

  private final GameService gameService;
  private TableView<Game> gamesTable;
  private Game selectedGame;

  public GameForm(GameService gameService, TableView<Game> gamesTable, Game selectedGame /* Nullable */) {
    this.gameService = gameService;
    this.gamesTable = gamesTable;
    this.selectedGame = selectedGame;
  }

  private <T> void commitEditorText(Spinner<T> spinner) {
    if (!spinner.isEditable()) { return; }
    String text = spinner.getEditor().getText();
    SpinnerValueFactory<T> valueFactory = spinner.getValueFactory();
    if (valueFactory != null) {
      StringConverter<T> converter = valueFactory.getConverter();
      if (converter != null) {
        T value = converter.fromString(text);
        valueFactory.setValue(value);
      }
    }
  }

  public GameService getGameService() {
    return gameService;
  }

  public TableView<Game> getGamesTable() {
    return gamesTable;
  }

  abstract void onSubmit(String title, Genre genre, String publisher, Year releaseYear, Status status);

  @Override
  public void start(Stage stage) {
    GridPane gridPaneForm = new GridPane();

    Label titleLbl = new Label("Title");
    TextField titleTf = new TextField();

    Label genreLbl = new Label("Genre");
    ComboBox<Genre> genreComboBox = new ComboBox<>();
    genreComboBox.setPrefWidth(195);
    // Alfabetsko sortiranje, radi lakše navigacije korinika.
    List<Genre> sortedGenres = Stream.of(Genre.values())
            .sorted(Comparator.comparing(Enum::toString))
            .collect(Collectors.toList());

    genreComboBox.getItems().setAll(sortedGenres);
    genreComboBox.getSelectionModel().select(Genre.UNKNOWN);

    // Kontrolira na koji će se način korisniku prikazivati Genre objekti.
    genreComboBox.setConverter(new StringConverter<Genre>() {
      @Override
      public String toString(Genre object) {
        return GenreDisplay.getLabel(object);
      }

      @Override
      public Genre fromString(String string) {
        return null;
      }
    });

    Label publisherLbl = new Label("Publisher");
    TextField publisherTf = new TextField("");

    Label releaseLbl = new Label("Release Date");
    Spinner<Integer> yearSpinner = new Spinner<>(1980, 2023, 2023);
    yearSpinner.setPrefWidth(195);
    yearSpinner.setEditable(true);
    yearSpinner.focusedProperty().addListener((s, ov, nv) -> {
      if (nv) return;
      commitEditorText(yearSpinner);
    });

    Label statusLbl = new Label("Status");
    ComboBox<Status> statusComboBox = new ComboBox<>();
    statusComboBox.setPrefWidth(195);

    statusComboBox.getItems().setAll(Status.values());

    // Kontrolira na koji će se način korisniku prikazivati Status objekti.
    statusComboBox.setConverter(new StringConverter<Status>() {
      @Override
      public String toString(Status object) {
        return StatusDisplay.getLabel(object);
      }

      @Override
      public Status fromString(String string) {
        return null;
      }
    });

    Button submitGameBtn = new Button("Submit");
    submitGameBtn.setOnAction(e -> {
      try {
          onSubmit(titleTf.getText(),
              genreComboBox.getSelectionModel().getSelectedItem(),
              publisherTf.getText(),
              Year.of(yearSpinner.getValue()),
              statusComboBox.getSelectionModel().getSelectedItem()
      );
      stage.close();
      } catch (NullPointerException ex) {
        AlertUtil.displayAlert("Title and Status are mandatory fields! Please ensure they aren't blank.",
                AlertType.ERROR);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    });

    if (selectedGame != null) {
      titleTf.setText(selectedGame.getTitle());
      genreComboBox.getSelectionModel().select(selectedGame.getGenre());
      publisherTf.setText(selectedGame.getPublisher());
      yearSpinner.getValueFactory().setValue(selectedGame.getReleaseYear().getValue());
      statusComboBox.getSelectionModel().select(selectedGame.getStatus());
    }

    gridPaneForm.add(titleLbl, 0, 0);
    gridPaneForm.add(titleTf, 1, 0);

    gridPaneForm.add(genreLbl, 0, 1);
    gridPaneForm.add(genreComboBox, 1, 1);

    gridPaneForm.add(publisherLbl, 0, 2);
    gridPaneForm.add(publisherTf, 1, 2);

    gridPaneForm.add(releaseLbl, 0, 3);
    gridPaneForm.add(yearSpinner, 1, 3);

    gridPaneForm.add(statusLbl, 0, 4);
    gridPaneForm.add(statusComboBox, 1, 4);

    gridPaneForm.add(submitGameBtn, 1, 5);

    GridPane.setMargin(submitGameBtn, new Insets(10, 25, 10, 25));
    gridPaneForm.setAlignment(Pos.CENTER);
    gridPaneForm.setHgap(10);
    gridPaneForm.setVgap(15);
    // gridPaneForm.setGridLinesVisible(true);

    Scene scene = new Scene(gridPaneForm, 300, 300);
    stage.setScene(scene);
    stage.setTitle("Game form");
    stage.show();
  }

}
