package liubov.taskmanager.models;

//import liubov.taskmanager.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String header;
    private String description;
    private EStatus status;
    private EPriority priority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;


}
