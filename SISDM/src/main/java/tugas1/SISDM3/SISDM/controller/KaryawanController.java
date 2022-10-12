package tugas1.SISDM3.SISDM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugas1.SISDM3.SISDM.model.*;
import tugas1.SISDM3.SISDM.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class KaryawanController {
    @Qualifier("karyawanServiceImpl")
    @Autowired
    private KaryawanService karyawanService;

    @Autowired
    private PresensiService presensiService;

    @Autowired
    private TugasService tugasService;

    @Autowired
    private SertifikasiService sertifikasiService;

    @Autowired
    private SertifikasiKaryawanService sertifikasiKaryawanService;

    @GetMapping("/karyawan")
    public String listKaryawan(Model model){
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();
        if(listKaryawan.size() == 0){
            System.out.println("tak ada");
        }
        model.addAttribute("listKaryawan", listKaryawan);
        return "viewall-karyawan";
    }

    @GetMapping("/karyawan/{idKaryawan}")
    public String viewDetailKaryawanPage(@PathVariable long idKaryawan, Model model){
        KaryawanModel karyawan = karyawanService.getKaryawanById(idKaryawan);
        List<SertifikasiKaryawanModel> listSertifikasi = karyawan.getListSertifikasi();
        model.addAttribute("listSertifikasi", listSertifikasi);
        model.addAttribute("karyawan", karyawan);
        return "detail-karyawan";
    }

    @GetMapping("/karyawan/tambah")
    public String addKaryawanFormPage(Model model) {
        KaryawanModel karyawan = new KaryawanModel();
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();
        List<SertifikasiKaryawanModel> listSertifikasiNew = new ArrayList<>();

        karyawan.setListSertifikasi(listSertifikasiNew);
        karyawan.getListSertifikasi().add(new SertifikasiKaryawanModel());

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);
        return "form-add-karyawan";
    }

    @PostMapping(value = "/karyawan/tambah", params = {"save"})
    public String addKaryawanSubmit(@ModelAttribute KaryawanModel karyawan, Model model){
        if (karyawan.getListSertifikasi() == null){
            karyawan.setListSertifikasi(new ArrayList<>());
        }
        else{
            List<SertifikasiKaryawanModel> listSertifikasi = karyawan.getListSertifikasi();
            karyawan.setListSertifikasi(new ArrayList<>());
            karyawan.setInsentif(0L);
            karyawanService.addKaryawan(karyawan);
            for(SertifikasiKaryawanModel sertifikasi : listSertifikasi){
                sertifikasi.setKaryawan(karyawan);
                SertifikasiModel sertifikasiModel = sertifikasiService.getSertifikasiById(sertifikasi.getSertifikasi().getIdSertifikasi());
                sertifikasi.setSertifikasi(sertifikasiModel);

                String noSertifikasi = sertifikasiKaryawanService.generateNoSertifikasi(sertifikasi);
                sertifikasi.setNoSertifikasi(noSertifikasi);

            }
            karyawan.setListSertifikasi(listSertifikasi);
        }
        karyawanService.addKaryawan(karyawan);
        model.addAttribute("karyawan", karyawan);
        return "add-karyawan";
    }

    @PostMapping(value = "/karyawan/tambah", params = {"addRowSertifikasi"})
    private String addRowSertifikasiMultiple(
            @ModelAttribute KaryawanModel karyawan,
            Model model
    ){
        if (karyawan.getListSertifikasi() == null || karyawan.getListSertifikasi().size() == 0){
            karyawan.setListSertifikasi(new ArrayList<>());
        }
        karyawan.getListSertifikasi().add(new SertifikasiKaryawanModel());
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);

        return "form-add-karyawan";
    }

    @PostMapping(value = "/karyawan/tambah", params = {"deleteRowSertifikasi"})
    private String deleteRowSertifikasiMultiple(
            @ModelAttribute KaryawanModel karyawan,
            @RequestParam("deleteRowSertifikasi") Integer row,
            Model model
    ){
        final Integer rowId = Integer.valueOf(row);
        karyawan.getListSertifikasi().remove(rowId.intValue());

        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);

        return "form-add-karyawan";
    }

    @GetMapping(value = "/karyawan/{idKaryawan}/ubah")
    private String updateKaryawanForm(@PathVariable Long idKaryawan, Model model){
        KaryawanModel karyawan = karyawanService.getKaryawanById(idKaryawan);
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();
        if (karyawan.getListSertifikasi() == null || karyawan.getListSertifikasi().size() == 0){
            List<SertifikasiKaryawanModel> listSertifikasiNew = new ArrayList<>();
            karyawan.setListSertifikasi(listSertifikasiNew);
            karyawan.getListSertifikasi().add(new SertifikasiKaryawanModel());
        }

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);
        return "form-update-karyawan";
    }

    @PostMapping(value = "/karyawan/{idKaryawan}/ubah")
    private String updateKaryawanSubmit(@ModelAttribute KaryawanModel karyawan, Model model){
        sertifikasiKaryawanService.deleteSertifikasiKaryawanById(karyawan.getIdKaryawan());
        if (karyawan.getListSertifikasi() == null){
            karyawan.setListSertifikasi(new ArrayList<>());
        }
        else{
            List<SertifikasiKaryawanModel> listSertifikasi = karyawan.getListSertifikasi();
            karyawan.setListSertifikasi(new ArrayList<>());
            karyawanService.updateKaryawan(karyawan);
            for(SertifikasiKaryawanModel sertifikasi : listSertifikasi){
                sertifikasi.setKaryawan(karyawan);
                SertifikasiModel sertifikasiModel = sertifikasiService.getSertifikasiById(sertifikasi.getSertifikasi().getIdSertifikasi());
                sertifikasi.setSertifikasi(sertifikasiModel);

                String noSertifikasi = sertifikasiKaryawanService.generateNoSertifikasi(sertifikasi);
                sertifikasi.setNoSertifikasi(noSertifikasi);

            }
            karyawan.setListSertifikasi(listSertifikasi);
        }
        karyawanService.updateKaryawan(karyawan);
        model.addAttribute("karyawan", karyawan);
        return "update-karyawan";
    }

    @PostMapping(value = "/karyawan/{idKaryawan}/ubah", params = {"addRowSertifikasi2"})
    private String addRowSertifMultiple(
            @ModelAttribute KaryawanModel karyawan,
            Model model
    ){
        if (karyawan.getListSertifikasi() == null || karyawan.getListSertifikasi().size() == 0){
            karyawan.setListSertifikasi(new ArrayList<>());
        }
        karyawan.getListSertifikasi().add(new SertifikasiKaryawanModel());
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);

        return "form-add-karyawan";
    }

    @PostMapping(value = "/karyawan/{idKaryawan}/ubah", params = {"deleteRowSertifikasi2"})
    private String deleteRowSertifMultiple(
            @ModelAttribute KaryawanModel karyawan,
            @RequestParam("deleteRowSertifikasi") Integer row,
            Model model
    ){
        final Integer rowId = Integer.valueOf(row);
        karyawan.getListSertifikasi().remove(rowId.intValue());

        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);

        return "form-add-karyawan";
    }

    @GetMapping("/karyawan/{idKaryawan}/hapus")
    private String deleteKaryawan(@PathVariable Long idKaryawan, Model model) {
        KaryawanModel deletedKaryawan = karyawanService.getKaryawanById(idKaryawan);

        for(PresensiModel presensi: deletedKaryawan.getListPresensi()){
            PresensiModel presensiModel = presensiService.getPresensiById(presensi.getIdPresensi());

            for (TugasModel tugas: presensiModel.getListTugas()){
                TugasModel tugasModel = tugasService.getTugasById(tugas.getIdTugas());
                tugasModel.setPresensi(null);

            }
        }
        karyawanService.deleteKaryawan(deletedKaryawan);
        model.addAttribute("karyawan", deletedKaryawan);
        return "delete-karyawan";
    }

    @GetMapping("/filter-sertifikasi")
    private String filterKaryawanForm(Model model){
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();
        model.addAttribute("listKaryawan", listKaryawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);
        return "filter-karyawan";
    }

    @PostMapping("/filter-sertifikasi")
    private String filterKaryawanSubmit(@RequestParam("idSertifikasi") Long idSertifikasi,
                                        Model model){
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();
        List<SertifikasiKaryawanModel> listKaryawanSearch = sertifikasiKaryawanService.getListKaryawanSearch(idSertifikasi);

        List<KaryawanModel> listKaryawan = new ArrayList<>();
        for (SertifikasiKaryawanModel sertifikasiKaryawan: listKaryawanSearch) {
            listKaryawan.add(sertifikasiKaryawan.getKaryawan());
        }

        model.addAttribute("listKaryawan", listKaryawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);
        return "filter-karyawan";
    }

    @GetMapping("karyawan/{idKaryawan}/insentif")
    private String generateInsentif(@PathVariable Long idKaryawan, Model model){
        KaryawanModel karyawan = karyawanService.getKaryawanById(idKaryawan);
        Long sumberTugas = karyawanService.penambahanInsentifSumberTugas(karyawan);
        Long sumberSertifikasi = karyawanService.penambahanInsentifSumberSertifikasi(karyawan);
        Long sumberPresensi = karyawanService.penguranganInsentifSumberPresensi(karyawan);
        Long kalkulasi = sumberTugas + sumberSertifikasi - sumberPresensi;
        if (kalkulasi > 0){
            karyawan.setInsentif(kalkulasi);
        } else{
            karyawan.setInsentif(0L);
        }
        karyawanService.updateKaryawan(karyawan);
        model.addAttribute("karyawan", karyawan);
        model.addAttribute("sumberTugas", sumberTugas);
        model.addAttribute("sumberSertifikasi", sumberSertifikasi);
        model.addAttribute("sumberPresensi", sumberPresensi);
        return "generate-insentif";
    }
}
