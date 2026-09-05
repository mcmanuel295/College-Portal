package com.mcmanuel.domain.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepo;

    public Token generateToken(String email){
        Token token = Token.builder()
                .token(generateOtp())
                .email(email)
                .build();


        token.setCreatedAt(LocalDateTime.now());
        return tokenRepo.save(token);
    }

    private String generateOtp(){
        String chars = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        StringBuilder builder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            builder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return builder.toString();
    }

    public boolean verifyOtp(String email, String otp) {
        Optional<Token> token =tokenRepo.findByEmailAndToken(email,otp);
        if(token.isPresent() && !token.get().isExpired()){
            tokenRepo.delete(token.get());
            return true;
        }{
            return false;
        }
    }


    @Scheduled(fixedRate = 600000)
    private void deleteExpiredTokens(){
        if(!tokenRepo.findAll().isEmpty()){
            for(Token token: tokenRepo.findAll()){
                if(token.isExpired()){
                    tokenRepo.delete(token);
                }
            }
            log.info("Expired Tokens Deleted");
        }
    }
}
