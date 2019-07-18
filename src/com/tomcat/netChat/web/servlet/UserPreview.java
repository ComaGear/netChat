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

    public static final String NO_PARAMETERS_IDENTIFICATION = "CmGr2844747235";
    public static final String NO_PARAMETERS_IDENTIFICATION1 = "your password must input when you modify your information";

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
                webContext.setVariable("self", true);
            } else {
                user = UserService.search(email);
                groupChatsByUser = ChatService.recordByUser(email);
                webContext.setVariable("self", false);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = null;
        String password = null;
        String comment = null;
        String userEmail = req.getParameter(User.EMAIL);

        if (req.getParameter(User.NAME) != null && !req.getParameter(User.NAME).equals(NO_PARAMETERS_IDENTIFICATION))
            userName = req.getParameter(User.NAME);
        if (req.getParameter(User.COMMENT) != null && !req.getParameter(User.COMMENT).equals(NO_PARAMETERS_IDENTIFICATION))
            comment = req.getParameter(User.COMMENT);
        if (req.getParameter(User.PASSWORD) != null && !req.getParameter(User.PASSWORD).equals(NO_PARAMETERS_IDENTIFICATION1)) {
            password = req.getParameter(User.PASSWORD);
        } else resp.sendRedirect(req.getContextPath() + GroupPreview.RELATIVE_PATH);

        try {
            User user = new User(userEmail);
            if (userEmail != null) user.setName(userName);
            if (comment != null) user.setComment(comment);
            user.setUserPassword(password);
            UserService.update(user);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        resp.sendRedirect(req.getContextPath() + req.getServletPath());
    }
}
