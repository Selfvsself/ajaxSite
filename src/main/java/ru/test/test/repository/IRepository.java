package ru.test.test.repository;

import ru.test.test.model.Author;
import ru.test.test.model.Book;

import java.util.List;

public interface IRepository {

    void add(Book book);

    void delete(String id);

    void update(Book book);

    List<Book> getAll();
    List<Author> getAllA();
}
