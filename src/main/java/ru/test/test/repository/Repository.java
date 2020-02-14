package ru.test.test.repository;

import org.springframework.stereotype.Component;
import ru.test.test.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Repository implements IRepository {

    private final String TABLE_NAME = "books_table";
    private final String BOOK_ID = "id";
    private final String BOOK_AUTHOR = "author";
    private final String BOOK_TITLE = "title";
    private final String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private final String user = "postgres";
    private final String password = "root";

    public Repository() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     String.format("CREATE TABLE IF NOT EXISTS %s(%s VARCHAR(225) PRIMARY KEY, %s VARCHAR(225), %s VARCHAR(225))",
                             TABLE_NAME, BOOK_ID, BOOK_AUTHOR, BOOK_TITLE))) {
            Class.forName("org.postgresql.Driver");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void add(Book book) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO books_table VALUES(?, ?, ?)")) {
            Class.forName("org.postgresql.Driver");
            int indexPrepared = 0;
            preparedStatement.setString(++indexPrepared, book.getId());
            preparedStatement.setString(++indexPrepared, book.getAuthor());
            preparedStatement.setString(++indexPrepared, book.getTitle());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        System.out.println("Try delete book " + id);
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM books_table WHERE id=?")) {
            Class.forName("org.postgresql.Driver");
            int indexPrepared = 0;
            preparedStatement.setString(++indexPrepared, id);
            preparedStatement.executeUpdate();
            System.out.println("deleting book " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(Book book) {

    }

    public List<Book> getAll() {
        return getDabaseAll();
    }

    private List<Book> getDabaseAll() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM books_table");
            return getListFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Book> getListFromResultSet(ResultSet resultSet) {
        List<Book> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String idBook = resultSet.getString(BOOK_ID);
                String author = resultSet.getString(BOOK_AUTHOR);
                String title = resultSet.getString(BOOK_TITLE);
                Book book = new Book(author, title);
                book.setId(idBook);
                result.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

