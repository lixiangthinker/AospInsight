package com.tonybuilder.dao;

import com.tonybuilder.entities.ProjectSummaryEntity;

import java.util.Date;
import java.util.List;

public interface ProjectSummaryEntityDao {
    ProjectSummaryEntity getProjectSummaryById(int id);
    List<ProjectSummaryEntity> getProjectSummaryList();
    List<ProjectSummaryEntity> getProjectSummaryListByDate(Date since, Date until);
    boolean addProjectSummary(ProjectSummaryEntity projectSummary);
    boolean updateProjectSummary(ProjectSummaryEntity projectSummary);
    boolean deleteProjectSummary(int id);
}
