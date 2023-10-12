package br.com.wagnercarvalho.todolist.task;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private ITaskRepository taskRepository;

    @PostMapping
    public TaskModel create(@RequestBody TaskModel task, HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        task.setUserId(((UUID) userId));
        return this.taskRepository.save(task);
    }

}
