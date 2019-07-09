package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.service.ChatService;
import com.tomcat.netChat.web.SessionService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupRecord extends HttpServlet {

    static final String RELATIVE_PATH = "/record";
    private static final String REPRESENT_RESOURCE_PATH = "Chat/groupSign";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        try {
            User user = SessionService.identifyUserFromCookie(req, resp);

            webContext.setVariable(User.TEMPLATE_VARIABLE, user);

        } catch (UserException e) {
            if (e.getEID() == UserException.NOT_EXIST_EXCEPTION_CODE) {
                resp.sendRedirect(req.getContextPath() + UserLogin.RELATIVE_PATH);
            } else {
                e.printStackTrace();
            }
            return;
        }
        templateEngine.process(REPRESENT_RESOURCE_PATH, webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String groupName = req.getParameter(GroupChat.NAME);
        String detail = req.getParameter(GroupChat.DETAIL);

        try {
            User user = SessionService.identifyUserFromCookie(req, resp);
            Integer id = ChatService.createRecord(groupName, detail, user);

            resp.sendRedirect(req.getContextPath() + Chats.RELATIVE_PATH + "?" + GroupChat.ID + "=" + id);
        } catch (UserException e) {
            if (e.getEID() == UserException.NOT_EXIST_EXCEPTION_CODE) {
                templateEngine.process("exception", webContext, resp.getWriter());
            } else {
                e.printStackTrace();
            }
        }
    }
}
