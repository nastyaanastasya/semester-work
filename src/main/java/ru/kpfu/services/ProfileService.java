package ru.kpfu.services;

import ru.kpfu.models.Recipe;
import ru.kpfu.models.User;
import ru.kpfu.repositories.RecipeRepository;
import ru.kpfu.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileService {
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;
    private MediaService mediaService;
    private Map<String, String> titles;

    public ProfileService(MediaService mediaService, RecipeRepository recipeRepository, UserRepository userRepository) {
        this.mediaService = mediaService;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        titles = getTitles();
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }



    public List<User> getUsersContent(String value, long id) {
        switch (value) {
            case "fol":
                return getFollowingUsers(id);
            case "sub":
                return getSubscribers(id);
            default:
                return null;
        }
    }

    public List<Recipe> getRecipesContent(String value, long id) {
        switch (value) {
            case "res":
                return getUserRecipes(id);
            case "liked":
                return getLikedRecipes(id);
            case "saved":
                return getSavedRecipes(id);
            default:
                return null;
        }
    }

    public String getTitle(String val) {
        return titles.getOrDefault(val, "Recipes");
    }

    public Integer getUserRecipeNum(long id) {
        List<Recipe> list = recipeRepository.findByUserId(id);
        return list != null ? list.size() : 0;
    }

    public void follow(long userId, long followerId) {
        userRepository.addFollower(userId, followerId);
    }

    public void unfollow(long userId, long followerId) {
        userRepository.unfollow(userId, followerId);
    }

    public boolean isFollow(long userId, long followerId) {
        return userRepository.findIfFollow(userId, followerId);
    }

    public String setProfileImage(User user) {
        return mediaService.getUserMedia(user);
    }

    private List<Recipe> getLikedRecipes(long userId) {
        List<Recipe> recipes = new ArrayList<>();
        List<Long> ids = recipeRepository.findUserLikedRecipes(userId);
        if (ids != null) {
            for (Long id : ids) {
                recipes.add(recipeRepository.findById(id));
            }
        }
        return recipes;
    }

    private List<Recipe> getSavedRecipes(long userId) {
        List<Recipe> recipes = new ArrayList<>();
        List<Long> ids = recipeRepository.findUserSavedRecipes(userId);
        if (ids != null) {
            for (long id : ids) {
                recipes.add(recipeRepository.findById(id));
            }
        }
        return recipes;
    }

    private List<Recipe> getUserRecipes(long userId) {
        return recipeRepository.findByUserId(userId);
    }

    private List<User> getFollowingUsers(long userId) {
        List<User> users = new ArrayList<>();
        List<Long> ids = userRepository.findUserFollowing(userId);
        if (ids != null) {
            for (long id : ids) {
                users.add(userRepository.findById(id));
            }
        }
        return users;
    }

    private List<User> getSubscribers(long userId) {
        List<User> users = new ArrayList<>();
        List<Long> ids = userRepository.findUserSubscribers(userId);
        if (ids != null) {
            for (long id : ids) {
                users.add(userRepository.findById(id));
            }
        }
        return users;
    }

    private Map<String, String> getTitles() {
        Map<String, String> map = new HashMap<>();
        map.put("fol", "Following");
        map.put("sub", "Subscribers");
        map.put("liked", "Liked recipes");
        map.put("saved", "Saved recipes");
        map.put("res", "Recipes");
        return map;
    }
}
