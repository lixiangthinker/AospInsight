package com.tonybuilder.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "commit", schema = "aosp_insight", catalog = "")
public class CommitEntity {
    private int commitId;
    private Integer commitInProject;
    private String commitAuthor;
    private String commitAuthorMail;
    private String commitAlterDate;
    private String commitHashId;
    private String commitSubmitDate;
    private Integer commitAddedLines;
    private Integer commitDeletedLines;
    private String commitBranch;
    private Integer commitChangedLines;
    private String commitLog;

    @Id
    @Column(name = "commit_id", nullable = false)
    public int getCommitId() {
        return commitId;
    }

    public void setCommitId(int commitId) {
        this.commitId = commitId;
    }

    @Basic
    @Column(name = "commit_in_project", nullable = true)
    public Integer getCommitInProject() {
        return commitInProject;
    }

    public void setCommitInProject(Integer commitInProject) {
        this.commitInProject = commitInProject;
    }

    @Basic
    @Column(name = "commit_author", nullable = false, length = 200)
    public String getCommitAuthor() {
        return commitAuthor;
    }

    public void setCommitAuthor(String commitAuthor) {
        this.commitAuthor = commitAuthor;
    }

    @Basic
    @Column(name = "commit_author_mail", nullable = false, length = 200)
    public String getCommitAuthorMail() {
        return commitAuthorMail;
    }

    public void setCommitAuthorMail(String commitAuthorMail) {
        this.commitAuthorMail = commitAuthorMail;
    }

    @Basic
    @Column(name = "commit_alter_date", nullable = true, length = 45)
    public String getCommitAlterDate() {
        return commitAlterDate;
    }

    public void setCommitAlterDate(String commitAlterDate) {
        this.commitAlterDate = commitAlterDate;
    }

    @Basic
    @Column(name = "commit_hash_id", nullable = true, length = 45)
    public String getCommitHashId() {
        return commitHashId;
    }

    public void setCommitHashId(String commitHashId) {
        this.commitHashId = commitHashId;
    }

    @Basic
    @Column(name = "commit_submit_date", nullable = true, length = 45)
    public String getCommitSubmitDate() {
        return commitSubmitDate;
    }

    public void setCommitSubmitDate(String commitSubmitDate) {
        this.commitSubmitDate = commitSubmitDate;
    }

    @Basic
    @Column(name = "commit_added_lines", nullable = true)
    public Integer getCommitAddedLines() {
        return commitAddedLines;
    }

    public void setCommitAddedLines(Integer commitAddedLines) {
        this.commitAddedLines = commitAddedLines;
    }

    @Basic
    @Column(name = "commit_deleted_lines", nullable = true)
    public Integer getCommitDeletedLines() {
        return commitDeletedLines;
    }

    public void setCommitDeletedLines(Integer commitDeletedLines) {
        this.commitDeletedLines = commitDeletedLines;
    }

    @Basic
    @Column(name = "commit_branch", nullable = true, length = 100)
    public String getCommitBranch() {
        return commitBranch;
    }

    public void setCommitBranch(String commitBranch) {
        this.commitBranch = commitBranch;
    }

    @Basic
    @Column(name = "commit_changed_lines", nullable = true)
    public Integer getCommitChangedLines() {
        return commitChangedLines;
    }

    public void setCommitChangedLines(Integer commitChangedLines) {
        this.commitChangedLines = commitChangedLines;
    }

    @Basic
    @Column(name = "commit_log", nullable = true, length = -1)
    public String getCommitLog() {
        return commitLog;
    }

    public void setCommitLog(String commitLog) {
        this.commitLog = commitLog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommitEntity that = (CommitEntity) o;
        return commitId == that.commitId &&
                Objects.equals(commitInProject, that.commitInProject) &&
                Objects.equals(commitAuthor, that.commitAuthor) &&
                Objects.equals(commitAuthorMail, that.commitAuthorMail) &&
                Objects.equals(commitAlterDate, that.commitAlterDate) &&
                Objects.equals(commitHashId, that.commitHashId) &&
                Objects.equals(commitSubmitDate, that.commitSubmitDate) &&
                Objects.equals(commitAddedLines, that.commitAddedLines) &&
                Objects.equals(commitDeletedLines, that.commitDeletedLines) &&
                Objects.equals(commitBranch, that.commitBranch) &&
                Objects.equals(commitChangedLines, that.commitChangedLines) &&
                Objects.equals(commitLog, that.commitLog);
    }

    @Override
    public int hashCode() {

        return Objects.hash(commitId, commitInProject, commitAuthor, commitAuthorMail, commitAlterDate, commitHashId, commitSubmitDate, commitAddedLines, commitDeletedLines, commitBranch, commitChangedLines, commitLog);
    }
}
