package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.consulta.ReservaConsultas;
import med.voll.api.domain.consulta.validaciones.cancelamiento.DatosCancelamientoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ReservaConsultas reserva;


    @PostMapping
    @Transactional
    public ResponseEntity <DatosDetalleConsulta> reservar (@RequestBody @Valid DatosReservaConsulta datos) {

        var detalleConsulta = reserva.reservar(datos);

        return ResponseEntity.ok(detalleConsulta);
    }
    @PutMapping("/cancelar")
    @Transactional
    public ResponseEntity<Void> cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos) {
        reserva.cancelar(datos); // ✅ llamada sobre el bean inyectado
        return ResponseEntity.noContent().build();
    }


}
