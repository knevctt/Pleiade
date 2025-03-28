package app.Pleiade.Repository;

import app.Pleiade.Entity.Book;
import app.Pleiade.Entity.Enum.Genero;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    List<Book> findByGenerosContaining(Genero genero);
    List<Book> findByTitleContainingOrAuthorContainingOrSynopsisContaining(String title, String author, String synopsis);
    Page<Book> findAll(Pageable pageable);
}