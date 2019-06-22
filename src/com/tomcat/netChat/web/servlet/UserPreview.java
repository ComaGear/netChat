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

public class UserPreview extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String email = req.getParameter("email");

        // it will return if email is null from parameter and cookies.
        if (email == null) {
            Cookie[] cookies = req.getCookies();
            Cookie cookieEmail = getSpecifyNamedCookie(cookies, "email");
            if (cookieEmail.getValue() != null) {
                email = cookieEmail.getValue();
            } else {
                templateEngine.process("exception", webContext, resp.getWriter());
                return;
            }
        }

        // email parameter got, continue process the user description and display them.
        User searchedUser = null;
        try {
            searchedUser = UserService.search(email);

        } catch (UserException e) {
            if (e.getEID() == UserException.NOT_EXIST_EXCEPTION_CODE) {
                templateEngine.process("exception", webContext, resp.getWriter());
            } else {
                e.printStackTrace();
            }

        } finally {
            if (searchedUser == null) {
                templateEngine.process("exception", webContext, resp.getWriter());
            } else {
                webContext.setVariable("user", searchedUser);
                templateEngine.process("Chat/user", webContext, resp.getWriter());
            }
        }
    }

    private Cookie getSpecifyNamedCookie(Cookie[] cookies, String name) {
        if (cookies == null || name == null) throw new NullPointerException("Ranking Cookies is null");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) return cookie;
        }
        return null;
    }
}
