package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.validaciones.cancelamiento.MotivoCancelamiento;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@Setter
@AllArgsConstructor   // ✅ genera constructor con todos los campos
@NoArgsConstructor    // ✅ genera constructor vacío (requerido por JPA)
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime fecha;
    private Boolean cancelada = false;
    private String motivoCancelamiento;

    // Constructor parcial para reservas nuevas (no canceladas)
    public Consulta(Long id, Medico medico, Paciente paciente, LocalDateTime fecha) {
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
        this.cancelada = false; // por defecto
        this.motivoCancelamiento = null; // por defecto
    }

    // Metodo de negocio para cancelar
    public void cancelar(MotivoCancelamiento motivo) {
        if (motivo == null || motivo.motivo().isBlank()) {
            throw new ValidacionException("El motivo de cancelación es obligatorio");
        }
        this.cancelada = true;
        this.motivoCancelamiento = motivo.motivo();
    }
}
