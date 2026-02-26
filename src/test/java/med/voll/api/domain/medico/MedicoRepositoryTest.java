package med.voll.api.domain.medico;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.consulta.Consulta;

import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;


    @Autowired
    private EntityManager em;


    @Test
    @DisplayName("Deberia retornar null cuando el medico buscado existe pero no esta disponible en la fecha")
    void elegirMedicoAleatorioDisponibleEnLaFecha() {

        //given o arrange- Contexto correcto
        var lunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = registrarMedico("Medico1","email@gmail","123456",Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("Paciente1","emailpaciente@gmail","123456");
        registrarConsulta(medico, paciente, lunesSiguienteALas10);
        //when o act- Ejecuto la accion a testear
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA,lunesSiguienteALas10);
        //then o assert- Resultado esperado
        assertThat(medicoLibre).isNull();
    }


    @Test
    @DisplayName("Deberia retornar un medico cuando este disponible en la fecha")
    void elegirMedicoAleatorioDisponibleEnLaFecha1() {

        //given o arrange- Contexto correcto
        var lunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(11,0);
        var medico = registrarMedico("Medico1","email@gmail","123456",Especialidad.CARDIOLOGIA);
        //when o act- Ejecuto la accion a testear
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA,lunesSiguienteALas10);
        //then o assert- Resultado esperado
        assertThat(medicoLibre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Deberia retornar null cuando el medico buscado existe pero no est a disponible en la fecha")
    void elegirMedicoAleatorioDisponibleEnLaFecha2() {


        var lunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(11,0);

        var medico = registrarMedico("Medico1","email@gmail","123456",Especialidad.CARDIOLOGIA);
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA,lunesSiguienteALas10);

        assertThat(medicoLibre).isEqualTo(medico);
    }






    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        em.persist(new Consulta(null, medico, paciente, fecha));


    }
    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad){
        var medico = new Medico(datosMedico(nombre,email,documento,especialidad));
        em.persist(medico);


        return medico;
    }


    private Paciente registrarPaciente(String nombre, String email, String documento){
        var paciente = new Paciente(datosPaciente(nombre,email,documento));
        em.persist(paciente);


        return paciente;
   }
   private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad){
        return new DatosRegistroMedico(
                nombre,
                email,
                "123456789",
                documento,
                especialidad,
                datosDireccion()
        );
   }
   private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento){
        return new DatosRegistroPaciente(
                nombre,
                email,
                "123456",
                documento,
                datosDireccion()
        );
   }
   private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                "calle falsa",
                "districto x",
                "Buenos Aires",
                "1922",
                "1"
        );
   }

}