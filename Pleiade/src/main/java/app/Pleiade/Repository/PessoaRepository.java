package app.Pleiade.Repository;

import app.Pleiade.Entity.PessoasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<PessoasEntity, Long> {

    PessoasEntity findPessoaByDocumento(String documento);
}