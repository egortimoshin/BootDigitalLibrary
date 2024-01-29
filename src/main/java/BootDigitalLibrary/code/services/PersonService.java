package BootDigitalLibrary.code.services;

import BootDigitalLibrary.code.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import BootDigitalLibrary.code.repositories.PersonRepository;

import java.util.List;

@Service
@Transactional
public class PersonService {
    private PersonRepository personRepository;
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public PersonService(PersonRepository personRepository, JdbcTemplate jdbcTemplate) {
        this.personRepository = personRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public Person findById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public void insertPerson(Person person) {
        personRepository.save(person);
    }

    public void editPerson(Person newOne) {
        Person oldOne = findById(newOne.getId());
        oldOne.setName(newOne.getName());
        oldOne.setTg(newOne.getTg());
        personRepository.save(oldOne);
    }

    public Person showPersonByName(String name) {
        return personRepository.findByName(name);
    }

    public Person showPersonByTg(String tg) {
        return personRepository.findByTg(tg);
    }

    public List<Person> getPeopleReadingBook(int book_id) {
        List<Person> list = personRepository.findPeopleReadingBook(book_id);
        for (int i = 0; i < list.size(); i++) {
            Person person = list.get(i);
            person = personRepository.findById(person.getId()).orElse(null);
            list.set(i, person);
        }
        return list;
    }
}
