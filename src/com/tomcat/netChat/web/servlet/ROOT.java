package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ROOT extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        TemplateEngine templateEngine = (TemplateEngine) servletContext.getAttribute(NetChatApplication.TEMPLATE_ENGINE);

        WebContext webContext = new WebContext(req, resp, getServletContext(), resp.getLocale());
        templateEngine.process("index", webContext, resp.getWriter());
    }
}
