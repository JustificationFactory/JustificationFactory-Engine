package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.support.evidence.Document;

import java.time.LocalDate;
import java.util.Objects;

public class RedmineDocument extends Document {

    private String reference;
    private String name;
    private String author;
    private LocalDate releaseDate;

    public RedmineDocument(String url) {
        super(url);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RedmineDocument that = (RedmineDocument) o;
        return Objects.equals(reference, that.reference) &&
                Objects.equals(name, that.name) &&
                Objects.equals(author, that.author) &&
                Objects.equals(releaseDate, that.releaseDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), reference, name, author, releaseDate);
    }
}
