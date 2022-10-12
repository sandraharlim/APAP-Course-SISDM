package tugas1.SISDM3.SISDM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1.SISDM3.SISDM.model.SertifikasiKaryawanModel;
import tugas1.SISDM3.SISDM.model.SertifikasiModel;
import tugas1.SISDM3.SISDM.repository.SertifikasiKaryawanDb;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class SertifikasiKaryawanServiceImpl implements SertifikasiKaryawanService{
    @Autowired
    SertifikasiKaryawanDb sertifikasiKaryawanDb;

    public String generateNoSertifikasi(SertifikasiKaryawanModel sertifikasiKaryawan){
        String tigaKarakterPertama = "SER";
        LocalDate tanggalLahir = sertifikasiKaryawan.getKaryawan().getTanggalLahir();
        int tahun = tanggalLahir.getYear();
        LocalDate tanggalPengambilan = sertifikasiKaryawan.getTanggalPengambilan();
        int tanggal = tanggalPengambilan.getDayOfMonth();
        int bulan = tanggalPengambilan.getMonthValue();
        String tanggalBulanPengambilanStr = String.format("%02d%02d", tanggal, bulan);
        long empatKarakterBerikutnya = Long.parseLong(tanggalBulanPengambilanStr) + tahun;

        char hurufPertamaSertifikasi = sertifikasiKaryawan.getSertifikasi().getNama().toLowerCase().charAt(0);
        int hurufPertamaSertif = hurufPertamaSertifikasi - 'a' + 1;

        char hurufPertamaNamaKaryawan = sertifikasiKaryawan.getKaryawan().getNamaDepan().toLowerCase().charAt(0);
        int hurufPertamaKaryawan = hurufPertamaNamaKaryawan - 'a' + 1;

        long idKaryawan = sertifikasiKaryawan.getKaryawan().getIdKaryawan();

        String result = String.format("%s%d%02d%02d%03d", tigaKarakterPertama, empatKarakterBerikutnya, hurufPertamaSertif, hurufPertamaKaryawan, idKaryawan);
        return result;
    }

    public void deleteSertifikasiKaryawanById(Long idKaryawan){
        sertifikasiKaryawanDb.deleteSertifikasiKaryawanModelByKaryawan_IdKaryawan(idKaryawan);
    }

    public List<SertifikasiKaryawanModel> getListKaryawanSearch(Long idSertifikasi){
        return sertifikasiKaryawanDb.findAllBySertifikasi_IdSertifikasi(idSertifikasi);

    }
}
