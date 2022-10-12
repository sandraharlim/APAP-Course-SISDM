package tugas1.SISDM3.SISDM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tugas1.SISDM3.SISDM.model.KaryawanModel;
import tugas1.SISDM3.SISDM.model.SertifikasiKaryawanModel;
import tugas1.SISDM3.SISDM.model.SertifikasiModel;
import tugas1.SISDM3.SISDM.model.TugasModel;
import tugas1.SISDM3.SISDM.service.KaryawanService;
import tugas1.SISDM3.SISDM.service.TugasService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TugasController {
    @Qualifier("tugasServiceImpl")
    @Autowired
    TugasService tugasService;

    @Autowired
    KaryawanService karyawanService;

    @GetMapping("/tambah-tugas")
    private String addTugasFormPage(Model model) {
        model.addAttribute("tugas", new TugasModel());
        return "form-tambah-tugas";
    }

    @PostMapping("/tambah-tugas")
    private String addTugasSubmit(@ModelAttribute TugasModel tugas, Model model) {
        tugasService.addTugas(tugas);
        model.addAttribute("tugas", tugas);
        return "tambah-tugas";
    }

    @GetMapping("/filter-tugas")
    private String filterTugasForm(Model model){
        List<TugasModel> listTugas = tugasService.getListTugas();
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();
        model.addAttribute("listKaryawanExisting", listKaryawan);
        model.addAttribute("listTugasExisting", listTugas);
        return "filter-tugas";
    }

    @PostMapping("/filter-tugas")
    private String filterTugasSubmit(@RequestParam("idKaryawan") Long idKaryawan,
                                     @RequestParam("status") Integer status,
                                     Model model){
        List<TugasModel> listTugasSearch = tugasService.getListTugasSearch(idKaryawan, status);
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();
        model.addAttribute("listKaryawanExisting", listKaryawan);
        model.addAttribute("listTugasExisting", listTugasSearch);
        return "filter-tugas";
    }
}
