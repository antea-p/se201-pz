package org.gameplanner;

import java.time.Year;
import java.util.Objects;
import java.util.UUID;

public class Game {

    private final String id;
    private String title;
    private Genre genre;
    private String publisher;
    private Year releaseYear;
    private Status status;

    public Game(String id, String title, Genre genre, String publisher, Year releaseYear, Status status) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.publisher = publisher;
        this.releaseYear = releaseYear;
        this.status = status;
    }

    public Game(String title, Genre genre, String publisher, Year releaseYear, Status status) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.genre = genre;
        this.publisher = publisher;
        this.releaseYear = releaseYear;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Game setTitle(String title) {
        this.title = title;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public Game setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public Game setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public Game setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Game setStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(title, game.title) && genre == game.genre && Objects.equals(publisher, game.publisher) && Objects.equals(releaseYear, game.releaseYear) && status == game.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, genre, publisher, releaseYear, status);
    }

    @Override
    public String toString() {
        return "Game{" +
                "title=" + title +
                ", genre=" + genre +
                ", publisher=" + publisher +
                ", releaseYear=" + releaseYear +
                ", status=" + status +
                '}';
    }

}
