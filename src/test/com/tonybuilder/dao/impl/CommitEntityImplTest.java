package test.com.tonybuilder.dao.impl;

import com.tonybuilder.dao.CommitEntityDao;
import com.tonybuilder.dao.impl.CommitEntityImpl;
import com.tonybuilder.entities.CommitEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * CommitEntityImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>七月 15, 2018</pre>
 */
public class CommitEntityImplTest {
    CommitEntityDao commitEntityDao;
    @Before
    public void before() throws Exception {
        commitEntityDao = new CommitEntityImpl();
    }

    @After
    public void after() throws Exception {
        commitEntityDao = null;
    }
    private Timestamp parseStringDate(String strDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy ZZZZ");
        LocalDateTime dateTime = LocalDateTime.parse(strDate, dateTimeFormatter);
        System.out.println("dateTime = " + dateTime);
        Timestamp timestamp = Timestamp.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("timestamp = " + timestamp);
        return timestamp;
    }
    private CommitEntity getFakeCommitEntity() {
        String commitLog = "    Remove unusual static method call\n" +
                "    \n" +
                "    The code is using static method Calendar.getInstance() but via a\n" +
                "    subclass of Calendar. It works, and getInstance() returns a\n" +
                "    GregorianCalendar on Android, but it's odd and was probably unintended.\n" +
                "    \n" +
                "    Noticed during static analysis of SystemUI bytecode.\n" +
                "    \n" +
                "    Bug: 111055375\n" +
                "    Test: build / boot\n" +
                "    Change-Id: I1762cbeb2cc7882868f84ec11100815671cd29ec\n";
        CommitEntity commit = new CommitEntity();
        commit.setCommitInProject(0);
        commit.setCommitAuthor("Neil Fuller");
        commit.setCommitAuthorMail("nfuller@google.com");
        commit.setCommitAlterDate(parseStringDate("Wed Jul 4 16:41:14 2018 +0100"));
        commit.setCommitDeletedLines(200);
        commit.setCommitChangedLines(1200);
        commit.setCommitAddedLines(1000);
        commit.setCommitLog(commitLog);
        commit.setCommitHashId("9c610f7567bc713e802842bd6c541d22941d8cea");
        commit.setCommitBranch("master");
        return commit;
    }
    /**
     * Method: addCommit(CommitEntity commit)
     */
    @Test
    public void testAddCommit() throws Exception {
        boolean result;
        result = commitEntityDao.addCommit(getFakeCommitEntity());
        Assert.assertTrue(result);
    }

    /**
     * Method: getCommitList()
     */
    @Test
    public void testGetCommitList() throws Exception {
        List<CommitEntity> commit = commitEntityDao.getCommitList();
        if (commit.size() == 0) {
            commitEntityDao.addCommit(getFakeCommitEntity());
            commit = commitEntityDao.getCommitList();
        }

        Assert.assertNotNull(commit);
        Assert.assertEquals("9c610f7567bc713e802842bd6c541d22941d8cea", commit.get(0).getCommitHashId());
    }

    /**
     * Method: getCommitList(YearMonth month)
     */
    @Test
    public void testGetCommitListByMonth() throws Exception {
        YearMonth month = YearMonth.of(2018, 6);
        List<CommitEntity> commitList = commitEntityDao.getCommitList(month);
        Assert.assertNotNull(commitList);
        System.out.println("commitList.size() " + commitList.size());
        for(CommitEntity c: commitList) {
            System.out.println(c.getCommitSubmitDate());
        }
    }
    /**
     * Method: getCommitById(int id)
     */
    @Test
    public void testGetCommitById() throws Exception {
        List<CommitEntity> commitList = commitEntityDao.getCommitList();
        if (commitList.size() == 0) {
            commitEntityDao.addCommit(getFakeCommitEntity());
            commitList = commitEntityDao.getCommitList();
        }
        Assert.assertNotNull(commitList);

        CommitEntity commit = commitList.get(0);
        Assert.assertNotNull(commit);

        CommitEntity testResult = commitEntityDao.getCommitById(commit.getCommitId());
        Assert.assertEquals("9c610f7567bc713e802842bd6c541d22941d8cea", testResult.getCommitHashId());
    }

    /**
     * Method: updateCommit(CommitEntity commit)
     */
    @Test
    public void testUpdateCommit() throws Exception {
        List<CommitEntity> commitList = commitEntityDao.getCommitList();
        if (commitList.size() == 0) {
            commitEntityDao.addCommit(getFakeCommitEntity());
            commitList = commitEntityDao.getCommitList();
        }
        Assert.assertNotNull(commitList);

        CommitEntity commit = commitList.get(0);
        Assert.assertNotNull(commit);

        commit.setCommitBranch("test");
        boolean result = commitEntityDao.updateCommit(commit);
        Assert.assertTrue(result);
//        CommitEntity newCommit = commitEntityDao.getCommitById(1);
//        Assert.assertEquals("test", newCommit.getCommitBranch());
    }

    /**
     * Method: deleteCommit(int id)
     */
    @Test
    public void testDeleteCommit() throws Exception {
        List<CommitEntity> commitList = commitEntityDao.getCommitList();
        if (commitList.size() == 0) {
            commitEntityDao.addCommit(getFakeCommitEntity());
            commitList = commitEntityDao.getCommitList();
        }
        Assert.assertNotNull(commitList);

        CommitEntity commit = commitList.get(0);
        Assert.assertNotNull(commit);
        Assert.assertEquals("9c610f7567bc713e802842bd6c541d22941d8cea", commit.getCommitHashId());
        boolean result = commitEntityDao.deleteCommit(commit.getCommitId());
        Assert.assertTrue(result);
    }
}
