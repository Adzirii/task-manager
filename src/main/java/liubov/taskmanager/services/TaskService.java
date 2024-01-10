package liubov.taskmanager.services;

import liubov.taskmanager.dto.request.TaskForm;
import liubov.taskmanager.models.EPriority;
import liubov.taskmanager.models.EStatus;
import liubov.taskmanager.models.Task;
import liubov.taskmanager.models.User;
import liubov.taskmanager.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final JwtService jwtService;
    private final UserService userService;

    public Task findTaskById(Long id){
        return taskRepository.findTasksById(id).orElseThrow(() -> new IllegalArgumentException(
                "Такой задачи не существует."
        ));
    }

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByAuthor_Id(userId);
    }

    public void delete(Long id){
        taskRepository.delete(findTaskById(id));
    }

    public ResponseEntity<?> update(TaskForm taskForm, Long id, User user){

        Task task = findTaskById(id);
        if (!task.getAuthor().getId().equals(user.getId()))
            return ResponseEntity.badRequest().body("Вы не можете редактировать эту задачу");

        task.setHeader(taskForm.header());
        task.setDescription(taskForm.description());
        task.setStatus(EStatus.valueOf(taskForm.status()));
        task.setPriority(EPriority.valueOf(taskForm.priority()));
        return ResponseEntity.ok(taskRepository.save(task));
    }
    public Task save(TaskForm taskForm, User user){
        Task task = new Task();
        task.setHeader(taskForm.header());
        task.setDescription(taskForm.description());
        task.setStatus(EStatus.valueOf(taskForm.status()));
        task.setPriority(EPriority.valueOf(taskForm.priority()));
        task.setAuthor(user);
        return taskRepository.save(task);
    }
}
