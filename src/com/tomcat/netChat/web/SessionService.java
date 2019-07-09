package com.tomcat.netChat.web;

import com.tomcat.netChat.exception.UserException;
import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.service.UserService;
import com.tomcat.netChat.web.servlet.UserJoin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionService {

    public static User identifyUserFromCookie(HttpServletRequest request, HttpServletResponse response) throws IOException, UserException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie namedCookie = getSpecifyNamedCookie(cookies, User.EMAIL);
            if (namedCookie == null) throw new UserException(UserException.NOT_EXIST_EXCEPTION_CODE);

            String userEmail = namedCookie.getValue();
            try {
                User searchedUser = UserService.search(userEmail);
                return searchedUser;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UserException e) {
                if (e.getEID() == UserException.NOT_EXIST_EXCEPTION_CODE) {
                    response.sendRedirect(request.getContextPath() + UserJoin.RELATIVE_PATH);
                } else {
                    e.printStackTrace();
                }
            }
        } else {
            response.sendRedirect(request.getContextPath() + UserJoin.RELATIVE_PATH);
            throw new UserException(UserException.NOT_EXIST_EXCEPTION_CODE);
        }
        return null;
    }

    private static Cookie getSpecifyNamedCookie(Cookie[] cookies, String name) {
        if (cookies == null || name == null) throw new NullPointerException("Ranking Cookies is null");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) return cookie;
        }
        return null;
    }
}
