package com.tonybuilder.utils;

import com.tonybuilder.dao.ProjectEntityDao;
import com.tonybuilder.dao.impl.ProjectEntityImpl;
import com.tonybuilder.entities.ProjectEntity;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class LineOfCodesUtil {

    private ProjectEntityDao projectEntityDao;
    private String projectBasePrefix = GlobalSettings.AOSP_SOURCE_PATH_PREFIX;

    public LineOfCodesUtil() {
        projectEntityDao = new ProjectEntityImpl();
    }

    private boolean isClocExist() {
        boolean result = false;
        String[] cmd = {"/bin/sh", "-c", "which cloc"};

        Runtime runtime = Runtime.getRuntime();

        Process process;

        try {
            process = runtime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.contains("/cloc")) {
                    result = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private int getLineOfCodeFromXml(File xmlFile) {
        if (xmlFile == null || !xmlFile.exists() || !xmlFile.isFile()) {
            System.out.println("xml file not exist");
            return 0;
        }

        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(xmlFile);
            Element resultRoot = document.getRootElement();
            for (Iterator i = resultRoot.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                if (!"languages".equals(element.getName())) {
                    continue;
                }

                for(Iterator j = element.elementIterator(); j.hasNext(); ) {
                    Element totalElement = (Element) j.next();
                    //System.out.println("name: " + totalElement.getName());
                    if (!"total".equals(totalElement.getName())) {
                        continue;
                    }
                    Iterator attrIt = totalElement.attributeIterator();
                    while (attrIt.hasNext()) {
                        Attribute a = (Attribute) attrIt.next();
                        //System.out.println("attribute: " + a.getName() + " " + a.getValue());
                        if ("code".equals(a.getName())) {
                            return Integer.parseInt(a.getValue());
                        }
                    }

                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int getLineOfCodeFromCache(File directory) {
        int result = 0;
        if (directory == null || !directory.isDirectory()) {
            return result;
        }

        String cacheFileName = directory.getName();
        File xmlCacheFile = new File(directory, cacheFileName+".xml");
        try {
            System.out.println(directory.getCanonicalPath()+"/"+cacheFileName+".xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!xmlCacheFile.exists()) { // TODO: or cache file outdated
            Runtime runtime = Runtime.getRuntime();
            String[] cmd = {"/bin/sh", "-c", "cloc . --out="+cacheFileName+".xml --xml"};

            try {
                Process process = runtime.exec(cmd, null, directory);
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return 0;
            }
        }

        result = getLineOfCodeFromXml(xmlCacheFile);

        return result;
    }

    private int getLineOfCode(File directory) {
        int result = 0;
        if (directory == null || !directory.isDirectory()) {
            return result;
        }

        Runtime runtime = Runtime.getRuntime();
        String[] cmd = {"/bin/sh", "-c", "cloc ."};

        Process process;

        try {
            System.out.println(directory.getCanonicalPath());
            process = runtime.exec(cmd, null, directory);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        try (BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("SUM:")) {
                    System.out.println(line);
                    String[] parts = line.split(" ");
                    if (parts.length != 0) {
                        result = Integer.parseInt(parts[parts.length - 1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        LineOfCodesUtil loc = new LineOfCodesUtil();

        if (!loc.isClocExist()) {
            System.out.println("cloc is not installed");
            System.out.println("Please excute: sudo apt-get install cloc");
            return;
        }

        List<ProjectEntity> projectList = loc.projectEntityDao.getProjectList();

        if (projectList == null || projectList.size() == 0) {
            System.out.println("Project Entity Table is null, please input project first");
            return;
        }

        for (ProjectEntity p : projectList) {
            String path = p.getProjectPath();
            if (path == null) {
                continue;
            }

            File projectDir = new File(loc.projectBasePrefix, path);
            if (!projectDir.exists() || !projectDir.isDirectory()) {
                System.out.println("Error path: " + loc.projectBasePrefix + path);
                continue;
            }

            // count line of code for each project
            //int locProject = loc.getLineOfCode(projectDir);
            int locProject = loc.getLineOfCodeFromCache(projectDir);
            System.out.println("loc: " + locProject);
            p.setProjectTotalLines((double) locProject);
        }

        loc.projectEntityDao.updateProjectLoc(projectList);
    }
}
