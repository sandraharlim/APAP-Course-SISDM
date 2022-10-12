package tugas1.SISDM3.SISDM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tugas1.SISDM3.SISDM.model.PresensiModel;

import java.util.Optional;

public interface PresensiDb extends JpaRepository<PresensiModel, Long> {
    // JPA
    Optional<PresensiModel> findById(Long idPresensi);
}
