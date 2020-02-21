package ru.test.test.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.test.test.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorRowMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        String id = resultSet.getString("author_id");
        String authorName = resultSet.getString("author");
        return new Author(id, authorName);
    }
}
