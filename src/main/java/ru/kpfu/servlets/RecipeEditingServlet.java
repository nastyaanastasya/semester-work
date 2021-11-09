package ru.kpfu.servlets;

import ru.kpfu.models.Ingredient;
import ru.kpfu.models.Recipe;
import ru.kpfu.models.User;
import ru.kpfu.services.MediaService;
import ru.kpfu.services.RecipeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/recipe_editing")
@MultipartConfig
public class RecipeEditingServlet extends HttpServlet {
    private RecipeService recipeService;
    private MediaService mediaService;

    @Override
    public void init() throws ServletException {
        recipeService = (RecipeService) getServletContext().getAttribute("recipeService");
        mediaService = (MediaService) getServletContext().getAttribute("mediaService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("recipe");
        if (id != null) {
            req.setAttribute("recipe", recipeService.findById(Long.parseLong(id)));
        }
        req.setAttribute("user", req.getSession().getAttribute("user"));
        getServletContext().getRequestDispatcher("/WEB-INF/views/recipe_editing.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String attr = "message";
        User user = (User) req.getSession().getAttribute("user");
        if (req.getParameter("delete") != null) {
            String id = req.getParameter("id");
            if (id != null)
                recipeService.delete(Long.parseLong(id));
            resp.sendRedirect(req.getContextPath() + "/profile");
            return;
        }
        if (req.getParameter("save-changes") != null) {
            String title = req.getParameter("recipe-title");
            String hours = req.getParameter("hours");
            String minutes = req.getParameter("minutes");
            String desc = req.getParameter("description");
            String ingr = req.getParameter("save-changes");
            List<Part> fileParts = req.getParts().stream().filter(part -> "loaded-img".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());
            if (title != null && !title.isEmpty() && (minutes != null && !minutes.isEmpty() || hours != null && !hours.isEmpty()) && desc != null && !desc.isEmpty()) {
                try {
                    long recipeId = recipeService.save(title, hours, minutes, desc, user.getId());
                    recipeService.saveIngredients(ingr, recipeId);
                    mediaService.saveRecipeMedia(recipeId, fileParts);
                    resp.sendRedirect(req.getContextPath() + "/profile");
                    return;
                } catch (NumberFormatException e) {
                    req.setAttribute(attr, "Invalid time parameters.");
                }
            } else req.setAttribute(attr, "Some fields are empty.");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/recipe_editing.jsp").forward(req, resp);
    }
}
