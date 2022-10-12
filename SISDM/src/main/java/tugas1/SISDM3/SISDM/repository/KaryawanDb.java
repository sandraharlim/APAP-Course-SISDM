package tugas1.SISDM3.SISDM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tugas1.SISDM3.SISDM.model.KaryawanModel;
import tugas1.SISDM3.SISDM.model.TugasModel;

import java.util.List;
import java.util.Optional;

public interface KaryawanDb extends JpaRepository<KaryawanModel, Long> {
    // JPA
    Optional<KaryawanModel> findById(Long idKaryawan);

//    @Query("SELECT k FROM KaryawanModel k WHERE k.idKaryawan = :idKaryawan")
//    Optional<KaryawanModel> findByIdUsingQuery(@Param("idKaryawan") Long idKaryawan);
}
