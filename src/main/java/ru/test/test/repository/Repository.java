package ru.test.test.repository;

import org.springframework.stereotype.Component;
import ru.test.test.model.Author;
import ru.test.test.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Repository implements IRepository {

    private final String TABLE_NAME = "books_table";
    private final String TABLE_AUTHOR_NAME = "author_table";
    private final String AUTHOR_ID = "author_id";
    private final String BOOK_ID = "id";
    private final String BOOK_AUTHOR = "author";
    private final String BOOK_TITLE = "title";
    private final String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private final String user = "postgres";
    private final String password = "root";

    public Repository() {
        createBookDB();
        createAuthorDB();
    }

    private void createBookDB() {
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

    private void createAuthorDB() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            statement.executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s(%s VARCHAR(225) PRIMARY KEY, %s VARCHAR (225))",
                    TABLE_AUTHOR_NAME, AUTHOR_ID, BOOK_AUTHOR));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void add(Book book) {
        addDB(book);
        String authorName = book.getAuthor();
        if (countAuthors(book.getAuthor()) < 1) {
            Author author = new Author("0", authorName);
            author.setId(author.calcId());
            addADB(author);
        }
    }

    private void addDB(Book book) {
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

    private void addADB(Author author) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO author_table VALUES(?, ?)")) {
            Class.forName("org.postgresql.Driver");
            int indexPrepared = 0;
            preparedStatement.setString(++indexPrepared, author.getId());
            preparedStatement.setString(++indexPrepared, author.getAuthorName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        deleteDB(id);
    }

    private void deleteDB(String id) {
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

    public List<Author> getAllA() {
        return getDabaseAllA();
    }

    private List<Author> getDabaseAllA() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");

            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s", TABLE_AUTHOR_NAME));
            return getListFromResultSetA(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int countAuthors(String authorName) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");

            ResultSet resultSet = statement.executeQuery(String.format("SELECT COUNT(*) FROM %s WHERE %s='%s'", TABLE_AUTHOR_NAME, BOOK_AUTHOR, authorName));
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int countAuthorsInBookTable(String authorName) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");

            ResultSet resultSet = statement.executeQuery(String.format("SELECT COUNT(%s) FROM %s", BOOK_AUTHOR, TABLE_NAME));
            return resultSet.getInt(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
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

    private List<Author> getListFromResultSetA(ResultSet resultSet) {
        List<Author> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String id = resultSet.getString(AUTHOR_ID);
                String author = resultSet.getString(BOOK_AUTHOR);
                Author author1 = new Author(id, author);
                result.add(author1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

