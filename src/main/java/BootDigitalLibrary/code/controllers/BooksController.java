package BootDigitalLibrary.code.controllers;

import BootDigitalLibrary.code.models.Book;
import BootDigitalLibrary.code.models.Person;
import BootDigitalLibrary.code.services.BookService;
import BootDigitalLibrary.code.util.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import BootDigitalLibrary.code.services.PersonService;

@Controller
@ComponentScan("dao")
@ComponentScan("util")
@ComponentScan("services")
@RequestMapping("/books")
public class BooksController {
    private final BookValidator bookValidator;
    private final PersonService personService;

    private final BookService bookService;

    @Autowired
    public BooksController(BookValidator bookValidator,
                           PersonService personService, BookService bookService) {
        this.bookValidator = bookValidator;
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/showAll";
    }

    @GetMapping("/{id}")
    public String showBook(Model model, @PathVariable("id") int id,
                           @ModelAttribute("person") Person peron) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("people", personService.getPeopleReadingBook(id));
        model.addAttribute("allPeople", personService.findAll());
        return "books/showBook";
    }

    @GetMapping("/new")
    public String showNewPersonView(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult br) {
        bookValidator.validate(book, br);
        if (br.hasErrors()) return "books/new";
        bookService.insertBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showBookEditionView(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @PostMapping("/{id}")
    public String editBook(@ModelAttribute("book") @Valid Book book, BindingResult br) {
        bookValidator.validate(book, br);
        if (br.hasErrors()) return "books/edit";
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping("/{book_id}/connect")
    public String giveBookToPerson(@PathVariable("book_id") int book_id,
                              @ModelAttribute("person") Person person,
                              Model model) {
        Book book = bookService.findById(book_id);
        if (book.getQuantity() != 0)
            bookService.giveBookToPerson(book, person);
        return "redirect:/books/{book_id}";
    }
}