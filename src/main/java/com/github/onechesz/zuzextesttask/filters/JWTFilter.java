package com.github.onechesz.zuzextesttask.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.onechesz.zuzextesttask.services.UserDetailsService;
import com.github.onechesz.zuzextesttask.utils.JWTUtil;
import com.github.onechesz.zuzextesttask.utils.exceptions.UserNotAuthenticatedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JWTFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && !authorizationHeader.isBlank() && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);

            if (jwt.isBlank())
                request.setAttribute("exception", new UserNotAuthenticatedException("пустой JWT"));
            else
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.validateTokenAndRetrieveClaim(jwt));
                    SecurityContext securityContext = SecurityContextHolder.getContext();

                    if (securityContext.getAuthentication() == null)
                        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
                } catch (UsernameNotFoundException usernameNotFoundException) {
                    request.setAttribute("exception", new UserNotAuthenticatedException("пользователь с таким именем не найден"));
                } catch (JWTVerificationException jwtVerificationException) {
                    request.setAttribute("exception", new UserNotAuthenticatedException("неверный JWT"));
                }
        } else
            request.setAttribute("exception", new UserNotAuthenticatedException("отсутствует аутентификация"));


        filterChain.doFilter(request, response);
    }
}
