package ru.test.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.test.test.model.Author;
import ru.test.test.model.Book;
import ru.test.test.repository.IRepository;

import java.util.List;

@RestController
@RequestMapping("book")
public class MainController {

    IRepository repository;

    public MainController(IRepository repository) {
        this.repository = repository;
    }

    @GetMapping("all")
    public List<Book> getAllBooks() {
        return repository.getAll();
    }

    @GetMapping("allA")
    public List<Author> getAllAuthors() {
        return repository.getAllA();
    }

    @GetMapping("add")
    public void addBook(@RequestParam String author, @RequestParam String title) {
        Book book = new Book(author, title);
        book.setId(book.calcId());
        repository.add(book);
    }

    @GetMapping("delete")
    public void deleteBook(@RequestParam String id) {
        repository.delete(id);
    }
}
