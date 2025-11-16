package com.example.dockergithubactions.service;

import com.example.dockergithubactions.model.Task;
import com.example.dockergithubactions.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
	private final TaskRepository taskRepository;

	public TaskService(final TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public Optional<Task> getTaskById(final Long id) {
		return taskRepository.findById(id);
	}

	public Task createTask(final Task task) {
		return taskRepository.save(task);
	}

	public Task updateTask(final Long id, final Task taskDetails) {
		return taskRepository.findById(id).map(task -> {
			task.setTitle(taskDetails.getTitle());
			task.setDescription(taskDetails.getDescription());
			task.setStatus(taskDetails.getStatus());
			task.setAssignee(taskDetails.getAssignee());
			task.setProject(taskDetails.getProject());
			return taskRepository.save(task);
		}).orElseThrow(() -> new RuntimeException("Task not found"));
	}

	public void deleteTask(final Long id) {
		taskRepository.deleteById(id);
	}
}
