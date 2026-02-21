package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;


@Component
public class ValidadorPacienteMismoConsultaEnElMismoDia implements ValidadorDeConsultas {
    private ConsultaRepository consultaRepository;

    public void validar(DatosReservaConsulta datos) {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);
        var pacienteTieneOtraMismaConsulta = consultaRepository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ultimoHorario);
        if (pacienteTieneOtraMismaConsulta) {
            throw new RuntimeException("El paciente ya tiene una consulta agendada para el mismo día");
        }
    }
}