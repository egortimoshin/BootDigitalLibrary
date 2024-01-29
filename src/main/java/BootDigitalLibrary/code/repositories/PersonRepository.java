package BootDigitalLibrary.code.repositories;

import BootDigitalLibrary.code.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByName(String name);
    Person findByTg(String tg);

    @Query(value = "SELECT * FROM person WHERE id IN (SELECT person_id FROM person_book WHERE book_id = :bookId)",
            nativeQuery = true)
    List<Person> findPeopleReadingBook(@Param("bookId") int bookId);
}
