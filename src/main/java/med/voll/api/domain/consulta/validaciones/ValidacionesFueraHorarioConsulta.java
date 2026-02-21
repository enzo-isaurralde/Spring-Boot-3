package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;


@Component
public class ValidacionesFueraHorarioConsulta implements ValidadorDeConsultas{
    public void validar(DatosReservaConsulta datos){
        var fechaDeConsulta = datos.fecha();
        var domingo = fechaDeConsulta.getDayOfWeek().toString().equals("SUNDAY");
        var horarioAperturaClinica = fechaDeConsulta.getHour() < 7;
        var horarioCierreClinica = fechaDeConsulta.getHour() > 18;
        if (domingo || horarioAperturaClinica || horarioCierreClinica){
            throw new ValidacionException("Consulta fuera del horario de atención de la clínica");
        }
    }
}
