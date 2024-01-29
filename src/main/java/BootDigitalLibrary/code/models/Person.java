package BootDigitalLibrary.code.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Имя пользователя не может быть пустым")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{2,} [A-Za-zА-Яа-я]{2,}", message = "Неверно введено имя пользователя")
    @Column(name = "name")
    private String name;

    @Size(min = 5, message = "Ник телеграма слишком короткий")
    @Pattern(regexp = "[a-zA-Z]+[\\da-zA-Z_]*[\\da-zA-Z]+", message = "Ник телеграма введен неверно")
    @NotEmpty(message = "Телеграм не может быть пустым")
    @Column(name = "tg")
    private String tg;

    @ManyToMany(mappedBy = "people")
    private List<Book> books;

    public Person() {
    }

    public Person(int id, String name, String tg) {
        this.id = id;
        this.name = name;
        this.tg = tg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    @Override
    public String toString() {
        return id + " " + name;
    }
}
