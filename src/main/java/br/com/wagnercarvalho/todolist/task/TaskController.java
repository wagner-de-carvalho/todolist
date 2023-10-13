package br.com.wagnercarvalho.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wagnercarvalho.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel task, HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        task.setUserId(((UUID) userId));

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(task.getStartsAt()) || currentDate.isAfter(task.getEndsAt())) {
            var message = "As datas de início e término devem ser maiores que a data atual";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        if (task.getStartsAt().isAfter(task.getEndsAt()) || task.getStartsAt().isEqual(task.getEndsAt())) {
            var message = "As datas de início e término não devem ser iguais ou data de término anterior à data de início";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        var taskCreated = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping
    public List<TaskModel> list(HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        return this.taskRepository.findByUserId((UUID) userId);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity update(@RequestBody TaskModel task, @PathVariable UUID taskId, HttpServletRequest request) {
        var savedTask = this.taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tarefa não encontrada");
        }

        var userId = request.getAttribute("userId");

        if (!task.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário sem permissão para alterar esta tarefa");
        }

        Utils.copyNonNullProperties(task, savedTask);
        return ResponseEntity.ok().body(this.taskRepository.save(savedTask));
    }
}
