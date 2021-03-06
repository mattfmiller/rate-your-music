

import com.google.common.collect.Lists;
import dao.Sql2oAlbumDao;
import dao.Sql2oArtistDao;
import dao.Sql2oReviewDao;
import models.Album;
import models.Artist;
import models.Review;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oArtistDao artistDao = new Sql2oArtistDao(sql2o);
        Sql2oAlbumDao albumDao = new Sql2oAlbumDao(sql2o);
        Sql2oReviewDao reviewDao = new Sql2oReviewDao(sql2o);

        //get: show all albums by all artists and show all artists
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artists", allArtists);
            List<Album> albums = albumDao.getAll();
            model.put("albums", albums);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //show new artist form
        get("/artists/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> artists = artistDao.getAll();
            model.put("artists", artists);
            return new ModelAndView(model, "artist-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new artist form
        post("/artists", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String imageUrl = req.queryParams("imageUrl");
            Artist newArtist = new Artist(name, imageUrl);
            artistDao.add(newArtist);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual artist and their albums
        get("/artists/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfArtistToFind = Integer.parseInt(req.params("id"));
            Artist foundArtist = artistDao.findById(idOfArtistToFind);
            model.put("artist", foundArtist);
            List<Album> allAlbumsByArtist = artistDao.getAllAlbumsByArtist(idOfArtistToFind);
            model.put("albums", allAlbumsByArtist);
            model.put("artists", artistDao.getAll());
            return new ModelAndView(model, "artist-details.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //get: show a form to update an artist
        get("/artists/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editArtist", true);
            Artist artist = artistDao.findById(Integer.parseInt(req.params("id")));
            model.put("artist", artist);
            model.put("artists", artistDao.getAll());
            return new ModelAndView(model, "artist-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update an artist
        post("/artists/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfArtistToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newArtistName");
            String newArtistImageUrl = req.queryParams("newArtistImageUrl");
            artistDao.update(idOfArtistToEdit, newName, newArtistImageUrl);
            res.redirect("/artists/" + idOfArtistToEdit);
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new album form
        get("/albums/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artists", allArtists);
            return new ModelAndView(model, "album-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new album form
        post("/albums", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("allArtists", allArtists);
            String name = req.queryParams("name");
            String releaseDate = req.queryParams("releaseDate");
            String tracks = req.queryParams("tracks");
            String imageUrl = req.queryParams("imageUrl");
            int artistId = Integer.parseInt(req.queryParams("artistId"));
            Album newAlbum = new Album(name, releaseDate,tracks,imageUrl, artistId);
            albumDao.add(newAlbum);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual album that is nested in an artist
        get("/artists/:artist_id/albums/:album_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAlbumToFind = Integer.parseInt(req.params("album_id"));
            Album foundAlbum = albumDao.findById(idOfAlbumToFind);
            int idOfArtistToFind = Integer.parseInt(req.params("artist_id"));
            Artist foundArtist = artistDao.findById(idOfArtistToFind);
            model.put("album", foundAlbum);
            model.put("artist", foundArtist);
            List<String> tracks = new ArrayList<>(Arrays.asList(foundAlbum.getTracks().split("; ")));
            model.put("tracks", tracks);
            List<Review> reviews = albumDao.getAllReviewsByAlbum(idOfAlbumToFind);
            model.put("reviews", Lists.reverse(reviews));
            return new ModelAndView(model, "album-details.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update an album
        get("/albums/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artists", allArtists);
            Album album = albumDao.findById(Integer.parseInt(req.params("id")));
            model.put("album", album);
            model.put("editAlbum", true);
            return new ModelAndView(model, "album-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update an album
        post("/albums/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artists", allArtists);
            int albumToEditId = Integer.parseInt(req.params("id"));
            String newAlbumName = req.queryParams("newAlbumName");
            String newAlbumReleaseDate = req.queryParams("newAlbumReleaseDate");
            String newAlbumTracks = req.queryParams("newAlbumTracks");
            String newAlbumImageUrl = req.queryParams("newAlbumImageUrl");
            int newAlbumArtistId = Integer.parseInt(req.params("newAlbumArtistId"));
            albumDao.update(albumToEditId, newAlbumName, newAlbumReleaseDate, newAlbumTracks, newAlbumImageUrl, newAlbumArtistId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //post: process new review form
        post("/artists/:artist_id/albums/:album_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAlbumToFind = Integer.parseInt(req.params("album_id"));
            int idOfArtistToFind = Integer.parseInt(req.params("artist_id"));
            List<Artist> allArtists = artistDao.getAll();
            model.put("allArtists", allArtists);
            int rating = Integer.parseInt(req.queryParams("rating"));
            String author = req.queryParams("author");
            String comment = req.queryParams("comment");
            Review newReview = new Review(rating, author, comment, idOfAlbumToFind);
            reviewDao.add(newReview);
            res.redirect("/artists/" + idOfArtistToFind +"/albums/" + idOfAlbumToFind);
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a review
        get("/reviews/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artists", allArtists);
            Review review = reviewDao.findById(Integer.parseInt(req.params("id")));
            model.put("review", review);
            return new ModelAndView(model, "review-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a review
        post("/reviews/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artists", allArtists);
            int reviewToEditId = Integer.parseInt(req.params("id"));
            int newReviewRating = Integer.parseInt(req.queryParams("newReviewRating"));
            String newReviewAuthor = req.queryParams("newReviewAuthor");
            String newReviewComment = req.queryParams("newReviewComment");
            Review reviewToEdit = reviewDao.findById(reviewToEditId);
            int newReviewAlbumId = reviewToEdit.getAlbumId();
            reviewDao.update(reviewToEditId, newReviewRating, newReviewAuthor, newReviewComment, newReviewAlbumId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}
