package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.repository.dao.EmployeeMapper;
import com.tomcat.netChat.javaBeans.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Chat extends HttpServlet {

    private final String configureSourcePath = "com/tomcat/netChat/repository/resources/mybatis-config.xml";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        process(req, resp, getServletContext());

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        resp.setContentType("text/html;charset=UTF-8");

        SqlSession openSession = null;
        Employee employee = null;
        try {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(configureSourcePath));
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
            templateEngine.process("employee", webContext, resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response, ServletContext context){
        NetChatApplication application = new NetChatApplication(context);
        context.setAttribute(NetChatApplication.TEMPLATE_ENGINE, application.getTemplateEngine());
    }
}