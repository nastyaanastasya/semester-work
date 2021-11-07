package ru.kpfu.servlets;

import ru.kpfu.exceptions.UnknownEmailException;
import ru.kpfu.exceptions.WrongPasswordException;
import ru.kpfu.services.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class AuthServlet extends HttpServlet {
    private SecurityService securityService;

    @Override
    public void init() throws ServletException {
        securityService = (SecurityService) getServletContext().getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/auth.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String attr = "message";
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (req.getParameter("sent") != null) {
            if (email != null && password != null) {
                try {
                    securityService.auth(email, password, req, resp);
                    resp.sendRedirect(req.getContextPath() + "/profile");
                    return;
                } catch (UnknownEmailException e) {
                    req.setAttribute(attr, "Unknown user. Sign up, if you don't have a page.");
                } catch (WrongPasswordException e) {
                    req.setAttribute("email", email);
                    req.setAttribute(attr, "Wrong password.");
                }
            }
            else req.setAttribute(attr, "Some fields are empty.");
        }
        req.setAttribute("email", email);
        getServletContext().getRequestDispatcher("/WEB-INF/views/auth.jsp").forward(req, resp);
    }
}
