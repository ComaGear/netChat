package com.tomcat.netChat.servlet;

import com.tomcat.netChat.dao.EmployeeMapper;
import com.tomcat.netChat.javaBeans.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Chat extends HttpServlet {

    private final String configureSourcePath = "com/tomcat/netChat/resources/mybatis-config.xml";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        SqlSession openSession = null;
        Employee employee = null;
        resp.setContentType("text/html");
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
            String messages = "<html><body>" + employee.toString() + "</body></html>";
            PrintWriter writer = resp.getWriter();
            writer.println(messages);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}