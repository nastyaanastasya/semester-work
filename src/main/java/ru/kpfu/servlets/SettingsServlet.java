package ru.kpfu.servlets;

import ru.kpfu.exceptions.*;
import ru.kpfu.models.User;
import ru.kpfu.services.MediaService;
import ru.kpfu.services.ProfileService;
import ru.kpfu.services.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/profile/settings")
@MultipartConfig
public class SettingsServlet extends HttpServlet {
    private SecurityService securityService;
    private MediaService mediaService;
    private ProfileService profileService;

    @Override
    public void init() throws ServletException {
        securityService = (SecurityService) getServletContext().getAttribute("securityService");
        mediaService = (MediaService) getServletContext().getAttribute("mediaService");
        profileService = (ProfileService) getServletContext().getAttribute("profileService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", profileService.findById(((User)req.getSession().getAttribute("user")).getId()));
        getServletContext().getRequestDispatcher("/WEB-INF/views/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String attr = "message";
        String newUsername = req.getParameter("new-username");
        String curPass = req.getParameter("cur-pass");
        String newPass = req.getParameter("new-pass");
        String repNewPass = req.getParameter("rep-new-pass");
        Part image = req.getPart("uploaded-image");
        User user = profileService.findById(((User)req.getSession().getAttribute("user")).getId());
        if (req.getParameter("save-changes") != null) {
            try {
                securityService.changeUserData(req.getSession(), user, newUsername, curPass, newPass, repNewPass);
                if(image.getSubmittedFileName() != null && !image.getSubmittedFileName().isEmpty()) {
                    mediaService.saveUserMedia(user.getId(), image);
                    user.setImage(mediaService.getUserMedia(user.getId()));
                }
                securityService.updateUserInSession(req, user);
                resp.sendRedirect(getServletContext().getContextPath() + "/profile");
                return;
            } catch (InvalidUsernameException e) {
                req.setAttribute(attr, "Invalid username.");
            } catch (OccupiedUsernameException e) {
                req.setAttribute(attr, "This name is already in use. Please, choose another username.");
            } catch (WrongPasswordException e) {
                req.setAttribute(attr, "Wrong password.");
            } catch (InvalidPasswordException e) {
                req.setAttribute(attr, "Invalid new password.");
            } catch (DifferentPasswordsException e) {
                req.setAttribute(attr, "Entered passwords are different");
            } catch (FileUploadException e){
                req.setAttribute(attr, "Can't upload image.");
            }
        } else req.setAttribute(attr, "Can't save changes.");
        getServletContext().getRequestDispatcher("/WEB-INF/views/settings.jsp").forward(req, resp);
    }
}
