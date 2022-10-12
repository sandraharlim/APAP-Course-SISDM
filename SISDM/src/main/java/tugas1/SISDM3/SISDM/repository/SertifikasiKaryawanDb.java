package tugas1.SISDM3.SISDM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tugas1.SISDM3.SISDM.model.SertifikasiKaryawanModel;

import java.util.List;
import java.util.Optional;

public interface SertifikasiKaryawanDb extends JpaRepository<SertifikasiKaryawanModel, Long> {
    Optional<SertifikasiKaryawanModel> deleteSertifikasiKaryawanModelByKaryawan_IdKaryawan(Long idKaryawan);

    List<SertifikasiKaryawanModel> findAllBySertifikasi_IdSertifikasi(Long idSertifikasi);
}
