package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.service.ChatService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupRecord extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            Cookie cookie = getSpecifyNamedCookie(cookies, "userEmail");
            if (cookie == null || cookie.getValue() == null) {
                resp.sendRedirect(req.getContextPath() + "/userLogin");
                return;
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/userLogin");
            return;
        }

        templateEngine.process("Chat/groupSign", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String userEmail;

        Cookie[] cookies = req.getCookies();
        Cookie cookie = getSpecifyNamedCookie(cookies, "UserEmail");
        if (cookie.getValue() != null) {
            userEmail = cookie.getValue();
        } else {
            templateEngine.process("exception", webContext, resp.getWriter());
            return;
        }

        String groupName = req.getParameter("groupName");
        String detail = req.getParameter("detail");

        int record;
        try {
            record = ChatService.createRecord(groupName, userEmail, detail);
        } catch (UserException e) {
            if (e.getEID() == UserException.NOT_EXIST_EXCEPTION_CODE) {
                templateEngine.process("exception", webContext, resp.getWriter());
            } else {
                e.printStackTrace();
            }
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/chat?groupId=" + record);
    }

    private Cookie getSpecifyNamedCookie(Cookie[] cookies, String name) {
        if (cookies == null || name == null) throw new NullPointerException("Ranking Cookies is null");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) return cookie;
        }
        return null;
    }
}
