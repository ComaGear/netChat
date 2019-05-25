package com.tomcat.netChat.web.servlet;

import com.tomcat.netChat.NetChatApplication;
import com.tomcat.netChat.service.ChatService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class User extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        boolean isPreview = false;
        try {
            String userId = req.getParameter("userId");
            isPreview = true;
            com.tomcat.netChat.javaBeans.User user = ChatService.user(Integer.parseInt(userId));
            webContext.setVariable("user", user);
            webContext.setVariable("isPreview", true);
            templateEngine.process("Chat/user", webContext, resp.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isPreview) {
            boolean isLogin = false;
            try {
                Cookie[] cookies = req.getCookies();
                Cookie cookie = cookies[0];
                String userId = cookie.getValue();
                isLogin = true;
                com.tomcat.netChat.javaBeans.User user = ChatService.user(Integer.parseInt(userId));

                webContext.setVariable("user", user);
                templateEngine.process("Chat/user", webContext, resp.getWriter());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if (!isLogin) {
                webContext.setVariable("isPreview", false);
                templateEngine.process("Chat/user", webContext, resp.getWriter());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(NetChatApplication.TEMPLATE_ENGINE);
        WebContext webContext = new WebContext(req, resp, getServletContext(), req.getLocale());

        String userName = req.getParameter("userName");
        String comment = req.getParameter("comment");

        com.tomcat.netChat.javaBeans.User user = ChatService.user(userName, comment);


        Cookie idCookie = new Cookie("userId", String.valueOf(user.getId()));
        idCookie.setMaxAge(-1);
        resp.addCookie(idCookie);

        if (user != null) {
            doGet(req, resp);
        } else {
            webContext.setVariable("exception", "post Chat is failed!!");
            templateEngine.process("exception", webContext, resp.getWriter());
        }
    }
}
