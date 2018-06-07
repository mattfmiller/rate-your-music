package dao;

import models.Album;
import models.Artist;
import models.Review;
import org.junit.*;

import org.sql2o.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Sql2oAlbumDaoTest {
    private Sql2oAlbumDao albumDao;
    private Sql2oReviewDao reviewDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        albumDao = new Sql2oAlbumDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();
    }

    public Album setupNewAlbum(){
        return new Album("Voices In The Dark", "2018-06", "Hey Little Girl, Beach", "testUrl", 1);
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add_addingAlbumSetsId() {
        Album album = setupNewAlbum();
        int originalAlbumId = album.getId();
        Album album2 = new Album ("Voices In The Dark2", "2018-05", "Gotta Get Away, Freak Out", "testUrl2", 2);
        albumDao.add(album);
        albumDao.add(album2);
        assertNotEquals(originalAlbumId, album.getId());
        assertEquals(2, album2.getId());
    }

    @Test
    public void getAll_noAlbumsAreFound_0() {
        List<Album> allAlbums = albumDao.getAll();
        assertEquals(0, allAlbums.size());
    }

    @Test
    public void getAll_allAlbumsAreFound_2() {
        Album album = setupNewAlbum();
        Album album2 = new Album ("Voices In The Dark2", "2018-05", "Gotta Get Away, Freak Out", "testUrl2", 2);
        albumDao.add(album);
        albumDao.add(album2);
        List<Album> allAlbums = albumDao.getAll();
        assertEquals(2, allAlbums.size());
    }


    @Test
    public void getAllReviewsByAlbumReturnsReviewsCorrectly() throws Exception{
        Album album = setupNewAlbum();
        albumDao.add(album);
        int albumId = album.getId();
        Review review1 = new Review (4, "Jim", "Great record", albumId);
        Review review2 = new Review (3, "Bob", "Bad record", 2);
        reviewDao.add(review1);
        assertEquals(1, albumDao.getAllReviewsByAlbum(albumId).size());
        assertTrue(albumDao.getAllReviewsByAlbum(albumId).contains(review1));
        assertFalse(albumDao.getAllReviewsByAlbum(albumId).contains(review2));
    }

//    @Test
//    public void getArtist_findsArtistByArtistId_artist() throws Exception{
//        Album testAlbum = setupNewAlbum();
//        albumDao.add(testAlbum);
//        Artist foundArtist = albumDao.getArtist(testAlbum.getArtistId());
//        assertEquals(testAlbum.getArtistId(), foundArtist.getId());
//    }


    @Test
    public void findById_findsAlbumById_album() throws Exception{
        Album testAlbum = setupNewAlbum();
        albumDao.add(testAlbum);
        Album foundAlbum = albumDao.findById(testAlbum.getId());
        assertEquals(testAlbum.getId(), foundAlbum.getId());
    }

//    @Test
//    public void update_updatesAlbum_() {
//        Album album = setupNewAlbum();
//        albumDao.add(album);
//        int id = album.getId();
//        System.out.println(album.getTracks());
//        albumDao.update(id, "Voices In The Dark2", "2018-05", "Gotta Get Away, Freak Out", "testUrl2", 2);
//        Album updatedAlbum = albumDao.findById(id);
//        List<String> expectedTracks = new ArrayList<>(Arrays.asList("Gotta Get Away, Freak Out".split(", ")));
//        System.out.println(updatedAlbum.getTracks());
//        assertEquals("Voices In The Dark2", updatedAlbum.getName());
//        assertEquals("2018-05", updatedAlbum.getReleaseDate());
////        assertEquals(expectedTracks, updatedAlbum.getTracks());
//
//    }

    @Test
    public void update_updatesAlbum_() {
        Album album = setupNewAlbum();
        albumDao.add(album);
        int id = album.getId();
        albumDao.update(id, "Voices In The Dark2", "2018-05", "Gotta Get Away, Freak Out", "testUrl2", 2);
        Album updatedAlbum = albumDao.findById(id);
        assertEquals("Voices In The Dark2", updatedAlbum.getName());
        assertEquals("2018-05", updatedAlbum.getReleaseDate());
        assertEquals("Gotta Get Away, Freak Out", updatedAlbum.getTracks());

    }

    @Test
    public void deleteById() {
        Album album = setupNewAlbum();
        Album album2 = new Album ("Voices In The Dark2", "2018-05", "Gotta Get Away, Freak Out", "testUrl2", 2);
        albumDao.add(album);
        albumDao.add(album2);
        albumDao.deleteByArtistId(2);
        List<Album> allAlbums = albumDao.getAll();
        assertEquals(1, allAlbums.size());
        assertTrue(allAlbums.contains(album));
    }

    @Test
    public void deleteByArtistId() {
        Album album = setupNewAlbum();
        Album album2 = new Album ("Voices In The Dark2", "2018-05", "Gotta Get Away, Freak Out", "testUrl2", 2);
        albumDao.add(album);
        albumDao.add(album2);
        albumDao.deleteByArtistId(2);
        List<Album> allAlbums = albumDao.getAll();
        assertEquals(1, allAlbums.size());
        assertFalse(allAlbums.contains(album2));
    }

    @Test
    public void albumIdIsReturnedCorrectly() throws Exception {
        Album album = setupNewAlbum();
        int originalArtistId = album.getArtistId();
        albumDao.add(album);
        assertEquals(originalArtistId, albumDao.findById(album.getId()).getArtistId());
    }

}