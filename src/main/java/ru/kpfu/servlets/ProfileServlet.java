package ru.kpfu.servlets;

import ru.kpfu.models.User;
import ru.kpfu.services.ProfileService;
import ru.kpfu.services.RecipeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private ProfileService profileService;
    private RecipeService recipeService;

    @Override
    public void init() throws ServletException {
        profileService = (ProfileService) getServletContext().getAttribute("profileService");
        recipeService = (RecipeService) getServletContext().getAttribute("recipeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String content = req.getParameter("content");
        User user;
        if (id != null) {
            user = profileService.findById(Long.parseLong(id));
            req.setAttribute("user", user);
        } else if ((user = (User) req.getAttribute("user")) != null) {
            req.setAttribute("user", user);
        } else {
            user = (User) req.getSession().getAttribute("user");
        }
        user.setImage(profileService.setProfileImage(user));
        req.setAttribute("user", user);
        if (content != null) {
            if (content.equals("follower") || content.equals("subscribers")) {
                req.setAttribute("list", profileService.getUsersContent(content, user.getId()));
                req.setAttribute("users", true);
            } else {
                req.setAttribute("list", profileService.getRecipesContent(content, user.getId()));
                req.setAttribute("recipes", true);
            }
        } else req.setAttribute("list", profileService.getRecipesContent("res", user.getId()));
        req.setAttribute("contentTitle", profileService.getTitle(content));
        getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }
}
