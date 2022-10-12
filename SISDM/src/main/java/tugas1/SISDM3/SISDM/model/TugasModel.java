package tugas1.SISDM3.SISDM.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tugas")
public class TugasModel {
    @Id
    @Column(name = "id_tugas", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTugas;

    @NotNull
    @Size(max=255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max=255)
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @NotNull
    @Column(name = "story_point", nullable = false)
    private Integer storyPoint;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    // Relasi dengan PresensiModel
    @ManyToOne(fetch = FetchType.EAGER  )
    @JoinColumn(name = "id_presensi", referencedColumnName = "id_presensi")
    private PresensiModel presensi;
}
