package com.example.task_manager.repositories;

import com.example.task_manager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>
{
    Task findById(long id);
}
