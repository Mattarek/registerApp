package com.example.dockergithubactions.service;

import com.example.dockergithubactions.model.Project;
import com.example.dockergithubactions.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
	private final ProjectRepository projectRepository;

	public ProjectService(final ProjectRepository projectRepository){
		this.projectRepository = projectRepository;
	}

	public List<Project> getAllProjects(){
		return projectRepository.findAll();
	}

	public Project createProject(final Project project){
		return projectRepository.save(project);
	}
}
