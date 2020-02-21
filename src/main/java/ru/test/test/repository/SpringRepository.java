package ru.test.test.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.test.test.model.Author;
import ru.test.test.model.Book;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class SpringRepository implements IRepository {

    private JdbcTemplate jdbcTemplate;

    public SpringRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Book book) {
        jdbcTemplate.update("INSERT INTO books_table VALUES(?, ?, ?)", book.getId(), book.getAuthor(), book.getTitle());
        if (!isAuthorExist(book.getAuthor())) {
            Author author = new Author("0", book.getAuthor());
            author.setId(author.calcId());
            jdbcTemplate.update("INSERT INTO author_table VALUES(?, ?)", author.getId(), author.getAuthorName());
        }
    }

    private boolean isAuthorExist(String authorName) {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM author_table WHERE author=?", new Object[]{authorName}, Integer.class);
        return count > 0;
    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM books_table WHERE id=?", new Object[] {id});
    }

    @Override
    public void update(Book book) {
        System.out.println("add");
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM books_table", new BookRowMapper());
    }

    @Override
    public List<Author> getAllA() {
        return jdbcTemplate.query("SELECT * FROM author_table", new AuthorRowMapper());
    }
}
