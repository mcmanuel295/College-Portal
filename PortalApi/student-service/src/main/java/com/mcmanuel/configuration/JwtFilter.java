package com.mcmanuel.configuration;

import com.mcmanuel.domain.student.JwtService;
import com.mcmanuel.domain.student.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header= request.getHeader("Authorization");
        String userneame = null;
        String token =null;

        if ( header!= null && !header.startsWith("Bearer ")) {

            token = header.substring(7);

            userneame = jwtService.extractUsername(token);

            if (SecurityContextHolder.getContext().getAuthentication() ==null && userneame != null) {

               UserDetails userDetails =userDetailsService.loadUserByUsername(userneame);

               if(jwtService.verify(userDetails,token)){
                   UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                   authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                   SecurityContextHolder.getContext().setAuthentication(authToken);
               }

            }
        }
        else{
                filterChain.doFilter(request, response);
                return;
        }
        filterChain.doFilter(request,response);
    }
}
