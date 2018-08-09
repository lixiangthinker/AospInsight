package test.com.tonybuilder.utils;

import com.tonybuilder.repo.GitLogUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;

/**
 * GitLogUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>七月 23, 2018</pre>
 */
public class GitLogUtilTest {

    GitLogUtil gitLogUtil;
    @Before
    public void before() throws Exception {
        gitLogUtil = new GitLogUtil();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: genProjectGitLogSince(String path, String since)
     */
    @Test
    public void testGenProjectGitLogSince() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = GitLogUtil.getClass().getMethod("genProjectGitLogSince", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }
    /**
     * Method: getAddedLines(String line)
     */
    @Test
    public void testGetAddedLines() throws Exception {
        try {
            Method method = GitLogUtil.class.getDeclaredMethod("getAddedLines", String.class);
            method.setAccessible(true);
            int insertion = (int) method.invoke(gitLogUtil, "1 file changed, 1 insertion(+), 2 deletions(-)");
            Assert.assertEquals(1, insertion);
            insertion = (int) method.invoke(gitLogUtil, "1 file changed, 1 insertion(+)");
            Assert.assertEquals(1, insertion);
            insertion = (int) method.invoke(gitLogUtil, "1 file changed, 1 deletions(-)");
            Assert.assertEquals(0, insertion);
            insertion = (int) method.invoke(gitLogUtil, "1 file changed, 3 insertions(+), 4 deletions(-)");
            Assert.assertEquals(3, insertion);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    /**
     * Method: getDeletedLines(String line)
     */
    @Test
    public void testGetDeletedLines() throws Exception {
        try {
            Method method = GitLogUtil.class.getDeclaredMethod("getDeletedLines", String.class);
            method.setAccessible(true);
            int deletion = (int) method.invoke(gitLogUtil, "1 file changed, 1 insertion(+), 2 deletions(-)");
            Assert.assertEquals(2, deletion);
            deletion = (int) method.invoke(gitLogUtil, "1 file changed, 1 insertion(+)");
            Assert.assertEquals(0, deletion);
            deletion = (int) method.invoke(gitLogUtil, "1 file changed, 1 deletions(-)");
            Assert.assertEquals(1, deletion);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    /**
     * Method: private Timestamp parseStringDate(String strDate)
     */
    @Test
    public void testParseStringDate() throws Exception {
        try {
            Method method = GitLogUtil.class.getDeclaredMethod("parseStringDate", String.class);
            method.setAccessible(true);
            Timestamp timestamp = (Timestamp) method.invoke(gitLogUtil, "Wed Jul 4 16:41:14 2018 +0100");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: getHashId(String line)
     */
    @Test
    public void testGetHashId() throws Exception {
        try {
            Method method = GitLogUtil.class.getDeclaredMethod("getHashId", String.class);
            method.setAccessible(true);
            String hashId = (String) method.invoke(gitLogUtil, "commit 9c610f7567bc713e802842bd6c541d22941d8cea");
            System.out.println("hash id " + hashId);
            Assert.assertEquals("9c610f7567bc713e802842bd6c541d22941d8cea", hashId);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    /**
     * Method: getAuthor(String line)
     */
    @Test
    public void testGetAuthor() throws Exception {
        try {
            Method method = GitLogUtil.class.getDeclaredMethod("getAuthor", String.class);
            method.setAccessible(true);
            String author = (String) method.invoke(gitLogUtil, "Author:     Neil Fuller <nfuller@google.com>");
            System.out.println("author " + author);
            Assert.assertEquals("Neil Fuller", author);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    /**
     * Method: getMail(String line)
     */
    @Test
    public void testGetMail() throws Exception {
        try {
            Method method = GitLogUtil.class.getDeclaredMethod("getMail", String.class);
            method.setAccessible(true);
            String mail = (String) method.invoke(gitLogUtil, "Author:     Neil Fuller <nfuller@google.com>");
            System.out.println("mail " + mail);
            Assert.assertEquals("nfuller@google.com",mail);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    /**
     * Method: parseGitLog(File gitLog)
     */
    @Test
    public void testParseGitLog() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = GitLogUtil.getClass().getMethod("parseGitLog", File.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: parseProjectLog(String path)
     */
    @Test
    public void testParseProjectLog() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = GitLogUtil.getClass().getMethod("parseProjectLog", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
