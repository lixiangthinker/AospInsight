package com.tonybuilder.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "project_summary", schema = "aosp_insight", catalog = "")
public class ProjectSummaryEntity {
    private int projectSummaryId;
    private Integer projectSummaryOrigId;
    private Integer projectSummaryAdded;
    private Integer projectSummaryDeleted;
    private Integer projectSummaryTotal;
    private Timestamp projectSummarySince;
    private Timestamp projectSummaryUntil;

    @Id
    @Column(name = "project_summary_id", nullable = false)
    public int getProjectSummaryId() {
        return projectSummaryId;
    }

    public void setProjectSummaryId(int projectSummaryId) {
        this.projectSummaryId = projectSummaryId;
    }

    @Basic
    @Column(name = "project_summary_orig_id", nullable = true)
    public Integer getProjectSummaryOrigId() {
        return projectSummaryOrigId;
    }

    public void setProjectSummaryOrigId(Integer projectSummaryOrigId) {
        this.projectSummaryOrigId = projectSummaryOrigId;
    }

    @Basic
    @Column(name = "project_summary_added", nullable = true)
    public Integer getProjectSummaryAdded() {
        return projectSummaryAdded;
    }

    public void setProjectSummaryAdded(Integer projectSummaryAdded) {
        this.projectSummaryAdded = projectSummaryAdded;
    }

    @Basic
    @Column(name = "project_summary_deleted", nullable = true)
    public Integer getProjectSummaryDeleted() {
        return projectSummaryDeleted;
    }

    public void setProjectSummaryDeleted(Integer projectSummaryDeleted) {
        this.projectSummaryDeleted = projectSummaryDeleted;
    }

    @Basic
    @Column(name = "project_summary_total", nullable = true)
    public Integer getProjectSummaryTotal() {
        return projectSummaryTotal;
    }

    public void setProjectSummaryTotal(Integer projectSummaryTotal) {
        this.projectSummaryTotal = projectSummaryTotal;
    }

    @Basic
    @Column(name = "project_summary_since", nullable = true)
    public Timestamp getProjectSummarySince() {
        return projectSummarySince;
    }

    public void setProjectSummarySince(Timestamp projectSummarySince) {
        this.projectSummarySince = projectSummarySince;
    }

    @Basic
    @Column(name = "project_summary_until", nullable = true)
    public Timestamp getProjectSummaryUntil() {
        return projectSummaryUntil;
    }

    public void setProjectSummaryUntil(Timestamp projectSummaryUntil) {
        this.projectSummaryUntil = projectSummaryUntil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectSummaryEntity that = (ProjectSummaryEntity) o;
        return projectSummaryId == that.projectSummaryId &&
                Objects.equals(projectSummaryOrigId, that.projectSummaryOrigId) &&
                Objects.equals(projectSummaryAdded, that.projectSummaryAdded) &&
                Objects.equals(projectSummaryDeleted, that.projectSummaryDeleted) &&
                Objects.equals(projectSummaryTotal, that.projectSummaryTotal) &&
                Objects.equals(projectSummarySince, that.projectSummarySince) &&
                Objects.equals(projectSummaryUntil, that.projectSummaryUntil);
    }

    @Override
    public int hashCode() {

        return Objects.hash(projectSummaryId, projectSummaryOrigId, projectSummaryAdded, projectSummaryDeleted, projectSummaryTotal, projectSummarySince, projectSummaryUntil);
    }
}
