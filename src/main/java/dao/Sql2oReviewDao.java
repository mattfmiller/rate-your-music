package dao;

import models.Review;
import org.sql2o.*;

import java.util.List;

public class Sql2oReviewDao implements ReviewDao {

    private final Sql2o sql2o;

    public Sql2oReviewDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Review review) {
        String sql = "INSERT INTO reviews (rating, author, comment, albumId) VALUES (:rating, :author, :comment, :albumId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(review)
                    .executeUpdate()
                    .getKey();
            review.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public List<Review> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM reviews")
                    .executeAndFetch(Review.class);
        }
    }

    @Override
    public Review findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM reviews WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Review.class);
        }
    }

    @Override
    public void update(int id, int rating, String author, String comment, int albumId) {
        String sql = "UPDATE reviews SET (rating, author, comment, albumId) = (:rating, :author, :comment, :albumId)  WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("rating", rating)
                    .addParameter("author", author)
                    .addParameter("comment", comment)
                    .addParameter("comment", comment)
                    .addParameter("albumId", albumId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from reviews WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void deleteByAlbumId(int albumId) {
        String sql = "DELETE from reviews WHERE albumId=:albumId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("albumId", albumId)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }
}
