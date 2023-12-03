package org.gameplanner;

import java.util.Objects;

public class GenreDisplay {

    public static String getLabel(Genre genre) {
        Objects.requireNonNull(genre, "Genre value is null!");
        switch (genre) {
            case FPS:
                return "FPS";
            case RPG:
                return "RPG";
            case VISUAL_NOVEL:
                return "Visual novel";
            default:
                String genre_ = genre.toString();
                return genre_.substring(0, 1).toUpperCase() + genre_.substring(1).toLowerCase();
        }
    }
}
