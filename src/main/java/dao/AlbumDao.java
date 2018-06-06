package dao;

import models.Album;
import models.Review;

import java.util.List;

public interface AlbumDao {
    // LIST
    List<Album> getAll();
    List<Review> getAllReviewssByAlbum(int albumId);

    // CREATE
    void add(Album album);

    // READ
    Album findById(int id);

    // UPDATE
    void update(int id, String name, String releaseDate, String tracks, String imageUrl, int artistId);

    // DELETE
    void deleteById(int id);
//    void clearAllAlbums();
    void deleteByArtistId(int artistId);
}
