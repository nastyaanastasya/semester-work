package ru.kpfu.servlets;

import ru.kpfu.exceptions.*;
import ru.kpfu.services.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private SecurityService securityService;

    @Override
    public void init() throws ServletException {
        securityService = (SecurityService)getServletContext().getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String attr = "message";
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String repPassword = req.getParameter("repPassword");

        if(req.getParameter("sent") != null) {
            if (username != null && email != null && password != null && repPassword != null) {
                try {
                    securityService.register(username, email, password, repPassword, req, resp);
                    resp.sendRedirect(req.getContextPath() + "/profile");
                    return;
                } catch (OccupiedEmailException e) {
                    req.setAttribute(attr, "Such email has already been registered. Please, go to sign in page.");
                } catch (InvalidUsernameException e) {
                    req.setAttribute("email", email);
                    req.setAttribute(attr, "Invalid username.");
                } catch (OccupiedUsernameException e){
                    req.setAttribute("email", email);
                    req.setAttribute(attr, "This name is already in use. Please, choose another username.");
                } catch (InvalidEmailException e) {
                    req.setAttribute("username", username);
                    req.setAttribute(attr, "Invalid email.");
                } catch (InvalidPasswordException e) {
                    req.setAttribute("username", username);
                    req.setAttribute("email", email);
                    req.setAttribute(attr, "Invalid password.");
                } catch (DifferentPasswordsException e){
                    req.setAttribute("username", username);
                    req.setAttribute("email", email);
                    req.setAttribute(attr, "Entered passwords are different");
                }
            }
            else req.setAttribute(attr, "Some fields are empty.");
        }
        req.setAttribute("username", username);
        req.setAttribute("email", email);
        getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }
}
