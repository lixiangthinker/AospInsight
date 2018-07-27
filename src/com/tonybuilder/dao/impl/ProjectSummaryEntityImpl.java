package com.tonybuilder.dao.impl;

import com.tonybuilder.dao.ProjectSummaryEntityDao;
import com.tonybuilder.entities.CommitEntity;
import com.tonybuilder.entities.ProjectSummaryEntity;
import com.tonybuilder.utils.HibernateSessionFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
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
    public List<ProjectSummaryEntity> getProjectSummaryListByDate(Date since, Date until) {
        List<ProjectSummaryEntity> result = null;

        if (since == null) {
            since = new Date();
        } else if (until == null) {
            until = new Date();
        }

        String queryString = "from ProjectSummaryEntity p where p.projectSummarySince >= ?1 and p.projectSummarySince <= ?2";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<ProjectSummaryEntity> query = session.createQuery(queryString, ProjectSummaryEntity.class);
            query.setParameter(1, since);
            query.setParameter(2, until);
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

    private boolean isProjectSummaryExist(Session session, ProjectSummaryEntity projectSummary) {
        boolean result = false;
        String queryString = "select count(*) from ProjectSummaryEntity p where p.projectSummarySince=?1 " +
                "and p.projectSummaryOrigId = ?2";
        Query query = session.createQuery(queryString);
        query.setParameter(1, projectSummary.getProjectSummarySince());
        query.setParameter(2, projectSummary.getProjectSummaryOrigId());
        Long count = (Long) query.uniqueResult();
        if (count != 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean addProjectSummaryList(List<ProjectSummaryEntity> projectSummaryEntities) {
        boolean result = false;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (ProjectSummaryEntity projectSummary : projectSummaryEntities) {
                if (isProjectSummaryExist(session, projectSummary)) {
                    System.out.println("update projectSummary " + projectSummary.getProjectSummarySince());
                    String queryString = "update ProjectSummaryEntity p set " +
                            "p.projectSummarySince = ?1, " +
                            "p.projectSummaryUntil = ?2, " +
                            "p.projectSummaryAdded = ?3, " +
                            "p.projectSummaryDeleted = ?4, " +
                            "p.projectSummaryTotal = ?5, " +
                            "p.projectSummaryOrigId = ?6 " +
                            "where p.projectSummarySince = ?7 and p.projectSummaryOrigId = ?8";
                    Query query = session.createQuery(queryString);
                    query.setParameter(1, projectSummary.getProjectSummarySince());
                    query.setParameter(2, projectSummary.getProjectSummaryUntil());
                    query.setParameter(3, projectSummary.getProjectSummaryAdded());
                    query.setParameter(4, projectSummary.getProjectSummaryDeleted());
                    query.setParameter(5, projectSummary.getProjectSummaryTotal());
                    query.setParameter(6, projectSummary.getProjectSummaryOrigId());
                    query.setParameter(7, projectSummary.getProjectSummarySince());
                    query.setParameter(8, projectSummary.getProjectSummaryOrigId());

                    query.executeUpdate();
                } else {
                    System.out.println("insert projectSummary " + projectSummary.getProjectSummarySince());
                    session.save(projectSummary);
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
