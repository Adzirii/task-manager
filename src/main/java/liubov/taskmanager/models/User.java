package liubov.taskmanager.models;

import liubov.taskmanager.models.Task;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Table
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Task> createdTasks;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_tasks",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "task_id")
//    )
//    private Set<Task> tasks;
}
