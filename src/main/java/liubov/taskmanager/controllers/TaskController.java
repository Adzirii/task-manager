package liubov.taskmanager.controllers;

import liubov.taskmanager.dto.request.TaskForm;
import liubov.taskmanager.models.Task;
import liubov.taskmanager.models.User;
import liubov.taskmanager.services.TaskService;
import liubov.taskmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskService.findTaskById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskForm taskForm, @AuthenticationPrincipal UserDetails userDetails){ // @RequestHeader("Authorization") String token){

        //String jwt = token.substring(7);

        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username).get();
        return ResponseEntity.ok(taskService.save(taskForm, user));
    }

//    @GetMapping("/user-tasks")
//    public ResponseEntity<List<Task>> getUserTasks(@AuthenticationPrincipal UserDetails userDetails) {
//        String username = userDetails.getUsername();
//        User user = userService.findUserById(Long.valueOf(username));
//
//        if (user != null) {
//            List<Task> userTasks = taskService.getTasksByUserId(user.getUserId());
//            return ResponseEntity.ok(userTasks);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.ok("Задача удалена");
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@RequestBody TaskForm taskForm, @PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){ //@RequestHeader("Authorization") String token){
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username).get();
        return taskService.update(taskForm, id, user);
    }

}
