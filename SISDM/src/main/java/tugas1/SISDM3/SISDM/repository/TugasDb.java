package tugas1.SISDM3.SISDM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tugas1.SISDM3.SISDM.model.PresensiModel;
import tugas1.SISDM3.SISDM.model.TugasModel;

import java.util.List;
import java.util.Optional;

public interface TugasDb extends JpaRepository<TugasModel, Long> {
    // JPA
    Optional<TugasModel> findById(Long idTugas);

    List<TugasModel> findAllByPresensiOrPresensi (PresensiModel presensi, PresensiModel presensi2);

    List<TugasModel> findAllByPresensi_Karyawan_IdKaryawanAndAndStatus(Long idKaryawan, Integer status);
}
