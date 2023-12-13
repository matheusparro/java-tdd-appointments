package repositories;

import models.WorkDays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface WorkDaysRepository extends JpaRepository<WorkDays, String> {
    // Aqui você pode adicionar métodos de consulta personalizados, se necessário

    // Método para criar WorkDays

    Optional<WorkDays> findByCreationDateAndUserId(Date date, Long userId);
}
