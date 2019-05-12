package com.tomcat.netChat.web.filter;

import com.tomcat.netChat.NetChatApplication;
import org.thymeleaf.ITemplateEngine;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NetChatInitialFilter implements Filter {

    private ServletContext servletContext;
    private NetChatApplication application;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        this.application = new NetChatApplication(servletContext);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        process((HttpServletRequest) servletRequest,(HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    public boolean process(HttpServletRequest servletRequest, HttpServletResponse servletResponse){
        if (servletContext.getAttribute(NetChatApplication.TEMPLATE_ENGINE) == null) {
            ITemplateEngine templateEngine = this.application.getTemplateEngine();
            servletContext.setAttribute(NetChatApplication.TEMPLATE_ENGINE, templateEngine);
        }
        return true;
    }

}
