package com.example.dockergithubactions.repository;

import com.example.dockergithubactions.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
