package tugas1.SISDM3.SISDM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1.SISDM3.SISDM.model.PresensiModel;
import tugas1.SISDM3.SISDM.repository.PresensiDb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PresensiServiceImpl implements PresensiService{
    @Autowired
    PresensiDb presensiDb;

    @Override
    public PresensiModel getPresensiById(Long id){
        Optional<PresensiModel> presensi = presensiDb.findById(id);
        if (presensi.isPresent()){
            return presensi.get();
        } else return null;
    }

    @Override
    public List<PresensiModel> getListPresensi() {
        return presensiDb.findAll();
    }

    @Override
    public PresensiModel addPresensi(PresensiModel presensi){
        presensiDb.save(presensi);
        return presensi;
    }

    @Override
    public PresensiModel updatePresensi(PresensiModel presensi){
        presensiDb.save(presensi);
        return presensi;
    }
}
