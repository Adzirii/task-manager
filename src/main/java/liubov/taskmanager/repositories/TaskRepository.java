package liubov.taskmanager.repositories;

import liubov.taskmanager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findTasksById(Long id);
    List<Task> findByAuthor_Id(Long authorId);
}
