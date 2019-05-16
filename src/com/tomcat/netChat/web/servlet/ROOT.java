package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.service.ChatService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ROOT extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), resp.getLocale());

        List<GroupChat> groupChats = ChatService.groupChatList();

        webContext.setVariable("chatList", groupChats);
        templateEngine.process("Chat/chatList", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String groupName = req.getParameter("groupName");
        String creator = (req.getParameter("creatorId"));
        String detail = req.getParameter("detail");
        boolean groupChat = ChatService.createGroupChat(groupName, Integer.parseInt(creator), detail);

        if (groupChat) {
            doGet(req, resp);
        } else {
            webContext.setVariable("exception", "Creating group post is failed!!");
            templateEngine.process("exception", webContext, resp.getWriter());
        }
    }
}
