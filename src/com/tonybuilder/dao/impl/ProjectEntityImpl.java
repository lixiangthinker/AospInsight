package com.tonybuilder.dao.impl;

import com.tonybuilder.dao.ProjectEntityDao;
import com.tonybuilder.entities.ProjectEntity;
import com.tonybuilder.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProjectEntityImpl implements ProjectEntityDao {
    @Override
    public ProjectEntity getProjectById(int id) {
        ProjectEntity result = null;
        String queryString = "from ProjectEntity p where p.projectId=?1";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<ProjectEntity> query = session.createQuery(queryString, ProjectEntity.class);
            query.setParameter(1, id);
            result = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<ProjectEntity> getProjectList() {
        List<ProjectEntity> result = null;
        String queryString = "from ProjectEntity p order by p.projectName asc";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<ProjectEntity> query = session.createQuery(queryString, ProjectEntity.class);
            result = query.list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public int getProjectIdByPath(String path) {
        int result = -1;
        String queryString = "from ProjectEntity p where p.projectPath = ?1";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<ProjectEntity> query = session.createQuery(queryString, ProjectEntity.class);
            query.setParameter(1, path);
            ProjectEntity project = query.uniqueResult();
            if (project != null) {
                result = project.getProjectId();
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean addProject(ProjectEntity project) {
        boolean result;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(project);
            transaction.commit();
            result = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            result = false;
        } finally {
            session.close();
        }
        return result;
    }

    private boolean isProjectExist(Session session, String projectName) {
        boolean result = false;
        String queryString = "select count(*) from ProjectEntity p where p.projectName=?1";
        Query query = session.createQuery(queryString);
        query.setParameter(1, projectName);
        Long count = (Long) query.uniqueResult();
        if (count != 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateProjectLoc(List<ProjectEntity> projectList) {
            boolean result;
            Session session = HibernateSessionFactory.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                for (ProjectEntity p : projectList) {
                    if (isProjectExist(session, p.getProjectName())) {
                        System.out.println("update project " + p.getProjectName());
                        String queryString = "update ProjectEntity p set " +
                                "p.projectTotalLines = ?1 " +
                                "where p.projectName = ?2";
                        Query query = session.createQuery(queryString);
                        query.setParameter(1, p.getProjectTotalLines());
                        query.setParameter(2, p.getProjectName());
                        query.executeUpdate();
                    } else {
                        System.out.println("insert project " + p.getProjectName());
                        session.save(p);
                    }
                }
                transaction.commit();
                result = true;
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                result = false;
            } finally {
                session.close();
            }
            return result;
        }

    @Override
    public boolean addProjectList(List<ProjectEntity> projectList) {
        boolean result;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (ProjectEntity p : projectList) {
                if (isProjectExist(session, p.getProjectName())) {
                    System.out.println("update project " + p.getProjectName());
                    String queryString = "update ProjectEntity p set " +
                            "p.projectPath = ?1, " +
                            "p.projectIsDiscarded = ?2, " +
                            "p.projectIsExternalSrc = ?3, " +
                            "p.projectModuleType = ?4 " +
                            "where p.projectName = ?5";
                    Query query = session.createQuery(queryString);
                    query.setParameter(1, p.getProjectPath());
                    query.setParameter(2,p.getProjectIsDiscarded());
                    query.setParameter(3,p.getProjectIsExternalSrc());
                    query.setParameter(4,p.getProjectModuleType());
                    query.setParameter(5, p.getProjectName());
                    query.executeUpdate();
                } else {
                    System.out.println("insert project " + p.getProjectName());
                    session.save(p);
                }
            }
            transaction.commit();
            result = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            result = false;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean updateProject(ProjectEntity project) {
        boolean result;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(project);
            transaction.commit();
            result = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            result = false;
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean deleteProject(int id) {
        boolean result;
        String queryString = "delete from ProjectEntity p where p.projectId=?1";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<ProjectEntity> query = session.createQuery(queryString);
            query.setParameter(1, id);
            query.executeUpdate();
            transaction.commit();
            result = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            result = false;
        } finally {
            session.close();
        }
        return result;
    }
}
