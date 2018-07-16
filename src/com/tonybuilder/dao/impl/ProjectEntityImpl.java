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
        String queryString = "from ProjectEntity p";
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
