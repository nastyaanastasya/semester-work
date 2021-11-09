package ru.kpfu.services;

import ru.kpfu.exceptions.*;
import ru.kpfu.models.User;
import ru.kpfu.repositories.UserRepository;
import ru.kpfu.utils.PasswordEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityService {
    public final String AUTH_COOKIE = "user_uuid";
    public final String SESSION_USER_ATTR = "user";
    public final int MAX_AGE_COOKIE = 60 * 60 * 24;
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    public SecurityService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void register(String username, String email, String password, String repPass, HttpServletRequest req, HttpServletResponse resp) {
        validateInputData(username, email, password, repPass);
        userRepository.save(createNewUser(username, email, password));
        User user = userRepository.findByEmail(email);
        addUserToSession(req.getSession(), user);
        UUID uuid = updateUserUUID(user);
        addCookie(resp, AUTH_COOKIE, uuid.toString(), MAX_AGE_COOKIE);
    }

    public void auth(String email, String password, HttpServletRequest req, HttpServletResponse resp) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UnknownEmailException("Unknown email");
        if (!encoder.encode(password).equals(user.getPasswordHash()))
            throw new WrongPasswordException("Wrong password.");
        addUserToSession(req.getSession(), user);
        UUID uuid = updateUserUUID(user);
        addCookie(resp, AUTH_COOKIE, uuid.toString(), MAX_AGE_COOKIE);
    }

    public boolean isAuth(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute(SESSION_USER_ATTR) != null) {
            return true;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTH_COOKIE)) {
                    User user = userRepository.findByUUID(UUID.fromString(cookie.getValue()));
                    if (user != null) {
                        addUserToSession(request.getSession(), user);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public User getUser(HttpServletRequest req) {
        if (isAuth(req)) {
            return (User) req.getSession().getAttribute(SESSION_USER_ATTR);
        }
        return null;
    }

    public void changeUserData(HttpSession session, User user, String newUsername, String curPass, String newPass, String repNewPass){
        if((newUsername != null) && !newUsername.isEmpty() && !newUsername.equals(user.getUsername())){
            checkUsername(newUsername);
            checkIfUsernameIsAlreadyExists(newUsername);
            user.setUsername(newUsername);
        }
        if((curPass != null) && (newPass != null) && (repNewPass != null) && !curPass.isEmpty() && !newPass.isEmpty() && !repNewPass.isEmpty()) {
            if (!encoder.encode(curPass).equals(user.getPasswordHash()))
                throw new WrongPasswordException("Wrong password");
            checkPassword(newPass);
            checkRepeatedPassword(newPass, repNewPass);
            user.setPasswordHash(encoder.encode(newPass));
        }
        userRepository.update(user);
        addUserToSession(session, user);
    }

    public void updateUserInSession(HttpServletRequest request, User user){
        addUserToSession(request.getSession(), user);
    }

    public void addCookie(HttpServletResponse resp, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        resp.addCookie(cookie);
    }

    public String removeCookie(HttpServletRequest request, HttpServletResponse response, String name){
        Cookie[] cookies = request.getCookies();
        String value = null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(name)){
                value = cookie.getValue();
                cookie.setMaxAge(1);
                response.addCookie(cookie);
            }
        }
        return value;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response){
        String value = removeCookie(request, response, AUTH_COOKIE);
        if(value != null){
            HttpSession session = request.getSession();
            userRepository.removeUUID(((User)session.getAttribute(SESSION_USER_ATTR)).getId());
            session.removeAttribute(SESSION_USER_ATTR);
        }
    }

    public void fullLogout(HttpServletRequest request, HttpServletResponse response){
        User user = (User)request.getSession().getAttribute(SESSION_USER_ATTR);
        logout(request, response);
        userRepository.delete(user);
    }

    private void addUserToSession(HttpSession session, User user){
        session.setAttribute(SESSION_USER_ATTR, user);
        session.setMaxInactiveInterval(-1);
    }

    private UUID updateUserUUID(User user){
        UUID uuid = UUID.randomUUID();
        userRepository.updateUUID(uuid, user.getId());
        return uuid;
    }

    private void validateInputData(String username, String email, String pass, String repPass) {
        checkUsername(username);
        checkIfUsernameIsAlreadyExists(username);
        checkEmail(email);
        checkIfEmailIsAlreadyRegistered(email);
        checkPassword(pass);
        checkRepeatedPassword(pass, repPass);
    }

    private void checkUsername(String username) {
        if (username.startsWith(" ") || username.endsWith(" ") || username.trim().length() == 0)
            throw new InvalidUsernameException("Invalid username.");
    }

    private void checkIfUsernameIsAlreadyExists(String username){
        if(userRepository.findByUsername(username) != null)
            throw new OccupiedUsernameException("This name is already in use. Please, choose another username.");
    }

    private void checkIfEmailIsAlreadyRegistered(String email) {
        if (userRepository.findByEmail(email) != null)
            throw new OccupiedEmailException("This email has already been register");
    }

    private void checkRepeatedPassword(String pass, String repPass){
        if(!repPass.equals(pass))
            throw new DifferentPasswordsException("Entered passwords are different");
    }

    private void checkEmail(String email) {
        String emailRegex = "^(?:(?:(?:[^.()<>;:@\\\\]+)(?:.)*(?:[^.()<>;:@\\\\]+))+){1,64}@" +
                "(?:(?:(?:[0-9a-z]+)(?:[a-z0-9-])*(?:[0-9a-z]+))+\\.(?:[a-z0-9]+)){1,255}$";
        if (!isMatch(emailRegex, email))
            throw new InvalidEmailException("Invalid email.");
    }

    private void checkPassword(String pass) {
        String passRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d@#$%]).{8,20}$";
        if (!isMatch(passRegex, pass))
            throw new InvalidPasswordException("Invalid password.");
    }

    private boolean isMatch(String regex, String str) {
        Matcher matcher = Pattern.compile(regex).matcher(str);
        return matcher.find();
    }

    private User createNewUser(String username, String email, String password) {
        return User.builder()
                .username(username)
                .email(email)
                .passwordHash(encoder.encode(password))
                .build();
    }
}
