package dao;

import models.Album;
import models.Review;
import org.sql2o.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sql2oAlbumDao  implements AlbumDao {

    private final Sql2o sql2o;

    public Sql2oAlbumDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public List<Album> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM albums")
                    .executeAndFetch(Album.class);
        }
    }

    @Override
    public List<Review> getAllReviewsByAlbum(int albumId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM reviews WHERE albumId = :albumId")
                    .addParameter("albumId", albumId)
                    .executeAndFetch(Review.class);
        }
    }

    @Override
    public void add(Album album) {
        String sql = "INSERT INTO albums (name, releaseDate, tracks, imageUrl, artistId) VALUES (:name, :releaseDate, :tracks, :imageUrl, :artistId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(album)
                    .executeUpdate()
                    .getKey();
            album.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public Album findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM albums WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Album.class);
        }
    }

    @Override
    public void update(int id, String name, String releaseDate, String tracks, String imageUrl, int artistId) {
        String sql = "UPDATE albums SET (name, releaseDate, tracks, imageUrl, artistId) = (:name, :releaseDate, :tracks, :imageUrl, :artistId) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("releaseDate", releaseDate)
                    .addParameter("tracks", new ArrayList<>(Arrays.asList(tracks.split(", "))))
                    .addParameter("imageUrl", imageUrl)
                    .addParameter("artistId", artistId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from albums WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteByArtistId(int artistId) {
        String sql = "DELETE from albums WHERE artistId=:artistId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("artistId", artistId)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }
}
