package ru.test.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.test.test.model.Book;
import ru.test.test.repository.IRepository;

import java.util.List;

@RestController
@RequestMapping()
public class MainController {

    IRepository repository;

    public MainController(IRepository repository) {
        this.repository = repository;
    }

    @GetMapping("all")
    public List<Book> getTest() {
        return repository.getAll();
    }

    @GetMapping("add")
    public void addBook(@RequestParam String author, @RequestParam String title) {
        Book book = new Book(author, title);
        book.setId(book.calcId());
        repository.add(book);
    }
}