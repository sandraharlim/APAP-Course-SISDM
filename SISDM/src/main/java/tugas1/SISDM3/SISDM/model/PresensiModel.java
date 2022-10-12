package tugas1.SISDM3.SISDM.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "presensi")
public class PresensiModel {
    @Id
    @Column(name = "id_presensi", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPresensi;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(name = "tanggal", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggal;

    @NotNull
    @Column(name = "waktu_masuk", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime waktuMasuk;

    @NotNull
    @Column(name = "waktu_keluar", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime waktuKeluar;

    // Relasi dengan KaryawanModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_karyawan", referencedColumnName = "id_karyawan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KaryawanModel karyawan;

    // Relasi dengan TugasModel
    @OneToMany(mappedBy = "presensi", fetch = FetchType.LAZY)
    private List<TugasModel> listTugas;
}
