package tugas1.SISDM3.SISDM.service;

import tugas1.SISDM3.SISDM.model.PresensiModel;
import tugas1.SISDM3.SISDM.model.TugasModel;

import java.util.List;

public interface TugasService {
    List<TugasModel> getListTugas();

    TugasModel addTugas(TugasModel tugas);

    List<TugasModel> getListTugasNull(PresensiModel presensi);
    TugasModel getTugasById(Long idTugas);

    List<TugasModel> getListTugasSearch(Long idKaryawan, Integer status);
}
