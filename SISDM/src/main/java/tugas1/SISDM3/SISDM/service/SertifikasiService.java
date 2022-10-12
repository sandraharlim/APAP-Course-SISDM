package tugas1.SISDM3.SISDM.service;

import tugas1.SISDM3.SISDM.model.SertifikasiModel;

import java.util.List;

public interface SertifikasiService {
    //Method untuk mendapatkan daftar sertifikasi yang telah tersimpan
    List<SertifikasiModel> getListSertifikasi();

    //Method untuk mendapatkan data sertifikasi berdasarkan id sertifikasi
    SertifikasiModel getSertifikasiById(Long id);
}
