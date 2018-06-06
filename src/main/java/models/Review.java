package models;

import java.util.Objects;

public class Review {
    private int rating;
    private String author;
    private String comment;
    private int albumId;
    private int id;

    public Review(int rating, String author, String comment, int albumId){
        this.rating = rating;
        this.author = author;
        this.comment = comment;
        this.albumId = albumId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return rating == review.rating &&
                albumId == review.albumId &&
                id == review.id &&
                Objects.equals(author, review.author) &&
                Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rating, author, comment, albumId, id);
    }
}
