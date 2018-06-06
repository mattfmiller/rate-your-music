package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtistTest {

    @Before
    public void setUp() throws Exception {
    }

    public Artist setUpNewArtist() {
        return new Artist("Chuck Berry", "testUrl");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void NewArtistObjectGetsCorrectlyCreated_true() throws Exception {
        Artist testArtist = setUpNewArtist();
        assertEquals(true, testArtist instanceof Artist);
    }

    @Test
    public void getName() {
        Artist testArtist = setUpNewArtist();
        assertEquals("Chuck Berry", testArtist.getName());
    }

    @Test
    public void setName() {
        Artist testArtist = setUpNewArtist();
        testArtist.setName("Charles Berry");
        assertEquals("Charles Berry", testArtist.getName());
    }

    @Test
    public void getImageUrl() {
        Artist testArtist = setUpNewArtist();
        assertEquals("testUrl", testArtist.getImageUrl());
    }

    @Test
    public void setImageUrl() {
        Artist testArtist = setUpNewArtist();
        testArtist.setImageUrl("testUrl2");
        assertEquals("testUrl2", testArtist.getImageUrl());
    }

}