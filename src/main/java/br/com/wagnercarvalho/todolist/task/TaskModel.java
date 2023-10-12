package br.com.wagnercarvalho.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tasks")
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    @Column(length = 50)
    private String title;

    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private String priority;

    private UUID userId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
