package tugas1.SISDM3.SISDM.service;

import tugas1.SISDM3.SISDM.model.KaryawanModel;

import java.util.List;

public interface KaryawanService {
    //Method untuk mendapatkan daftar karyawan yang telah tersimpan
    List<KaryawanModel> getListKaryawan();

    //Method untuk mendapatkan data karyawan berdasarkan id karyawan
    KaryawanModel getKaryawanById(Long id);

    //Method untuk menambahkan karyawan
    KaryawanModel addKaryawan(KaryawanModel karyawan);

    //Method untuk mengubah data karyawan
    KaryawanModel updateKaryawan(KaryawanModel karyawan);

    KaryawanModel deleteKaryawan(KaryawanModel karyawan);

    Long penambahanInsentifSumberTugas(KaryawanModel karyawan);

    Long penambahanInsentifSumberSertifikasi(KaryawanModel karyawan);

    Long penguranganInsentifSumberPresensi(KaryawanModel karyawan);
}
