

import dao.Sql2oAlbumDao;
import dao.Sql2oArtistDao;
import dao.Sql2oReviewDao;
import models.Album;
import models.Artist;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    }
}
