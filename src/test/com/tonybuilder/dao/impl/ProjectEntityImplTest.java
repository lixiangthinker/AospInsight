package test.com.tonybuilder.dao.impl;

import com.tonybuilder.dao.ProjectEntityDao;
import com.tonybuilder.dao.impl.ProjectEntityImpl;
import com.tonybuilder.entities.ProjectEntity;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectEntityImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>七月 19, 2018</pre>
 */
public class ProjectEntityImplTest {
    ProjectEntityDao projectEntityDao;
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
     * Method: addProject(ProjectEntity project)
     */
    @Test
    public void testAddProject() throws Exception {
//TODO: Test goes here... 
    }

    private ProjectEntity getEntity(String projectName, String projectPath) {
        ProjectEntity entity = new ProjectEntity();
        if (projectName != null) {
            entity.setProjectName(projectName);
        }

        if (projectPath != null) {
            entity.setProjectPath(projectPath);
        }

        return entity;
    }

    private List<ProjectEntity> getTestEntityList() {
        List<ProjectEntity> entityList = new ArrayList<>();
        entityList.add(getEntity("platform/build/blueprint", "build/blueprint"));
        entityList.add(getEntity("platform/frameworks/base", "frameworks/base"));
        return entityList;
    }
    /**
     * Method: addProjectList(List<ProjectEntity> projectList)
     */
    @Test
    public void testAddProjectList() throws Exception {
        projectEntityDao.addProjectList(getTestEntityList());
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
}
