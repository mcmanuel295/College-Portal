package com.mcmanuel.domain.staff;

import com.mcmanuel.domain.token.TokenService;
import com.mcmanuel.email.EmailService;
import com.mcmanuel.pojo.LoginRequest;
import com.mcmanuel.pojo.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class StaffServiceImpl<T,R extends RegisterRequest> implements StaffService<T,R>{
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
            System.out.println("The login staff role(s) "+roles);
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
