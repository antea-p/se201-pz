package org.gameplanner.gui;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.gameplanner.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    GameRepository gameRepository;
    GameService gameService;
    ObservableList<Game> gameList;
    TableView<Game> tableView;
    Random random;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/games",
                    "gamer", "password");
            AbstractFactory repositoryFactory = GameRepositoryFactoryProducer.getFactory(true);
            gameRepository = repositoryFactory.getGameRepository("JDBC", con);
            gameService = new GameServiceImpl(gameRepository);
        } catch (SQLException throwables) {
            System.out.println("Failed to connect to the database! The program will use a temporary game repository.");
            AlertUtil.displayAlert("Could not connect to the database! The program will work, but its functionality will be limited. " +
                            "Any changes you make will be lost after closing the program.",
                    Alert.AlertType.WARNING);
            throwables.printStackTrace();
            AbstractFactory repositoryFactory = GameRepositoryFactoryProducer.getFactory(false);
            gameRepository = repositoryFactory.getGameRepository("DUMMY", null);
            gameService = new GameServiceImpl(gameRepository);
        }

        // Header
        Label gameCountLbl = new Label("Total games: ");
        Label gameStatusLbl = new Label("Games by status: ");

    /*
      --------------
         TABLEVIEW
      --------------
     */

        tableView = new TableView<>();
        tableView.setPrefWidth(500);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(true);

        TableColumn<Game, String> titleCol = new TableColumn<>("Title");
        TableColumn<Game, String> genreCol = new TableColumn<>("Genre");
        TableColumn<Game, String> publisherCol = new TableColumn<>("Publisher");
        TableColumn<Game, Year> yearCol = new TableColumn<>("Year");
        TableColumn<Game, String> statusCol = new TableColumn<>("Status");

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(GenreDisplay.getLabel(data.getValue().getGenre())));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        statusCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(StatusDisplay.getLabel(data.getValue().getStatus())));

        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        genreCol.setCellFactory(ComboBoxTableCell.forTableColumn());

        tableView.getColumns().add(titleCol);
        tableView.getColumns().add(genreCol);
        tableView.getColumns().add(publisherCol);
        tableView.getColumns().add(yearCol);
        tableView.getColumns().add(statusCol);

        titleCol.setPrefWidth(125);

        gameList = FXCollections.observableArrayList();
        gameList.setAll(gameService.getAllGames());
        tableView.setItems(gameList);

        random = new Random();
        if (!gameList.isEmpty()) {
            int randomGameIndex = random.nextInt(gameList.size());
            tableView.getSelectionModel().clearAndSelect(randomGameIndex);
            tableView.scrollTo(randomGameIndex);
        }

        setGameCountLabel(gameCountLbl);
        setGameStatusLabel(gameStatusLbl);

        /*
           --------------
              BUTTONS
           --------------
        */
        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit");
        Button removeBtn = new Button("Remove");
        TextField searchTf = new TextField();
        searchTf.setPromptText("Search by title...");

        // Kako bi sva dugmad bila uniformne širine. Izvor:
        // https://docs.oracle.com/javase/8/javafx/layout-tutorial/size_align.htm
        addBtn.setMaxWidth(Double.MAX_VALUE);
        editBtn.setMaxWidth(Double.MAX_VALUE);
        removeBtn.setMaxWidth(Double.MAX_VALUE);


        /*
         --------------
         EVENT HANDLERI
         --------------
        */
        addBtn.setOnAction(e -> {
            try {
                AddGameForm addForm = new AddGameForm(gameService, tableView);
                addForm.start(new Stage());
            } catch (Exception ex) {
                AlertUtil.displayAlert("An error has occured while adding the game!", Alert.AlertType.ERROR);
                ex.printStackTrace();
            }
        });

        editBtn.setOnAction(e -> {
            Game selectedRow = tableView.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                try {
                    EditGameForm editForm = new EditGameForm(gameService, tableView, selectedRow, selectedRow.getId());
                    editForm.start(new Stage());
                } catch (Exception ex) {
                    AlertUtil.displayAlert("An error has occured while updating the boxer!", Alert.AlertType.ERROR);
                    ex.printStackTrace();
                }
            }
        });

        removeBtn.setOnAction(e -> {
            // Može se izbrisati više zapisa odjednom.
            ObservableList<Game> selectedRows = tableView.getSelectionModel().getSelectedItems();
            ArrayList<Game> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                if (gameService.delete(row)) {
                    tableView.getItems().remove(row);
                }
            });
            setGameCountLabel(gameCountLbl);
            setGameStatusLabel(gameStatusLbl);
        });

        searchTf.setOnAction(e -> {
            ObservableList<Game> matches = FXCollections.observableList(gameService.search(searchTf.getText()));
            tableView.setItems(matches);
        });

        tableView.itemsProperty().addListener(new ChangeListener<ObservableList<Game>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<Game>> observableValue, ObservableList<Game> games, ObservableList<Game> t1) {
                setGameCountLabel(gameCountLbl);
                gameStatusLbl.setText("Games by status: " +
                        "\n100%-ed: " + getCount(Status.COMPLETED_100) +
                        "\nCompleted: " + getCount(Status.COMPLETED) +
                        "\nIn progress: " + getCount(Status.IN_PROGRESS) +
                        "\nAbandoned: " + getCount(Status.ABANDONED));
            }
        });


        /*
         --------------
             LAYOUT
         --------------
        */

        VBox vbox = new VBox(15, addBtn, editBtn, removeBtn, gameCountLbl, gameStatusLbl, searchTf);

        HBox hbox = new HBox(30, tableView, vbox);

        BorderPane borderPane = new BorderPane(hbox);
        BorderPane.setMargin(hbox, new Insets(20));

        Scene scene = new Scene(borderPane, 650, 400);
        stage.setScene(scene);
        stage.setTitle("Game View");
        stage.show();

    }

    private void setGameCountLabel(Label gameCountLbl) {
        gameCountLbl.setText(String.format("Total games: %d", tableView.getItems().size()));
    }

    private void setGameStatusLabel(Label gameStatusLbl) {
        gameStatusLbl.setText("Games by status: " +
                "\n100%-ed: " + getCount(Status.COMPLETED_100) +
                "\nCompleted: " + getCount(Status.COMPLETED) +
                "\nIn progress: " + getCount(Status.IN_PROGRESS) +
                "\nAbandoned: " + getCount(Status.ABANDONED));
    }

    private long getCount(Status status) {
        return tableView.getItems().stream().filter(e -> e.getStatus().equals(status)).count();
    }
}