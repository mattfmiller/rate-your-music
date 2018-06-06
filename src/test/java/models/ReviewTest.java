package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest {

    @Before
    public void setUp() throws Exception {
    }

    public Review setUpNewReview() {
        return new Review(5, "Jack", "Such a good record!", 1);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void NewAlbumObjectGetsCorrectlyCreated_true() throws Exception {
        Review testReview = setUpNewReview();
        assertEquals(true, testReview instanceof Review);
    }

    @Test
    public void getRating() {
        Review testReview = setUpNewReview();
        assertEquals(5, testReview.getRating());
    }

    @Test
    public void setRating() {
        Review testReview = setUpNewReview();
        testReview.setRating(4);
        assertEquals(4, testReview.getRating());
    }

    @Test
    public void getAuthor() {
    Review testReview = setUpNewReview();
    assertEquals("Jack", testReview.getAuthor());
    }

    @Test
    public void setAuthor() {
        Review testReview = setUpNewReview();
        testReview.setAuthor("Tom");
        assertEquals("Tom", testReview.getAuthor());
    }

    @Test
    public void getComment() {
        Review testReview = setUpNewReview();
        assertEquals("Such a good record!", testReview.getComment());
    }

    @Test
    public void setComment() {
        Review testReview = setUpNewReview();
        testReview.setComment("Crap");
        assertEquals("Crap", testReview.getComment());
    }

    @Test
    public void getAlbumId() {
        Review testReview = setUpNewReview();
        assertEquals(1, testReview.getAlbumId());
    }

    @Test
    public void setAlbumId() {
        Review testReview = setUpNewReview();
        testReview.setAlbumId(2);
        assertEquals(2, testReview.getAlbumId());
    }
}