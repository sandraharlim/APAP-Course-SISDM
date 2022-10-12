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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sertifikasi_karyawan")
public class SertifikasiKaryawanModel {
    // Foreign key, yaitu id_sertifikasi
    @ManyToOne
    @JoinColumn(name = "id_sertifikasi")
    SertifikasiModel sertifikasi;

    // Foreign key, yaitu id_karyawan
    @ManyToOne
    @JoinColumn(name = "id_karyawan")
    KaryawanModel karyawan;

    @NotNull
    @Column(name = "tanggal_pengambilan", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalPengambilan;

    @NotNull
    @Size(max=14)
    @Column(name = "no_sertifikasi", nullable = false)
    private String noSertifikasi;

    @Id
    @Column(name = "id_sertifikasi_karyawan", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSertifikasi;
}
