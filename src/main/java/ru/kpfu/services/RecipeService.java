package ru.kpfu.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.kpfu.models.Comment;
import ru.kpfu.models.Ingredient;
import ru.kpfu.models.Recipe;
import ru.kpfu.repositories.CommentRepository;
import ru.kpfu.repositories.IngredientsRepository;
import ru.kpfu.repositories.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class RecipeService {
    private RecipeRepository recipeRepository;
    private CommentRepository commentRepository;
    private IngredientsRepository ingredientsRepository;

    public RecipeService(IngredientsRepository ingredientsRepository, CommentRepository commentRepository, RecipeRepository recipeRepository) {
        this.ingredientsRepository = ingredientsRepository;
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
    }

    public long save(Recipe recipe, long userId){
        return recipeRepository.save(recipe, userId);
    }

    public List<Comment> getComments(long recipeId){
        return commentRepository.findByRecipeId(recipeId);
    }

    public List<Ingredient> getIngredients(long id){
        return ingredientsRepository.findByRecipeId(id);
    }

    public void deleteIngredients(long id){
        ingredientsRepository.delete(id);
    }

    public void saveIngredients(List<Ingredient> list, long recipeId){
        for(Ingredient i : list){
            ingredientsRepository.save(i, recipeId);
        }
    }

    public Recipe findById(long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> findByUserId(long id) {
        return recipeRepository.findByUserId(id);
    }

    public long getRecipeLikeNumber(long id) {
        List<Long> list = recipeRepository.findLikedRecipeUsers(id);
        return list != null ? list.size() : 0;
    }

    public long getRecipeSaveNumber(long id) {
        List<Long> list = recipeRepository.findSavedRecipeUsers(id);
        return list != null ? list.size() : 0;
    }

    public void saveRecipe(long recipeId, long userId){
        recipeRepository.addSavedRecipe(recipeId, userId);
    }

    public void likeRecipe(long recipeId, long userId){
        recipeRepository.addLikedRecipe(recipeId, userId);
    }

    public void update(Recipe recipe){
        recipeRepository.update(recipe);
    }

    public void delete(long id) {
        recipeRepository.delete(id);
    }

    public boolean isSaved(long recipeId, long userId){
        return recipeRepository.findIfSaved(recipeId, userId);
    }

    public boolean isLiked(long recipeId, long userId){
        return recipeRepository.findIfLiked(recipeId, userId);
    }

    public Recipe createRecipe(String title, String hours, String minutes, String desc) {
        int time = 0;
        if(hours!=null && !hours.isEmpty()) time += Integer.parseInt(hours)*60;
        if(minutes!=null && !minutes.isEmpty()) time += Integer.parseInt(minutes);
        return Recipe.builder()
                .title(title)
                .timeOfCooking(time)
                .description(desc)
                .build();
    }

    public List<Ingredient> createIngredients(String ingr) {
        float amount;
        String name;
        String unit;
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(ingr, JsonArray.class);
        List<Ingredient> ingredients = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            JsonObject object = element.getAsJsonObject();
            name = object.get("name").getAsString();
            amount = object.get("amount").getAsFloat();
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
