package com.tonybuilder.utils;

import com.tonybuilder.dao.CommitEntityDao;
import com.tonybuilder.dao.ProjectEntityDao;
import com.tonybuilder.dao.impl.CommitEntityImpl;
import com.tonybuilder.dao.impl.ProjectEntityImpl;
import com.tonybuilder.entities.CommitEntity;
import com.tonybuilder.entities.ProjectEntity;

import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*
commit 9c610f7567bc713e802842bd6c541d22941d8cea
Author:     Neil Fuller <nfuller@google.com>
AuthorDate: 2018-07-04 16:41:14 +0100
Commit:     Neil Fuller <nfuller@google.com>
CommitDate: 2018-07-05 10:23:53 +0100

    Remove unusual static method call

    The code is using static method Calendar.getInstance() but via a
    subclass of Calendar. It works, and getInstance() returns a
    GregorianCalendar on Android, but it's odd and was probably unintended.

    Noticed during static analysis of SystemUI bytecode.

    Bug: 111055375
    Test: build / boot
    Change-Id: I1762cbeb2cc7882868f84ec11100815671cd29ec

packages/SystemUI/src/com/android/systemui/doze/DozeUi.java | 3 +--
1 file changed, 1 insertion(+), 2 deletions(-)
*/
public class GitLogUtil {
    private CommitEntityDao commitEntityDao;
    private ProjectEntityDao projectEntityDao;
    private String projectBasePrefix = GlobalSettings.AOSP_SOURCE_PATH_PREFIX;

    public GitLogUtil() {
        commitEntityDao = new CommitEntityImpl();
        projectEntityDao = new ProjectEntityImpl();
    }

    private boolean genProjectGitLogSince(String path, String since) {
        if (path == null || since == null) {
            System.out.println("invalid path or since date");
            return false;
        }

        LocalDate sinceDate = LocalDate.parse(since, DateTimeFormatter.ISO_DATE);
        //System.out.println("genProjectGitLogSince sinceDate: " + sinceDate + " project: " + path);

        File projectDir = new File(projectBasePrefix, path);

        String cacheFileName = "git-log-" + projectDir.getName() + "-" + sinceDate + ".log";
        String[] cmd = {"/bin/sh", "-c",
                "git log --pretty=fuller --no-merges " +
                        "--since=" + sinceDate + " " +
                        "--stat > " + cacheFileName};
        System.out.println("genProjectGitLogSince cmd: " + cmd[2]);
        Runtime runtime = Runtime.getRuntime();

        Process process;

        try {
            process = runtime.exec(cmd, null, projectDir);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
    commit 9c610f7567bc713e802842bd6c541d22941d8cea
    Author:     Neil Fuller <nfuller@google.com>
    AuthorDate: 2018-07-04 16:41:14 +0100
    Commit:     Neil Fuller <nfuller@google.com>
    CommitDate: 2018-07-05 10:23:53 +0100

        Remove unusual static method call

        The code is using static method Calendar.getInstance() but via a
        subclass of Calendar. It works, and getInstance() returns a
        GregorianCalendar on Android, but it's odd and was probably unintended.

        Noticed during static analysis of SystemUI bytecode.

        Bug: 111055375
        Test: build / boot
        Change-Id: I1762cbeb2cc7882868f84ec11100815671cd29ec

    packages/SystemUI/src/com/android/systemui/doze/DozeUi.java | 3 +--
    1 file changed, 1 insertion(+), 2 deletions(-)
    */

    enum ParingState {
        IDLE,
        PARSING_HASH_ID,
        PARSING_AUTHOR,
        PARSING_AUTHOR_DATE,
        PARSING_COMMIT,
        PARSING_COMMIT_DATE,
        PARSING_CONTENT,
        PARSING_CHANGED_FILES,
        PARSING_TOTAL_LINES,
        PARSING_STATE_MAX
    }

    private String getHashId(String line) {
        int hashIdIndex = "commit ".length();
        String hashId = line.substring(hashIdIndex);
        hashId = hashId.trim();
        return hashId;
    }

    private String getAuthor(String line) {
        int authorEndIndex = line.indexOf("<");
        String author = line.substring("Author:".length(), authorEndIndex);
        author = author.trim();
        return author;
    }

    private String getCommit(String line) {
        int commitEndIndex = line.indexOf("<");
        String commit = line.substring("Commit:".length(), commitEndIndex);
        commit = commit.trim();
        return commit;
    }

    private String getMail(String line) {
        int mailBeginIndex = line.indexOf("<");
        int mailEndIndex = line.indexOf(">");
        String mail = line.substring(mailBeginIndex + 1, mailEndIndex);
        mail = mail.trim();
        return mail;
    }

    // Wed Jul 4 16:41:14 2018 +0100
    private Timestamp parseStringDate(String strDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z").withLocale(Locale.ENGLISH);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(strDate, dateTimeFormatter);
        Instant instant = zonedDateTime.toInstant();
        return Timestamp.from(instant);
    }

    private Timestamp getAuthorDate(String line) {
        int authorDateIndex = "AuthorDate: ".length();
        String authorDate = line.substring(authorDateIndex);
        authorDate = authorDate.trim();
        return parseStringDate(authorDate);
    }

    private Timestamp getCommitDate(String line) {
        int commitDateIndex = "CommitDate: ".length();
        String commitDate = line.substring(commitDateIndex);
        commitDate = commitDate.trim();
        return parseStringDate(commitDate);
    }

    //1 file changed, 1 insertion(+), 2 deletions(-)
    private int getAddedLines(String line) {
        int result = 0;
        if (!line.contains("insertion(+)") && !line.contains("insertions(+)")) {
            return 0;
        }

        String[] parts = line.split(",");
        int index = 0;
        while (index < parts.length) {
            if (parts[index].contains("insertion")) {
                break;
            }
            index++;
        }
        String insertionPart = parts[index].trim();
        String[] insertionPartSplit = insertionPart.split(" ");
        result = Integer.parseInt(insertionPartSplit[0]);
        return result;
    }

    private int getDeletedLines(String line) {
        int result = 0;
        if (!line.contains("deletion(-)") && !line.contains("deletions(-)")) {
            return 0;
        }

        String[] parts = line.split(",");
        int index = 0;
        while (index < parts.length) {
            if (parts[index].contains("deletion")) {
                break;
            }
            index++;
        }
        String deletionPart = parts[index].trim();
        String[] deletionPartSplit = deletionPart.split(" ");
        result = Integer.parseInt(deletionPartSplit[0]);
        return result;
    }

    private int getTotalLines(String line) {
        return getAddedLines(line) + getDeletedLines(line);
    }

    private List<CommitEntity> parseGitLog(File gitLog) {
        System.out.println("parseGitLog " + gitLog.getName());
        List<CommitEntity> result = new ArrayList<>();
        try (BufferedReader gitLogReader = new BufferedReader(new FileReader(gitLog))) {
            String line;
            CommitEntity commit = new CommitEntity();
            StringBuffer commitString = null;
            StringBuffer changedFiles = null;
            ParingState state = ParingState.PARSING_HASH_ID;
            while ((line = gitLogReader.readLine()) != null) {
                switch (state) {
                    case PARSING_HASH_ID:
                        if (line.startsWith("commit ")) {
                            state = ParingState.PARSING_AUTHOR;
                            commit.setCommitHashId(getHashId(line));
                        }
                        break;
                    case PARSING_AUTHOR:
                        if (line.startsWith("Author:")) {
                            state = ParingState.PARSING_AUTHOR_DATE;
                            commit.setCommitAuthor(getAuthor(line));
                            commit.setCommitAuthorMail(getMail(line));
                        }
                        break;
                    case PARSING_AUTHOR_DATE:
                        if (line.startsWith("AuthorDate:")) {
                            state = ParingState.PARSING_COMMIT;
                            commit.setCommitAlterDate(getAuthorDate(line));
                        }
                        break;
                    case PARSING_COMMIT:
                        if (line.startsWith("Commit:")) {
                            state = ParingState.PARSING_COMMIT_DATE;
                            //commit.setCommitAuthor(getCommit(line));
                        }
                        break;
                    case PARSING_COMMIT_DATE:
                        if (line.startsWith("CommitDate:")) {
                            state = ParingState.PARSING_CONTENT;
                            commit.setCommitSubmitDate(getCommitDate(line));
                        }
                        break;
                    case PARSING_CONTENT:
                        if (line.startsWith("    ") || line.trim().length() == 0) {
                            if (commitString == null) {
                                commitString = new StringBuffer();
                            }
                            commitString.append(line);
                            commitString.append("\n");
                        } else {
                            state = ParingState.PARSING_CHANGED_FILES;
                            if (commitString != null) {
                                commit.setCommitLog(filterEmoji(commitString.toString(), " "));
                                commitString = null;
                            }
                            if (changedFiles == null) {
                                changedFiles = new StringBuffer();
                                changedFiles.append(line);
                            }
                        }
                        break;
                    case PARSING_CHANGED_FILES:
                        if (line.contains("insertion(+)") || line.contains("deletions(-)")
                                || line.contains("insertions(+)") || line.contains("deletion(-)")) {
                            state = ParingState.PARSING_HASH_ID;
                            commit.setCommitAddedLines(getAddedLines(line));
                            commit.setCommitDeletedLines(getDeletedLines(line));
                            commit.setCommitChangedLines(getTotalLines(line));
                            //commit.setChangeFiles(changedFiles.toString());
                            changedFiles = null;
                            result.add(commit);
                            commit = new CommitEntity();
                        } else {
                            if (changedFiles != null) {
                                changedFiles.append(line);
                            }
                        }
                        break;
                    case PARSING_TOTAL_LINES:
                        break;
                    default:
                        System.out.println("error parsing state: " + line);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * emoji表情替换
     *
     * @param source  原字符串
     * @param slipStr emoji表情替换成的字符串
     * @return 过滤后的字符串
     */
    private String filterEmoji(String source, String slipStr) {
        if (source != null) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        } else {
            return null;
        }
    }

    private List<CommitEntity> parseProjectLog(String path) {
        System.out.println("parseProjectLog path " + path);
        List<CommitEntity> result = new ArrayList<>();

        if (path == null) {
            System.out.println("invalid git log cache path");
            return result;
        }

        File projectDir = new File(projectBasePrefix, path);
        if (!projectDir.exists() || !projectDir.isDirectory()) {
            System.out.println("project directory is invalid, path = " + path);
            return result;
        }

        File[] gitCacheFiles = projectDir.listFiles(new GitLogCacheFileFilter());

        if (gitCacheFiles == null || gitCacheFiles.length == 0) {
            return result;
        }

        // get latest log file
        Arrays.sort(gitCacheFiles, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return -1;
                else if (diff == 0)
                    return 0;
                else
                    return 1;
            }
        });
        result = parseGitLog(gitCacheFiles[0]);
        return result;
    }

    private boolean getGitLogForAllRepo(String since) {
        boolean result = false;
        /* get project list*/
        List<ProjectEntity> projectEntityList = projectEntityDao.getProjectList();
        if (projectEntityList == null || projectEntityList.size() == 0) {
            System.out.println("could not get project list");
            return result;
        }

        int totalProjects = projectEntityList.size();
        int currentProject = 0;
        for (ProjectEntity p : projectEntityList) {
            System.out.println("current " + currentProject + " totalProject " + totalProjects);
            currentProject++;
            result = genProjectGitLogSince(p.getProjectPath(), since);
            if (!result) {
                System.out.println("could not generate git log for " + p.getProjectPath());
                continue;
            }

            if (p.getProjectIsExternalSrc() == 1) {
                System.out.println("ignore external source");
                continue;
            }

            if (p.getProjectModuleType() == GlobalSettings.PROJECT_CATEGORY_PREBUILTS) {
                System.out.println("ignore prebuilts source");
                continue;
            }

            List<CommitEntity> commitEntityList = parseProjectLog(p.getProjectPath());
            int projectId = projectEntityDao.getProjectIdByPath(p.getProjectPath());
            System.out.println("commitEntityList.size = " + commitEntityList.size());
            for (CommitEntity c : commitEntityList) {
                c.setCommitInProject(projectId);
                c.setCommitBranch("master");
            }
            result = commitEntityDao.addCommitList(commitEntityList);
        }

        return result;
    }

    private boolean getGItLogForSingleRepo(String path, String since, boolean cleanCache) {
        boolean result = false;
        File projectDir = new File(projectBasePrefix, path);
        if (!projectDir.exists() || !projectDir.isDirectory()) {
            System.out.println("project directory is invalid, path = " + path);
            return result;
        }

        if (cleanCache) {
            File[] gitCacheFiles = projectDir.listFiles(new GitLogCacheFileFilter());

            // clean previous cache log files
            if (gitCacheFiles != null && gitCacheFiles.length != 0) {
                for (File cacheFile : gitCacheFiles) {
                    cacheFile.delete();
                }
            }

            result = genProjectGitLogSince(path, since);
            if (!result) {
                System.out.println("could not generate git log for " + path + " since " + since);
                return false;
            }
        }

        int projectId = projectEntityDao.getProjectIdByPath(path);
        List<CommitEntity> commitEntityList = parseProjectLog(path);
        System.out.println("path: " + path + " since: " + since + " commitEntityList.size = " + commitEntityList.size());
        for (CommitEntity c : commitEntityList) {
            c.setCommitInProject(projectId);
            c.setCommitBranch("master");
        }

        result = commitEntityDao.addCommitList(commitEntityList);
        return result;
    }

    public static void main(String[] args) {
        GitLogUtil gitLogUtil = new GitLogUtil();

        gitLogUtil.getGItLogForSingleRepo("frameworks/base", "2017-01-01", false);

        //gitLogUtil.getGitLogForAllRepo("2018-06-01");

        System.out.println("End of GitLogUtil.main()");
    }

    private class GitLogCacheFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File file, String s) {
            boolean result = false;
            if (s.startsWith("git-log-") && s.endsWith(".log")) {
                result = true;
            }
            return result;
        }
    }

}