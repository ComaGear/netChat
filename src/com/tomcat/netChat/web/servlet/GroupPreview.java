package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.ChatException;
import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.javaBeans.Chat;
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
import java.util.List;

public class GroupPreview extends HttpServlet {

    public static final String RELATIVE_PATH = "/group";
    public static final String REPRESENT_RESOURCE_PATH = "Chat/group";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), resp.getLocale());

        try {
            List<GroupChat> groupChats = ChatService.allRecordDescription();
            User user = SessionService.identifyUserFromCookie(req, resp);

            webContext.setVariable(GroupChat.TEMPLATE_VARIABLE_COLLECTION, groupChats);
            webContext.setVariable(User.TEMPLATE_VARIABLE, user);
        } catch (ChatException e) {
            if (e.getEID() == ChatException.NOT_EXIST_GROUP_CODE) {
                resp.sendRedirect(req.getContextPath() + GroupRecord.RELATIVE_PATH);
            } else {
                e.printStackTrace();
            }
            return;
        } catch (UserException e) {
            if (e.getEID() == UserException.NOT_EXIST_EXCEPTION_CODE) {
                resp.sendRedirect(req.getContextPath() + UserJoin.RELATIVE_PATH);
            } else {
                e.printStackTrace();
            }
            return;
        }
        templateEngine.process(REPRESENT_RESOURCE_PATH, webContext, resp.getWriter());
    }
}