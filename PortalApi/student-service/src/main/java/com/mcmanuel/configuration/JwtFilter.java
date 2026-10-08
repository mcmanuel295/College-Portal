package com.mcmanuel.configuration;

import com.mcmanuel.domain.student.JwtService;
import com.mcmanuel.domain.student.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header= request.getHeader("Authorization");
        log.info("JWT filter: {} header present={}",request.getRequestURL(),header!=null);
        String username ;
        String token ;

        if ( header== null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = header.substring(7);
        username = jwtService.extractUsername(token);
        log.info("JWT subject={}",username);

        if (SecurityContextHolder.getContext().getAuthentication() ==null && username != null) {

           UserDetails userDetails =userDetailsService.loadUserByUsername(username);

           if(jwtService.verify(userDetails,token)){
               log.info("verify={} authorities={}",jwtService.verify(userDetails,token),userDetails.getAuthorities());
               UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authToken);
           }

        }
        filterChain.doFilter(request,response);
    }
}
