package dao;

import models.Album;
import models.Artist;

import java.util.List;

public interface ArtistDao {
    // LIST
    List<Artist> getAll();
    List<Album> getAllAlbumsByArtist(int artistId);

    // CREATE
    void add(Artist artist);

    // READ
    Artist findById(int id);

    // UPDATE
    void update(int id, String name, String imageUrl);

    // DELETE
    void deleteById(int id);
//    void clearAllArtists();
}
