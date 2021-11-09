package ru.kpfu.servlets;

import ru.kpfu.models.Recipe;
import ru.kpfu.models.User;
import ru.kpfu.services.ProfileService;
import ru.kpfu.services.RecipeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/recipe")
public class RecipeServlet extends HttpServlet {
    private RecipeService recipeService;
    private ProfileService profileService;

    @Override
    public void init() throws ServletException {
        recipeService = (RecipeService) getServletContext().getAttribute("recipeService");
        profileService = (ProfileService) getServletContext().getAttribute("profileService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        user = profileService.setUserContent(user);
        String id = req.getParameter("id");
        Recipe recipe = null;
        if (id != null) {
            long recipeId = Long.parseLong(id);
            recipe = recipeService.findById(recipeId);
            req.setAttribute("isLiked", recipeService.isLiked(recipe.getId(), user.getId()));
            req.setAttribute("isSaved", recipeService.isSaved(recipe.getId(), user.getId()));
            req.setAttribute("timeOfCooking", recipeService.getTimeString(recipe.getTimeOfCooking()));
        }
        req.setAttribute("recipe", recipe);
        req.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/WEB-INF/views/recipe.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("add-comment") != null) {
            User user = (User) req.getSession().getAttribute("user");
            String recId = req.getParameter("id");
            String rating = req.getParameter("rating");
            String review = req.getParameter("comment-text");
            if (rating != null && !rating.isEmpty() && review != null && !review.isEmpty()) {
                recipeService.addComment(rating, review, user.getId(), Long.parseLong(recId));
                req.setAttribute("id", recId);
                resp.sendRedirect(req.getContextPath() + "/recipe?id=" + recId);
                return;
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/recipe.jsp").forward(req, resp);
    }
}
