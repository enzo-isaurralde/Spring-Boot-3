package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;


@Component
public class ValidacionConConsultaDeAnticipacion implements ValidadorDeConsultas{
    public void validar(DatosReservaConsulta datos){
        var fecha = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaHorario = Duration.between(fecha, ahora).toMinutes();
        if (diferenciaHorario < 30){
            throw new ValidacionException("La consulta debe ser reservada con al menos 30 minutos de anticipación");
        }
    }
}
