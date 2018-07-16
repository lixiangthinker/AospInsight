package com.tonybuilder;

import com.google.gson.Gson;
import com.tonybuilder.dao.ProjectSummaryEntityDao;
import com.tonybuilder.dao.impl.ProjectSummaryEntityImpl;
import com.tonybuilder.entities.ProjectSummaryEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "HelloWorldServlet", urlPatterns = {"/HelloWorld"})
public class HelloWorldServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        ProjectSummaryEntityDao projectSummaryEntityDao = new ProjectSummaryEntityImpl();
        List<ProjectSummaryEntity> list = projectSummaryEntityDao.getProjectSummaryList();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String jsonStr = gson.toJson(list);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
