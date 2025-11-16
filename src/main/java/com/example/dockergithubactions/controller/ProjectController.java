package com.example.dockergithubactions.controller;

import com.example.dockergithubactions.model.Project;
import com.example.dockergithubactions.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
	private final ProjectService projectService;

	public ProjectController(final ProjectService projectService){
		this.projectService = projectService;
	}

	@GetMapping
	public List<Project> getAllProjects() {
		return projectService.getAllProjects();
	}

	@PostMapping
	public Project createProject(@RequestBody final Project project) {
		return projectService.createProject(project);
	}
}
