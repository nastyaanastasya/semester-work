package ru.kpfu.services;

import ru.kpfu.models.Recipe;
import ru.kpfu.models.User;
import ru.kpfu.repositories.RecipeRepository;
import ru.kpfu.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ProfileService {
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;
    private MediaService mediaService;

    public ProfileService(MediaService mediaService, RecipeRepository recipeRepository, UserRepository userRepository) {
        this.mediaService = mediaService;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public List<Recipe> getLikedRecipes(long userId){
        List<Recipe> recipes = new ArrayList<>();
        List<Long> ids = recipeRepository.findUserLikedRecipes(userId);
        for(long id: ids){
            recipes.add(recipeRepository.findById(id));
        }
        return recipes;
    }

    public List<Recipe> getSavedRecipes(long userId){
        List<Recipe> recipes = new ArrayList<>();
        List<Long> ids = recipeRepository.findUserSavedRecipes(userId);
        for(long id: ids){
            recipes.add(recipeRepository.findById(id));
        }
        return recipes;
    }

    public List<Recipe> getUserRecipes(long userId){
        return recipeRepository.findByUserId(userId);
    }

    public List<User> getFollowingUsers(long userId){
        List<User> users = new ArrayList<>();
        List<Long> ids = userRepository.findUserFollowing(userId);
        for(long id: ids){
            users.add(userRepository.findById(id));
        }
        return users;
    }

    public List<User> getSubscribers(long userId){
        List<User> users = new ArrayList<>();
        List<Long> ids = userRepository.findUserSubscribers(userId);
        for(long id: ids){
            users.add(userRepository.findById(id));
        }
        return users;
    }

    public void follow(long userId, long followerId){
        userRepository.addFollower(userId, followerId);
    }

    public void unfollow(long userId, long followerId){
        userRepository.unfollow(userId, followerId);
    }

    public boolean isFollow(long userId, long followerId){
        return userRepository.findIfFollow(userId, followerId);
    }

    public String setProfileImage(User user){
        return mediaService.getUserMedia(user);
    }
}
