package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.ChatException;
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

public class GroupPreview extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), resp.getLocale());

        String groupId = req.getParameter("groupId");
        if (groupId != null) {

            GroupChat groupChat = ChatService.recordDescription(Integer.parseInt(groupId));
            if (groupChat != null) {
                webContext.setVariable("chatsDescription", groupChat);
                templateEngine.process("Chat/group", webContext, resp.getWriter());
            } else {
                templateEngine.process("exception", webContext, resp.getWriter());
            }
        } else {

            List<GroupChat> groupChats;
            try {
                groupChats = ChatService.allRecordDescription();
            } catch (ChatException e) {
                if (e.getEID() == ChatException.NOT_EXIST_GROUP_CODE) {
                    resp.sendRedirect(req.getContextPath() + "/groupRecord");
                } else {
                    e.printStackTrace();
                }
                return;
            }
            webContext.setVariable("chatList", groupChats);
            templateEngine.process("Chat/group", webContext, resp.getWriter());
        }
    }
}
