package com.tonybuilder.services;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ProjectSummaryServlet", urlPatterns = {"/ProjectSummary.json"})
public class ProjectSummaryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        ProjectSummaryEntityDao projectSummaryEntityDao = new ProjectSummaryEntityImpl();

//        Calendar calendarSince = Calendar.getInstance();
//        calendarSince.set(Calendar.YEAR, 2017);
//        calendarSince.set(Calendar.MONTH, Calendar.DECEMBER);
//        calendarSince.set(Calendar.DATE, 5);
//
//        Date since = calendarSince.getTime();
        //List<ProjectSummaryEntity> list = projectSummaryEntityDao.getProjectSummaryListByDate(since, null);
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
