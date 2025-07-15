//Filter for Each Request

package com.notes.notesapp.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get token from header
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        // 2. Check if header starts with Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Remove "Bearer "
            username = jwtUtil.getUsernameFromToken(token);
        }

        // 3. If username is found and not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user from DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate token
            if (jwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // Set user as authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 4. Continue the request
        filterChain.doFilter(request, response);
    }
}
