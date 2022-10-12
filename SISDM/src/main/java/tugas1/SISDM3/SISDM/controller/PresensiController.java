package tugas1.SISDM3.SISDM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugas1.SISDM3.SISDM.model.*;
import tugas1.SISDM3.SISDM.service.KaryawanService;
import tugas1.SISDM3.SISDM.service.PresensiService;
import tugas1.SISDM3.SISDM.service.TugasService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PresensiController {
    @Qualifier("presensiServiceImpl")
    @Autowired
    PresensiService presensiService;

    @Autowired
    TugasService tugasService;

    @Autowired
    KaryawanService karyawanService;

    @GetMapping("/presensi")
    public String listPresensi(Model model){
        List<PresensiModel> listPresensi = presensiService.getListPresensi();
        if(listPresensi.size() == 0){
            System.out.println("tak ada");
        }
        model.addAttribute("listPresensi", listPresensi);
        return "viewall-presensi";
    }

    @GetMapping("/presensi/{idPresensi}")
    public String viewDetailPresensiPage(@PathVariable long idPresensi, Model model){
        PresensiModel presensi = presensiService.getPresensiById(idPresensi);
        List<TugasModel> listTugas = presensi.getListTugas();
        model.addAttribute("listTugas", listTugas);
        model.addAttribute("presensi", presensi);
        return "detail-presensi";
    }

    @GetMapping("/presensi/tambah")
    public String addPresensiForm(Model model){
        PresensiModel presensi = new PresensiModel();
        List<KaryawanModel> listKaryawanExisting = karyawanService.getListKaryawan();
        List<TugasModel> listTugasExisting = tugasService.getListTugasNull(null);
        List<TugasModel> listTugasNew = new ArrayList<>();

        presensi.setListTugas(listTugasNew);
        presensi.getListTugas().add(new TugasModel());

        model.addAttribute("presensi", presensi);
        model.addAttribute("listTugasExisting", listTugasExisting);
        model.addAttribute("listKaryawanExisting", listKaryawanExisting);
        return "form-add-presensi";
    }

    @PostMapping(value="/presensi/tambah", params = {"save"})
    public String addPresensiSubmit(@ModelAttribute PresensiModel presensi, Model model){

        KaryawanModel karyawan = karyawanService.getKaryawanById(presensi.getKaryawan().getIdKaryawan());
        presensi.setKaryawan(karyawan);

        LocalTime batasTelat = LocalTime.of(7,0);
        if(presensi.getWaktuMasuk().isAfter(batasTelat)){
            // Terlambat
            presensi.setStatus(0);
        } else{
            presensi.setStatus(1);
        }

        presensiService.addPresensi(presensi);
        if (presensi.getListTugas() == null){
            presensi.setListTugas(new ArrayList<>());
        } else {
            List<TugasModel> listTugas = presensi.getListTugas();
            for (TugasModel tugas : listTugas){
                TugasModel tugasDb = tugasService.getTugasById(tugas.getIdTugas());
                tugasDb.setPresensi(presensi);
                tugasDb.setStatus(tugas.getStatus());
                tugasService.addTugas(tugasDb);
            }
        }

        presensiService.addPresensi(presensi);
        model.addAttribute("presensi", presensi);
        return "add-presensi";
    }

    @PostMapping(value = "/presensi/tambah", params = {"addRowTugas"})
    private String addRowTugasMultiple(
            @ModelAttribute PresensiModel presensi,
            Model model
    ){
        if(presensi.getListTugas() == null || presensi.getListTugas().size()==0){
            presensi.setListTugas(new ArrayList<>());
        }
        presensi.getListTugas().add(new TugasModel());
        List<TugasModel> listTugas = tugasService.getListTugas();

        model.addAttribute("presensi", presensi);
        model.addAttribute("listTugasExisting", listTugas);

        return "form-add-presensi";
    }

    @PostMapping(value = "/presensi/tambah", params = {"deleteRowTugas"})
    private String deleteRowTugasMultiple(
            @ModelAttribute PresensiModel presensi,
            @RequestParam("deleteRowTugas") Integer row,
            Model model
    ){
        final Integer rowId = Integer.valueOf(row);
        presensi.getListTugas().remove(rowId.intValue());

        List<TugasModel> listTugas = tugasService.getListTugas();

        model.addAttribute("presensi", presensi);
        model.addAttribute("listTugasExisting", listTugas);

        return "form-add-presensi";
    }

    @GetMapping(value = "/presensi/{idPresensi}/ubah")
    private String updatePresensiForm(@PathVariable Long idPresensi, Model model){
        PresensiModel presensi = presensiService.getPresensiById(idPresensi);
        List<KaryawanModel> listKaryawanExisting = karyawanService.getListKaryawan();
        List<TugasModel> listTugasNull = tugasService.getListTugasNull(presensi);

        if (presensi.getListTugas() == null || presensi.getListTugas().size() == 0){
            List<TugasModel> listTugasNew = new ArrayList<>();
            presensi.setListTugas(listTugasNew);
            presensi.getListTugas().add(new TugasModel());
        }

        model.addAttribute("presensi", presensi);
        model.addAttribute("listTugasExisting", listTugasNull);
        model.addAttribute("listKaryawanExisting", listKaryawanExisting);
        return "form-update-presensi";
    }


    @PostMapping(value = "/presensi/{idPresensi}/ubah", params = {"save"})
    private String updatePresensiSubmit(@ModelAttribute PresensiModel presensi, Model model){
        KaryawanModel karyawan = karyawanService.getKaryawanById(presensi.getKaryawan().getIdKaryawan());
        presensi.setKaryawan(karyawan);
        List<TugasModel> listTugas = presensi.getListTugas();
        LocalTime waktuKeluar = presensi.getWaktuKeluar();

        presensi = presensiService.getPresensiById(presensi.getIdPresensi());
        presensi.setWaktuKeluar(waktuKeluar);

        for(TugasModel tugas: presensi.getListTugas()){
            TugasModel tugasDb = tugasService.getTugasById(tugas.getIdTugas());
            tugasDb.setPresensi(null);
            tugasService.addTugas(tugas);
        }

        for(TugasModel tugas : listTugas){
            TugasModel tugasDb = tugasService.getTugasById(tugas.getIdTugas());
            tugasDb.setPresensi(presensi);
            tugasDb.setStatus(tugas.getStatus());
            tugasService.addTugas(tugasDb);
        }
        presensiService.updatePresensi(presensi);
        model.addAttribute("presensi", presensi);
        return "update-presensi";
    }

    @PostMapping(value = "/presensi/{idPresensi}/ubah", params = {"addRowTugas2"})
    private String addRowTgsMultiple(
            @ModelAttribute PresensiModel presensi,
            Model model
    ){
        if(presensi.getListTugas() == null || presensi.getListTugas().size()==0){
            presensi.setListTugas(new ArrayList<>());
        }
        presensi.getListTugas().add(new TugasModel());
        List<TugasModel> listTugas = tugasService.getListTugas();

        model.addAttribute("presensi", presensi);
        model.addAttribute("listTugasExisting", listTugas);

        return "form-update-presensi";
    }

    @PostMapping(value = "/presensi/{idPresensi}/ubah", params = {"deleteRowTugas2"})
    private String deleteRowTgsMultiple(
            @ModelAttribute PresensiModel presensi,
            @RequestParam("deleteRowTugas2") Integer row,
            Model model
    ){
        final Integer rowId = Integer.valueOf(row);
        presensi.getListTugas().remove(rowId.intValue());

        List<TugasModel> listTugas = tugasService.getListTugas();

        model.addAttribute("presensi", presensi);
        model.addAttribute("listTugasExisting", listTugas);

        return "form-update-presensi";
    }
}
