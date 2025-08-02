package com.tddJava.tddJava;

import models.Appointment;
import models.Employee;
import models.WorkDays;
import models.enums.AppointmentTypeEnum;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import repositories.WorkDaysRepository;
import services.WorkDaysService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class WorkDaysServiceTest {

    @TestConfiguration
    static class WorkDaysServiceTestConfig {
        @Bean
        public WorkDaysService workDaysService() {
            return new WorkDaysService();
        }
    }
    @Autowired
    private WorkDaysService workDaysService;

    @MockBean
    private WorkDaysRepository workDaysRepository;

    @Test
    public void testCreateWorkDays() {
        // Criando um objeto WorkDays fictício
        WorkDays workDays = new WorkDays();
        workDays.setHashId("hash123");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        workDays.setEmployee(employee);

        workDays.setWorkDate(new Date());
        workDays.setCreatedAt(new Date());
        workDays.setUpdatedAt(new Date());

        // Configurando o mock para retornar o objeto workDays
        Mockito.when(workDaysRepository.save(any(WorkDays.class))).thenReturn(workDays);
        // Chamando o método de criação no serviço
        WorkDays workCreated = workDaysService.save(workDays);

        assertNotNull(workCreated, "Criação do Work Day falhou = null");
        assertInstanceOf(WorkDays.class, workCreated, "O tipo da classe não é WorkDays");
    }

    @Test
    public void testFindWorkDaysById() {
        // Chamando o método para obter um WorkDays pelo ID
        Optional<WorkDays> workDaysFound = workDaysService.findById("hash123");

        // Verificando se o WorkDays está presente
        assertTrue(workDaysFound.isPresent(), "A busca por WorkDays findbyId falhou ");

        // Obtendo o objeto WorkDays
        WorkDays workDays = workDaysFound.get();

    }

    @Test
    public void testDoAppointment() {
        // Criando um objeto de Appointment para o teste
        Appointment appointment = new Appointment();
        appointment.setType(AppointmentTypeEnum.ENTRADA);
        appointment.setCreatedAt(new Date());

        // Criando um objeto WorkDays fictício para o teste
        WorkDays workDays = new WorkDays();
        workDays.setCreatedAt(new Date());
        workDays.setUpdatedAt(new Date());
        workDays.setAppointments(new ArrayList<>());

        // Configurando o mock para retornar o objeto WorkDays quando chamado pelo método findByCreationDateAndUserId
        Mockito.when(workDaysRepository.findByCreatedAtAndEmployee_Id(new Date(), 1L)).thenReturn(Optional.of(workDays));

        // Executando o método doAppointment
        workDaysService.doAppointment(new Date(),appointment, 1L);

        // Verificando se o método save do repositório foi chamado
        Mockito.verify(workDaysRepository, Mockito.times(1)).save(any(WorkDays.class));
    }

    @Test
    public void testDoAppointmentWithMismatchedType() {
        // Criando um objeto de Appointment para o teste
        Appointment appointment = new Appointment();
        appointment.setType(AppointmentTypeEnum.ENTRADA);
        appointment.setCreatedAt(new Date());

        // Criando um objeto WorkDays fictício para o teste com um appointment de tipo diferente
        WorkDays workDays = new WorkDays();
        workDays.setCreatedAt(new Date());
        workDays.setUpdatedAt(new Date());

        List<Appointment> appointments = new ArrayList<>();
        Appointment differentTypeAppointment = new Appointment();
        differentTypeAppointment.setType(AppointmentTypeEnum.ENTRADA);
        appointments.add(differentTypeAppointment);

        workDays.setAppointments(appointments);

        // Configurando o mock para retornar o objeto WorkDays quando chamado pelo método findByCreationDate
        Date specificDate = new Date();
        Mockito.when(workDaysRepository.findByCreatedAtAndEmployee_Id(specificDate, 1L))
                .thenReturn(Optional.of(workDays));


        // Executando o método doAppointment e verificando se uma exceção é lançada
        assertThrows(IllegalArgumentException.class,
                () -> workDaysService.doAppointment(specificDate,appointment, 1L));

        // Verificando se o método save do repositório não foi chamado
        Mockito.verify(workDaysRepository, Mockito.never()).save(any(WorkDays.class));
    }


    @BeforeEach
    public void setUp() {
        WorkDays workDays = new WorkDays();
        workDays.setHashId("hash123");
        workDays.setWorkDate(new Date());
        workDays.setCreatedAt(new Date());
        workDays.setUpdatedAt(new Date());

        //Mockito.when(workDaysRepository.save(workDays)).thenReturn(workDays);
        Mockito.when(workDaysRepository.findById(Mockito.anyString())).thenReturn(Optional.of(workDays));
    }


}
