package ru.kpfu.services;

import ru.kpfu.repositories.CommentRepository;
import ru.kpfu.repositories.IngredientsRepository;
import ru.kpfu.repositories.RecipeRepository;

public class RecipeService {
    private RecipeRepository recipeRepository;
    private CommentRepository commentRepository;
    private IngredientsRepository ingredientsRepository;

    public RecipeService(IngredientsRepository ingredientsRepository, CommentRepository commentRepository, RecipeRepository recipeRepository) {
        this.ingredientsRepository = ingredientsRepository;
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
    }

}
