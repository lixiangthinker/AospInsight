package com.tonybuilder.repo;

import com.tonybuilder.dao.ProjectEntityDao;
import com.tonybuilder.dao.impl.ProjectEntityImpl;
import com.tonybuilder.entities.ProjectEntity;
import com.tonybuilder.utils.GlobalSettings;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CleanRepoBuffer {
    private String projectBasePrefix = GlobalSettings.AOSP_SOURCE_PATH_PREFIX;
    private ProjectEntityDao projectEntityDao;

    public CleanRepoBuffer() {
        projectEntityDao = new ProjectEntityImpl();
    }

    public static void main(String[] args) {
        CleanRepoBuffer cleanRepoBuffer = new CleanRepoBuffer();
        List<ProjectEntity> projectList = cleanRepoBuffer.projectEntityDao.getProjectList();

        System.out.println("total project: " + projectList.size());
        int index = 0;
        for (ProjectEntity p: projectList) {
            index++;
            System.out.println("current project " + index + " " + p.getProjectPath());
            File projectDir = new File(cleanRepoBuffer.projectBasePrefix, p.getProjectPath());
            try {
                System.out.println(projectDir.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            LineOfCodesUtil.clearCacheFile(projectDir);
            GitLogUtil.cleanCache(projectDir);
        }
        System.exit(0);
    }
}
