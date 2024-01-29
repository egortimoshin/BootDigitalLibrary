package BootDigitalLibrary.code.controllers;

import BootDigitalLibrary.code.models.Person;
import BootDigitalLibrary.code.util.PersonValidator;
import jakarta.validation.Valid;
import BootDigitalLibrary.code.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import BootDigitalLibrary.code.services.BookService;
import BootDigitalLibrary.code.services.PersonService;

@Controller
@RequestMapping("/people")
@ComponentScan("dao")
@ComponentScan("util")
@ComponentScan("services")
public class PeopleController {
    private final PersonValidator personValidator;
    private final PersonService personService;
    private final BookService bookService;

    @Autowired
    public PeopleController(PersonValidator personValidator,
                            PersonService personService, BookService bookService) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String showAllPeople(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/showAll";
    }

    @GetMapping("/{id}")
    public String showPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.findById(id));
        model.addAttribute("books", bookService.getBooksReadByPerson(id));
        return "people/showPerson";
    }

    @GetMapping("/new")
    public String showNewPersonView(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid Person person, BindingResult br) {
        personValidator.validate(person, br);
        if (br.hasErrors()) return "people/new";
        personService.insertPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String showPersonEditionView(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.findById(id));
        return "people/edit";
    }

    @PostMapping("/{id}")
    public String editPerson(@ModelAttribute("person") @Valid Person person, BindingResult br) {
        personValidator.validate(person, br);
        if (br.hasErrors()) return "people/edit";
        personService.editPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }

    @PostMapping("/{person_id}/release/{book_id}")
    public String takeBookFromPerson(@PathVariable("book_id") int book_id,
                                     @PathVariable("person_id") int person_id) {
        Person person = personService.findById(person_id);
        Book book = bookService.findById(book_id);
        bookService.takeBookFromPerson(person, book);
        return "redirect:/people/{person_id}";
    }
}
