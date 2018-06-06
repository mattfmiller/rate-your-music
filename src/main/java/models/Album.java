package models;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Album {
    private String name;
    private YearMonth releaseDate;
    private List<String> tracks;
    private String imageUrl;
    private int id;
    private int artistId;

    public Album(String name, String releaseDate,String tracks, String imageUrl, int artistId ) {
        this.name = name;
        this.releaseDate = YearMonth.parse(releaseDate);
        this.tracks = new ArrayList<>(Arrays.asList(tracks.split(",")));
        this.imageUrl = imageUrl;
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public YearMonth getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = YearMonth.parse(releaseDate);
    }

    public List<String> getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = new ArrayList<>(Arrays.asList(tracks.split(",")));
    }

    public void addTrack(String track) {
        this.tracks.add(track);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id == album.id &&
                artistId == album.artistId &&
                Objects.equals(name, album.name) &&
                Objects.equals(releaseDate, album.releaseDate) &&
                Objects.equals(tracks, album.tracks) &&
                Objects.equals(imageUrl, album.imageUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, releaseDate, tracks, imageUrl, id, artistId);
    }
}
