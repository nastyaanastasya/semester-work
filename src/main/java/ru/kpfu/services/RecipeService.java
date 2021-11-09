package ru.kpfu.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.kpfu.models.Comment;
import ru.kpfu.models.Ingredient;
import ru.kpfu.models.Recipe;
import ru.kpfu.models.User;
import ru.kpfu.repositories.CommentRepository;
import ru.kpfu.repositories.IngredientsRepository;
import ru.kpfu.repositories.RecipeRepository;
import ru.kpfu.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class RecipeService {
    private MediaService mediaService;
    private RecipeRepository recipeRepository;
    private CommentRepository commentRepository;
    private IngredientsRepository ingredientsRepository;
    private UserRepository userRepository;

    public RecipeService(MediaService mediaService, IngredientsRepository ingredientsRepository, CommentRepository commentRepository, RecipeRepository recipeRepository, UserRepository userRepository) {
        this.mediaService = mediaService;
        this.ingredientsRepository = ingredientsRepository;
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public long save(String title, String hours, String minutes, String desc, long userId) {
        Recipe recipe = createRecipe(title, hours, minutes, desc);
        return recipeRepository.save(recipe, userId);
    }

    public void saveRecipe(long recipeId, long userId) {
        recipeRepository.addSavedRecipe(recipeId, userId);
    }

    public void likeRecipe(long recipeId, long userId) {
        recipeRepository.addLikedRecipe(recipeId, userId);
    }

    public boolean isSaved(long recipeId, long userId) {
        return recipeRepository.findIfSaved(recipeId, userId);
    }

    public boolean isLiked(long recipeId, long userId) {
        return recipeRepository.findIfLiked(recipeId, userId);
    }

    public List<Comment> getComments(long recipeId) {
        List<Comment> list = commentRepository.findByRecipeId(recipeId);
        if (list != null) {
            for (Comment c : list) {
                long userId = commentRepository.findUserByCommentId(c.getId());
                User user = userRepository.findById(userId);
                user.setImage(mediaService.getUserMedia(userId));
                c.setUser(user);
            }
        }
        return list;
    }

    public void update(Recipe recipe) {
        recipeRepository.update(recipe);
    }

    public void addComment(String rating, String review, long userId, long recipeId) {
        commentRepository.save(
                Comment.builder()
                        .rating(Integer.parseInt(rating))
                        .review(review.replace("\n", "<br>"))
                        .build(),
                userId, recipeId);
    }

    public void delete(long id) {
        recipeRepository.delete(id);
    }

    public List<Ingredient> getIngredients(long id) {
        return ingredientsRepository.findByRecipeId(id);
    }

    public void deleteIngredients(long id) {
        ingredientsRepository.delete(id);
    }

    public void saveIngredients(String ingr, long recipeId) {
        List<Ingredient> list = createIngredients(ingr);
        for (Ingredient i : list) {
            ingredientsRepository.save(i, recipeId);
        }
    }

    public List<Long> getUserSavedRecipes(long id) {
        return recipeRepository.findUserSavedRecipes(id);
    }

    public List<Long> getUserLikedRecipes(long id) {
        return recipeRepository.findUserLikedRecipes(id);
    }

    public Recipe findById(long id) {
        Recipe recipe = recipeRepository.findById(id);
        if (recipe != null) {
            setRecipeAttrs(recipe);
        }
        return recipe;
    }

    public List<Recipe> findByUserId(long id) {
        List<Recipe> recipes = recipeRepository.findByUserId(id);
        if (recipes != null) {
            for (Recipe r : recipes) {
                setRecipeAttrs(r);
            }
        }
        return recipes;
    }

    public List<Recipe> getAllRecipes(){
        return recipeRepository.findAll();
    }

    public String getTimeString(int timeOfCooking) {
        String minutes = "";
        String hours = "";
        if (timeOfCooking % 60 != 0)
            minutes = String.valueOf(timeOfCooking % 60) + "m";
        if (timeOfCooking >= 60)
            hours = String.valueOf(timeOfCooking / 60) + "h ";
        return hours + minutes;
    }

    private Recipe setRecipeAttrs(Recipe recipe) {
        recipe.setComments(getComments(recipe.getId()));
        recipe.setIngredients(getIngredients(recipe.getId()));
        recipe.setLikesNumber(getRecipeLikeNumber(recipe.getId()));
        recipe.setSaveAmount(getRecipeSaveNumber(recipe.getId()));
        recipe.setImages(mediaService.getRecipeMedia(recipe.getId()));
        recipe.setRating(commentRepository.findRecipeRating(recipe.getId()));
        return recipe;
    }

    private long getRecipeLikeNumber(long id) {
        List<Long> list = recipeRepository.findLikedRecipeUsers(id);
        return list != null ? list.size() : 0;
    }

    private long getRecipeSaveNumber(long id) {
        List<Long> list = recipeRepository.findSavedRecipeUsers(id);
        return list != null ? list.size() : 0;
    }

    private Recipe createRecipe(String title, String hours, String minutes, String desc) {
        int time = 0;
        if (hours != null && !hours.isEmpty()) time += Integer.parseInt(hours) * 60;
        if (minutes != null && !minutes.isEmpty()) time += Integer.parseInt(minutes);
        return Recipe.builder()
                .title(title)
                .timeOfCooking(time)
                .description(desc.replace("\n", "<br>"))
                .build();
    }

    private List<Ingredient> createIngredients(String ingr) {
        int amount;
        String name;
        String unit;
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(ingr, JsonArray.class);
        List<Ingredient> ingredients = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            JsonObject object = element.getAsJsonObject();
            name = object.get("name").getAsString();
            amount = object.get("amount").getAsInt();
            unit = object.get("unit").getAsString();

            ingredients.add(Ingredient.builder()
                    .name(name)
                    .amount(amount)
                    .unit(unit)
                    .build());
        }
        return ingredients;
    }
}
