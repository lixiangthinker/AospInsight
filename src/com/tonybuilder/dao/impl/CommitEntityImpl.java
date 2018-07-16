package com.tonybuilder.dao.impl;

import com.tonybuilder.dao.CommitEntityDao;
import com.tonybuilder.entities.CommitEntity;
import com.tonybuilder.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CommitEntityImpl implements CommitEntityDao {
    @Override
    public CommitEntity getCommitById(int id) {
        CommitEntity commit = null;
        String queryString = "from CommitEntity c where c.commitId=?1";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query<CommitEntity> query = session.createQuery(queryString, CommitEntity.class);
            query.setParameter(1, id);
            commit = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return commit;
    }

    @Override
    public List<CommitEntity> getCommitList() {
        List<CommitEntity> result = null;
        String queryString = "from CommitEntity";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<CommitEntity> query = session.createQuery(queryString, CommitEntity.class);
            result = query.list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public boolean addCommit(CommitEntity commit) {
        boolean result;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(commit);
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
    public boolean updateCommit(CommitEntity commit) {
        boolean result;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(commit);
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
    public boolean deleteCommit(int id) {
        boolean result;
        String queryString = "delete from CommitEntity c where c.commitId=?1";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<CommitEntity> query = session.createQuery(queryString);
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
