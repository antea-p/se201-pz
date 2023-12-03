package org.gameplanner;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class JDBCGameRepository implements GameRepository {

    private final Connection con;

    public JDBCGameRepository(Connection con) {
        this.con = con;
    }

    @Override
    public List<Game> getAllGames() {
        String query = "SELECT * FROM Game";
        List<Game> games = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            try (ResultSet rs = st.executeQuery(query)) {
                games = readGameRows(rs);
            }
        } catch (SQLException throwables) {
            System.out.println("An error has occured while retrieving games!");
            throwables.printStackTrace();
        }
        return games;
    }

    private List<Game> readGameRows(ResultSet rs) throws SQLException {
        ArrayList<Game> resultsList = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String title = rs.getString("title");
            Genre genre = Genre.valueOf(rs.getString("genre"));
            String publisher = rs.getString("publisher");
            Year releaseYear = Year.of(rs.getInt("releaseYear"));
            Status status = Status.valueOf(rs.getString("status"));
            resultsList.add(new Game(id, title, genre, publisher, releaseYear, status));
        }
        return resultsList;
    }

    public List<Game> search(String title) {
            String query = "SELECT * FROM Game WHERE title LIKE ?";
            List<Game> matches = new ArrayList<>();
            try (PreparedStatement st = con.prepareStatement(query)) {
                st.setString(1, "%" + title + "%");
                try (ResultSet rs = st.executeQuery()) {
                    matches = readGameRows(rs);
                }
            } catch (SQLException throwables) {
                System.out.println("An error has occured while retrieving matching games!");
                throwables.printStackTrace();
            }
            return matches;
        }

    @Override
    public void add(Game game) {
        String query = "INSERT INTO Game(id, title, genre, publisher, releaseYear, status) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(query)) {
            setValues(game, st);
            st.execute();
        } catch (SQLException throwables) {
            System.out.println("An error has occured while adding the game!");
            throwables.printStackTrace();
        }
    }

    private void setValues(Game game, PreparedStatement st) throws SQLException {
        st.setString(1, game.getId());
        st.setString(2, game.getTitle());
        st.setString(3, game.getGenre().toString());
        st.setString(4, game.getPublisher());
        st.setInt(5, game.getReleaseYear().getValue());
        st.setString(6, game.getStatus().toString());
    }

    @Override
    public void update(Game game) {
        String query = "UPDATE Game SET id=?, title=?, genre=?, publisher=?, releaseYear=?, status=? WHERE id=?";
        int rowsUpdated;
        try (PreparedStatement st = con.prepareStatement(query)) {
            setValues(game, st);
            st.setString(7, game.getId());
            rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("The game was successfully updated!");
            }
        } catch (SQLException throwables) {
            System.out.println("An error has occured while updating the game!");
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM Game WHERE id=?";
        try (PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, id);
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("The game was successfully deleted!");
                return true;
            }
        } catch (SQLException throwables) {
            System.out.println("An error has occured while deleting the game!");
            throwables.printStackTrace();
        }
        return false;
    }
}
