package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.javaBeans.GroupChat;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.service.ChatService;
import com.tomcat.netChat.service.UserService;
import com.tomcat.netChat.web.SessionService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserPreview extends HttpServlet {

    public static final String RELATIVE_PATH = "/userPreview";
    public static final String REPRESENT_RESOURCE_PATH = "Chat/user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String email = req.getParameter(User.EMAIL);
        try {
            User user;
            List<GroupChat> groupChatsByUser;
            if (email == null) {
                user = SessionService.identifyUserFromCookie(req, resp);
                groupChatsByUser = ChatService.recordByUser(user.getEmail());
            } else {
                user = UserService.search(email);
                groupChatsByUser = ChatService.recordByUser(email);
            }

            webContext.setVariable(User.TEMPLATE_VARIABLE, user);
            if (groupChatsByUser != null)
                webContext.setVariable(GroupChat.TEMPLATE_VARIABLE_COLLECTION, groupChatsByUser);
        } catch (UserException e) {
            if (e.getEID() == UserException.NOT_EXIST_EXCEPTION_CODE) {
                templateEngine.process("exception", webContext, resp.getWriter());
            } else {
                e.printStackTrace();
            }
            return;
        }
        templateEngine.process(REPRESENT_RESOURCE_PATH, webContext, resp.getWriter());
    }
}
