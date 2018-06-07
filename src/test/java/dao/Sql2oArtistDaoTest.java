package dao;

import models.Album;
import models.Artist;
import org.junit.*;
import org.sql2o.*;

import java.util.List;

import static org.junit.Assert.*;

public class Sql2oArtistDaoTest {
    private Sql2oArtistDao artistDao;
    private Sql2oAlbumDao albumDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        artistDao = new Sql2oArtistDao(sql2o);
//        albumDao = new Sql2oAlbumDao(sql2o);
        conn = sql2o.open();
    }

    public Artist setupNewArtist(){
        return new Artist("Chuck Berry", "testUrl");
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add_addingArtistSetsId() {
        Artist artist = setupNewArtist();
        int originalArtistId = artist.getId();
        Artist artist2 = new Artist ("Elvis", "testUrl2");
        artistDao.add(artist);
        artistDao.add(artist2);
        assertNotEquals(originalArtistId, artist.getId());
        assertEquals(2, artist2.getId());
    }

    @Test
    public void getAll_noArtistsAreFound_0() throws Exception {
        List<Artist> allArtists = artistDao.getAll();
        assertEquals(0, allArtists.size());
    }

    @Test
    public void getAll_allArtistsAreFound_2() {
        Artist artist = setupNewArtist();
        Artist artist2 = new Artist ("Elvis", "testUrl2");
        artistDao.add(artist);
        artistDao.add(artist2);
        List<Artist> allArtists = artistDao.getAll();
        assertEquals(2, allArtists.size());
    }

    @Test
    public void getAllAlbumsByArtistReturnsAlbumCorrectly() {
        Artist artist = setupNewArtist();
        artistDao.add(artist);
        int artistId = artist.getId();
        Album album1 = new Album("Voices In The Dark", "2018-06", "Hey Little Girl, Beach", "testUrl", artistId);
        Album album2 = new Album ("Voices In The Dark2", "2018-05", "Gotta Get Away, Freak Out", "testUrl2", 2);
        albumDao.add(album1);
        assertEquals(1, artistDao.getAllAlbumsByArtist(artistId).size());
        assertTrue(artistDao.getAllAlbumsByArtist(artistId).contains(album1));
        assertFalse(artistDao.getAllAlbumsByArtist(artistId).contains(album2));
    }

    @Test
    public void findById_findsArtistById_artist() {
        Artist artist = setupNewArtist();
        artistDao.add(artist);
        Artist foundArtist = artistDao.findById(artist.getId());
        assertEquals(artist, foundArtist);
    }

    @Test
    public void update_updatesArtist_badShadows() {
        Artist artist = setupNewArtist();
        artistDao.add(artist);
        int id = artist.getId();
        artistDao.update(id, "Bad Shadows", "testUrl2");
        Artist updatedArtist = artistDao.findById(id);
        assertEquals("Bad Shadows", updatedArtist.getName());
        assertEquals("testUrl2", updatedArtist.getImageUrl());
    }

    @Test
    public void deleteById_deletesArtistById_1() {
        Artist artist = setupNewArtist();
        Artist artist2 = new Artist ("Elvis", "testUrl2");
        artistDao.add(artist);
        artistDao.add(artist2);
        artistDao.deleteById(2);
        List<Artist> allArtists = artistDao.getAll();
        assertEquals(1, allArtists.size());
//        assertTrue(allArtists.contains(artist));
        assertFalse(allArtists.contains(artist2));
    }

}