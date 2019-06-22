package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.service.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        templateEngine.process("Chat/userSign", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            UserService.match(email, password);
        } catch (UserException e) {
            if (e.getEID() == UserException.LOGIN_EXCEPTION_CODE) {
                templateEngine.process("exception", webContext, resp.getWriter());
            } else {
                e.printStackTrace();
            }
            return;
        }

        Cookie cookie = new Cookie("userEmail", email);
        cookie.setMaxAge(-1);
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath() + "/userPreview?email=" + email);
    }
}
