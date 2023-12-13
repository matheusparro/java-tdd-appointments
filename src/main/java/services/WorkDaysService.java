package services;

import models.Appointment;
import models.Employee;
import models.WorkDays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.WorkDaysRepository;

import java.util.*;

@Service
public class WorkDaysService {
//
    @Autowired
    private WorkDaysRepository workDaysRepository;

    public Optional<WorkDays[]> findAllWorkDays(){
        return Optional.ofNullable(workDaysRepository.findAll().toArray(new WorkDays[0]));
    }
    public WorkDays save(WorkDays workDays){
        return workDaysRepository.save(workDays);
    }

    public Optional<WorkDays> findById(String hashId){
        return workDaysRepository.findById(hashId);
    }

    public Optional<WorkDays> updateById(String hashId){
        Optional<WorkDays> workDays = workDaysRepository.findById(hashId);
        if(workDays.isPresent()){
            WorkDays workDaysUpdated = workDays.get();
            workDaysUpdated.setUpdatedAt(new java.util.Date());
            return Optional.ofNullable(workDaysRepository.save(workDaysUpdated));
        }
        return Optional.empty();
    }

    public void doAppointment(Date currentDate, Appointment appointment, Long userId) {
        Optional<WorkDays> workDays = workDaysRepository.findByCreationDateAndUserId(currentDate,userId);
        WorkDays workDayFinal = workDays.get();
        if (!workDays.isPresent()) {

                // Se o WorkDays não existir, criar um novo e adicionar o novo appointment
            WorkDays newWorkDay = new WorkDays();
            newWorkDay.setCreatedAt(new Date());
            newWorkDay.setUpdatedAt(new Date());
            newWorkDay.setAppointments(new ArrayList<>());
            Employee employee = new Employee();
            employee.setId(userId);
            newWorkDay.setEmployee(employee);
            newWorkDay.setEmployee(employee);  // (Adicione o mé
            workDayFinal = newWorkDay;
        }
            WorkDays workDaysUpdated = workDayFinal;

            List<Appointment> appointments = workDaysUpdated.getAppointments();
            if (!appointments.isEmpty()) {
                // Ordenar a lista de appointments pela data de criação
                appointments.sort(Comparator.comparing(Appointment::getCreatedAt));

                // Obter o último appointment
                Appointment lastAppointment = appointments.get(appointments.size() - 1);

                // Verificar se o último é do mesmo tipo que o novo
                if (lastAppointment.getType() == appointment.getType()) {
                    throw new IllegalArgumentException("The appointment type does not match the expected type.");
                }
            }

            workDaysUpdated.getAppointments().add(appointment);
            workDaysRepository.save(workDaysUpdated);

    }


}
