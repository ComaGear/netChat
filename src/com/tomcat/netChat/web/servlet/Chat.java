package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
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

public class Chat extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String id = req.getParameter("groupId");
        List<com.tomcat.netChat.javaBeans.Chat> chats = ChatService.chat(Integer.parseInt(id));
        List<GroupChat> groupChats = ChatService.groupChat();
        GroupChat currentGroup = ChatService.groupChat(Integer.parseInt(id));

        webContext.setVariable("chats", chats);
        webContext.setVariable("chatList", groupChats);
        webContext.setVariable("leftType", "target");
        webContext.setVariable("currentChats", currentGroup);
        templateEngine.process("Chat/chat", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        Cookie[] cookies = req.getCookies();
        Cookie cookie = cookies[0];
        String userId = cookie.getValue();

        String message = req.getParameter("message");
        String group = req.getParameter("groupId");
        String sender = userId;
        boolean chat = ChatService.chat(message, Integer.parseInt(group), Integer.parseInt(sender));

        if (chat) {
            doGet(req, resp);
        } else {
            webContext.setVariable("exception", "post Chat is failed!!");
            templateEngine.process("exception", webContext, resp.getWriter());
        }
    }
}