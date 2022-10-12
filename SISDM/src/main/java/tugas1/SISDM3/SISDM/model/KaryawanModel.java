package tugas1.SISDM3.SISDM.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "karyawan")
public class KaryawanModel {
    @Id
    @Column(name = "id_karyawan", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKaryawan;

    @NotNull
    @Size(max=255)
    @Column(name = "nama_depan", nullable = false)
    private String namaDepan;

    @NotNull
    @Size(max=255)
    @Column(name = "nama_belakang", nullable = false)
    private String namaBelakang;

    @NotNull
    @Column(name = "jenis_kelamin", nullable = false)
    private Integer jenisKelamin;

    @NotNull
    @Column(name = "tanggal_lahir", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalLahir;

    @NotNull
    @Size(max=255)
    @Column(name = "email", nullable = false)
    private String email;

    // Relasi dengan PresensiModel
    @OneToMany(mappedBy = "karyawan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PresensiModel> listPresensi;

    // Relasi dengan Sertifikasi
    @OneToMany(mappedBy = "karyawan", cascade = CascadeType.ALL)
    private List<SertifikasiKaryawanModel> listSertifikasi;

    @Column(name = "insentif")
    private Long insentif;
}
