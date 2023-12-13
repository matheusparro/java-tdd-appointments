package models;

import lombok.*;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class WorkDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String hashId;

    private Date workDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "employee_id")  // Nome da coluna de chave estrangeira na tabela WorkDays
    private Employee employee;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workDays")
    private List<Appointment> appointments;

    public WorkDays() {

    }
}
