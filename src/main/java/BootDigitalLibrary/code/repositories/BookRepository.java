package BootDigitalLibrary.code.repositories;

import BootDigitalLibrary.code.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT * FROM book WHERE id in (SELECT book_id FROM person_book WHERE person_id = :personId)",
            nativeQuery = true)
    List<Book> findBooksReadByPerson(@Param("personId") int personId);
}
