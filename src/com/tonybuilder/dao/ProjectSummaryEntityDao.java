package com.tonybuilder.dao;

import com.tonybuilder.entities.ProjectSummaryEntity;

import java.util.List;

public interface ProjectSummaryEntityDao {
    ProjectSummaryEntity getProjectSummaryById(int id);
    List<ProjectSummaryEntity> getProjectSummaryList();
    boolean addProjectSummary(ProjectSummaryEntity projectSummary);
    boolean updateProjectSummary(ProjectSummaryEntity projectSummary);
    boolean deleteProjectSummary(int id);
}
