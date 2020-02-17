package ru.test.test.model;

public class Author {

    String id;
    String authorName;

    public Author() {
    }

    public Author(String id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }

    public String calcId() {
        StringBuilder builder = new StringBuilder();
        builder.append("A").append(authorName.hashCode());
        builder.append("T").append(System.currentTimeMillis());
        return builder.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
