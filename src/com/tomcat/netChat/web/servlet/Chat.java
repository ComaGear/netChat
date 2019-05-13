package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.web.resources.LayoutInitialize;
import com.tomcat.netChat.repository.dao.EmployeeMapper;
import com.tomcat.netChat.javaBeans.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Chat extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        resp.setContentType("text/html;charset=UTF-8");

        SqlSession openSession = null;
        Employee employee = null;
        try {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
            openSession = factory.openSession();
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            employee = mapper.getEmpById(1);
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (openSession != null) openSession.close();
        }

        if (employee != null) {
            WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());
            webContext.setVariable("emp", employee);
            webContext.setVariable("layout", new LayoutInitialize(true, true, false, false));
            templateEngine.process("employee", webContext, resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}