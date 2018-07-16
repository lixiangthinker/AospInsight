package com.tonybuilder.dao;

import com.tonybuilder.entities.CommitEntity;

import java.util.List;

public interface CommitEntityDao {
    CommitEntity getCommitById(int id);
    List<CommitEntity> getCommitList();
    boolean addCommit(CommitEntity commit);
    boolean updateCommit(CommitEntity commit);
    boolean deleteCommit(int id);
}
