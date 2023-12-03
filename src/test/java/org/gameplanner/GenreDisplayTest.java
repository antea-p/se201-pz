package org.gameplanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreDisplayTest {

    @Test
    void testFPS() {
        assertEquals("FPS", GenreDisplay.getLabel(Genre.FPS));
    }

    @Test
    void testRPG() {
        assertEquals("RPG", GenreDisplay.getLabel(Genre.RPG));
    }

    @Test
    void testVisualNovel() {
        assertEquals("Visual novel", GenreDisplay.getLabel(Genre.VISUAL_NOVEL));
    }

    @Test
    void testOtherGenres() {
        assertEquals("Action", GenreDisplay.getLabel(Genre.ACTION));
        assertEquals("Adventure", GenreDisplay.getLabel(Genre.ADVENTURE));
        assertEquals("Strategy", GenreDisplay.getLabel(Genre.STRATEGY));
        assertEquals("Sandbox", GenreDisplay.getLabel(Genre.SANDBOX));
        assertEquals("Survival", GenreDisplay.getLabel(Genre.SURVIVAL));
        assertEquals("Horror", GenreDisplay.getLabel(Genre.HORROR));
        assertEquals("Platformer", GenreDisplay.getLabel(Genre.PLATFORMER));
        assertEquals("Puzzle", GenreDisplay.getLabel(Genre.PUZZLE));
        assertEquals("Simulation", GenreDisplay.getLabel(Genre.SIMULATION));
        assertEquals("Sports", GenreDisplay.getLabel(Genre.SPORTS));
        assertEquals("Unknown", GenreDisplay.getLabel(Genre.UNKNOWN));
    }

    @Test
    void testNullGenreThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> GenreDisplay.getLabel(null));
    }
}