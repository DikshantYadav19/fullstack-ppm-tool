package com.practice.restservices.ppmtoolservices.services;

import com.practice.restservices.ppmtoolservices.domain.Backlog;
import com.practice.restservices.ppmtoolservices.domain.Project;
import com.practice.restservices.ppmtoolservices.domain.ProjectTask;
import com.practice.restservices.ppmtoolservices.exceptions.ProjectNotFoundException;
import com.practice.restservices.ppmtoolservices.repositories.BacklogRepository;
import com.practice.restservices.ppmtoolservices.repositories.ProjectRepository;
import com.practice.restservices.ppmtoolservices.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();  //backlogRepository.findByProjectIdentifier(projectIdentifier);

        projectTask.setBacklog(backlog);

        Integer backlogSequence = backlog.getPTSequence();
        backlogSequence++;

        backlog.setPTSequence(backlogSequence);

        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }

        if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);

    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id, String username) {

        /*Project project = projectRepository.findByProjectIdentifier(backlog_id);

        if (project == null) {
            throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist");
        }*/

        projectService.findProjectByIdentifier(backlog_id, username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String projectSequence, String username ) {

        /*Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);

        if (backlog == null) {
            throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist");
        }*/

        projectService.findProjectByIdentifier(backlog_id, username);

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence);

        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task: '" + projectSequence + "' does not exist");
        }

        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task: '" + projectSequence + "' does not exist in project: '" + backlog_id + "'");
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTaskRepository.delete(projectTask);
    }
}
