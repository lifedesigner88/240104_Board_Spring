package com.example.board.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication aut)
            throws IOException, ServletException {

        HttpSession httpSession = req.getSession();
//        authentication객체 안에는 User객체가 들어가 있고, 여기서 getName은 email을 의미.
        httpSession.setAttribute("email", aut.getName());
        res.sendRedirect("/");
    }
}
