package com.example.projektarbete_java_web_services.security;


import com.example.projektarbete_java_web_services.services.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils; //The code starts by importing the classes that are needed.
    //The class JwtUtils is used to parse and create a JSON Web Token (JWT).
    private UserDetailsService userDetailsService;
    //The UserDetailsService is used to load user details from the database.

    public JwtRequestFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) { //
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }


//    The doFilterInternal method starts by checking if there is an Authorization header in the request.
//    If so, it extracts out what was sent as part of that header and parses it into a Claims object using jwtUtils .
//    It then uses this Claims object to find out which username was sent with it and loads up that user's details from the UserDetailsService .
//    The code is a filter that will be applied to the request.
//    The filter will parse the Authorization header and extract the token from it.
//    The filter then extracts the username from the Claims body, which was parsed by JwtUtils class.


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            String token = authHeader.substring(7);
            Claims body = jwtUtils.parseBody(token);
            String username = body.getSubject();
            String id = body.getId();


            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, id, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);

    }
}
