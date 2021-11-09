package ru.kpfu.services;

import ru.kpfu.models.Recipe;
import ru.kpfu.models.User;
import ru.kpfu.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileService {
    private String defaultImg = "/res/user_default.png";
    private RecipeService recipeService;
    private UserRepository userRepository;
    private MediaService mediaService;
    private Map<String, String> titles;

    public ProfileService(MediaService mediaService, RecipeService recipeService, UserRepository userRepository) {
        this.mediaService = mediaService;
        this.recipeService = recipeService;
        this.userRepository = userRepository;
        titles = getTitles();
    }

    public User findById(long id) {
        User user = userRepository.findById(id);
        if (user != null)
            user = setUserContent(user);
        return user;
    }

    public User setUserContent(User user) {
        String img = mediaService.getUserMedia(user.getId());
        if (img == null)
            img = defaultImg;
        user.setImage(img);
        user.setRecipes(getUserRecipes(user.getId()));
        user.setRating(getUserRating(user.getId()));
        return user;
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
            case "liked":
                return getLikedRecipes(id);
            case "saved":
                return getSavedRecipes(id);
            default:
                return getUserRecipes(id);
        }
    }

    public float getUserRating(long id) {
        List<Recipe> list = recipeService.findByUserId(id);
        float rate = 0;
        if (list != null) {
            for (Recipe recipe : list) {
                rate += recipe.getRating();
            }
            rate = rate / list.size();
        }
        return rate;
    }

    public String getContentTitle(String val) {
        return titles.getOrDefault(val, "Recipes");
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
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

    private List<Recipe> getLikedRecipes(long userId) {
        List<Recipe> recipes = new ArrayList<>();
        List<Long> ids = recipeService.getUserLikedRecipes(userId);
        if (ids != null) {
            for (Long id : ids) {
                recipes.add(recipeService.findById(id));
            }
        }
        return recipes;
    }

    public List<Recipe> getSavedRecipes(long userId) {
        List<Recipe> recipes = new ArrayList<>();
        List<Long> ids = recipeService.getUserSavedRecipes(userId);
        if (ids != null) {
            for (long id : ids) {
                recipes.add(recipeService.findById(id));
            }
        }
        return recipes;
    }

    public List<Recipe> getUserRecipes(long userId) {
        return recipeService.findByUserId(userId);
    }

    public List<User> getFollowingUsers(long userId) {
        List<User> users = new ArrayList<>();
        List<Long> ids = userRepository.findUserFollowing(userId);
        if (ids != null) {
            for (long id : ids) {
                users.add(findById(id));
            }
        }
        return users;
    }

    public List<User> getSubscribers(long userId) {
        List<User> users = new ArrayList<>();
        List<Long> ids = userRepository.findUserSubscribers(userId);
        if (ids != null) {
            for (long id : ids) {
                users.add(findById(id));
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
