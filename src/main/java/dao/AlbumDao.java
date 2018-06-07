package dao;

import models.Album;
import models.Artist;
import models.Review;

import java.util.List;

public interface AlbumDao {
    // LIST
    List<Album> getAll();
    List<Review> getAllReviewsByAlbum(int albumId);
//    Artist getArtist(int artistId);

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
