package com.tonybuilder.dao.impl;

import com.tonybuilder.dao.ProjectSummaryEntityDao;
import com.tonybuilder.entities.ProjectSummaryEntity;
import com.tonybuilder.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProjectSummaryEntityImpl implements ProjectSummaryEntityDao {
    @Override
    public ProjectSummaryEntity getProjectSummaryById(int id) {
        ProjectSummaryEntity result = null;
        String queryString = "from ProjectSummaryEntity p where p.projectSummaryId=?1";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<ProjectSummaryEntity> query = session.createQuery(queryString, ProjectSummaryEntity.class);
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
    public List<ProjectSummaryEntity> getProjectSummaryList() {
        List<ProjectSummaryEntity> result = null;
        String queryString = "from ProjectSummaryEntity p";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<ProjectSummaryEntity> query = session.createQuery(queryString, ProjectSummaryEntity.class);
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
    public boolean addProjectSummary(ProjectSummaryEntity projectSummary) {
        boolean result;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(projectSummary);
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
    public boolean updateProjectSummary(ProjectSummaryEntity projectSummary) {
        boolean result;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(projectSummary);
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
    public boolean deleteProjectSummary(int id) {
        boolean result;
        String queryString = "delete from ProjectSummaryEntity p where p.projectSummaryId=?1";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<ProjectSummaryEntity> query = session.createQuery(queryString);
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
