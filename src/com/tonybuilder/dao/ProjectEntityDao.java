package com.tonybuilder.dao;

import com.tonybuilder.entities.ProjectEntity;

import java.util.List;

public interface ProjectEntityDao {
    ProjectEntity getProjectById(int id);
    ProjectEntity getProjectByPath(String path);
    List<ProjectEntity> getProjectList();
    int getProjectIdByPath(String path);
    boolean addProject(ProjectEntity project);
    boolean addProjectList(List<ProjectEntity> projectList);
    boolean updateProjectLoc(List<ProjectEntity> projectList);
    boolean updateProject(ProjectEntity project);
    boolean deleteProject(int id);
}
