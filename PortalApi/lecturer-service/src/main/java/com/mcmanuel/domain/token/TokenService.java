package com.mcmanuel.domain.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepo;

    public Token generateToken(String email){
        return tokenRepo.save(
        Token.builder()
                .token(generateOtp())
                .email(email)
                .build()
        );
    }

    private String generateOtp(){
        String chars = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        StringBuilder builder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            builder.append(chars.charAt(random.nextInt()));
        }
        return builder.toString();
    }

    public boolean verifyOtp(String email, String otp) {
        if(tokenRepo.findByEmailAndToken(email,otp).isPresent()){
            return true;
        }{
            return false;
        }
    }
}
