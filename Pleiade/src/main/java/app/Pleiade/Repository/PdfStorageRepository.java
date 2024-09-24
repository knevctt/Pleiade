package app.Pleiade.Repository;

import app.Pleiade.Entity.PdfData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfStorageRepository extends JpaRepository<PdfData, Long> {

}
