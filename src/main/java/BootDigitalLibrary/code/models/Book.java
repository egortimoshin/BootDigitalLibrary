package BootDigitalLibrary.code.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Название не может быть пустым")
    @Column(name = "title")
    private String title;

    @Pattern(regexp = "[A-Za-zА-Яа-я ]{3,}", message = "Не правильное имя автора")
    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "Год издания должен быть >= 0")
    @Column(name = "year")
    private int year;

    @Min(value = 0, message = "Количество книг должно быть >= 0")
    @Column(name = "quantity")
    private int quantity;

    @ManyToMany
    @JoinTable(name = "person_book", joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Person> people;

    public Book() {
    }

    public Book(int id, String title, String author, int year, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
