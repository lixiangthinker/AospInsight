package com.tonybuilder.dao;

import com.tonybuilder.entities.CommitEntity;

import java.time.YearMonth;
import java.util.List;

public interface CommitEntityDao {
    CommitEntity getCommitById(int id);
    List<CommitEntity> getCommitList();
    List<CommitEntity> getCommitList(YearMonth month);
    boolean addCommit(CommitEntity commit);
    boolean addCommitList(List<CommitEntity> commitEntityList);
    boolean updateCommit(CommitEntity commit);
    boolean deleteCommit(int id);
}
