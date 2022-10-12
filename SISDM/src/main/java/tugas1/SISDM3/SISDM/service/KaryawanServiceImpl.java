package tugas1.SISDM3.SISDM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1.SISDM3.SISDM.model.KaryawanModel;
import tugas1.SISDM3.SISDM.model.PresensiModel;
import tugas1.SISDM3.SISDM.model.SertifikasiKaryawanModel;
import tugas1.SISDM3.SISDM.model.TugasModel;
import tugas1.SISDM3.SISDM.repository.KaryawanDb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KaryawanServiceImpl implements KaryawanService {
    @Autowired
    KaryawanDb karyawanDb;


    @Override
    public KaryawanModel getKaryawanById(Long id){
        Optional<KaryawanModel> karyawan = karyawanDb.findById(id);
        if (karyawan.isPresent()){
            return karyawan.get();
        } else return null;
    }

    @Override
    public List<KaryawanModel> getListKaryawan(){
        return karyawanDb.findAll();
    }

    @Override
    public KaryawanModel addKaryawan(KaryawanModel karyawan){
        karyawanDb.save(karyawan);
        return karyawan;
    }

    @Override
    public KaryawanModel updateKaryawan(KaryawanModel karyawan){
        karyawanDb.save(karyawan);
        return karyawan;
    }

    @Override
    public KaryawanModel deleteKaryawan(KaryawanModel karyawan){
        karyawanDb.delete(karyawan);
        return karyawan;
    }

    @Override
    public Long penambahanInsentifSumberTugas(KaryawanModel karyawan){
        Long counter = new Long(0);
        List<PresensiModel> listPresensi = karyawan.getListPresensi();
        if(listPresensi != null || listPresensi.size() > 0){
            for(PresensiModel presensi: listPresensi){
                for(TugasModel tugas: presensi.getListTugas()){
                    if(tugas.getStatus() == 2){
                        Long temp = Long.valueOf(tugas.getStoryPoint() * 1000);
                        counter += temp;
                    }
                }
            }
        }
        return counter;
    }

    @Override
    public Long penambahanInsentifSumberSertifikasi(KaryawanModel karyawan){
        Long counter = new Long(0);
        List<SertifikasiKaryawanModel> listSertifikasi = karyawan.getListSertifikasi();
        if(listSertifikasi != null || listSertifikasi.size()>0){
            for(int i = 0; i<listSertifikasi.size(); i++){
                counter += 3000L;
            }
        }
        return counter;
    }

    @Override
    public Long penguranganInsentifSumberPresensi(KaryawanModel karyawan){
        List<PresensiModel> listPresensi = karyawan.getListPresensi();
        Long counter = new Long(0);
        if(listPresensi != null || listPresensi.size()>0){
            for(PresensiModel presensi: listPresensi){
                if(presensi.getStatus() == 0){
                    counter += 1000L;
                }
            }
        }
        return counter;
    }
}
