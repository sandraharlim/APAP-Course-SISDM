package tugas1.SISDM3.SISDM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1.SISDM3.SISDM.model.SertifikasiModel;
import tugas1.SISDM3.SISDM.repository.SertifikasiDb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SertifikasiServiceImpl implements SertifikasiService{
    @Autowired
    SertifikasiDb sertifikasiDb;

    //Method untuk mendapatkan daftar sertifikasi yang telah tersimpan
    @Override
    public List<SertifikasiModel> getListSertifikasi(){
        return sertifikasiDb.findAll();
    }

    //Method untuk mendapatkan data sertifikasi berdasarkan id sertifikasi
    @Override
    public SertifikasiModel getSertifikasiById(Long id){
        Optional<SertifikasiModel> sertifikasi = sertifikasiDb.findById(id);
        if (sertifikasi.isPresent()){
            return sertifikasi.get();
        } else return null;
    }
}
