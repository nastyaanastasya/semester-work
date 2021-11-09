package ru.kpfu.repositories;

import ru.kpfu.models.Recipe;
import ru.kpfu.models.User;
import ru.kpfu.utils.RowMapper;

import java.util.List;

public class RecipeRepository {
    private JdbcTemplate<Recipe> source;
    private RowMapper<Recipe> builder;

    private final String SQL_FIND_BY_ID =
            "select * from public.recipe where id = ?";

    private final String SQL_FIND_BY_USER_ID =
            "select * from public.recipe where user_id = ? order by date desc";

    private final String SQL_FIND_LIKED_RECIPE_USERS =
            "select user_id from public.liked_recipe where recipe_id = ?";

    private final String SQL_FIND_SAVED_RECIPE_USERS =
            "select user_id from public.saved_recipe where recipe_id = ?";

    private final String SQL_ADD_SAVED_RECIPE_USER =
            "insert into public.saved_recipe (recipe_id, user_id) values (?, ?)";

    private final String SQL_ADD_LIKED_RECIPE_USER =
            "insert into public.liked_recipe (recipe_id, user_id) values (?, ?)";

    private final String SQL_SAVE =
            "insert into public.recipe (title, time_of_cooking, description, user_id) values (?, ?, ?, ?) returning id";

    private final String SQL_UPDATE =
            "update public.recipe set (title, time_of_cooking, description) values (?, ?, ?)";

    private final String SQL_DELETE =
            "delete from public.recipe where id = ?";

    private final String SQL_FIND_SAVED_RECIPES_BY_USER_ID =
            "select recipe_id from public.liked_recipe where user_id = ?";

    private final String SQL_FIND_LIKED_RECIPES_BY_USER_ID =
            "select recipe_id from public.liked_recipe where user_id = ?";

    private final String SQL_FIND_IF_SAVED =
            "select id from public.liked_recipe where recipe_id = ? and user_id = ?";

    private final String SQL_FIND_IF_LIKED =
            "select id from public.saved_recipe where recipe_id = ? and user_id = ?";

    public RecipeRepository(String driver, String url, String user, String pass) {
        builder = getRecipeBuilder();
        this.source = new JdbcTemplate<>(driver, url, user, pass);
    }

    public Recipe findById(long id) {
        List<Recipe> list = source.query(SQL_FIND_BY_ID, builder, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<Recipe> findByUserId(long id) {
        List<Recipe> list = source.query(SQL_FIND_BY_USER_ID, builder, id);
        return list.size() > 0 ? list : null;
    }

    public List<Long> findLikedRecipeUsers(long id) {
        List<Long> list = source.query(SQL_FIND_LIKED_RECIPE_USERS, row -> row.getLong("user_id"), id);
        return list.size() > 0 ? list : null;
    }

    public List<Long> findSavedRecipeUsers(long id) {
        List<Long> list = source.query(SQL_FIND_SAVED_RECIPE_USERS, row -> row.getLong("user_id"), id);
        return list.size() > 0 ? list : null;
    }

    public void addSavedRecipe(long recipeId, long userId) {
        source.update(SQL_ADD_SAVED_RECIPE_USER, recipeId, userId);
    }

    public void addLikedRecipe(long recipeId, long userId) {
        source.update(SQL_ADD_LIKED_RECIPE_USER, recipeId, userId);
    }

    public Long save(Recipe recipe, long userId) {
        return source.query(
                SQL_SAVE, row -> row.getLong("id"), recipe.getTitle(), recipe.getTimeOfCooking(),
                recipe.getDescription(), userId
        ).get(0);
    }

    public void update(Recipe recipe) {
        source.update(SQL_UPDATE, recipe.getTitle(), recipe.getTimeOfCooking(), recipe.getDescription());
    }

    public void delete(long id) {
        source.update(SQL_DELETE, id);
    }

    public List<Long> findUserSavedRecipes(long id) {
        List<Long> list = source.query(SQL_FIND_SAVED_RECIPES_BY_USER_ID, row -> row.getLong("recipe_id"), id);
        return list.size() > 0 ? list : null;
    }

    public List<Long> findUserLikedRecipes(long id) {
        List<Long> list = source.query(SQL_FIND_LIKED_RECIPES_BY_USER_ID, row -> row.getLong("recipe_id"), id);
        return list.size() > 0 ? list : null;
    }

    public boolean findIfSaved(long recipeId, long userId) {
        List<Long> list = source.query(SQL_FIND_IF_SAVED, row -> row.getLong("id"), recipeId, userId);
        return list.size() > 0;
    }

    public boolean findIfLiked(long recipeId, long userId) {
        List<Long> list = source.query(SQL_FIND_IF_LIKED, row -> row.getLong("id"), recipeId, userId);
        return list.size() > 0;
    }

    private RowMapper<Recipe> getRecipeBuilder() {
        return row -> Recipe.builder()
                .id(row.getLong("id"))
                .title(row.getString("title"))
                .timeOfCooking(row.getInt("time_of_cooking"))
                .description(row.getString("description"))
                .date(row.getDate("date"))
                .user(User.builder().id(row.getLong("user_id")).build())
                .build();
    }
}
