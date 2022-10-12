package tugas1.SISDM3.SISDM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1.SISDM3.SISDM.model.PresensiModel;
import tugas1.SISDM3.SISDM.model.TugasModel;
import tugas1.SISDM3.SISDM.repository.SertifikasiDb;
import tugas1.SISDM3.SISDM.repository.TugasDb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TugasServiceImpl implements TugasService{
    @Autowired
    TugasDb tugasDb;

    @Override
    public List<TugasModel> getListTugas(){
        return tugasDb.findAll();
    }

    @Override
    public TugasModel addTugas(TugasModel tugas){
        tugasDb.save(tugas);
        return tugas;
    }

    @Override
    public List<TugasModel> getListTugasNull(PresensiModel presensi) {
        return tugasDb.findAllByPresensiOrPresensi(null, presensi);
    }

    @Override
    public TugasModel getTugasById(Long idTugas) {
        Optional<TugasModel> tugas = tugasDb.findById(idTugas);
        if(tugas.isPresent())
            return tugas.get();
        return null;
    }

    @Override
    public List<TugasModel> getListTugasSearch(Long idKaryawan, Integer status){
        return tugasDb.findAllByPresensi_Karyawan_IdKaryawanAndAndStatus(idKaryawan, status);
    }

}
