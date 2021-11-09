package ru.kpfu.servlets;

import ru.kpfu.models.Recipe;
import ru.kpfu.models.User;
import ru.kpfu.services.ProfileService;
import ru.kpfu.services.RecipeService;
import ru.kpfu.utils.StringMatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private RecipeService recipeService;
    private ProfileService profileService;

    @Override
    public void init() throws ServletException {
        recipeService = (RecipeService) getServletContext().getAttribute("recipeService");
        profileService = (ProfileService) getServletContext().getAttribute("profileService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("search-btn") != null) {
            StringMatcher sm = new StringMatcher();
            String content = req.getParameter("content");
            String query = req.getParameter("search-field");

            if(content != null && !content.isEmpty()){
                if(content.equals("recipes")){
                    List<Recipe> recMatches = new ArrayList<>();
                    List<Recipe> recipeList = recipeService.getAllRecipes();
                    for(Recipe r : recipeList){
                        if(sm.matchString(r.getTitle(), query)){
                            recMatches.add(r);
                        }
                    }
                    req.getSession().setAttribute("recipeList", recMatches);
                    resp.sendRedirect(req.getContextPath() + "/search?content=recipes");
                    return;
                }
                else{
                    List<User> userList = profileService.findAllUsers();
                    List<User> usMatches = new ArrayList<>();
                    for(User u : userList){
                        if(sm.matchString(u.getUsername(), query)){
                            usMatches.add(u);
                        }
                    }
                    req.getSession().setAttribute("userList", usMatches);
                    resp.sendRedirect(req.getContextPath() + "/search?content=users");
                    return;
                }
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/search.jsp").forward(req, resp);
    }
}
