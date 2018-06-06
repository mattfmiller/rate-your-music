package dao;

import models.Album;
import models.Artist;
import org.sql2o.*;

import java.util.List;

public class Sql2oArtistDao implements ArtistDao {

    private final Sql2o sql2o;

    public Sql2oArtistDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public List<Artist> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM artists")
                    .executeAndFetch(Artist.class);
        }
    }

    @Override
    public List<Album> getAllAlbumsByArtist(int artistId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM albums WHERE artistId = :artistId")
                    .addParameter("artistId", artistId)
                    .executeAndFetch(Album.class);
        }
    }

    @Override
    public void add(Artist artist) {
        String sql = "INSERT INTO artists (name, imageUrl) VALUES (:name, :imageUrl)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(artist)
                    .executeUpdate()
                    .getKey();
            artist.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Artist findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM artists WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Artist.class);
        }
    }

    @Override
    public void update(int id, String name, String imageUrl) {
        String sql = "UPDATE artists SET (name, imageUrl) = (:name, :imageUrl) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("imageUrl", imageUrl)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from artists WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
