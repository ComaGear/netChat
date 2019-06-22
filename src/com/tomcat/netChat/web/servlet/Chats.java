package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.ChatException;
import com.tomcat.netChat.javaBeans.Chat;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.service.ChatService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Chats extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String id = req.getParameter("groupId");
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/group");
            return;
        }

        try {
            List<Chat> chats = ChatService.allChatInRecord(Integer.parseInt(id));
            List<GroupChat> groupChats = ChatService.allRecordDescription();
            GroupChat currentGroup = ChatService.recordDescription(Integer.parseInt(id));

            webContext.setVariable("chats", chats);
            webContext.setVariable("chatList", groupChats);
            webContext.setVariable("currentChats", currentGroup);
        } catch (ChatException e) {
            if (e.getEID() == ChatException.NOT_EXIST_GROUP_CODE) {
                resp.sendRedirect(req.getContextPath() + "/group");
            } else if (e.getEID() == ChatException.NOT_EXISTED_GROUPS_CODE) {
                resp.sendRedirect(req.getContextPath() + "/groupRecord");
            } else {
                e.printStackTrace();
            }
            return;
        }
        webContext.setVariable("leftType", "target");
        templateEngine.process("Chats/allChatInRecord", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String userEmail;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            Cookie cookie = getSpecifyNamedCookie(cookies, "userEmail");
            if (cookie != null && (cookie.getValue() != null)) {
                userEmail = cookie.getValue();
            } else {
                resp.sendRedirect(req.getContextPath() + "/userLogin");
                return;
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/userLogin");
            return;
        }

        String message = req.getParameter("message");
        String group = req.getParameter("groupId");
        String sender = userEmail;
        try {
            ChatService.recordingChat(message, Integer.parseInt(group), sender);

            doGet(req, resp);
        } catch (ChatException e) {
            if (e.getEID() == ChatException.CHAT_INSERT_EXCEPTION_CODE) {
                webContext.setVariable("message", "post Chat is failed!");
                templateEngine.process("exception", webContext, resp.getWriter());
            } else if (e.getEID() == ChatException.NOT_EXIST_GROUP_CODE) {
                resp.sendRedirect(req.getContextPath() + "/group");
            } else {
                e.printStackTrace();
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