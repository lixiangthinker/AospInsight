package com.tonybuilder.dao.impl;

import com.tonybuilder.dao.CommitEntityDao;
import com.tonybuilder.entities.CommitEntity;
import com.tonybuilder.utils.DateTimeUtils;
import com.tonybuilder.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    public List<CommitEntity> getCommitList(YearMonth month) {
        List<CommitEntity> result = null;
        Timestamp[] since = DateTimeUtils.getSinceAndUntilTsByMonth(month);

        String queryString = "from CommitEntity c where c.commitSubmitDate > ?1 and c.commitSubmitDate <?2";
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<CommitEntity> query = session.createQuery(queryString, CommitEntity.class);
            query.setParameter(1, since[0]);
            query.setParameter(2, since[1]);
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

    private boolean isCommitExist(Session session, String hashId, int project) {
        boolean result = false;
        String queryString = "select count(*) from CommitEntity c where c.commitHashId=?1 and c.commitInProject = ?2";
        Query query = session.createQuery(queryString);
        query.setParameter(1, hashId);
        query.setParameter(2, project);
        Long count = (Long) query.uniqueResult();
        if (count != 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean addCommitList(List<CommitEntity> commitEntityList) {
        boolean result = false;
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (CommitEntity c : commitEntityList) {
                if (isCommitExist(session, c.getCommitHashId(), c.getCommitInProject())) {
                    System.out.println("update commit " + c.getCommitHashId());
                    String queryString = "update CommitEntity c set " +
                            "c.commitAddedLines = ?1, " +
                            "c.commitAlterDate = ?2, " +
                            "c.commitAuthor = ?3, " +
                            "c.commitAuthorMail = ?4, " +
                            "c.commitBranch = ?5, " +
                            "c.commitChangedLines = ?6, " +
                            "c.commitDeletedLines = ?7, " +
                            "c.commitInProject = ?8, " +
                            "c.commitHashId = ?9, " +
                            "c.commitLog = ?10, " +
                            "c.commitSubmitDate = ?11 " +
                            "where c.commitHashId = ?12";
                    Query query = session.createQuery(queryString);
                    query.setParameter(1, c.getCommitAddedLines());
                    query.setParameter(2, c.getCommitAlterDate());
                    query.setParameter(3, c.getCommitAuthor());
                    query.setParameter(4, c.getCommitAuthorMail());
                    query.setParameter(5, c.getCommitBranch());
                    query.setParameter(6, c.getCommitChangedLines());
                    query.setParameter(7, c.getCommitDeletedLines());
                    query.setParameter(8, c.getCommitInProject());
                    query.setParameter(9, c.getCommitHashId());
                    query.setParameter(10, c.getCommitLog());
                    query.setParameter(11, c.getCommitSubmitDate());
                    query.setParameter(12, c.getCommitHashId());

                    query.executeUpdate();
                } else {
                    System.out.println("insert commit " + c.getCommitHashId());
                    session.save(c);
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
