package ru.kpfu.servlets;

import ru.kpfu.models.User;
import ru.kpfu.services.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private ProfileService profileService;

    @Override
    public void init() throws ServletException {
        profileService = (ProfileService) getServletContext().getAttribute("profileService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");
        String content = req.getParameter("content");
        if (id != null && !id.isEmpty()) {
            user = profileService.findById(Long.parseLong(id));
            req.setAttribute("user", user);
            req.setAttribute("isFollow", profileService.isFollow(user.getId(), ((User) req.getSession().getAttribute("user")).getId()));
        }
        user = profileService.setUserContent(user);
        req.setAttribute("user", user);

        if (content != null && !content.isEmpty()) {
            if (content.equals("fol") || content.equals("sub")) {
                req.setAttribute("userList", profileService.getUsersContent(content, user.getId()));
                req.setAttribute("users", true);
            } else {
                req.setAttribute("recipeList", profileService.getRecipesContent(content, user.getId()));
                req.setAttribute("recipes", true);
            }
        } else {
            req.setAttribute("recipeList", user.getRecipes());
            req.setAttribute("recipes", true);
        }
        req.setAttribute("rating", String.format("%.1f", profileService.getUserRating(user.getId())));
        req.setAttribute("contentTitle", profileService.getContentTitle(content));
        getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = (User) req.getSession().getAttribute("user");
        if (id != null && !id.isEmpty()) {
            long followId = Long.parseLong(id);
            if (profileService.isFollow(followId, user.getId())) {
                profileService.unfollow(followId, user.getId());
            } else profileService.follow(followId, user.getId());
            resp.sendRedirect(req.getContextPath() + "/profile?id=" + id);
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }
}
