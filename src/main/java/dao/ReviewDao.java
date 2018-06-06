package dao;

import models.Review;

import java.util.List;

public interface ReviewDao {

    // LIST
    List<Review> getAll();

    // CREATE
    void add(Review review);

    // READ
    Review findById(int id);

    // UPDATE
    void update(int id, int rating, String author, String comment, int albumId);

    // DELETE
    void deleteById(int id);
//    void clearAllReviews();
    void deleteByAlbumId(int albumId);
}
