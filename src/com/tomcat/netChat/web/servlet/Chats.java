package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.ChatException;
import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.javaBeans.Chat;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.service.ChatService;
import com.tomcat.netChat.service.UserService;
import com.tomcat.netChat.web.SessionService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Chats extends HttpServlet {

    public static final String RELATIVE_PATH = "/chat";
    public static final String REPRESENT_RESOURCE_PATH = "Chat/chat";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String id = req.getParameter(GroupChat.ID);
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + GroupPreview.RELATIVE_PATH);
            return;
        }

        try {
            List<Chat> chats = ChatService.allChatInRecord(Integer.parseInt(id));
            GroupChat currentGroup = ChatService.recordDescription(Integer.parseInt(id));
            User user = SessionService.identifyUserFromCookie(req, resp);

            webContext.setVariable(Chat.TEMPLATE_VARIABLE_COLLECTION, chats);
            webContext.setVariable(GroupChat.TEMPLATE_VARIABLE, currentGroup);
            webContext.setVariable(User.TEMPLATE_VARIABLE, user);
        } catch (ChatException e) {
            if (e.getEID() == ChatException.NOT_EXIST_GROUP_CODE) {
                resp.sendRedirect(req.getContextPath() + GroupPreview.RELATIVE_PATH);
            } else {
                e.printStackTrace();
            }
            return;
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

        String message = req.getParameter(Chat.MESSAGE);
        String group = req.getParameter(GroupChat.ID);

        try {
            User sender = SessionService.identifyUserFromCookie(req, resp);
            ChatService.recordingChat(message, Integer.parseInt(group), sender);

            doGet(req, resp);
        } catch (ChatException e) {
            if (e.getEID() == ChatException.CHAT_INSERT_EXCEPTION_CODE) {
                webContext.setVariable(Chat.MESSAGE, "post Chat is failed!");
                templateEngine.process("exception", webContext, resp.getWriter());
            } else if (e.getEID() == ChatException.NOT_EXIST_GROUP_CODE) {
                resp.sendRedirect(req.getContextPath() + GroupPreview.RELATIVE_PATH);
            } else {
                e.printStackTrace();
            }
        } catch (UserException e) {
            if (e.getEID() == UserException.NOT_EXIST_EXCEPTION_CODE) {
                resp.sendRedirect(req.getContextPath() + UserLogin.RELATIVE_PATH);
            }
        }
    }
}