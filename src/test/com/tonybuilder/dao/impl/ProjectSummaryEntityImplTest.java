package test.com.tonybuilder.dao.impl;

import com.tonybuilder.dao.ProjectSummaryEntityDao;
import com.tonybuilder.dao.impl.ProjectSummaryEntityImpl;
import com.tonybuilder.entities.ProjectSummaryEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * ProjectSummaryEntityImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>七月 16, 2018</pre>
 */
public class ProjectSummaryEntityImplTest {
    ProjectSummaryEntityDao projectSummaryEntityDao;
    @Before
    public void before() throws Exception {
        projectSummaryEntityDao = new ProjectSummaryEntityImpl();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getProjectSummaryById(int id)
     */
    @Test
    public void testGetProjectSummaryById() throws Exception {
    }

    /**
     * Method: getProjectSummaryListByDate(Date since, Date until)
     */
    @Test
    public void testGetProjectSummaryListByDate() throws Exception {
//        Date since = new Date();
//        System.out.println(since.toString());
//
//        ProjectSummaryEntity projectSummaryEntity = projectSummaryEntityDao.getProjectSummaryById(0);
//        Timestamp time = projectSummaryEntity.getProjectSummarySince();
//        Date projectSummarySinceDate = new Date(time.getTime());
//        System.out.println(projectSummarySinceDate);
//
//        System.out.println(since.before(projectSummarySinceDate));

        Calendar calendarSince = Calendar.getInstance();
        Calendar calendarUntil = Calendar.getInstance();

        calendarSince.set(Calendar.YEAR, 2018);
        calendarSince.set(Calendar.MONTH, Calendar.MARCH);
        calendarSince.set(Calendar.DATE, 5);

        Date since = calendarSince.getTime();

        calendarUntil.set(Calendar.YEAR, 2018);
        calendarUntil.set(Calendar.MONTH, Calendar.MAY);
        calendarUntil.set(Calendar.DATE, 5);
        Date until = calendarUntil.getTime();
        System.out.println("since: " + since);
        System.out.println("until: " + until);
        List<ProjectSummaryEntity> list = projectSummaryEntityDao.getProjectSummaryListByDate(since, until);
        for (ProjectSummaryEntity p: list) {
            System.out.println(p.getProjectSummaryId());
        }
    }

    /**
     * Method: getProjectSummaryList()
     */
    @Test
    public void testGetProjectSummaryList() throws Exception {
    }

    /**
     * Method: addProjectSummary(ProjectSummaryEntity projectSummary)
     */
    @Test
    public void testAddProjectSummary() throws Exception {
    }

    /**
     * Method: updateProjectSummary(ProjectSummaryEntity projectSummary)
     */
    @Test
    public void testUpdateProjectSummary() throws Exception {
    }

    /**
     * Method: deleteProjectSummary(int id)
     */
    @Test
    public void testDeleteProjectSummary() throws Exception {
    }
}
