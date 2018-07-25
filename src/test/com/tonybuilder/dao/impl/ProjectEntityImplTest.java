package test.com.tonybuilder.dao.impl;

import com.tonybuilder.dao.ProjectEntityDao;
import com.tonybuilder.dao.impl.ProjectEntityImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * ProjectEntityImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>七月 25, 2018</pre>
 */
public class ProjectEntityImplTest {

    private ProjectEntityDao projectEntityDao;
    @Before
    public void before() throws Exception {
        projectEntityDao = new ProjectEntityImpl();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getProjectById(int id)
     */
    @Test
    public void testGetProjectById() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getProjectList()
     */
    @Test
    public void testGetProjectList() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getProjectIdByPath(String path)
     */
    @Test
    public void testGetProjectIdByPath() throws Exception {
        int id = projectEntityDao.getProjectIdByPath("frameworks/base");
        System.out.println("id = " + id);
        Assert.assertNotEquals(-1, id);
        id = projectEntityDao.getProjectIdByPath("build/kati");
        System.out.println("id = " + id);
        Assert.assertNotEquals(-1, id);
        id = projectEntityDao.getProjectIdByPath("blablablabla");
        Assert.assertEquals(-1, id);
    }

    /**
     * Method: addProject(ProjectEntity project)
     */
    @Test
    public void testAddProject() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateProjectLoc(List<ProjectEntity> projectList)
     */
    @Test
    public void testUpdateProjectLoc() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: addProjectList(List<ProjectEntity> projectList)
     */
    @Test
    public void testAddProjectList() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateProject(ProjectEntity project)
     */
    @Test
    public void testUpdateProject() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteProject(int id)
     */
    @Test
    public void testDeleteProject() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: isProjectExist(Session session, String projectName)
     */
    @Test
    public void testIsProjectExist() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ProjectEntityImpl.getClass().getMethod("isProjectExist", Session.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
