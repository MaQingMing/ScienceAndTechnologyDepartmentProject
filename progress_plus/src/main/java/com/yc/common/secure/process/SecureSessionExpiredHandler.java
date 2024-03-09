package com.yc.common.secure.process;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: model_project
 * @description: Security Session 过期 Handler
 * @author: 作者 huchaojie
 * @create: 2023-06-16 08:49
 */
@Component
public class SecureSessionExpiredHandler implements SessionInformationExpiredStrategy {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "/"+contextPath+"/login");
    }
}

