package tugas1.SISDM3.SISDM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tugas1.SISDM3.SISDM.model.SertifikasiModel;

import java.util.Optional;

public interface SertifikasiDb extends JpaRepository<SertifikasiModel, Long> {
    // JPA
    Optional<SertifikasiModel> findById(Long idSertifikasi);
}
