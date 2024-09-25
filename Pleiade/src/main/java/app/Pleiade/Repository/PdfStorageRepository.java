package app.Pleiade.Repository;

import app.Pleiade.Entity.PdfData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PdfStorageRepository extends JpaRepository<PdfData, Long> {

    Optional<PdfData> findByName(String name);

}
