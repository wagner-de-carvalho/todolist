package br.com.wagnercarvalho.todolist.task;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private ITaskRepository taskRepository;

    @PostMapping
    public TaskModel create(@RequestBody TaskModel task) {
        return this.taskRepository.save(task);
    }

}
