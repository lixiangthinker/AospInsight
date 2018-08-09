package com.tonybuilder.repo;

import com.tonybuilder.dao.CommitEntityDao;
import com.tonybuilder.dao.ProjectEntityDao;
import com.tonybuilder.dao.ProjectSummaryEntityDao;
import com.tonybuilder.dao.impl.CommitEntityImpl;
import com.tonybuilder.dao.impl.ProjectEntityImpl;
import com.tonybuilder.dao.impl.ProjectSummaryEntityImpl;
import com.tonybuilder.entities.CommitEntity;
import com.tonybuilder.entities.ProjectEntity;
import com.tonybuilder.entities.ProjectSummaryEntity;

import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectSummaryUtil {
    private CommitEntityDao commitEntityDao;
    private ProjectSummaryEntityDao projectSummaryEntityDao;
    private ProjectEntityDao projectEntityDao;

    public ProjectSummaryUtil() {
        commitEntityDao = new CommitEntityImpl();
        projectEntityDao = new ProjectEntityImpl();
        projectSummaryEntityDao = new ProjectSummaryEntityImpl();
    }

    public static void main(String[] args) {
        ProjectSummaryUtil util = new ProjectSummaryUtil();
        ProjectEntity project = util.projectEntityDao.getProjectByPath("frameworks/base");
        if (project == null) {
            System.out.println("could not get project.");
            return;
        }

        YearMonth sinceDate = YearMonth.of(2017, 1);
        YearMonth untilDate = YearMonth.now();
        util.genProjectSummaryForSingleProject(project, sinceDate, untilDate);
        System.out.println("End of ProjectSummaryUtil.main()");
    }

    private boolean genProjectSummaryForSingleProject(ProjectEntity project, YearMonth sinceDate, YearMonth untilDate) {
        // get commit of each month between since and until, calculate changed lines, insert to summary table.
        boolean result = false;
        int projectId = project.getProjectId();
        ProjectSummaryEntity projectSummary = new ProjectSummaryEntity();
        projectSummary.setProjectSummaryOrigId(projectId);

        List<ProjectSummaryEntity> projectSummaryEntities = new ArrayList<>();

        for (YearMonth month = sinceDate; month.isBefore(untilDate.plusMonths(1)); month = month.plusMonths(1)) {
            List<CommitEntity> commitEntities = commitEntityDao.getCommitList(month);

            if (commitEntities == null) {
                System.out.println("could not find commits in month: " + month);
                continue;
            }

            int totalAddedLines = 0;
            int totalDeletedLines = 0;
            int totalChangedLines = 0;

            for (CommitEntity c : commitEntities) {
                totalAddedLines += c.getCommitAddedLines();
                totalDeletedLines += c.getCommitDeletedLines();
                totalChangedLines += c.getCommitChangedLines();
            }

            Timestamp[] tsSince = DateTimeUtils.getSinceAndUntilTsByMonth(month);
            projectSummary.setProjectSummarySince(tsSince[0]);
            projectSummary.setProjectSummaryUntil(tsSince[1]);
            projectSummary.setProjectSummaryAdded(totalAddedLines);
            projectSummary.setProjectSummaryDeleted(totalDeletedLines);
            projectSummary.setProjectSummaryTotal(totalChangedLines);

            projectSummaryEntities.add(projectSummary);
        }

        projectSummaryEntityDao.addProjectSummaryList(projectSummaryEntities);
        return result;
    }
}
