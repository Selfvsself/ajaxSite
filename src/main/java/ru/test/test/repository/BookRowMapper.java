package ru.test.test.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.test.test.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        String idBook = resultSet.getString("id");
        String author = resultSet.getString("author");
        String title = resultSet.getString("title");
        Book book = new Book(author, title);
        book.setId(idBook);
        return book;
    }
}
