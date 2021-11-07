package ru.kpfu.listeners;

import ru.kpfu.exceptions.UnknownAlgorithmException;
import ru.kpfu.repositories.*;
import ru.kpfu.services.*;
import ru.kpfu.utils.PasswordEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Properties properties = getProperties(context);
        PasswordEncoder passwordEncoder = getPasswordEncoder();

        String dbDriver = properties.getProperty("driver");
        String dbUrl = properties.getProperty("url");
        String dbUser = properties.getProperty("user");
        String dbPass = properties.getProperty("password");
        String repository = properties.getProperty("repository");

        CommentRepository commentRepository = new CommentRepository(dbDriver, dbUrl, dbUser, dbPass);
        RecipeRepository recipeRepository = new RecipeRepository(dbDriver, dbUrl, dbUser, dbPass);
        UserRepository userRepository = new UserRepository(dbDriver, dbUrl, dbUser, dbPass);
        MediaRepository mediaRepository = new MediaRepository(dbDriver, dbUrl, dbUser, dbPass);
        IngredientsRepository ingredientsRepository = new IngredientsRepository(dbDriver, dbUrl, dbUser, dbPass);

        SecurityService securityService = new SecurityService(userRepository, passwordEncoder);
        MediaService mediaService = new MediaService(mediaRepository, Paths.get(repository));
        RecipeService recipeService = new RecipeService(ingredientsRepository, commentRepository, recipeRepository);
        ProfileService profileService = new ProfileService(mediaService, recipeRepository, userRepository);

        context.setAttribute("securityService", securityService);
        context.setAttribute("mediaService", mediaService);
        context.setAttribute("profileService", profileService);
    }

    private PasswordEncoder getPasswordEncoder(){
        return str -> {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(hash);
            } catch (NoSuchAlgorithmException e) {
                throw new UnknownAlgorithmException("Unknown algorithm");
            }
        };
    }

    private Properties getProperties(ServletContext context){
        Properties properties = new Properties();
        try{
            properties.load(context.getResourceAsStream("WEB-INF/properties/database.properties"));
        } catch (IOException e) {
            System.out.println("Problems with reading data " + e.getMessage());
        }
        return properties;
    }
}
