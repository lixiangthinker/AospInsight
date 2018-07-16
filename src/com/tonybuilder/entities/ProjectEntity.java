package com.tonybuilder.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project", schema = "aosp_insight", catalog = "")
public class ProjectEntity {
    private int projectId;
    private String projectName;
    private Double projectTotalLines;
    private String projectLastSubmitData;
    private Byte projectIsExternalSrc;
    private Integer projectModuleType;
    private Byte projectIsDiscarded;
    private String projectPath;

    @Id
    @Column(name = "project_id", nullable = false)
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "project_name", nullable = false, length = 512)
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @Column(name = "project_total_lines", nullable = true, precision = 0)
    public Double getProjectTotalLines() {
        return projectTotalLines;
    }

    public void setProjectTotalLines(Double projectTotalLines) {
        this.projectTotalLines = projectTotalLines;
    }

    @Basic
    @Column(name = "project_last_submit_data", nullable = true, length = 45)
    public String getProjectLastSubmitData() {
        return projectLastSubmitData;
    }

    public void setProjectLastSubmitData(String projectLastSubmitData) {
        this.projectLastSubmitData = projectLastSubmitData;
    }

    @Basic
    @Column(name = "project_is_external_src", nullable = true)
    public Byte getProjectIsExternalSrc() {
        return projectIsExternalSrc;
    }

    public void setProjectIsExternalSrc(Byte projectIsExternalSrc) {
        this.projectIsExternalSrc = projectIsExternalSrc;
    }

    @Basic
    @Column(name = "project_module_type", nullable = true)
    public Integer getProjectModuleType() {
        return projectModuleType;
    }

    public void setProjectModuleType(Integer projectModuleType) {
        this.projectModuleType = projectModuleType;
    }

    @Basic
    @Column(name = "project_is_discarded", nullable = true)
    public Byte getProjectIsDiscarded() {
        return projectIsDiscarded;
    }

    public void setProjectIsDiscarded(Byte projectIsDiscarded) {
        this.projectIsDiscarded = projectIsDiscarded;
    }

    @Basic
    @Column(name = "project_path", nullable = true, length = 512)
    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity that = (ProjectEntity) o;
        return projectId == that.projectId &&
                Objects.equals(projectName, that.projectName) &&
                Objects.equals(projectTotalLines, that.projectTotalLines) &&
                Objects.equals(projectLastSubmitData, that.projectLastSubmitData) &&
                Objects.equals(projectIsExternalSrc, that.projectIsExternalSrc) &&
                Objects.equals(projectModuleType, that.projectModuleType) &&
                Objects.equals(projectIsDiscarded, that.projectIsDiscarded) &&
                Objects.equals(projectPath, that.projectPath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(projectId, projectName, projectTotalLines, projectLastSubmitData, projectIsExternalSrc, projectModuleType, projectIsDiscarded, projectPath);
    }
}
