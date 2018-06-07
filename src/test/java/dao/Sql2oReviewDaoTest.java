package dao;

import models.Review;
import org.junit.*;

import org.sql2o.*;

import java.util.List;

import static org.junit.Assert.*;

public class Sql2oReviewDaoTest {
    private Sql2oReviewDao reviewDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();
    }

    public Review setupNewReview(){
        return new Review(4, "Jim", "Good record", 1);
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add_addingReviewSetsId() {
        Review review = setupNewReview();
        int originalReviewId = review.getId();
        Review review2 = new Review (3, "Bob", "Bad record", 2);
        reviewDao.add(review);
        reviewDao.add(review2);
        assertNotEquals(originalReviewId, review.getId());
        assertEquals(2, review2.getId());
    }

    @Test
    public void getAll_noReviewsFound_0() {
        List<Review> allReviews = reviewDao.getAll();
        assertEquals(0, allReviews.size());

    }

    @Test
    public void getAll_allReviewsAreFound_2() {
        Review review = setupNewReview();
        Review review2 = new Review (3, "Bob", "Bad record", 2);
        reviewDao.add(review);
        reviewDao.add(review2);
        List<Review> allReviews = reviewDao.getAll();
        assertEquals(2, allReviews.size());
    }

    @Test
    public void findById_findsReviewById() {
        Review review = setupNewReview();
        reviewDao.add(review);
        Review foundReview = reviewDao.findById(review.getId());
        assertEquals(review, foundReview);
    }

    @Test
    public void update_updatesReview() {
        Review review = setupNewReview();
        reviewDao.add(review);
        int id = review.getId();
        reviewDao.update(id, 3, "Bob", "Bad record", 2);
        Review updatedReview = reviewDao.findById(id);
        assertEquals("Bob", updatedReview.getAuthor());
        assertEquals("Bad record", updatedReview.getComment());
    }

    @Test
    public void deleteById_deletesReviewById() {
        Review review = setupNewReview();
        Review review2 = new Review (3, "Bob", "Bad record", 2);
        reviewDao.add(review);
        reviewDao.add(review2);
        reviewDao.deleteById(2);
        List<Review> allReviews = reviewDao.getAll();
        assertEquals(1, allReviews.size());
        assertTrue(allReviews.contains(review));
        assertFalse(allReviews.contains(review2));
    }

    @Test
    public void deleteByAlbumId_deletesReviewByAlbumId() {
        Review review = setupNewReview();
        Review review2 = new Review (3, "Bob", "Bad record", 2);
        reviewDao.add(review);
        reviewDao.add(review2);
        reviewDao.deleteByAlbumId(2);
        List<Review> allReviews = reviewDao.getAll();
        assertEquals(1, allReviews.size());
        assertTrue(allReviews.contains(review));
        assertFalse(allReviews.contains(review2));
    }

    @Test
    public void albumIdIsReturnedCorrectly() throws Exception {
        Review review = setupNewReview();
        int originalAlbumId = review.getAlbumId();
        reviewDao.add(review);
        assertEquals(originalAlbumId, reviewDao.findById(review.getId()).getAlbumId());
    }

}