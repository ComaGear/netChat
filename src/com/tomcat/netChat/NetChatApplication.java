package com.tomcat.netChat;

import com.tomcat.netChat.javaBeans.User;
import com.tomcat.netChat.repository.dao.ChatMapper;
import com.tomcat.netChat.repository.dao.GroupChatMapper;
import com.tomcat.netChat.repository.dao.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.SQLException;

public class NetChatApplication {

    private TemplateEngine templateEngine;

    public static final String TEMPLATE_ENGINE = "TemplateEngine";
    public static final String REPOSITORY_RESOURCE = "com/tomcat/netChat/repository/resources/mybatis-config.xml";

    public NetChatApplication(final ServletContext servletContext){
        super();

        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
        templateResolver.setCacheable(false);

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);

        try {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(NetChatApplication.REPOSITORY_RESOURCE));
            SqlSession openSession = factory.openSession();
            UserMapper userMapper = openSession.getMapper(UserMapper.class);
            GroupChatMapper groupChatMapper = openSession.getMapper(GroupChatMapper.class);
            ChatMapper chatMapper = openSession.getMapper(ChatMapper.class);

            try {
                userMapper.initializeUserTable();
                groupChatMapper.initializeGroup();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            openSession.commit();
            openSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TemplateEngine getTemplateEngine(){
        return this.templateEngine;
    }
}
