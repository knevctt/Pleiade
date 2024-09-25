package app.Pleiade.Repository;

import app.Pleiade.Entity.PdfData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PdfStorageRepository extends JpaRepository<PdfData, Long> {

    Optional<PdfData> findByName(String name);

}
