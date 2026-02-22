package med.voll.api.domain.consulta.validaciones.cancelamiento;




import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record DatosCancelamientoConsulta(
        @NotNull Long idConsulta,
        @NotBlank MotivoCancelamiento motivo
) {
}
