package ru.kpfu.filters;

import ru.kpfu.services.SecurityService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("*")
public class AuthFilter extends HttpFilter {
    private String[] protectedPaths;
    private SecurityService securityService;

    @Override
    public void init() throws ServletException {
        protectedPaths = new String[]{"/profile"};
        securityService = (SecurityService) getServletContext().getAttribute("securityService");
    }

    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {

        if(securityService.isAuth(req)){
            req.setAttribute("isAuth", true);
            chain.doFilter(req, res);
        }
        else{
            req.setAttribute("isAuth", false);
            boolean isProtected = false;
            for(String path : protectedPaths){
                if(req.getRequestURI().startsWith(req.getContextPath() + path)){
                    isProtected = true;
                    break;
                }
            }
            if(!isProtected){
                chain.doFilter(req, res);
                return;
            }
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
