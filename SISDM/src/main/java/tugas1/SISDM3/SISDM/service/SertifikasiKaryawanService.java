package tugas1.SISDM3.SISDM.service;

import tugas1.SISDM3.SISDM.model.SertifikasiKaryawanModel;

import java.util.List;

public interface SertifikasiKaryawanService {

    String generateNoSertifikasi(SertifikasiKaryawanModel sertifikasiKaryawan);

    void deleteSertifikasiKaryawanById(Long idKaryawan);

    List<SertifikasiKaryawanModel> getListKaryawanSearch(Long idSertifikasi);
}
