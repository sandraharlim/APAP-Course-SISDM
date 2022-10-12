package tugas1.SISDM3.SISDM.service;

import tugas1.SISDM3.SISDM.model.PresensiModel;

import java.util.List;

public interface PresensiService {
    //Method untuk mendapatkan daftar presensi yang telah tersimpan
    List<PresensiModel> getListPresensi();

    //Method untuk mendapatkan data presensi berdasarkan id presensi
    PresensiModel getPresensiById(Long id);

    PresensiModel addPresensi(PresensiModel presensi);

    PresensiModel updatePresensi(PresensiModel presensi);
}
