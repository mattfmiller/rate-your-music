package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.YearMonth;

import static org.junit.Assert.*;

public class AlbumTest {

    @Before
    public void setUp() throws Exception {

    }

    public Album setUpNewAlbum() {
        return new Album("Voices In The Dark", "2018-06", "testUrl", 1);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void NewAlbumObjectGetsCorrectlyCreated_true() throws Exception {
        Album testAlbum = setUpNewAlbum();
        assertEquals(true, testAlbum instanceof Album);
    }

    @Test
    public void getName() {
        Album testAlbum = setUpNewAlbum();
        assertEquals("Voices In The Dark", testAlbum.getName());
    }

    @Test
    public void setName() {
        Album testAlbum = setUpNewAlbum();
        testAlbum.setName("test");
        assertEquals("test", testAlbum.getName());
    }

    @Test
    public void getReleaseDate() {
        Album testAlbum = setUpNewAlbum();
        YearMonth expected = YearMonth.parse("2018-06");
        assertEquals(expected, testAlbum.getReleaseDate());
    }

    @Test
    public void setReleaseDate() {
        Album testAlbum = setUpNewAlbum();
        testAlbum.setReleaseDate("2018-05");
        YearMonth expected = YearMonth.parse("2018-05");
        assertEquals(expected, testAlbum.getReleaseDate());
    }

    @Test
    public void getTracks() {
        Album testAlbum = setUpNewAlbum();
        assertEquals(0, testAlbum.getTracks().size());
    }

    @Test
    public void addTracks() {
        Album testAlbum = setUpNewAlbum();
        testAlbum.addTrack("Hey Little Girl");
        testAlbum.addTrack("Beach");
        assertEquals(2, testAlbum.getTracks().size());
    }

    @Test
    public void getImageUrl() {
        Album testAlbum = setUpNewAlbum();
        assertEquals("testUrl", testAlbum.getImageUrl());
    }

    @Test
    public void setImageUrl() {
        Album testAlbum = setUpNewAlbum();
        testAlbum.setImageUrl("testUrl2");
        assertEquals("testUrl2", testAlbum.getImageUrl());
    }

    @Test
    public void getArtistId() {
        Album testAlbum = setUpNewAlbum();
        assertEquals(1, testAlbum.getArtistId());
    }

    @Test
    public void setArtistId() {
        Album testAlbum = setUpNewAlbum();
        testAlbum.setArtistId(2);
        assertEquals(2, testAlbum.getArtistId());
    }
}