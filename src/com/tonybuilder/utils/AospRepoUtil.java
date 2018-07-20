package com.tonybuilder.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tonybuilder.dao.ProjectEntityDao;
import com.tonybuilder.dao.impl.ProjectEntityImpl;
import com.tonybuilder.entities.ProjectEntity;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class AospRepoUtil {
    private final String sourceRoot;
    private static final String AOSP_ROOT = "/home/lixiang/source/aosp";
    private static final String REPO_PATH = ".repo/manifest.xml";

    final Logger log = Logger.getLogger("AospRepoUtil");

    public File getSourceDir() {
        return sourceDir;
    }

    private final File sourceDir;

    public AospRepoUtil(String rootPath) {
        this.sourceRoot = rootPath;
        this.sourceDir = new File(sourceRoot);
    }

    public static void main(String[] args) {
        AospRepoUtil repo = new AospRepoUtil(AOSP_ROOT);
        PropertyConfigurator.configure("src/log4j.properties");
        File root = repo.getSourceDir();
        if (root == null || !root.isDirectory()) {
            repo.log.error("source root path is invalid.");
            return;
        }

        File manifest = new File(root, REPO_PATH);

        if (!manifest.exists() || !manifest.isFile()) {
            repo.log.error("could not find manifest file");
            return;
        }

        // parse manifest, get project path and name
        List<ProjectEntity> parseResult = repo.parserXml(manifest);
        for (ProjectEntity p : parseResult) {
            System.out.println("["+p.getProjectName()+", "+p.getProjectPath()+"]");
        }

        ProjectEntityDao projectEntityDao = new ProjectEntityImpl();
        projectEntityDao.addProjectList(parseResult);

        System.out.println(parseResult.size() + " projects added to database");
        return;
    }

    private List<ProjectEntity> parserXml(File xml) {
        List<ProjectEntity> result = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(xml);
            Element manifestRoot = document.getRootElement();
            for (Iterator i = manifestRoot.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                if (!"project".equals(element.getName())) {
                    continue;
                }
                //System.out.println("Element: " + element.getName());

                ProjectEntity project = new ProjectEntity();

                Iterator attrIt = element.attributeIterator();
                while (attrIt.hasNext()) {
                    Attribute a = (Attribute) attrIt.next();
                    if ("path".equals(a.getName())) {
                        project.setProjectPath(a.getValue());
                    } else if ("name".equals(a.getName())) {
                        project.setProjectName(a.getValue());
                    }
                    //System.out.println("Attribute: ["+a.getName()+","+a.getValue()+"]");
                }
                project.setProjectIsExternalSrc(isExternalProject(project));
                project.setProjectIsDiscarded(isProjectDiscarded(project));
                project.setProjectModuleType(getProjectModuleType(project));
                result.add(project);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Byte isExternalProject(ProjectEntity p) {
        String path = p.getProjectPath();
        if (path == null || !path.contains("external")) {
            return 0;
        }

        return 1;
    }

    private Byte isProjectDiscarded(ProjectEntity p) {
        return 0;
    }

    public static final int PROJECT_CATEGORY_APP = 0;
    public static final int PROJECT_CATEGORY_FRAMEWORK = 10;
    public static final int PROJECT_CATEGORY_FRAMEWORK_NATIVE = 11;
    public static final int PROJECT_CATEGORY_HAL = 20;
    public static final int PROJECT_CATEGORY_BUILD = 30;
    public static final int PROJECT_CATEGORY_TEST = 40;
    public static final int PROJECT_CATEGORY_TOOLS = 50;
    public static final int PROJECT_CATEGORY_PREBUILTS = 60;
    public static final int PROJECT_CATEGORY_OTHER = 100;

    private int getProjectModuleType(ProjectEntity p) {
        String path = p.getProjectPath();
        if (path == null) {
            return PROJECT_CATEGORY_OTHER;
        }

        if (path.startsWith("tools") || path.startsWith("toolchain")) {
            return PROJECT_CATEGORY_TOOLS;
        }

        if (path.startsWith("test") || path.startsWith("platform_testing")
                || path.startsWith("cts") || path.startsWith("vts")) {
            return PROJECT_CATEGORY_TEST;
        }

        if (path.startsWith("system") || path.startsWith("libnativehelper")
                || path.startsWith("dalvik") || path.startsWith("art")
                || path.startsWith("bionic") || path.startsWith("bootable")) {
            return PROJECT_CATEGORY_FRAMEWORK_NATIVE;
        }

        if (path.startsWith("prebuilts")) {
            return PROJECT_CATEGORY_PREBUILTS;
        }

        if (path.startsWith("pdk") || path.startsWith("sdk") || path.startsWith("build")) {
            return PROJECT_CATEGORY_BUILD;
        }

        if (path.startsWith("packages")) {
            return PROJECT_CATEGORY_APP;
        }

        if (path.startsWith("libcore") || path.startsWith("frameworks")) {
            return PROJECT_CATEGORY_FRAMEWORK;
        }

        if (path.startsWith("hardware") || path.startsWith("device")) {
            return PROJECT_CATEGORY_HAL;
        }
        return PROJECT_CATEGORY_OTHER;
    }
}
