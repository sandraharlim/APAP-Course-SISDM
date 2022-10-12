package tugas1.SISDM3.SISDM.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sertifikasi")
public class SertifikasiModel {
    @Id
    @Column(name = "id_sertifikasi", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSertifikasi;

    @NotNull
    @Size(max=255)
    @Column(name = "nama", nullable = false)
    private String nama;

    // Relasi dengan Karyawan
    @OneToMany(mappedBy = "sertifikasi")
    private List<SertifikasiKaryawanModel> listKaryawan;
}
