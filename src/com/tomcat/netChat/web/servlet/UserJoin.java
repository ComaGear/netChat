package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.service.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserJoin extends HttpServlet {

    public static final String RELATIVE_PATH = "/join";
    private static final String REPRESENT_RESOURCE_PATH = "Chat/userSign";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), resp.getLocale());

        webContext.setVariable("isLogin", false);

        templateEngine.process(REPRESENT_RESOURCE_PATH, webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String name = req.getParameter(User.NAME);
        String email = req.getParameter(User.EMAIL);
        String password = req.getParameter(User.PASSWORD);
        System.out.println(name + email + password);

        try {
            UserService.join(email, password, name);
        } catch (UserException e) {
            if (e.getEID() == UserException.JOIN_EXCEPTION_CODE) {
                templateEngine.process("exception", webContext, resp.getWriter());
            } else {
                e.printStackTrace();
            }
            return;
        }

        Cookie cookie = new Cookie(User.EMAIL, email);
        cookie.setMaxAge(-1);
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath() + UserPreview.RELATIVE_PATH + "?email=" + email);
    }
}
