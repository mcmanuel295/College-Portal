package com.mcmanuel.domain.staff;

import com.mcmanuel.domain.token.TokenService;
import com.mcmanuel.email.EmailService;
import com.mcmanuel.enums.Role;
import com.mcmanuel.pojo.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

@RequiredArgsConstructor
public abstract class  StaffServiceImpl<T> implements StaffService<T>{
    private final JwtService jwtService;
    private final AuthenticationManager manager;
    private final EmailService emailService;
    private final TokenService tokenService;



    @Override
    public String login(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getStaffId(),loginRequest.getPassword());
        Authentication authentication =manager.authenticate(token);
        if(authentication.isAuthenticated()) {

            List<String> roles = ((Staff)findStaffByStaffNumber(loginRequest.getStaffId())).getRoles().stream()
                    .map(Enum::name).toList();

            String jwtToken = jwtService.generateToken(loginRequest.getStaffId(),roles);
            System.out.println("JWT Token :"+jwtToken);
            return jwtToken;
        }
        else throw new RuntimeException("Invalid Login request");
    }

    public void sendUserEmail(String email) throws MessagingException, jakarta.mail.MessagingException{
        emailService.sendEmail(email,tokenService.generateToken(email).getToken());
    }

    public boolean verifyOtp(String email,String otp){
        return tokenService.verifyOtp(email,otp);
    }
}
